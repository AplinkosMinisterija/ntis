package lt.project.ntis.logic.forms.brokerws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import lt.project.ntis.dao.NtisBuildingNtrOwnersDAO;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
import lt.project.ntis.enums.NtisBuildingOwnerType;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.service.NtisBuildingNtrOwnersDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;

/**
 * Pagalbinė integracijos su Registrų Centro BrokerWS sistema klasė, naudojama darbui su DB.
 */
@Component
public class RcBrokerDb {

    private static final Logger log = LoggerFactory.getLogger(RcBrokerDb.class);

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprPersonsDBService personsDBService;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    NtisBuildingNtrOwnersDBService ntisBuildingNtrOwnersDBService;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Value("${brokerws.ntrRequestValidInMinutes}")
    private int requestValidInMinutes;

    SprPersonsDAO loadPerson(Connection conn, Double perId) throws Exception {
        return personsDBService.loadRecord(conn, perId);
    }

    SprOrganizationsDAO loadOrganization(Connection conn, Double orgId) throws Exception {
        return sprOrganizationsDBService.loadRecord(conn, orgId);
    }

    void startPersonNtrDataRequest(Connection conn, Double perId) throws Exception {
        sprProcessRequest.createRequest(conn, NtisProcessRequestTypes.BROKERWS_NTR_REQUEST, YesNo.N, perId, Languages.LT, new Date(), requestValidInMinutes,
                NtisBuildingOwnerType.F.getCode());
    }

    void startOrganizationNtrDataRequest(Connection conn, Double orgId) throws Exception {
        sprProcessRequest.createRequest(conn, NtisProcessRequestTypes.BROKERWS_NTR_REQUEST, YesNo.N, orgId, Languages.LT, new Date(), requestValidInMinutes,
                NtisBuildingOwnerType.J.getCode());
    }

    void createAndFillTempTable(Connection conn, Collection<Long> objInvCodes) throws Exception {
        if (objInvCodes != null && !objInvCodes.isEmpty()) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("""
                    DO
                     $$ <<first_block>>
                    begin
                       DROP TABLE IF EXISTS tmp_table;
                       CREATE TEMP TABLE tmp_table(obj_inv_code VARCHAR(50));
                    END first_block $$;
                    """);
            baseControllerJDBC.adjustRecordsInDB(conn, stmt);

            final String INSERT_SQL = "INSERT INTO tmp_table(obj_inv_code) VALUES (?);";
            try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {
                for (Long objInvCode : objInvCodes) {
                    pstmt.setString(1, Long.toString(objInvCode));
                    pstmt.execute();
                }
            }

            log.info("Using tmp_table with {} records: {}.", objInvCodes.size(), objInvCodes);
        }
    }

    void deleteObsoleteFacilityOwners(Connection conn, Double perId, Double orgId) throws Exception {
        if (orgId != null) {
            deleteObsoleteFacilityOwnerOrganization(conn, orgId);
        } else {
            deleteObsoleteFacilityOwnerPerson(conn, perId);
        }
    }

    private void deleteObsoleteFacilityOwnerPerson(Connection conn, Double perId) throws Exception {
        final String SQL = """
                UPDATE ntis_facility_owners
                   SET fo_date_to = now()
                 WHERE fo_id IN (
                        SELECT fo.fo_id
                          FROM ntis_facility_owners fo, ntis_served_objects so, ntis_building_ntrs bn
                         WHERE fo.fo_per_id = ?
                           AND fo.fo_org_id is null
                           AND COALESCE(fo.fo_date_to, now()) >= now()
                           AND fo.fo_owner_type = 'OWNER'
                           AND fo.fo_so_id = so.so_id
                           AND so.so_bn_id = bn.bn_id
                           AND bn.bn_obj_inv_code NOT IN (SELECT obj_inv_code FROM tmp_table)
                )
                """;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        stmt.addSelectParam(perId);
        int cnt = baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        log.info("Ntis_facility_owners records marked as obsolete for perId:{} - {}.", perId, cnt);
    }

    private void deleteObsoleteFacilityOwnerOrganization(Connection conn, Double orgId) throws Exception {
        final String SQL = """
                UPDATE ntis_facility_owners
                   SET fo_date_to = now()
                 WHERE fo_id IN (
                        SELECT fo.fo_id
                          FROM ntis_facility_owners fo, ntis_served_objects so, ntis_building_ntrs bn
                         WHERE fo.fo_org_id = ?
                           AND COALESCE(fo.fo_date_to, now()) >= now()
                           AND fo.fo_owner_type = 'OWNER'
                           AND fo.fo_so_id = so.so_id
                           AND so.so_bn_id = bn.bn_id
                           AND bn.bn_obj_inv_code NOT IN (SELECT obj_inv_code FROM tmp_table)
                )
                """;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        stmt.addSelectParam(orgId);
        int cnt = baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        log.info("Ntis_facility_owners records marked as obsolete for orgId {}: {}.", orgId, cnt);
    }

    void createNewFacilityOwners(Connection conn, Double perId, Double orgId) throws Exception {
        if (orgId != null) {
            createNewFacilityOwnerOrganization(conn, orgId);
        } else {
            createNewFacilityOwnerPerson(conn, perId);
        }
    }

    private void createNewFacilityOwnerOrganization(Connection conn, Double orgId) throws Exception {
        final String SQL = """
                SELECT so.so_id so_id, wtf.wtf_id wtf_id
                  FROM ntis_served_objects so, ntis_wastewater_treatment_faci wtf, ntis_building_ntrs bn, tmp_table tt
                 WHERE so.so_bn_id = bn.bn_id
                   AND bn.bn_obj_inv_code = tt.obj_inv_code
                   AND so.so_wtf_id = wtf.wtf_id

                EXCEPT

                SELECT fo.fo_so_id so_id, fo.fo_wtf_id wtf_id
                  FROM ntis_facility_owners fo
                 WHERE fo.fo_owner_type = 'OWNER'
                   AND COALESCE(fo.fo_date_to, now()) >= now()
                   AND fo.fo_org_id = ?
                """;

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        stmt.addSelectParam(orgId);

        processDataList(conn, null, orgId, baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt));
    }

    private void createNewFacilityOwnerPerson(Connection conn, Double perId) throws Exception {
        final String SQL = """
                SELECT so.so_id so_id, wtf.wtf_id wtf_id
                  FROM ntis_served_objects so, ntis_wastewater_treatment_faci wtf, ntis_building_ntrs bn, tmp_table tt
                 WHERE so.so_bn_id = bn.bn_id
                   AND bn.bn_obj_inv_code = tt.obj_inv_code
                   AND so.so_wtf_id = wtf.wtf_id

                EXCEPT

                SELECT fo.fo_so_id so_id, fo.fo_wtf_id wtf_id
                  FROM ntis_facility_owners fo
                 WHERE fo.fo_owner_type = 'OWNER'
                   AND COALESCE(fo.fo_date_to, now()) >= now()
                   AND fo.fo_org_id is null
                   AND fo.fo_per_id = ?
                """;

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        stmt.addSelectParam(perId);

        processDataList(conn, perId, null, baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt));
    }

    Collection<Integer> collectRenewableOrgCodes(Connection conn, ProblemsConsumer problemsConsumer) throws Exception {
        Collection<Integer> result = new HashSet<>();
        final String SQL = """
                SELECT org_code
                FROM spr_organizations
                WHERE NOW() - interval '1 month' > coalesce(d04, NOW() - interval '2 months')
                AND COALESCE(org_date_to, now()) >= now()
                AND org_name = 'Individuali įmonė'
                LIMIT 1000
                """;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        List<HashMap<String, String>> dataList = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        if (dataList == null || dataList.isEmpty()) {
            String message = "No more renewable organization records available.";
            problemsConsumer.accept(new RcBroker.Problem(message, null));
            log.warn(message);
        } else {
            for (HashMap<String, String> dataRecord : dataList) {
                String orgCode = dataRecord.get("org_code");
                try {
                    result.add(Integer.valueOf(orgCode));
                } catch (Exception e) {
                    String message = String.format("Could not parse '%s' into integer.", orgCode);
                    log.error(message, e);
                    problemsConsumer.accept(new RcBroker.Problem(message, e));
                }
            }
            log.info("Collected {} renewable orgCodes.", result.size());
        }
        return result;
    }

    private void processDataList(Connection conn, Double perId, Double orgId, List<HashMap<String, String>> dataList) throws Exception {
        if (dataList == null || dataList.isEmpty()) {
            log.info("No new ntis_facility_owners records added for perId:{}; orgId:{}.", perId, orgId);
        } else {
            for (HashMap<String, String> dataRecord : dataList) {
                String soId = dataRecord.get("so_id");
                String wtfId = dataRecord.get("wtf_id");

                NtisFacilityOwnersDAO daoObject = new NtisFacilityOwnersDAO();
                daoObject.setFo_owner_type(NtisFacilityOwnerType.OWNER.getCode());
                daoObject.setFo_date_from(new Date());
                daoObject.setFo_org_id(orgId);
                daoObject.setFo_per_id(perId);
                daoObject.setFo_wtf_id(Utils.getDouble(wtfId));
                daoObject.setFo_so_id(Utils.getDouble(soId));
                ntisFacilityOwnersDBService.insertRecord(conn, daoObject);
                log.info("New ntis_facility_owners record added for perId:{}; orgId:{}; soId:{}; wtfId:{}.", perId, orgId, soId, wtfId);
            }
        }
    }

    void deleteObsoleteBuildingOwners(Connection conn, String ownerCode) throws Exception {
        final String SQL = """
                DELETE FROM ntis_building_ntr_owners
                 WHERE bno_id IN (
                        SELECT bno.bno_id
                          FROM ntis_building_ntrs bn, ntis_building_ntr_owners bno
                         WHERE bn.bn_obj_inv_code NOT IN (SELECT obj_inv_code FROM tmp_table)
                           AND bn.bn_id = bno.bno_bn_id
                           AND bno.bno_code = ?
                )
                """;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        stmt.addSelectParam(ownerCode);
        int cnt = baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        log.info("Ntis_building_ntr_owners records deleted for bno_code:{} - {}.", ownerCode, cnt);
    }

    void createNewBuildingOwners(Connection conn, NtisBuildingNtrOwnersDAO ownerInfo) throws Exception {
        final String SQL = """
                SELECT bn.bn_id bn_id
                  FROM ntis_building_ntrs bn
                 WHERE bn.bn_obj_inv_code IN (SELECT obj_inv_code FROM tmp_table)

                EXCEPT

                SELECT bn.bn_id bn_id
                  FROM ntis_building_ntrs bn, ntis_building_ntr_owners bno
                 WHERE bn.bn_obj_inv_code IN (SELECT obj_inv_code FROM tmp_table)
                   AND bn.bn_id = bno.bno_bn_id
                   AND bno.bno_code = ?
                """;

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(SQL);
        stmt.addSelectParam(ownerInfo.getBno_code());

        List<HashMap<String, String>> dataList = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);

        Set<Double> missingBnIds = dataList.stream().map(dataRecord -> dataRecord.get("bn_id")).map(Utils::getDouble).filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (missingBnIds.isEmpty()) {
            log.info("No new ntis_building_ntr_owners records added for bno_type:{}; bno_code:{}; bno_org_name:{}; bno_name:{}; bno_lastname:{}.",
                    ownerInfo.getBno_type(), ownerInfo.getBno_code(), ownerInfo.getBno_org_name(), ownerInfo.getBno_name(), ownerInfo.getBno_lastname());
        } else {
            for (Double missingBnId : missingBnIds) {
                NtisBuildingNtrOwnersDAO daoObject = new NtisBuildingNtrOwnersDAO();
                daoObject.setBno_type(ownerInfo.getBno_type());
                daoObject.setBno_code(ownerInfo.getBno_code());
                daoObject.setBno_org_name(ownerInfo.getBno_org_name());
                daoObject.setBno_name(ownerInfo.getBno_name());
                daoObject.setBno_lastname(ownerInfo.getBno_lastname());
                daoObject.setBno_bn_id(missingBnId);

                ntisBuildingNtrOwnersDBService.insertRecord(conn, daoObject);
                log.info("New ntis_building_ntr_owners record added for bno_bn_id:{}; bno_type:{}; bno_code:{}; bno_org_name:{}; bno_name:{}; bno_lastname:{}.",
                        missingBnId, ownerInfo.getBno_type(), ownerInfo.getBno_code(), ownerInfo.getBno_org_name(), ownerInfo.getBno_name(),
                        ownerInfo.getBno_lastname());
            }
        }
    }

    void addOrganization(Connection conn, Jar4020Data data) throws Exception {
        sprOrganizationsDBService.insertRecord(conn, setOrganizationData((SprOrganizationsNtisDAO) sprOrganizationsDBService.newRecord(), data));
    }

    void updateOrganization(Connection conn, Integer objKodas, Jar4020Data data) throws Exception {
        SprOrganizationsNtisDAO organizationDAO = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecordByParams(conn, "org_code = ? ",
                new SelectParamValue(Integer.toString(objKodas)));

        sprOrganizationsDBService.updateRecord(conn, setOrganizationData(organizationDAO, data));
    }

    private SprOrganizationsDAO setOrganizationData(SprOrganizationsNtisDAO organizationDAO, Jar4020Data data) {
        if (data != null) {
            organizationDAO.setOrg_code(Integer.toString(data.getObjKodas()));
            organizationDAO.setOrg_name(data.getObjPav());
            organizationDAO.setRcOrgType(Utils.getDouble(data.getFormKodas()));
            organizationDAO.setOrg_address(data.getJadTekstas());
            organizationDAO.setOrg_date_from(data.getRegDate());
            String phone = data.getPhone();
            if (phone != null) {
                organizationDAO.setOrg_phone(phone);
            }
            String email = data.getEmail();
            if (email != null) {
                organizationDAO.setOrg_email(email);
            }
            String website = data.getWebsite();
            if (website != null) {
                organizationDAO.setOrg_website(website);
            }
        }
        organizationDAO.setRenewFromRcDate(new Date());
        return organizationDAO;
    }

    void removeOrganization(Connection conn, Integer objKodas, Date isregDate) throws Exception {
        SprOrganizationsDAO organizationDAO = sprOrganizationsDBService.loadRecordByParams(conn, "org_code = ? ",
                new SelectParamValue(Integer.toString(objKodas)));

        organizationDAO.setOrg_date_to(isregDate);
        sprOrganizationsDBService.updateRecord(conn, organizationDAO);
    }
}
