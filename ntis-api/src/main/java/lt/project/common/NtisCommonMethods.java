package lt.project.common;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.admin.dao.SprMenuStructuresDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprCacheManager;
import eu.itreegroup.spark.modules.admin.service.SprMenuStructuresDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
import lt.project.ntis.dao.NtisAdrResidencesDAO;
import lt.project.ntis.dao.NtisAdrStreetsDAO;
import lt.project.ntis.dao.NtisReviewsDAO;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.NtisCwDataList;
import lt.project.ntis.logic.forms.NtisReviewCreatePage;
import lt.project.ntis.logic.forms.model.NtisAddrSearchRequest;
import lt.project.ntis.logic.forms.model.NtisAddrSearchResult;
import lt.project.ntis.logic.forms.model.NtisMstAdditionalText;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.service.NtisAdrMappingsDBService;
import lt.project.ntis.service.NtisReviewsDBService;

/**
 * Klasė skirta bendrai naudojamiems komponentų metodams
 */

@Component
public class NtisCommonMethods {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    NtisAdrMappingsDBService ntisAdrMappingsDBService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprMenuStructuresDBService sprMenuStructuresDBService;

    @Autowired
    SprCacheManager sprCacheManager;

    @Autowired
    NtisReviewsDBService ntisReviewsDBService;

    public NtisReviewsDAO getReviewInfo(Connection conn, String id, Double usrId) throws NumberFormatException, Exception {
        NtisReviewsDAO ntisReviewsDAO = null;
        ntisReviewsDAO = ntisReviewsDBService.loadRecord(conn, Utils.getDouble(id));
        if (ntisReviewsDAO != null && ntisReviewsDAO.getRev_usr_id() != null && usrId.compareTo(ntisReviewsDAO.getRev_usr_id()) == 0) {
            return ntisReviewsDAO;
        } else {
            throw new SparkBusinessException(new S2Message("common.error.action_not_granted", SparkMessageType.ERROR));
        }
    }

    public String getAdditionalMstText(Connection conn, String mstId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select mst_id,
                       c01 as mst_tooltip
                  from spr_menu_structures
                       where mst_id = ?::int
                """);
        stmt.addSelectParam(Utils.getDouble(mstId));
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    public void saveAdditionalMstText(Connection conn, NtisMstAdditionalText txt) throws Exception {
        if (txt.getMst_id() != null) {
            SprMenuStructuresDAO mst = sprMenuStructuresDBService.loadRecord(conn, Utils.getDouble(txt.getMst_id()));
            mst.setC01(txt.getMst_tooltip());
            sprMenuStructuresDBService.saveRecord(conn, mst);
            sprCacheManager.clearCache(conn, "userMenu");
        }

    }

    /**
     * Metodas grąžins nuotekų tvarkymo įrenginių sąrašą pagal nurodytą filtro parametrą
     * @param conn - prisijungimas prie DB
     * @param foreignKeyParams - filtravimo parametras
     * @param lang - kalba, naudojama vertimams
     * @return JSON objektas
     * @throws Exception
     */
    public String findFacilities(Connection conn, ForeignKeyParams foreignKeyParam) throws Exception {
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*");
        Boolean isAddress = pattern.matcher(foreignKeyParam.getFilterValue()).matches();
        StatementAndParams stmt = new StatementAndParams();
        if (isAddress) {
            boolean searchLt = false;

            stmt.setStatement("""
                    SELECT WTF.wtf_id,
                           WAV.address_id,
                           WAV.full_address_text AS address
                    FROM ntis_wastewater_treatment_faci WTF
                    LEFT JOIN ntis_address_vw WAV ON WTF.wtf_ad_id = WAV.address_id
                                        """);

            List<String> ltCharsArr = new ArrayList<String>();
            ltCharsArr.add("ą");
            ltCharsArr.add("č");
            ltCharsArr.add("ę");
            ltCharsArr.add("ė");
            ltCharsArr.add("į");
            ltCharsArr.add("š");
            ltCharsArr.add("ų");
            ltCharsArr.add("ū");
            ltCharsArr.add("ž");

            for (String ltChar : ltCharsArr) {
                if (foreignKeyParam.getFilterValue().toLowerCase().contains(ltChar)) {
                    searchLt = true;
                }
            }

            if (searchLt) {
                stmt.addParam4WherePart(" lower(WAV.full_address_text) like '%'||lower(?)||'%' ", foreignKeyParam.getFilterValue());
            } else {
                stmt.addParam4WherePart(" WAV.ad_address_search like '%'||translate(lower(?), 'ąčęėįšųūž','aceeisuuz')||'%' ",
                        foreignKeyParam.getFilterValue());
            }
        } else {
            String[] coordinates = foreignKeyParam.getFilterValue().split(", ");
            String radiusLength = dbPropertyManager.getPropertyByName("COORDINATE_RADIUS_RANGE", "50");
            stmt.setStatement("""
                        SELECT WTF.wtf_id,
                               WAV.address_id AS ad_id,
                               CASE
                                 WHEN WAV.address_id IS NULL
                                 THEN MN.mp_name || ' (' || WTF.wtf_facility_latitude || ', ' || WTF.wtf_facility_longitude || ')'
                                 ELSE WAV.full_address_text
                               END AS address
                        FROM ntis.ntis_wastewater_treatment_faci WTF
                        JOIN ntis.ntis_municipalities MN ON MN.mp_code = WTF.wtf_facility_municipality_code
                        LEFT JOIN ntis.ntis_address_vw WAV ON WTF.wtf_ad_id = WAV.address_id
                        WHERE ST_DWithin(wtf_facility_geom, ST_SetSRID(ST_MakePoint(?,?), 3346), ?)
                    """);
            stmt.addSelectParam(Utils.getDouble(coordinates[0]));
            stmt.addSelectParam(Utils.getDouble(coordinates[1]));
            stmt.addSelectParam(Utils.getDouble(radiusLength));
        }
        stmt.setStatementOrderPart(dbStatementManager.colNamesToConcatString("WAV.municipality", "WAV.residence", "WAV.street", "WTF.wtf_facility_latitude",
                "WTF.wtf_facility_longitude"));
        stmt.setRecordCountLimitPart(dbStatementManager.getRecordLimitString(foreignKeyParam.getRecordCount()));
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins NtisWtfInfo objektą pagal nurodytą įrenginio ID
     * @param conn - prisijungimas prie DB
     * @param lang - kalba, naudojama vertimams
     * @param adId - adreso ID
     * @return NtisWtfInfo objektas
     * @throws Exception
     */
    public NtisWtfInfo getWtfInfo(Connection conn, String lang, Double wtfId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select wtf.wtf_id as id,
                       case when wav.address_id is null
                         then mn.mp_name || ' (' || wtf.wtf_facility_latitude || ', ' || wtf.wtf_facility_longitude || ')'
                         else wav.full_address_text
                       end as address,
                       coalesce(typ.rft_display_code, typ.rfc_meaning) as type,
                       wtf.wtf_technical_passport_id as technicalPassport,
                       coalesce(mnf.rft_display_code, mnf.rfc_meaning) as manufacturer,
                       coalesce(mdl.rft_display_code, mdl.rfc_meaning) as model,
                       to_char(wtf.wtf_installation_date, '%s') as installationDate,
                       wtf.wtf_type as typeClsf,
                       wtf.wtf_distance as distance,
                       coalesce(cap.rft_display_code, cap.rfc_meaning) capacity
                from ntis_municipalities mn, ntis_wastewater_treatment_faci wtf
                left join ntis_address_vw wav on wtf.wtf_ad_id = wav.address_id
                left join spr_ref_codes_vw typ on typ.rfc_code = wtf.wtf_type and typ.rfc_domain = 'NTIS_WTF_TYPE' and typ.rft_lang = ?
                left join spr_ref_codes_vw mnf on mnf.rfc_code = wtf.wtf_manufacturer and mnf.rfc_domain = 'NTIS_FACIL_MANUFA' and mnf.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw mdl on mdl.rfc_code = wtf.wtf_model and mdl.rfc_domain = 'NTIS_FACIL_MODEL' and mdl.rft_lang = typ.rft_lang
                left join spr_ref_codes_vw cap on cap.rfc_code = wtf.wtf_capacity and cap.rfc_domain = 'NTIS_FACIL_CAPACITY' and cap.rft_lang = typ.rft_lang
                where st_contains(mn.mp_geom, wtf.wtf_facility_geom) = 't' and wtf.wtf_id = ?::int
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wtfId);
        List<NtisWtfInfo> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWtfInfo.class);
        if (queryResult != null && !queryResult.isEmpty()) {
            return queryResult.get(0);
        } else {
            throw new Exception("No information was found");
        }
    }

    /**
     * Metodas grąžins nuotekų tvarkymo įrenginių sąrašą pagal nurodytus parametrus
     * @param conn - prisijungimas prie DB
     * @param params - paieškos parametrai
     * @return NtisWtfSearchResult objektų masyvas
     * @throws Exception
     */
    public List<NtisAddrSearchResult> onDetailedFaciSearch(Connection conn, NtisAddrSearchRequest params) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        if (params.getLatitude() == null && params.getLongitude() == null) {
            stmt.setStatement("""
                            SELECT WTF.wtf_id,
                                   WAV.address_id AS ad_id,
                                   WAV.full_address_text AS address
                            FROM ntis.ntis_wastewater_treatment_faci WTF
                            JOIN ntis.ntis_address_vw WAV ON WTF.wtf_ad_id = WAV.address_id
                    """);
            stmt.addParam4WherePart("WAV.municipality_code = ? ", params.getMunicipalityCode());
            stmt.addParam4WherePart("WAV.residence_code = ? ", params.getResidenceCode());
            stmt.addParam4WherePart("WAV.street_code = ? ", params.getStreetCode());
            stmt.addParam4WherePart("WAV.building_no = ? ", params.getHouseNo());
            stmt.addParam4WherePart("WAV.flat_no = ? ", params.getFlatNo());
        } else {
            String radiusLength = dbPropertyManager.getPropertyByName("COORDINATE_RADIUS_RANGE", "50");
            stmt.setStatement("""
                        SELECT WTF.wtf_id,
                               WAV.address_id AS ad_id,
                               CASE
                                 WHEN WAV.address_id IS NULL
                                 THEN MN.mp_name || ' (' || WTF.wtf_facility_latitude || ', ' || WTF.wtf_facility_longitude || ')'
                                 ELSE WAV.full_address_text
                               END AS address
                        FROM ntis.ntis_wastewater_treatment_faci WTF
                        JOIN ntis.ntis_municipalities MN ON MN.mp_code = WTF.wtf_facility_municipality_code
                        LEFT JOIN ntis.ntis_address_vw WAV ON WTF.wtf_ad_id = WAV.address_id
                        WHERE ST_DWithin(wtf_facility_geom, ST_SetSRID(ST_MakePoint(?,?), 3346), ?)
                    """);
            stmt.addSelectParam(params.getLatitude());
            stmt.addSelectParam(params.getLongitude());
            stmt.addSelectParam(Utils.getDouble(radiusLength));
        }
        List<NtisAddrSearchResult> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisAddrSearchResult.class);
        return queryResult;
    }

    /**
     * Metodas grąžins gyvenviečių, esančių registre, sąrašą pagal nurodytą savivaldybės kodą
     * @param municipalityCode - savivaldybės kodas
     * @param conn - prisijungimas prie DB
     * @return NtisAdrResidencesDAO objektų masyvas
     * @throws Exception
     */
    public List<NtisAdrResidencesDAO> getResidencesList(Connection conn, Double municipalityCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select re_recidence_code,
                       re_municipality_code,
                       CASE
                           WHEN re_type_abbreviation = 'm.'
                           THEN re_name
                           ELSE re_name_k ||' '|| re_type_abbreviation
                       END AS re_name
                FROM ntis_adr_residences
                WHERE re_municipality_code = ?
                ORDER BY re_name
                """);
        stmt.addSelectParam(municipalityCode);
        List<NtisAdrResidencesDAO> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisAdrResidencesDAO.class);
        return queryResult;
    }

    /**
     * Metodas grąžins gatvių, esančių registre, sąrašą pagal nurodytą gyvenvietės kodą
     * @param residenceCode - gyvenvietės kodas
     * @param conn - prisijungimas prie DB
     * @return NtisAdrStreetsDAO objektų masyvas
     * @throws Exception
     */
    public List<NtisAdrStreetsDAO> getStreetsList(Connection conn, Double residenceCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select str_street_code,
                       str_residence_code,
                       str_name ||' '|| str_type_abbreviation AS str_name
                from ntis_adr_streets
                WHERE str_residence_code = ?
                ORDER BY str_name
                """);
        stmt.addSelectParam(residenceCode);
        List<NtisAdrStreetsDAO> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisAdrStreetsDAO.class);
        return queryResult;
    }

    /**
     * Metodas grąžins įmonių sąrašą pagal nurodytą filtro parametrą
     * @param conn - prisijungimas prie DB
     * @param foreignKeyParams - filtravimo parametras
     * @return JSON objektas
     * @throws Exception
     */
    public String getNtisTypeOrgs(Connection conn, ForeignKeyParams foreignKeyParam) throws Exception {
        // TODO JAR interfeiso sinchronizacija
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT org_id,
                       org_name,
                       org_code
                FROM spr_organizations org
                WHERE upper(org_name||' '||org_code) like '%'||upper(?)||'%'
                                    """);
        stmt.addSelectParam(foreignKeyParam.getFilterValue());
        stmt.setStatementOrderPart("org_name");
        stmt.setRecordCountLimitPart(dbStatementManager.getRecordLimitString(foreignKeyParam.getRecordCount()));
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Pagal perduodamus parametrus, metodas prie statement pridės where sąlygą nuotekų tvarkymo įrenginio savininko patikrinimui
     * @param stmt - statement
     * @param orgId - sesijos org_id
     * @param perId - sesijos per_id
     * @throws Exception
     */
    public void addConditionForOwner(StatementAndParams stmt, Double orgId, Double perId, Double usrId) throws Exception {
        if (orgId != null) {
            stmt.addParam4WherePart(
                    """
                                                (ord.ord_org_id = ?::int or (ord.ord_org_id is null and exists (select 1
                                                from ntis_facility_owners fo
                                          inner join spr_org_users ou on ou.ou_org_id = fo.fo_org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                                               where ord.ord_wtf_id = fo.fo_wtf_id
                                                 and fo_org_id = ?::int
                                                 and fo_owner_type in ('OWNER','MANAGER')
                                                 and current_date between fo_date_from and COALESCE(fo_date_to, current_date))))
                            """,
                    new SelectParamValue(orgId), new SelectParamValue(usrId), new SelectParamValue(orgId));
        } else {
            stmt.addParam4WherePart("""
                                        (ord.ord_per_id = ?::int or (ord.ord_per_id is null and exists (select 1
                                        from ntis_facility_owners fo
                                       where ord.ord_wtf_id = fo.fo_wtf_id
                                         and fo_per_id = ?::int
                                         and fo_owner_type in ('OWNER','MANAGER')
                                         and current_date between fo_date_from and COALESCE(fo_date_to, current_date))))
                    """, new SelectParamValue(perId), new SelectParamValue(perId));
        }
    }

    /**
     * Pagal perduodamus parametrus, metodas grąžins nuotekų tvarkymo įrenginių, priklausančių naudotojui, sąrašą
     * @param conn - prisijungimas prie db
     * @param orgId - sesijos org_id
     * @param perId - sesijos per_id
     * @param lang - sesijos naudotojo kalba
     * @throws Exception
     */
    public String getWtfList(Connection conn, Double perId, Double orgId, String lang) throws Exception {
        String statement = null;
        if (orgId != null) {
            statement = """
                    with wtf_assigned as (
                               select o.fo_wtf_id wtf_id
                               from ntis_facility_owners o
                               where o.fo_org_id = ?::int
                               and %s
                               group by o.fo_wtf_id)
                    """.formatted(dbStatementManager.getPeriodValidationForCurrentDateStr("fo_date_from", "fo_date_to", false));
        } else {
            statement = """
                    with wtf_assigned as (
                               select o.fo_wtf_id wtf_id
                               from ntis_facility_owners o
                               where o.fo_per_id = ?::int
                               and %s
                               group by o.fo_wtf_id
                               )
                    """.formatted(dbStatementManager.getPeriodValidationForCurrentDateStr("fo_date_from", "fo_date_to", false));
        }
        statement = statement + " SELECT WTF.WTF_ID AS VALUE, " + //
                "CASE WHEN WTF.WTF_AD_ID IS NULL THEN " + //
                "WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ' - ' || COALESCE(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) " + //
                "ELSE  WAV.FULL_ADDRESS_TEXT || ' - '|| COALESCE(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) " + //
                "END AS OPTION " + //
                "FROM NTIS_WASTEWATER_TREATMENT_FACI WTF " + //
                "LEFT JOIN NTIS_ADDRESS_VW WAV ON WAV.ADDRESS_ID = WTF.WTF_AD_ID " + //
                "INNER JOIN WTF_ASSIGNED ASSIGNED ON ASSIGNED.WTF_ID = WTF.WTF_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ? ";
        StatementAndParams stmt = new StatementAndParams(statement);
        stmt.addSelectParam(orgId != null ? orgId : perId);
        stmt.addSelectParam(lang);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins seniūnijų, esančių registre, sąrašą 
     * @param conn - prisijungimas prie DB
     * @return json objektas
     * @throws Exception
     */
    public String getSenList(Connection conn) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   sen_id as id,
                   sen_name as value
                from ntis_adr_seniunijos
                 """);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins seniūnijų pagal savivaldybės kodą sąrašą
     * @param conn - prisijungimas prie DB
     * @return json objektas
     * @throws Exception
     */
    public String getSenList(Connection conn, String mnCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   sen_id as id,
                   sen_name as value
                from ntis_adr_seniunijos
                where sen_municipality_code = ?
                 """);
        stmt.addSelectParam(mnCode);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins vietovių pagal savivaldybės kodą sąrašą
     * @param conn - prisijungimas prie DB
     * @return json objektas
     * @throws Exception
     */
    public String getResMuniList(Connection conn, String mnCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   re_id as id,
                   re_name_k || ' ' || re_type as value
                from ntis_adr_residences
                where re_municipality_code::text = ?
                 """);
        stmt.addSelectParam(mnCode);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins vietovių pagal seniūnijos id sąrašą
     * @param conn - prisijungimas prie DB
     * @param snId - nuoroda į seniūnijos įrašą
     * @return json objektas
     * @throws Exception
     */
    public String getResSenList(Connection conn, String snId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   re_id as id,
                   re_name_k || ' ' || re_type as value
                from ntis_adr_residences
                inner join ntis_adr_seniunijos on re_sen_code = sen_code::numeric
                where sen_id =  ?::int
                 """);
        stmt.addSelectParam(Utils.getDouble(snId));
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas grąžins gatvių pagal vietovės id sąrašą
     * @param conn - prisijungimas prie DB
     * @param snId - nuoroda į vietovės įrašą
     * @return json objektas
     * @throws Exception
     */
    public String getStreetResList(Connection conn, String reId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   str_id as id,
                   str_name || ' ' || str_type as value
                from ntis_adr_streets
                inner join ntis_adr_residences on str_residence_code = re_recidence_code
                where re_id = ? ::int
                 """);
        stmt.addSelectParam(Utils.getDouble(reId));
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas sistemoje registruotų vandentvarkos įmonių sąrašą 
     * @param conn - prisijungimas prie DB
     * @return json objektas
     * @throws Exception
     */
    public String getOrganizations(Connection conn) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   org_id as id,
                   org_name as value
                from spr_organizations
                    where c01 in ('VANDEN', 'PASLAUG_VANDEN', 'PASLAUG')
                 """);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    /**
     * Metodas patikrins ar prisijungusio naudotojo asmens įrašas turi įvestą elektroninio pašto adresą
     * @param conn - prisijungimas prie DB
     * @param usrId - sesijos naudotojo id
     * @return boolean
     * @throws Exception
     */
    public Boolean checkIfPersonHasEmail(Connection conn, Double usrId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select per_id,
                       per_email
                from spr_persons
                join spr_users on usr_per_id = per_id
                and usr_id = ?::int
                """);
        stmt.addSelectParam(usrId);
        List<SprPersonsDAO> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprPersonsDAO.class);
        return (queryResult != null && !queryResult.isEmpty() && queryResult.get(0).getPer_email() != null
                && !queryResult.get(0).getPer_email().trim().isEmpty());
    }

    public void deleteAdrMapping(Connection conn, Double id, Double orgId) throws NumberFormatException, Exception {
        if (orgId != null) {
            boolean isWaterManager = false;
            SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, orgId);
            if (orgDao != null && orgDao.getOrgType() != null
                    && (orgDao.getOrgType().equals(NtisOrgType.VANDEN.getCode()) || orgDao.getOrgType().equals(NtisOrgType.PASLAUG_VANDEN.getCode()))) {
                isWaterManager = true;
            }
            if (isWaterManager) {
                NtisAdrMappingsDAO mapRecord = ntisAdrMappingsDBService.loadRecord(conn, id);
                if (mapRecord != null && mapRecord.getAm_org_id() != null && mapRecord.getAm_org_id().compareTo(orgId) != 0) {
                    throw new SparkBusinessException(
                            new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
                }
            }
        }
        ntisAdrMappingsDBService.deleteRecord(conn, id);
    }

    public NtisAdrMappingsDAO saveAdrMapping(Connection conn, NtisAdrMappingsDAO record, Double orgId) throws Exception {
        boolean isWaterManager = false;
        if (orgId != null) {
            SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, orgId);
            if (orgDao != null && orgDao.getOrgType() != null
                    && (orgDao.getOrgType().equals(NtisOrgType.VANDEN.getCode()) || orgDao.getOrgType().equals(NtisOrgType.PASLAUG_VANDEN.getCode()))) {
                isWaterManager = true;
            }
            if (record.getAm_org_id() == null && isWaterManager) {
                record.setAm_org_id(orgId);
            }
            if (isWaterManager && record.getAm_org_id() != null && record.getAm_org_id().compareTo(orgId) != 0) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        return ntisAdrMappingsDBService.saveRecord(conn, record);
    }

    public NtisAdrMappingsDAO getAdrMapping(Connection conn, RecordIdentifier recordIdentifier, Double orgId) throws NumberFormatException, Exception {
        NtisAdrMappingsDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            answerObj = ntisAdrMappingsDBService.newRecord();
        } else {
            boolean isWaterManager = false;
            if (orgId != null) {
                SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, orgId);
                if (orgDao != null && orgDao.getOrgType() != null
                        && (orgDao.getOrgType().equals(NtisOrgType.VANDEN.getCode()) || orgDao.getOrgType().equals(NtisOrgType.PASLAUG_VANDEN.getCode()))) {
                    isWaterManager = true;
                }
            }

            answerObj = ntisAdrMappingsDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
            if (answerObj != null && isWaterManager && answerObj.getAm_org_id() != null && answerObj.getAm_org_id().compareTo(orgId) != 0) {
                throw new SparkBusinessException(
                        new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
            }
        }
        return answerObj;
    }

    public StatementAndParams getAddressMappings(Connection conn, SelectRequestParams params, Double orgId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select
                   am_id as am_id,
                   am_address_type as am_address_type_code,
                   coalesce(adt.rfc_meaning, am_address_type) as am_address_type,
                   am_provided_addres_name,
                   am_municipality_code,
                   coalesce(mn.rfc_meaning, am_municipality_code) as am_municipality_name,
                   am_org_id,
                   org_name,
                   am_str_id,
                   str_name || ' ' || str_type as str_name,
                   am_sen_id,
                   sen_name,
                   am_re_id,
                   re_name_k || ' ' || re_type as re_name
                from ntis_adr_mappings
                left join spr_ref_codes_vw adt on adt.rfc_code = am_address_type and adt.rfc_domain = 'NTIS_ADDRESS_MAP_TYPE' and adt.rft_lang = ?
                left join spr_ref_codes_vw mn on mn.rfc_code = am_municipality_code and mn.rfc_domain = 'NTIS_MUNICIPALITIES' and mn.rft_lang = adt.rft_lang
                left join ntis_adr_seniunijos on sen_id = am_sen_id
                left join ntis_adr_streets on str_id = am_str_id
                left join ntis_adr_residences on am_re_id = re_id
                left join spr_organizations on am_org_id = org_id
                 """);
        stmt.addSelectParam(lang);

        if (orgId != null) {
            stmt.addParam4WherePart("(am_org_id = ?::int or am_org_id is null)", orgId);
            stmt.setStatementOrderPart("order by org_id nulls last");
        } else if (params.getParamList() != null && Utils.getDouble(params.getParamList().get("orgId")) != null) {
            stmt.addParam4WherePart("am_org_id = ?::int or am_org_id is null", Utils.getDouble(params.getParamList().get("orgId")));
            stmt.setStatementOrderPart("order by org_id nulls last");
        } else {
            stmt.setStatementOrderPart("order by org_id nulls first");
        }

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart("am_address_type", StatementAndParams.PARAM_STRING, advancedParamList.get("addressTypeCode"));
        stmt.addParam4WherePart("am_provided_addres_name", StatementAndParams.PARAM_STRING, advancedParamList.get("providedAddressName"));
        stmt.addParam4WherePart("am_municipality_code", StatementAndParams.PARAM_STRING, advancedParamList.get("municipalityCode"));
        stmt.addParam4WherePart("am_sen_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("senName"));
        stmt.addParam4WherePart("org_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("orgName"));
        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("adt.rfc_meaning", "am_provided_addres_name", "mn.rfc_meaning", "sen_name",
                "str_name", "re_name", "re_name_k", "org_name"), StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        return stmt;
    }

    public String getSelectedWtfInfo(Connection conn, String lang, Double wtfId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                     select wtf.wtf_id as id,
                         case when wtf.wtf_ad_id is null
                              then wtf.wtf_facility_latitude || ', '|| wtf.wtf_facility_longitude || ' - ' || coalesce(rfc.rfc_meaning, wtf.wtf_type)
                              else adv.full_address_text || ' - ' || coalesce(rfc.rfc_meaning, wtf.wtf_type)
                              end as value
                     from ntis_wastewater_treatment_faci wtf
                left join ntis_address_vw adv on adv.address_id = wtf.wtf_ad_id
                left join spr_ref_codes_vw rfc on rfc.rfc_code = wtf.wtf_type and rfc.rfc_domain = 'NTIS_WTF_TYPE' and rfc.rft_lang = ?
                    where wtf.wtf_id = ?::int
                    """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wtfId);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt);
    }

    public void deleteIndividualViisp(Connection conn, Double usrId, Double orgId) throws Exception {
        StatementAndParams stmtUr = new StatementAndParams("""
                delete from spr_user_roles
                where uro_usr_id = ?::int
                """);
        stmtUr.addSelectParam(usrId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmtUr);

        StatementAndParams stmtOur = new StatementAndParams("""
                delete from spr_org_user_roles
                where our_ou_id in
                    (select ou_id
                       from spr_org_users
                      where ou_org_id = ?::int and ou_usr_id = ?::int)
                """);
        stmtOur.addSelectParam(orgId);
        stmtOur.addSelectParam(usrId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmtOur);

        StatementAndParams stmtOar = new StatementAndParams("""
                delete from spr_org_available_roles
                where oar_org_id = ?::int
                """);
        stmtOar.addSelectParam(orgId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmtOar);

        StatementAndParams stmt = new StatementAndParams("""
                delete from spr_org_users
                where ou_org_id = ?::int and ou_usr_id = ?::int
                """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

        StatementAndParams stmtUsr = new StatementAndParams("""
                delete from spr_users
                where usr_id = ?::int
                """);
        stmtUsr.addSelectParam(usrId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmtUsr);

        StatementAndParams stmtOrg = new StatementAndParams("""
                delete from spr_organizations
                where org_id = ?::int
                """);
        stmtOrg.addSelectParam(orgId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmtOrg);

    }
}
