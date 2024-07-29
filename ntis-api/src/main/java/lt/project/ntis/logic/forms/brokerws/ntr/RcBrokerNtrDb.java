package lt.project.ntis.logic.forms.brokerws.ntr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprProcessRequestsDBService;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.project.ntis.brokerws.ntr762.Dataset;
import lt.project.ntis.brokerws.ntr762.ItemType;
import lt.project.ntis.brokerws.ntr762.MetadataType;
import lt.project.ntis.brokerws.ntr762.RowType;
import lt.project.ntis.dao.NtisBuildingNtrsDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.brokerws.ntr.RcBrokerNtr.Problem;
import lt.project.ntis.service.NtisBuildingNtrsDBService;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė skirta masiniam NTR pasikeitimų atsisiuntimui ir pritaikymui, 
 * naudojama darbui su DB.
 */
@Component
public class RcBrokerNtrDb {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerNtrDb.class);

    private static final char UNICODE_MINUS_SIGN = '−';

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dBStatementManager;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprJobDefinitionsDBService sprJobDefinitionDBService;

    @Autowired
    SprProcessRequestsDBService sprProcessRequestsDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    NtisBuildingNtrsDBService ntisBuildingNtrsDBService;

    void handleTransactionStart(Connection conn, Double jobRequestId, LocalDate date, String transactionId) throws Exception {
        SprProcessRequestsDAO request = sprProcessRequestsDBService.newRecord();
        request.setPrq_type(NtisProcessRequestTypes.BROKERWS_BATCH_NTR_REQUEST.getCode());
        request.setPrq_reference_id(jobRequestId);
        request.setPrq_lang(Languages.LT.getCode());
        request.setPrq_initiated_by_system(YesNo.Y.getCode());
        request.setPrq_token(transactionId);

        LocalDateTime dateFrom = LocalDateTime.now();
        request.setPrq_date_from(toDate(dateFrom));
        request.setPrq_date_to(toDate(dateFrom.plusDays(1)));

        setTransactionDateOfInterest(request, toDate(date));

        request = sprProcessRequestsDBService.saveRecord(conn, request);
        conn.commit();

        log.info("Created processRequest {} for RC batch NTR updates transaction {} (date {}).", request.getPrq_id(), request.getPrq_token(), date);
    }

    void closeProcessRequest(Connection conn, SprProcessRequestsDAO processRequest) throws Exception {
        if (processRequest != null) {
            log.info("Closing processRequest {} for RC batch NTR updates transaction {} (date: {}).", processRequest.getPrq_id(), processRequest.getPrq_token(),
                    getTransactionDateOfInterest(processRequest));
            processRequest.setPrq_date_to(new Date());
            sprProcessRequestsDBService.updateRecord(conn, processRequest);
            conn.commit();
        }
    }

    List<SprProcessRequestsDAO> loadProcessRequests(Connection conn) throws Exception {
        List<SprProcessRequestsDAO> result = sprProcessRequestsDBService.loadRecordsByParams(conn,
                " WHERE PRQ_TYPE = ? and prq_date_from < current_timestamp - time '01:00' and "
                        + dBStatementManager.getPeriodValidationForCurrentDateStr("PRQ_DATE_FROM", "PRQ_DATE_TO", false)
                        + " order by coalesce(d01, prq_date_from) asc ",
                new SelectParamValue(NtisProcessRequestTypes.BROKERWS_BATCH_NTR_REQUEST.getCode()));

        Map<LocalDate, String> requestsMap = result.stream()
                .collect(Collectors.toMap(dao -> toLocalDate(getTransactionDateOfInterest(dao)), SprProcessRequestsDAO::getPrq_token));
        log.info("Found unprocessed RC batch NTR updates transactions for these days: {}", requestsMap);

        return result;
    }

    void createFacilitiesNtrs(Connection conn) throws Exception {
        log.info("Some building records were updated by this scheduled job - calling stored procedure ntis.create_facilities_ntrs().");
        long t1 = System.currentTimeMillis();
        try (PreparedStatement ps = conn.prepareStatement("CALL ntis.create_facilities_ntrs()")) {
            ps.execute();
        }
        conn.commit();
        long t2 = System.currentTimeMillis();
        log.info("Stored procedure ntis.create_facilities_ntrs() completed in {} ms.", (t2 - t1));
    }

    void moveNextStartDate(Connection conn, SprJobRequestsDAO request, LocalDate endDateInclusive) throws Exception {
        SprJobDefinitionsDAO dao = sprJobDefinitionDBService.loadRecord(conn, request.getJrq_jde_id());

        LocalDate newStartDate = endDateInclusive.plusDays(1);
        dao.setJde_execution_parameter(newStartDate.toString());
        sprJobDefinitionDBService.updateRecord(conn, dao);
        conn.commit();

        log.info("Process requests were created up to and including {} - moving execution_parameter to {}.", endDateInclusive, newStartDate);
    }

    void applyDataset(Connection conn, Dataset dataset, Consumer<Problem> problemsConsumer) throws Exception {

        MetadataMapper mapper = new MetadataMapper(dataset.getMetadata());
        Date now = new Date();

        for (RowType row : dataset.getData().getRow()) {
            String objInvCode = mapper.getFieldData(row, DataFieldName.UNIKALUS_NR);

            long t1 = System.currentTimeMillis();
            NtisBuildingNtrsDAO dao = ntisBuildingNtrsDBService.loadRecordByParams(conn, "bn_obj_inv_code = ?", new SelectParamValue(objInvCode));
            long t2 = System.currentTimeMillis();

            if (mapper.isDelete(row, problemsConsumer)) {
                if (dao == null) {
                    log.info("Checked and found no current record with bn_obj_inv_code {} ({} ms) - nothing to delete.", objInvCode, (t2 - t1));
                } else {
                    dao.setBn_date_to(now);
                    log.info("Found current record with bn_obj_inv_code {} ({} ms) - closing record with bn_date_to {}.", (t2 - t1), objInvCode, now);
                    ntisBuildingNtrsDBService.saveRecord(conn, dao);
                }
            } else {
                NtisBuildingNtrsDAO oldDao = null;
                if (dao == null) {
                    log.info("Checked and found no current record with bn_obj_inv_code {} ({} ms) - adding new record.", objInvCode, (t2 - t1));
                    dao = ntisBuildingNtrsDBService.newRecord();
                } else {
                    oldDao = ntisBuildingNtrsDBService.newRecord();
                    oldDao.copyValues(dao);
                }

                dao.setBn_obj_inv_code(objInvCode);
                dao.setBn_reg_nr(mapper.getFieldData(row, DataFieldName.REG_NR));
                dao.setBn_reg_date(toDate(mapper.getFieldData(row, DataFieldName.REG_REGISTRAVIMO_DATA)));
                dao.setBn_aob_code(toDouble(mapper.getFieldData(row, DataFieldName.AOB_KODAS)));
                dao.setBn_adr_id(toDouble(mapper.getFieldData(row, DataFieldName.ADR_ID)));
                dao.setBn_municipality_code(mapper.getFieldData(row, DataFieldName.SAV_KODAS));
                dao.setBn_municipality(mapper.getFieldData(row, DataFieldName.SAV_PAVADINIMAS));
                dao.setBn_sen_code(toNumberString(mapper.getFieldData(row, DataFieldName.SEN_KODAS)));
                dao.setBn_sen_name(mapper.getFieldData(row, DataFieldName.SEN_PAVADINIMAS));
                dao.setBn_place_code(mapper.getFieldData(row, DataFieldName.GYV_KODAS));
                dao.setBn_place_name(mapper.getFieldData(row, DataFieldName.GYV_PAVADINIMAS));
                dao.setBn_street_code(mapper.getFieldData(row, DataFieldName.GAT_KODAS));
                dao.setBn_street(mapper.getFieldData(row, DataFieldName.GAT_PAVADINIMAS));
                dao.setBn_house_nr(mapper.getFieldData(row, DataFieldName.ADR_NAMO_NR));
                dao.setBn_housing_nr(mapper.getFieldData(row, DataFieldName.ADR_KORPUSO_NR));
                dao.setBn_flat_nr(mapper.getFieldData(row, DataFieldName.ADR_BUTO_NR));
                dao.setBn_obj_inv_parent_code(mapper.getFieldData(row, DataFieldName.OBJ_KODAS_PRIKLAUSO));
                dao.setBn_object_inv_date(toDate(mapper.getFieldData(row, DataFieldName.OBJ_INVENTORIZACIJOS_DATA)));
                dao.setBn_object_type(toDouble(mapper.getFieldData(row, DataFieldName.OBJE_TIPAS)));
                dao.setBn_object_name(mapper.getFieldData(row, DataFieldName.OBJE_PAV));
                dao.setBn_pask_type(toDouble(mapper.getFieldData(row, DataFieldName.PASK_TIPAS)));
                dao.setBn_pask_name(mapper.getFieldData(row, DataFieldName.PASK_PAV));
                dao.setBn_status(mapper.getFieldData(row, DataFieldName.OSTA_STATUSAS));
                dao.setBn_status_desc(mapper.getFieldData(row, DataFieldName.OSTA_PAV));
                dao.setBn_construction_start_year(toDouble(mapper.getFieldData(row, DataFieldName.STAT_PRADZIOS_METAI)));
                dao.setBn_construction_end_year(toDouble(mapper.getFieldData(row, DataFieldName.STAT_PABAIGOS_METAI)));
                dao.setBn_construction_completion(toDouble(mapper.getFieldData(row, DataFieldName.ATR_BAIGTUMAS)));
                dao.setBn_total_area(toDouble(mapper.getFieldData(row, DataFieldName.BENDRAS_PLOTAS)));
                dao.setBn_useable_area(toDouble(mapper.getFieldData(row, DataFieldName.NAUDINGAS_PLOTAS)));
                dao.setBn_living_area(toDouble(mapper.getFieldData(row, DataFieldName.GYV_PLOTAS)));
                dao.setBn_wastewater_treatment(mapper.getFieldData(row, DataFieldName.NUOTEKU_SALINIMAS));
                dao.setBn_water_supply(mapper.getFieldData(row, DataFieldName.VANDENTIEKIS));
                dao.setRec_timestamp(toDate(mapper.getFieldData(row, DataFieldName.IRASO_KOREGAVIMO_DATA)));

                if (oldDao != null) {
                    log.info("Found current record with bn_obj_inv_code {} ({} ms) - applying following changes {}.", objInvCode, (t2 - t1),
                            findDiffs(oldDao, dao));
                }

                ntisBuildingNtrsDBService.saveRecord(conn, dao);
            }
        }
    }

    private static String findDiffs(NtisBuildingNtrsDAO one, NtisBuildingNtrsDAO two) {
        StringBuilder buf = new StringBuilder();
        diff(buf, "reg_nr", one.getBn_reg_nr(), two.getBn_reg_nr());
        diff(buf, "reg_date", one.getBn_reg_date(), two.getBn_reg_date());
        diff(buf, "aob_code", one.getBn_aob_code(), two.getBn_aob_code());
        diff(buf, "adr_id", one.getBn_adr_id(), two.getBn_adr_id());
        diff(buf, "municipality_code", one.getBn_municipality_code(), two.getBn_municipality_code());
        diff(buf, "municipality", one.getBn_municipality(), two.getBn_municipality());
        diff(buf, "sen_code", one.getBn_sen_code(), two.getBn_sen_code());
        diff(buf, "sen_name", one.getBn_sen_name(), two.getBn_sen_name());
        diff(buf, "place_code", one.getBn_place_code(), two.getBn_place_code());
        diff(buf, "place_name", one.getBn_place_name(), two.getBn_place_name());
        diff(buf, "street_code", one.getBn_street_code(), two.getBn_street_code());
        diff(buf, "street", one.getBn_street(), two.getBn_street());
        diff(buf, "house_nr", one.getBn_house_nr(), two.getBn_house_nr());
        diff(buf, "housing_nr", one.getBn_housing_nr(), two.getBn_housing_nr());
        diff(buf, "flat_nr", one.getBn_flat_nr(), two.getBn_flat_nr());
        diff(buf, "obj_inv_parent_code", one.getBn_obj_inv_parent_code(), two.getBn_obj_inv_parent_code());
        diff(buf, "object_inv_date", one.getBn_object_inv_date(), two.getBn_object_inv_date());
        diff(buf, "object_type", one.getBn_object_type(), two.getBn_object_type());
        diff(buf, "object_name", one.getBn_object_name(), two.getBn_object_name());
        diff(buf, "pask_type", one.getBn_pask_type(), two.getBn_pask_type());
        diff(buf, "pask_name", one.getBn_pask_name(), two.getBn_pask_name());
        diff(buf, "status", one.getBn_status(), two.getBn_status());
        diff(buf, "status_desc", one.getBn_status_desc(), two.getBn_status_desc());
        diff(buf, "construction_start_year", one.getBn_construction_start_year(), two.getBn_construction_start_year());
        diff(buf, "construction_end_year", one.getBn_construction_end_year(), two.getBn_construction_end_year());
        diff(buf, "construction_completion", one.getBn_construction_completion(), two.getBn_construction_completion());
        diff(buf, "total_area", one.getBn_total_area(), two.getBn_total_area());
        diff(buf, "useable_area", one.getBn_useable_area(), two.getBn_useable_area());
        diff(buf, "living_area", one.getBn_living_area(), two.getBn_living_area());
        diff(buf, "wastewater_treatment", one.getBn_wastewater_treatment(), two.getBn_wastewater_treatment());
        diff(buf, "water_supply", one.getBn_water_supply(), two.getBn_water_supply());
        diff(buf, "rec_timestamp", one.getRec_timestamp(), two.getRec_timestamp());

        return buf.toString();
    }

    private static void diff(StringBuilder buf, String fieldName, String one, String two) {
        boolean foundDiff = (one != null && two != null) ? !one.equals(two) : one != null ^ two != null;
        if (foundDiff) {
            buf.append("\n  ").append(fieldName).append(": ").append(one).append(" -> ").append(two).append(";");
        }
    }

    private static void diff(StringBuilder buf, String fieldName, Double one, Double two) {
        boolean foundDiff = (one != null && two != null) ? Double.compare(one, two) != 0 : one != null ^ two != null;
        if (foundDiff) {
            buf.append("\n  ").append(fieldName).append(": ").append(one).append(" -> ").append(two).append(";");
        }
    }

    private static void diff(StringBuilder buf, String fieldName, Date oneTimestamp, Date twoDate) {
        boolean foundDiff = (oneTimestamp != null && twoDate != null) ? !toLocalDate((Timestamp) oneTimestamp).equals(toLocalDate(twoDate))
                : oneTimestamp != null ^ twoDate != null;
        if (foundDiff) {
            buf.append("\n  ").append(fieldName).append(": ").append(oneTimestamp).append(" -> ").append(twoDate).append(";");
        }
    }

    private enum DataFieldName {

        UNIKALUS_NR("unikalus_nr"), REG_NR("reg_nr"), REG_REGISTRAVIMO_DATA("reg_registravimo_data"), AOB_KODAS("aob_kodas"), ADR_ID("adr_id"), SAV_KODAS(
                "sav_kodas"), SAV_PAVADINIMAS("sav_pavadinimas"), SEN_KODAS("sen_kodas"), SEN_PAVADINIMAS("sen_pavadinimas"), GYV_KODAS(
                        "gyv_kodas"), GYV_PAVADINIMAS("gyv_pavadinimas"), GAT_KODAS("gat_kodas"), GAT_PAVADINIMAS("gat_pavadinimas"), ADR_NAMO_NR(
                                "adr_namo_nr"), ADR_KORPUSO_NR("adr_korpuso_nr"), ADR_BUTO_NR("adr_buto_nr"), OBJ_KODAS_PRIKLAUSO(
                                        "obj_kodas_priklauso"), OBJ_INVENTORIZACIJOS_DATA("obj_inventorizacijos_data"), OBJE_TIPAS("obje_tipas"), OBJE_PAV(
                                                "obje_pav"), PASK_TIPAS("pask_tipas"), PASK_PAV("pask_pav"), OSTA_STATUSAS("osta_statusas"), OSTA_PAV(
                                                        "osta_pav"), STAT_PRADZIOS_METAI("stat_pradzios_metai"), STAT_PABAIGOS_METAI(
                                                                "stat_pabaigos_metai"), ATR_BAIGTUMAS("atr_baigtumas"), BENDRAS_PLOTAS(
                                                                        "bendras_plotas"), NAUDINGAS_PLOTAS(
                                                                                "naudingas_plotas"), GYV_PLOTAS("gyv_plotas"), NUOTEKU_SALINIMAS(
                                                                                        "nuoteku_salinimas"), VANDENTIEKIS("vandentiekis"), DEKL_GYV_POZYMIS(
                                                                                                "dekl_gyv_pozymis"), IRASO_KOREGAVIMO_DATA(
                                                                                                        "iraso_koregavimo_data");

        private final String fieldName;

        private DataFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }

    static LocalDate toLocalDate(Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalDate();
    }

    static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    static Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    static String toNumberString(String data) {
        return data == null ? null : data.replace(UNICODE_MINUS_SIGN, '-');
    }

    static Double toDouble(String data) {
        try {
            return data == null || "NULL".equalsIgnoreCase(data) ? null : Utils.getDouble(data.replace(UNICODE_MINUS_SIGN, '-').replace(',', '.'));
        } catch (Exception e) {
            log.error("Could not convert to double value '{}'.", data);
            throw e;
        }
    }

    static Date toDate(String data) throws ParseException {
        return Utils.getDateFromString(data, DBStatementManager.DATE_FORMAT_DAY_DEFAULT_JAVA);
    }

    static void setTransactionDateOfInterest(SprProcessRequestsDAO request, Date date) {
        request.setD01(date);
    }

    static Date getTransactionDateOfInterest(SprProcessRequestsDAO request) {
        return request.getD01();
    }

    private static final class MetadataMapper {

        private final Map<DataFieldName, Integer> nameToIndex;

        MetadataMapper(MetadataType meta) {
            Map<String, DataFieldName> fieldNameToEnum = Stream.of(DataFieldName.values())
                    .collect(Collectors.toMap(DataFieldName::getFieldName, Function.identity()));

            EnumMap<DataFieldName, Integer> map = new EnumMap<>(DataFieldName.class);

            List<ItemType> items = meta.getItem();
            for (int i = 0, cnt = items.size(); i < cnt; i++) {
                map.put(fieldNameToEnum.get(items.get(i).getName()), i);
            }

            this.nameToIndex = Collections.unmodifiableMap(map);
        }

        String getFieldData(RowType data, DataFieldName fieldName) {
            String result = null;
            Integer index = nameToIndex.get(fieldName);
            if (index != null) {
                List<String> list = data.getValue();
                if (index < 0 || index >= list.size()) {
                    throw new RuntimeException(String.format("Identified index %d for field %s is outside bounds of provided data list length %s.", index,
                            fieldName.getFieldName(), list.size()));
                }
                result = list.get(index);
            }
            return result;
        }

        boolean isDelete(RowType data, Consumer<Problem> problemsConsumer) {
            boolean isDelete = false;
            long nonNullCount = data.getValue().stream().filter(Objects::nonNull).count();
            if (nonNullCount <= 3) {
                isDelete = true;

                long expectedCount = 0;
                expectedCount += getFieldData(data, DataFieldName.UNIKALUS_NR) == null ? 0 : 1;
                expectedCount += getFieldData(data, DataFieldName.IRASO_KOREGAVIMO_DATA) == null ? 0 : 1;
                expectedCount += getFieldData(data, DataFieldName.OBJE_TIPAS) == null ? 0 : 1;

                if (expectedCount != nonNullCount) {
                    isDelete = false;
                    String message = String.format("Expected non-null fields count (%d) does not match found non-null fields count (%d). ", expectedCount,
                            nonNullCount);
                    if (expectedCount == 2) {
                        message += String.format("Record delete info should contain %s and %s. Instead found these non-empty fields - %s.",
                                DataFieldName.UNIKALUS_NR.getFieldName(), DataFieldName.IRASO_KOREGAVIMO_DATA.getFieldName(), buildNonEmptyFields(data));

                    } else if (expectedCount == 3) {
                        message += String.format("Record change type info should contain %s, %s and %s. Instead found these non-empty fields - %s.",
                                DataFieldName.UNIKALUS_NR.getFieldName(), DataFieldName.OBJE_TIPAS.getFieldName(),
                                DataFieldName.IRASO_KOREGAVIMO_DATA.getFieldName(), buildNonEmptyFields(data));
                    }
                    problemsConsumer.accept(new Problem(null, message, null));
                    log.warn(message);
                }
            }
            return isDelete;
        }

        private String buildNonEmptyFields(RowType data) {
            StringBuilder buf = new StringBuilder();
            for (DataFieldName name : DataFieldName.values()) {
                String fieldValue = getFieldData(data, name);
                if (fieldValue != null) {
                    buf.append(name.getFieldName()).append(":").append(fieldValue).append(";\n");
                }
            }
            return buf.toString();
        }
    }
}
