package lt.project.ntis.logic.forms.rcadrws;

import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_ADM_XML;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_ADRXY_XML;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_AOB_CODE;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_AOB_ID;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_GAT_XML;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_GYV_XML;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_PAT_CODE;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_PAT_ID;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_PAT_XML;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_RESIDENCE_CODE;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_RESIDENCE_ID;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_SEN_CODE;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_SEN_ID;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_STREET_CODE;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.MDC_STREET_ID;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.isBlank;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.safeToString;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.toDate;
import static lt.project.ntis.logic.forms.rcadrws.RcAdr.toDouble;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import lt.project.ntis.dao.NtisAdrPatLrsDAO;
import lt.project.ntis.dao.NtisAdrResidencesDAO;
import lt.project.ntis.dao.NtisAdrSeniunijosDAO;
import lt.project.ntis.dao.NtisAdrStatsDAO;
import lt.project.ntis.dao.NtisAdrStreetsDAO;
import lt.project.ntis.logic.forms.rcadrws.RcAdr.Problem;
import lt.project.ntis.rcadrws.adm.ADMINISTRACINIS;
import lt.project.ntis.rcadrws.adrxy.ADRESAS;
import lt.project.ntis.rcadrws.gat.GATVE;
import lt.project.ntis.rcadrws.gyv.GYVENVIETE;
import lt.project.ntis.rcadrws.pat.PATALPA;
import lt.project.ntis.service.NtisAdrPatLrsDBService;
import lt.project.ntis.service.NtisAdrResidencesDBService;
import lt.project.ntis.service.NtisAdrSeniunijosDBService;
import lt.project.ntis.service.NtisAdrStatsDBService;
import lt.project.ntis.service.NtisAdrStreetsDBService;

/**
 * Pagalbinė integracijos su Registrų Centro AdrWS sistema klasė, naudojama darbui su DB.
 */
@Component
public class RcAdrDb {

    private static final Logger log = LoggerFactory.getLogger(RcAdrDb.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    protected SprJobDefinitionsDBService sprJobDefinitionDBService;

    @Autowired
    NtisAdrSeniunijosDBService ntisAdrSeniunijosDBService;

    @Autowired
    NtisAdrResidencesDBService ntisAdrResidencesDBService;

    @Autowired
    NtisAdrStreetsDBService ntisAdrStreetsDBService;

    @Autowired
    NtisAdrStatsDBService ntisAdrStatsDBService;

    @Autowired
    NtisAdrPatLrsDBService ntisAdrPatLrsDBService;

    SprJobDefinitionsDAO updateRecord(Connection conn, SprJobDefinitionsDAO dao) throws Exception {
        return sprJobDefinitionDBService.updateRecord(conn, dao);
    }

    Optional<Problem> changeSeniunijaData(Connection conn, ADMINISTRACINIS administracinis) throws Exception {
        try {
            String tipas = administracinis.getTIPAS();
            if ("seniūnija".equalsIgnoreCase(tipas)) {
                String senCode = administracinis.getADMKODAS();
                MDC.put(MDC_SEN_CODE, senCode);

                NtisAdrSeniunijosDAO dao = findSeniunijaRecord(conn, administracinis);
                boolean searchDataChanged = hasAddressSearchDataChanged(dao, administracinis);

                dao.setSen_code(senCode);
                dao.setSen_name(administracinis.getVARDASK());
                dao.setSen_municipality_code(administracinis.getPRIKLAUSOKODAS());
                dao.setSen_date_from(toDate(administracinis.getADMNUO()));
                dao.setSen_date_to(toDate(administracinis.getADMIKI()));
                dao = ntisAdrSeniunijosDBService.saveRecord(conn, dao);
                log.info("Added/updated seniunija with senCode {} and senId {}.", senCode, dao.getSen_id());

                if (searchDataChanged) {
                    doAddressDataUpdate(conn, dao);
                }

            } else if ("savivaldybė".equalsIgnoreCase(tipas)) {
                log.info("Ignoring new SAVIVALDYBE record with code {}.", administracinis.getADMKODAS());
            } else if ("apskritis".equalsIgnoreCase(tipas)) {
                log.info("Ignoring new APSKRITIS record with code {}.", administracinis.getADMKODAS());
            } else {
                String message = String.format("Received %s record (code '%s') of unexpected type '%s'. Full xml: %s", ADMINISTRACINIS.class.getSimpleName(),
                        administracinis.getADMKODAS(), tipas, MDC.get(MDC_ADM_XML));
                log.error(message);
                return Optional.of(new Problem(message, null));
            }

            return Optional.empty();
        } catch (Exception e) {
            String message = String.format("Failed to insert/update seniunija data for sen_code: %s; sen_id: %s. Full xml: %s", MDC.get(MDC_SEN_CODE),
                    MDC.get(MDC_SEN_ID), MDC.get(MDC_ADM_XML));
            log.error(message, e);
            return Optional.of(new Problem(message, e));
        }
    }

    Optional<Problem> changeResidenceData(Connection conn, GYVENVIETE gyvenviete) throws Exception {
        try {
            String residenceCode = gyvenviete.getGYVKODAS();
            MDC.put(MDC_RESIDENCE_CODE, residenceCode);

            String admKodas = gyvenviete.getADMKODAS();
            SenCodeMunicipalityCode codes = toSenCodeMunicipalityCode(conn, admKodas);
            if (isBlank(codes.municipalityCode())) {
                String message = String.format(
                        "Could not identify mandatory field municipality_code for residence record with recidence_code %s based on ADMKODAS %s. Full xml: %s",
                        MDC.get(MDC_RESIDENCE_CODE), admKodas, MDC.get(MDC_GYV_XML));
                log.error(message);
                return Optional.of(new Problem(message, null));
            }

            NtisAdrResidencesDAO dao = findResidenceRecord(conn, gyvenviete);
            boolean searchDataChanged = hasAddressSearchDataChanged(dao, gyvenviete);

            dao.setRe_recidence_code(toDouble(gyvenviete.getGYVKODAS()));
            dao.setRe_type(gyvenviete.getTIPAS());
            dao.setRe_type_abbreviation(gyvenviete.getTIPOSANTRUMPA());
            dao.setRe_name_k(gyvenviete.getVARDASK());
            dao.setRe_name(gyvenviete.getVARDASV());
            dao.setRe_sen_code(toDouble(codes.senCode()));
            dao.setRe_municipality_code(toDouble(codes.municipalityCode()));
            dao.setRe_date_from(toDate(gyvenviete.getGYVNUO()));
            dao.setRe_date_to(toDate(gyvenviete.getGYVIKI()));
            dao.setC01(buildResidenceC01Value(dao));
            dao = ntisAdrResidencesDBService.saveRecord(conn, dao);
            log.info("Added/updated gyvenviete with residenceCode {} and reId {}.", residenceCode, dao.getRe_id());

            if (searchDataChanged) {
                doAddressDataUpdate(conn, dao);
            }

            return Optional.empty();
        } catch (Exception e) {
            String message = String.format("Failed to insert/update residence data for residence_code: %s; re_id: %s. Full xml: %s",
                    MDC.get(MDC_RESIDENCE_CODE), MDC.get(MDC_RESIDENCE_ID), MDC.get(MDC_GYV_XML));
            log.error(message, e);
            return Optional.of(new Problem(message, e));
        }
    }

    Optional<Problem> changeStreetData(Connection conn, GATVE gatve) throws Exception {
        try {
            String streetCode = gatve.getGATKODAS();
            MDC.put(MDC_STREET_CODE, streetCode);

            NtisAdrStreetsDAO dao = findGatveRecord(conn, gatve);
            boolean searchDataChanged = hasAddressSearchDataChanged(dao, gatve);

            dao.setStr_street_code(toDouble(streetCode));
            dao.setStr_type(gatve.getTIPAS());
            dao.setStr_type_abbreviation(gatve.getTIPOSANTRUMPA());
            dao.setStr_name(gatve.getVARDASK());
            dao.setStr_residence_code(toDouble(gatve.getGYVKODAS()));
            dao.setStr_date_from(toDate(gatve.getGATNUO()));
            dao.setStr_date_to(toDate(gatve.getGATIKI()));
            dao.setC01(buildStreetC01Value(dao));
            dao = ntisAdrStreetsDBService.saveRecord(conn, dao);
            log.info("Added/updated gatve with streetCode {} and strId {}.", streetCode, dao.getStr_id());

            if (searchDataChanged) {
                doAddressDataUpdate(conn, dao);
            }

            return Optional.empty();
        } catch (Exception e) {
            String message = String.format("Failed to insert/update street data for street_code: %s; str_id: %s. Full xml: %s", MDC.get(MDC_STREET_CODE),
                    MDC.get(MDC_STREET_ID), MDC.get(MDC_GAT_XML));
            log.error(message, e);
            return Optional.of(new Problem(message, e));
        }
    }

    Optional<Problem> changeAddressData(Connection conn, ADRESAS adresas) throws Exception {
        try {
            String aobCode = adresas.getAOBKODAS();
            MDC.put(MDC_AOB_CODE, aobCode);

            NtisAdrStatsDAO dao = findAdresasRecord(conn, adresas);
            boolean isNewRecord = dao.getAds_id() == null;
            boolean searchDataChanged = hasAddressSearchDataChanged(dao, adresas);
            boolean coordinatesChanged = hasAddressCoordinatesChanged(dao, adresas);

            MissingData missingData = loadMissingData(conn, adresas.getGYVKODAS(), adresas.getGATKODAS());
            if (missingData.municipalityCode() == null) {
                String message = String.format(
                        "Could not identify mandatory field municipality_code for address record with aob_code %s and ads_id %s based on GYV_KODAS %s and GAT_KODAS %s. Full xml: %s",
                        MDC.get(MDC_AOB_CODE), MDC.get(MDC_AOB_ID), adresas.getGYVKODAS(), adresas.getGATKODAS(), MDC.get(MDC_ADRXY_XML));
                log.error(message);
                return Optional.of(new Problem(message, null));
            }

            dao.setAds_municipality_code(missingData.municipalityCode());
            dao.setAds_aob_code(toDouble(aobCode));
            dao.setAds_residence_code(toDouble(adresas.getGYVKODAS()));
            dao.setAds_street_code(toDouble(adresas.getGATKODAS()));
            dao.setAds_coordinate_latitude(toDouble(adresas.getY()));
            dao.setAds_coordinate_longitude(toDouble(adresas.getX()));
            dao.setAds_nr(adresas.getNR());
            dao.setAds_housing_nr(adresas.getKORPUSONR());
            dao.setAds_post_code(adresas.getPASTOKODAS());
            dao.setAds_re_id(missingData.reId());
            dao.setAds_str_id(missingData.strId());
            dao.setAds_date_from(toDate(adresas.getAOBNUO()));
            dao.setAds_date_to(toDate(adresas.getAOBIKI()));
            dao = ntisAdrStatsDBService.saveRecord(conn, dao);
            log.info("Added/updated address with aobCode {} and adsId {}.", aobCode, dao.getAds_id());

            if (isNewRecord) {
                doAddressDataInsert(conn, dao);
                mapBuildings(conn, dao);
            } else if (searchDataChanged || coordinatesChanged) {
                doAddressDataUpdate(conn, dao);
            }

            return Optional.empty();
        } catch (Exception e) {
            String message = String.format("Failed to insert/update address data for aob_code: %s; ads_id: %s. Full xml: %s", MDC.get(MDC_AOB_CODE),
                    MDC.get(MDC_AOB_ID), MDC.get(MDC_ADRXY_XML));
            log.error(message, e);
            return Optional.of(new Problem(message, e));
        }
    }

    Optional<Problem> changePatalpaData(Connection conn, PATALPA patalpa) throws Exception {
        try {
            String patCode = patalpa.getPATKODAS();
            MDC.put(MDC_PAT_CODE, patCode);

            NtisAdrPatLrsDAO dao = findPatalpaRecord(conn, patalpa);
            boolean isNewRecord = dao.getApl_id() == null;
            boolean searchDataChanged = hasAddressSearchDataChanged(dao, patalpa);

            NtisAdrStatsDAO adrStats = loadAdrStats(conn, patalpa.getAOBKODAS());
            if (adrStats == null) {
                String message = String.format(
                        "Could not identify address record (needed for municipality_code value) for patalpa record with pat_code %s and apl_id %s based on AOBKODAS %s. Full xml: %s",
                        MDC.get(MDC_PAT_CODE), MDC.get(MDC_PAT_ID), patalpa.getAOBKODAS(), MDC.get(MDC_PAT_XML));
                log.error(message);
                return Optional.of(new Problem(message, null));
            }

            dao.setApl_municipality_code(adrStats.getAds_municipality_code());
            dao.setApl_pat_code(toDouble(patCode));
            dao.setApl_aob_code(toDouble(patalpa.getAOBKODAS()));
            dao.setApl_pat_nr(patalpa.getPATALPOSNR());
            dao.setApl_date_from(toDate(patalpa.getPATNUO()));
            dao.setApl_date_to(toDate(patalpa.getPATIKI()));
            dao.setApl_ads_id(adrStats.getAds_id());
            dao = ntisAdrPatLrsDBService.saveRecord(conn, dao);
            log.info("Added/updated patalpa with patCode {} and aplId {}.", patCode, dao.getApl_id());

            if (isNewRecord) {
                doAddressDataInsert(conn, dao);
                mapBuildings(conn, dao);
            } else if (searchDataChanged) {
                doAddressDataUpdate(conn, dao);
            }

            return Optional.empty();
        } catch (Exception e) {
            String message = String.format("Failed to insert/update patalpa data for pat_code: %s; apl_id: %s. Full xml: %s", MDC.get(MDC_PAT_CODE),
                    MDC.get(MDC_PAT_ID), MDC.get(MDC_PAT_XML));
            log.error(message, e);
            return Optional.of(new Problem(message, e));
        }
    }

    private NtisAdrSeniunijosDAO findSeniunijaRecord(Connection conn, ADMINISTRACINIS administracinis) throws Exception {
        String senCode = administracinis.getADMKODAS();
        NtisAdrSeniunijosDAO dao = ntisAdrSeniunijosDBService.loadRecordByParams(conn, " sen_code = ? ", new SelectParamValue(senCode));
        if (dao != null) {
            log.info("Updating seniunija with senCode {}.", senCode);
        } else {
            log.info("Adding new seniunija with senCode {}.", senCode);
            dao = ntisAdrSeniunijosDBService.newRecord();
        }
        MDC.put(MDC_SEN_ID, safeToString(dao.getSen_id()));
        return dao;
    }

    private NtisAdrResidencesDAO findResidenceRecord(Connection conn, GYVENVIETE gyvenviete) throws Exception {
        String residenceCode = gyvenviete.getGYVKODAS();
        NtisAdrResidencesDAO dao = ntisAdrResidencesDBService.loadRecordByParams(conn, " re_recidence_code = ? ",
                new SelectParamValue(toDouble(residenceCode)));
        if (dao != null) {
            log.info("Updating gyvenviete with residenceCode {}.", residenceCode);
        } else {
            log.info("Adding new gyvenviete with residenceCode {}.", residenceCode);
            dao = ntisAdrResidencesDBService.newRecord();
        }
        MDC.put(MDC_RESIDENCE_ID, safeToString(dao.getRe_id()));
        return dao;
    }

    private NtisAdrStreetsDAO findGatveRecord(Connection conn, GATVE gatve) throws Exception {
        String streetCode = gatve.getGATKODAS();
        NtisAdrStreetsDAO dao = ntisAdrStreetsDBService.loadRecordByParams(conn, " str_street_code = ? ", new SelectParamValue(toDouble(streetCode)));
        if (dao != null) {
            log.info("Updating gatve with streetCode {}.", streetCode);
        } else {
            log.info("Adding new gatve with streetCode {}.", streetCode);
            dao = ntisAdrStreetsDBService.newRecord();
        }
        MDC.put(MDC_STREET_ID, safeToString(dao.getStr_id()));
        return dao;
    }

    private NtisAdrStatsDAO findAdresasRecord(Connection conn, ADRESAS adresas) throws Exception {
        String aobCode = adresas.getAOBKODAS();
        NtisAdrStatsDAO dao = ntisAdrStatsDBService.loadRecordByParams(conn, " ads_aob_code = ? ", new SelectParamValue(toDouble(aobCode)));
        if (dao != null) {
            log.info("Updating address with aobCode {}.", aobCode);
        } else {
            log.info("Adding new address with aobCode {}.", aobCode);
            dao = ntisAdrStatsDBService.newRecord();
        }
        MDC.put(MDC_AOB_ID, safeToString(dao.getAds_id()));
        return dao;
    }

    private NtisAdrPatLrsDAO findPatalpaRecord(Connection conn, PATALPA patalpa) throws Exception {
        String patCode = patalpa.getPATKODAS();
        NtisAdrPatLrsDAO dao = ntisAdrPatLrsDBService.loadRecordByParams(conn, " apl_pat_code = ? ", new SelectParamValue(toDouble(patCode)));
        if (dao != null) {
            log.info("Updating patalpa with patCode {}.", patCode);
        } else {
            log.info("Adding new patalpa with patCode {}.", patCode);
            dao = ntisAdrPatLrsDBService.newRecord();
        }
        MDC.put(MDC_PAT_ID, safeToString(dao.getApl_id()));
        return dao;
    }

    private boolean hasAddressSearchDataChanged(NtisAdrSeniunijosDAO dao, ADMINISTRACINIS administracinis) {
        return dao.getSen_id() != null && !(dao.getSen_name().equals(administracinis.getVARDASK()));
    }

    private boolean hasAddressSearchDataChanged(NtisAdrResidencesDAO dao, GYVENVIETE gyvenviete) {
        return dao.getRe_id() != null
                && !(dao.getRe_name_k().equals(gyvenviete.getVARDASK()) && dao.getRe_type_abbreviation().equals(gyvenviete.getTIPOSANTRUMPA()));
    }

    private boolean hasAddressSearchDataChanged(NtisAdrStreetsDAO dao, GATVE gatve) {
        return dao.getStr_id() != null && !(dao.getStr_name().equals(gatve.getVARDASK()) && dao.getStr_type_abbreviation().equals(gatve.getTIPOSANTRUMPA()));
    }

    private boolean hasAddressSearchDataChanged(NtisAdrStatsDAO dao, ADRESAS adresas) {
        return dao.getAds_id() != null && !dao.getAds_nr().equals(adresas.getNR());
    }

    private boolean hasAddressCoordinatesChanged(NtisAdrStatsDAO dao, ADRESAS adresas) throws Exception {
        boolean result = false;
        if (dao.getAds_id() != null) {
            Double longitude = dao.getAds_coordinate_longitude();
            Double latitude = dao.getAds_coordinate_latitude();
            Double x = toDouble(adresas.getX());
            Double y = toDouble(adresas.getY());
            if (longitude == null || latitude == null || x == null || y == null) {
                throw new Exception(String.format(
                        "Cannot compare coordinates change for ntis_adr_stats record with id:%d and longitude:%d; latitude:%d versus coordinates x:%d; y:%d.",
                        dao.getAds_id(), longitude, latitude, x, y));
            }
            result = Double.compare(longitude, x) != 0 || Double.compare(latitude, y) != 0;
        }
        return result;
    }

    private boolean hasAddressSearchDataChanged(NtisAdrPatLrsDAO dao, PATALPA patalpa) {
        return dao.getApl_id() != null && !dao.getApl_pat_nr().equals(patalpa.getPATALPOSNR());
    }

    private static String buildResidenceC01Value(NtisAdrResidencesDAO dao) {
        return (dao.getRe_name_k() + " " + dao.getRe_type() + "|" + dao.getRe_name_k() + " " + dao.getRe_type_abbreviation() + "|" + dao.getRe_name() + "|")
                .toLowerCase();
    }

    private record SenCodeMunicipalityCode(String senCode, String municipalityCode) {
    }

    private SenCodeMunicipalityCode toSenCodeMunicipalityCode(Connection conn, String admKodas) throws Exception {
        log.info("Finding senCode and municipalityCode for admCode '{}'.", admKodas);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                select rfc_code county_code, null municipality_code
                from spark.spr_ref_codes
                where rfc_domain = 'NTIS_COUNTIES'
                and rfc_code = ?

                union

                select rfc_ref_code_1 county_code, rfc_code municipality_code
                from spark.spr_ref_codes
                where rfc_domain = 'NTIS_MUNICIPALITIES'
                and rfc_code = ?
                    """);
        stmt.addSelectParam(admKodas);
        stmt.addSelectParam(admKodas);
        List<HashMap<String, String>> rows = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        String senCode = null;
        String municipalityCode = null;
        for (HashMap<String, String> row : rows) {
            senCode = row.get("country_code");
            municipalityCode = row.get("municipality_code");
        }
        log.info("Finding senCode '{}' and municipalityCode '{}' for admCode '{}'.", senCode, municipalityCode, admKodas);
        return new SenCodeMunicipalityCode(senCode, municipalityCode);
    }

    private static String buildStreetC01Value(NtisAdrStreetsDAO dao) {
        return (dao.getStr_name() + " " + dao.getStr_type() + "|" + dao.getStr_name() + " "
                + (dao.getStr_type_abbreviation() == null ? "" : dao.getStr_type_abbreviation()) + "|" + dao.getStr_name() + "|").toLowerCase();
    }

    private MissingData loadMissingData(Connection conn, String residenceCode, String streetCode) throws Exception {
        return streetCode.isBlank() ? loadPartialMissingData(conn, residenceCode) : loadFullMissingData(conn, residenceCode, streetCode);
    }

    private MissingData loadFullMissingData(Connection conn, String residenceCode, String streetCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                select re.re_id as re_id, re.re_municipality_code as re_municipality_code, str.str_id as str_id
                from ntis.ntis_adr_residences re, ntis.ntis_adr_streets str
                where re.re_recidence_code = ?
                and str.str_street_code = ?
                and str.str_residence_code = re.re_recidence_code
                and now() between re.re_date_from and coalesce(re.re_date_to, now())
                and now() between str.str_date_from and coalesce(str.str_date_to, now());
                """);
        stmt.addSelectParam(toDouble(residenceCode));
        stmt.addSelectParam(toDouble(streetCode));

        log.info("Loading missing data using residenceCode {} and streetCode {}.", residenceCode, streetCode);
        long t1 = System.currentTimeMillis();

        List<HashMap<String, String>> rows = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        Double municipalityCode = null;
        Double reId = null;
        Double strId = null;
        for (HashMap<String, String> row : rows) {
            municipalityCode = toDouble(row.get("re_municipality_code"));
            reId = toDouble(row.get("re_id"));
            strId = toDouble(row.get("str_id"));
        }
        log.info("Loaded missing data (municipalityCode {}; residenceId {}; streetId {}) using residenceCode {} and streetCode {} in {} ms.", municipalityCode,
                reId, strId, residenceCode, streetCode, (System.currentTimeMillis() - t1));
        return new MissingData(municipalityCode, reId, strId);
    }

    private MissingData loadPartialMissingData(Connection conn, String residenceCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                select re.re_id as re_id, re.re_municipality_code as re_municipality_code
                from ntis.ntis_adr_residences re
                where re.re_recidence_code = ?
                and now() between re.re_date_from and coalesce(re.re_date_to, now())
                """);
        stmt.addSelectParam(toDouble(residenceCode));

        log.info("Loading missing data using only residenceCode {}.", residenceCode);
        long t1 = System.currentTimeMillis();

        List<HashMap<String, String>> rows = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        Double municipalityCode = null;
        Double reId = null;
        for (HashMap<String, String> row : rows) {
            municipalityCode = toDouble(row.get("re_municipality_code"));
            reId = toDouble(row.get("re_id"));
        }
        log.info("Loaded missing data (municipalityCode {}; residenceId {}) using only residenceCode {} in {} ms.", municipalityCode, reId, residenceCode,
                (System.currentTimeMillis() - t1));
        return new MissingData(municipalityCode, reId, null);
    }

    private record MissingData(Double municipalityCode, Double reId, Double strId) {
    }

    private NtisAdrStatsDAO loadAdrStats(Connection conn, String aobCode) throws Exception {
        log.info("Loading adrStats using aobCode {}.", aobCode);
        long t1 = System.currentTimeMillis();
        NtisAdrStatsDAO result = ntisAdrStatsDBService.loadRecordByParams(conn,
                " ads_aob_code = ? and now() between ads_date_from and coalesce(ads_date_to, now())", new SelectParamValue(toDouble(aobCode)));
        log.info("Loaded adrStats using aobCode {} in {} ms.", aobCode, (System.currentTimeMillis() - t1));
        return result;
    }

    private void mapBuildings(Connection conn, NtisAdrStatsDAO dao) throws Exception {
        final String STMT = """
                update ntis.ntis_building_ntrs
                set bn_ad_id = ad_id from (
                    select a1.ad_id, s1.ads_aob_code as aob_code
                    from ntis.ntis_adr_addresses a1, ntis.ntis_adr_stats s1
                    where a1.ad_ads_id = s1.ads_id
                    and a1.ad_apl_id is null
                    and s1.ads_aob_code = ?
                ) t
                where t.aob_code = bn_aob_code
                and bn_ad_id is null;
                """;
        Double aobCode = dao.getAds_aob_code();
        log.info("Mapping buildings using aobCode {}.", aobCode);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT)) {
            pstmt.setDouble(1, aobCode);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Mapped {} buildings using aobCode {} in {} ms.", cnt, aobCode, (System.currentTimeMillis() - t1));
    }

    private void mapBuildings(Connection conn, NtisAdrPatLrsDAO dao) throws Exception {
        final String STMT = """
                update ntis.ntis_building_ntrs
                set bn_ad_id = ad_id from (
                    select a1.ad_id, p1.apl_pat_code as aob_code
                    from ntis.ntis_adr_addresses a1, ntis.ntis_adr_pat_lrs p1
                    where a1.ad_apl_id = p1.apl_id
                    and p1.apl_pat_code = ?
                ) t
                where t.aob_code = bn_aob_code
                and bn_ad_id is null;
                """;
        Double patCode = dao.getApl_pat_code();
        log.info("Mapping buildings using patCode {}.", patCode);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT)) {
            pstmt.setDouble(1, patCode);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Mapped {} buildings using patCode {} in {} ms.", cnt, patCode, (System.currentTimeMillis() - t1));
    }

    private void doAddressDataInsert(Connection conn, NtisAdrStatsDAO dao) throws Exception {
        final String STMT = """
                INSERT INTO ntis.ntis_adr_addresses (ad_address, ad_address_search, ad_apl_id, ad_ads_id, ad_coordinate_latitude, ad_coordinate_longitude, ad_geom)
                (SELECT case when d.str_name is not null then d.str_name || ' ' || d.str_type_abbreviation || ' '
                         || b.ads_nr
                         || case when c.re_name_k is not null then  ', '||c.re_name_k || ' ' || c.re_type_abbreviation else '' end
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       else
                         c.re_name_k || ' ' || c.re_type_abbreviation || ', '  || b.ads_nr
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       end ad_address,
                       translate (lower(case when d.str_name is not null then d.str_name || ' ' || d.str_type_abbreviation || ' '
                         || b.ads_nr
                         || case when c.re_name_k is not null then  ', '||c.re_name_k || ' ' || c.re_type_abbreviation else '' end
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       else
                         c.re_name_k || ' ' || c.re_type_abbreviation || ', '  || b.ads_nr
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       end), 'ąčęėįšųūž','aceeisuuz') ad_address_search,
                 null, b.ads_id,
                 b.ads_coordinate_latitude,
                 b.ads_coordinate_longitude,
                 public.st_setsrid(public.st_makepoint(b.ads_coordinate_latitude, b.ads_coordinate_longitude), 3346)
                FROM ntis.ntis_adr_stats b
                JOIN ntis.ntis_adr_residences c on c.re_id = b.ads_re_id and now() between c.re_date_from and coalesce(c.re_date_to, now())
                LEFT JOIN ntis.ntis_adr_streets d on d.str_id = b.ads_str_id and now() between d.str_date_from and coalesce(d.str_date_to, now())
                LEFT JOIN ntis.ntis_adr_seniunijos sen on sen.sen_code = c.re_sen_code :: text and now() between sen.sen_date_from and coalesce(sen.sen_date_to, now())
                join spark.spr_ref_codes m on m.rfc_code = (c.re_municipality_code :: text) and rfc_domain = 'NTIS_MUNICIPALITIES'

                where b.ads_id = ?
                and now() between b.ads_date_from and coalesce(b.ads_date_to, now())
                );""";
        Double adsId = dao.getAds_id();
        log.info("Inserting new addresses without patalpos;  adsId {}.", adsId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT)) {
            pstmt.setDouble(1, adsId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Inserted {} addresses without patalpos for adsId {} in {} ms.", cnt, adsId, (System.currentTimeMillis() - t1));
    }

    private void doAddressDataInsert(Connection conn, NtisAdrPatLrsDAO dao) throws Exception {
        final String STMT = """
                INSERT INTO ntis.ntis_adr_addresses (ad_address, ad_address_search, ad_apl_id, ad_ads_id, ad_coordinate_latitude, ad_coordinate_longitude, ad_geom)
                (SELECT case when d.str_name is not null then d.str_name || ' ' || d.str_type_abbreviation || ' '
                         || b.ads_nr
                         || case when e.apl_pat_nr is not null then ' - ' || e.apl_pat_nr else '' end
                         || case when c.re_name_k is not null then  ', '||c.re_name_k || ' ' || c.re_type_abbreviation else '' end
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       else
                         c.re_name_k || ' ' || c.re_type_abbreviation || ', '  || b.ads_nr
                         || case when e.apl_pat_nr is not null then ' - ' || e.apl_pat_nr else '' end
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       end ad_address,
                       translate (lower(case when d.str_name is not null then d.str_name || ' ' || d.str_type_abbreviation || ' '
                         || b.ads_nr
                         || case when e.apl_pat_nr is not null then ' - ' || e.apl_pat_nr else '' end
                         || case when c.re_name_k is not null then  ', '||c.re_name_k || ' ' || c.re_type_abbreviation else '' end
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       else
                         c.re_name_k || ' ' || c.re_type_abbreviation || ', '  || b.ads_nr
                         || case when e.apl_pat_nr is not null then ' - ' || e.apl_pat_nr else '' end
                         || case when c.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                         ||REPLACE(m.rfc_meaning,'rajono','raj.') || ' sav.'
                       end), 'ąčęėįšųūž','aceeisuuz') ad_address_search,
                 e.apl_id, b.ads_id,
                 b.ads_coordinate_latitude,
                 b.ads_coordinate_longitude,
                 public.st_setsrid(public.st_makepoint(b.ads_coordinate_latitude, b.ads_coordinate_longitude), 3346)
                FROM ntis.ntis_adr_stats b
                JOIN ntis.ntis_adr_pat_lrs e on e.apl_aob_code = b.ads_aob_code
                JOIN ntis.ntis_adr_residences c on c.re_id = b.ads_re_id and now() between c.re_date_from and coalesce(c.re_date_to, now())
                LEFT JOIN ntis.ntis_adr_streets d on d.str_id = b.ads_str_id and now() between d.str_date_from and coalesce(d.str_date_to, now())
                LEFT JOIN ntis.ntis_adr_seniunijos sen on sen.sen_code = c.re_sen_code :: text and now() between sen.sen_date_from and coalesce(sen.sen_date_to, now())
                join spark.spr_ref_codes m on m.rfc_code = (c.re_municipality_code :: text) and rfc_domain = 'NTIS_MUNICIPALITIES'

                where e.apl_id = ?
                and now() between b.ads_date_from and coalesce(b.ads_date_to, now())
                );""";

        Double aplId = dao.getApl_id();
        log.info("Inserting new addresses with patalpos aplId {}.", aplId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT)) {
            pstmt.setDouble(1, dao.getApl_id());
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Inserted {} addresses with patalpos aplId {} in {} ms", cnt, aplId, (System.currentTimeMillis() - t1));
    }

    private static final String UPDATE_WITHOUT_PATALPA_TEMPLATE = """
            update ntis.ntis_adr_addresses ad
            set ad_address = (case when str.str_name is not null then str.str_name || ' ' || str.str_type_abbreviation || ' '
                     || ads.ads_nr
                     || case when re.re_name_k is not null then  ', '||re.re_name_k || ' ' || re.re_type_abbreviation else '' end
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   else
                     re.re_name_k || ' ' || re.re_type_abbreviation || ', '  || ads.ads_nr
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   end),
            ad_address_search = (translate (lower(case when str.str_name is not null then str.str_name || ' ' || str.str_type_abbreviation || ' '
                     || ads.ads_nr
                     || case when re.re_name_k is not null then  ', '||re.re_name_k || ' ' || re.re_type_abbreviation else '' end
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   else
                     re.re_name_k || ' ' || re.re_type_abbreviation || ', '  || ads.ads_nr
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   end), 'ąčęėįšųūž','aceeisuuz')),
            ad_coordinate_latitude = ads.ads_coordinate_latitude,
            ad_coordinate_longitude = ads.ads_coordinate_longitude

            from ntis.ntis_adr_stats ads
            join ntis.ntis_adr_residences re on ads.ads_residence_code = re.re_recidence_code and now() between re.re_date_from and coalesce(re.re_date_to, now())
            left join ntis.ntis_adr_streets str on ads.ads_street_code = str.str_street_code and now() between str.str_date_from and coalesce(str.str_date_to, now())
            left join ntis.ntis_adr_seniunijos sen on sen.sen_code = re.re_sen_code :: text and now() between sen.sen_date_from and coalesce(sen.sen_date_to, now())
            join spark.spr_ref_codes rfc on rfc.rfc_code = (re.re_municipality_code :: text) and rfc.rfc_domain = 'NTIS_MUNICIPALITIES'
            where ad.ad_apl_id is null
            and ads.ads_id = ad.ad_ads_id

            <<CUSTOM_CONDITION>>

            and now() between ads.ads_date_from and coalesce(ads.ads_date_to, now())
            """;

    private static final String UPDATE_WITH_PATALPA_TEMPLATE = """
            update ntis.ntis_adr_addresses ad
            set ad_address = (case when str.str_name is not null then str.str_name || ' ' || str.str_type_abbreviation || ' '
                     || ads.ads_nr
                     || case when apl.apl_pat_nr is not null then ' - ' || apl.apl_pat_nr else '' end
                     || case when re.re_name_k is not null then  ', '||re.re_name_k || ' ' || re.re_type_abbreviation else '' end
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   else
                     re.re_name_k || ' ' || re.re_type_abbreviation || ', '  || ads.ads_nr
                     || case when apl.apl_pat_nr is not null then ' - ' || apl.apl_pat_nr else '' end
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   end),
            ad_address_search = (translate (lower(case when str.str_name is not null then str.str_name || ' ' || str.str_type_abbreviation || ' '
                     || ads.ads_nr
                     || case when apl.apl_pat_nr is not null then ' - ' || apl.apl_pat_nr else '' end
                     || case when re.re_name_k is not null then  ', '||re.re_name_k || ' ' || re.re_type_abbreviation else '' end
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   else
                     re.re_name_k || ' ' || re.re_type_abbreviation || ', '  || ads.ads_nr
                     || case when apl.apl_pat_nr is not null then ' - ' || apl.apl_pat_nr else '' end
                     || case when re.re_sen_code is not null then ', '||sen.sen_name ||' sen., ' else ' ' end
                     ||REPLACE(rfc.rfc_meaning,'rajono','raj.') || ' sav.'
                   end), 'ąčęėįšųūž','aceeisuuz')),
            ad_coordinate_latitude = ads.ads_coordinate_latitude,
            ad_coordinate_longitude = ads.ads_coordinate_longitude

            from ntis.ntis_adr_stats ads
            join ntis.ntis_adr_pat_lrs apl on ads.ads_id = apl.apl_ads_id and now() between apl.apl_date_from and coalesce(apl.apl_date_to, now())
            join ntis.ntis_adr_residences re on ads.ads_residence_code = re.re_recidence_code and now() between re.re_date_from and coalesce(re.re_date_to, now())
            left join ntis.ntis_adr_streets str on ads.ads_street_code = str.str_street_code and now() between str.str_date_from and coalesce(str.str_date_to, now())
            left join ntis.ntis_adr_seniunijos sen on sen.sen_code = re.re_sen_code:: text and now() between sen.sen_date_from and coalesce(sen.sen_date_to, now())
            join spark.spr_ref_codes rfc on rfc.rfc_code = (re.re_municipality_code :: text) and rfc.rfc_domain = 'NTIS_MUNICIPALITIES'
            where apl.apl_id = ad.ad_apl_id
            and ads.ads_id = ad.ad_ads_id

            <<CUSTOM_CONDITION>>

            and now() between ads.ads_date_from and coalesce(ads.ads_date_to, now())
            """;

    private static final String CUSTOM_CONDITION = "<<CUSTOM_CONDITION>>";

    private void doAddressDataUpdate(Connection conn, NtisAdrSeniunijosDAO dao) throws Exception {
        final String STMT_ONE = UPDATE_WITHOUT_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and sen.sen_id = ?");
        final String STMT_TWO = UPDATE_WITH_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and sen.sen_id = ?");

        Double senId = dao.getSen_id();
        log.info("Updating addresses using seniunija senId {} and patalpa == null.", senId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_ONE)) {
            pstmt.setDouble(1, senId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using seniunija senId {} and patalpa == null in {} ms", cnt, senId, (System.currentTimeMillis() - t1));

        log.info("Updating addresses using seniunija senId {} and patalpa != null.", senId);
        t1 = System.currentTimeMillis();
        cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_TWO)) {
            pstmt.setDouble(1, senId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using seniunija senId {} and patalpa != null in {} ms", cnt, senId, (System.currentTimeMillis() - t1));
    }

    private void doAddressDataUpdate(Connection conn, NtisAdrResidencesDAO dao) throws Exception {
        final String STMT_ONE = UPDATE_WITHOUT_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and re.re_id = ?");
        final String STMT_TWO = UPDATE_WITH_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and re.re_id = ?");

        Double reId = dao.getRe_id();
        log.info("Updating addresses using residence reId {} and patalpa == null.", reId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_ONE)) {
            pstmt.setDouble(1, reId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using residence reId {} and patalpa == null in {} ms", cnt, reId, (System.currentTimeMillis() - t1));

        log.info("Updating addresses using residence reId {} and patalpa != null.", reId);
        t1 = System.currentTimeMillis();
        cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_TWO)) {
            pstmt.setDouble(1, reId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using residence reId {} and patalpa != null in {} ms", cnt, reId, (System.currentTimeMillis() - t1));
    }

    private void doAddressDataUpdate(Connection conn, NtisAdrStreetsDAO dao) throws Exception {
        final String STMT_ONE = UPDATE_WITHOUT_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and str.str_id = ?");
        final String STMT_TWO = UPDATE_WITH_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and str.str_id = ?");

        Double strId = dao.getStr_id();
        log.info("Updating addresses using street strId {} and patalpa == null.", strId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_ONE)) {
            pstmt.setDouble(1, strId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using street strId {} and patalpa == null in {} ms", cnt, strId, (System.currentTimeMillis() - t1));

        log.info("Updating addresses using street strId {} and patalpa != null.", strId);
        t1 = System.currentTimeMillis();
        cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_TWO)) {
            pstmt.setDouble(1, strId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using street strId {} and patalpa != null in {} ms", cnt, strId, (System.currentTimeMillis() - t1));
    }

    private void doAddressDataUpdate(Connection conn, NtisAdrStatsDAO dao) throws Exception {
        final String STMT_ONE = UPDATE_WITHOUT_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and ads.ads_id = ?");
        final String STMT_TWO = UPDATE_WITH_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and ads.ads_id = ?");

        Double adsId = dao.getAds_id();
        log.info("Updating addresses using adreso adsId {} and patalpa == null.", adsId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_ONE)) {
            pstmt.setDouble(1, adsId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using adreso adsId {} and patalpa == null in {} ms", cnt, adsId, (System.currentTimeMillis() - t1));

        log.info("Updating addresses using adreso adsId {} and patalpa != null.", adsId);
        t1 = System.currentTimeMillis();
        cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_TWO)) {
            pstmt.setDouble(1, adsId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using adreso adsId {} and patalpa != null in {} ms", cnt, adsId, (System.currentTimeMillis() - t1));
    }

    private void doAddressDataUpdate(Connection conn, NtisAdrPatLrsDAO dao) throws Exception {
        final String STMT_TWO = UPDATE_WITH_PATALPA_TEMPLATE.replace(CUSTOM_CONDITION, "and apl.apl_id = ?");

        Double aplId = dao.getApl_id();
        log.info("Updating addresses using patalpos aplId {}.", aplId);
        long t1 = System.currentTimeMillis();
        int cnt = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(STMT_TWO)) {
            pstmt.setDouble(1, aplId);
            pstmt.execute();
            cnt = pstmt.getUpdateCount();
        }
        log.info("Updated {} addresses using patalpos aplId {} in {} ms", cnt, aplId, (System.currentTimeMillis() - t1));
    }

}
