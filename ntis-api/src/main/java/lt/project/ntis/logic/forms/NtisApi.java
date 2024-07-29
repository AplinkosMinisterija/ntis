package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.MethodCaller;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementDataGetter;
import eu.itreegroup.spark.dao.query.objectprocessor.StatementStringGetter;
import eu.itreegroup.spark.modules.admin.dao.SprApiKeysDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRefTranslationsDAO;
import eu.itreegroup.spark.modules.admin.service.SprApiKeysDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import jakarta.servlet.http.HttpServletRequest;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisOrderTypeConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisAdrPatLrsDAO;
import lt.project.ntis.dao.NtisBuildingNtrsDAO;
import lt.project.ntis.dao.NtisCarsDAO;
import lt.project.ntis.dao.NtisCwFileDataDAO;
import lt.project.ntis.dao.NtisCwFileDataErrsDAO;
import lt.project.ntis.dao.NtisCwFilesDAO;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.dao.NtisServicesDAO;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
import lt.project.ntis.enums.NtisCwFilStatus;
import lt.project.ntis.enums.NtisFacilityOwnerType;
import lt.project.ntis.enums.NtisFacilityStatus;
import lt.project.ntis.enums.NtisOrderMethod;
import lt.project.ntis.enums.NtisOrderStatus;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.models.api.ApiCentralizedWastewaterError;
import lt.project.ntis.models.api.ApiDeclareCentralizedWastewaterResponse;
import lt.project.ntis.models.api.ApiDeclareTechOrderResponse;
import lt.project.ntis.models.api.ApiDeclareWastewaterRemovalOrderResponse;
import lt.project.ntis.models.api.ApiTechnicalSupportOrderResponse;
import lt.project.ntis.models.api.ApiWastewaterRemovalOrderResponse;
import lt.project.ntis.models.api.ApiWastewaterTreatmentFacilityResponse;
import lt.project.ntis.rest.controller.models.ApiCentralizedWastewaterPostParameters;
import lt.project.ntis.rest.controller.models.ApiTechnicalSupportOrderParameters;
import lt.project.ntis.rest.controller.models.ApiTechnicalSupportOrdersListParameters;
import lt.project.ntis.rest.controller.models.ApiWastewaterRemovalOrderParameters;
import lt.project.ntis.rest.controller.models.ApiWastewaterRevomalOrdersListParameters;
import lt.project.ntis.service.NtisAdrPatLrsDBService;
import lt.project.ntis.service.NtisBuildingNtrsDBService;
import lt.project.ntis.service.NtisCarsDBService;
import lt.project.ntis.service.NtisCwFileDataDBService;
import lt.project.ntis.service.NtisCwFileDataErrsDBService;
import lt.project.ntis.service.NtisCwFilesDBService;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisOrderCompletedWorksDBService;
import lt.project.ntis.service.NtisOrdersDBService;
import lt.project.ntis.service.NtisServicesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta formų "G5020", "G5030", "G5040", "G5050", "G5060", "G5070", "G5080" biznio logikai apibrėžti
 */
@Component
public class NtisApi extends FormBase {

    public static String ACTION_READ_ALL = "READ_ALL";

    public static String ACTION_READ_ALL_DESC = "Read all records right";

    private static final String API = "API";

    private static final Logger log = LoggerFactory.getLogger(NtisApi.class);

    private final BaseControllerJDBC baseControllerJDBC;

    private final NtisBuildingNtrsDBService ntisBuildingNtrsDBService;

    private final DBStatementManager dbStatementManager;

    private final NtisOrdersDBService ntisOrdersDBService;

    private final NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    private final NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService;

    private final NtisServicesDBService ntisServicesDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    private final NtisCarsDBService ntisCarsDBService;

    private final NtisAdrPatLrsDBService ntisAdrPatLrsDBService;

    private final NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    private final NtisCwFilesDBService ntisCwFilesDBService;

    private final NtisCwFileDataDBService ntisCwFileDataDBService;

    private final NtisCwFileDataErrsDBService ntisCwFileDataErrsDBService;

    private final SprOrganizationsDBService sprOrganizationsDBService;

    private final SprApiKeysDBService sprApiKeysDBService;

    public NtisApi(BaseControllerJDBC baseControllerJDBC, NtisBuildingNtrsDBService ntisBuildingNtrsDBService, DBStatementManager dbStatementManager,
            NtisOrdersDBService ntisOrdersDBService, NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService,
            NtisOrderCompletedWorksDBService ntisOrderCompletedWorksDBService, NtisServicesDBService ntisServicesDBService,
            SprOrgUsersDBServiceImpl sprOrgUsersDBService, NtisCarsDBService ntisCarsDBService, NtisAdrPatLrsDBService ntisAdrPatLrsDBService,
            NtisFacilityOwnersDBService ntisFacilityOwnersDBService, NtisCwFilesDBService ntisCwFilesDBService, NtisCwFileDataDBService ntisCwFileDataDBService,
            NtisCwFileDataErrsDBService ntisCwFileDataErrsDBService, SprOrganizationsDBService sprOrganizationsDBService,
            SprApiKeysDBService sprApiKeysDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.ntisBuildingNtrsDBService = ntisBuildingNtrsDBService;
        this.dbStatementManager = dbStatementManager;
        this.ntisOrdersDBService = ntisOrdersDBService;
        this.ntisWastewaterTreatmentFaciDBService = ntisWastewaterTreatmentFaciDBService;
        this.ntisOrderCompletedWorksDBService = ntisOrderCompletedWorksDBService;
        this.ntisServicesDBService = ntisServicesDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.ntisCarsDBService = ntisCarsDBService;
        this.ntisAdrPatLrsDBService = ntisAdrPatLrsDBService;
        this.ntisFacilityOwnersDBService = ntisFacilityOwnersDBService;
        this.ntisCwFilesDBService = ntisCwFilesDBService;
        this.ntisCwFileDataDBService = ntisCwFileDataDBService;
        this.ntisCwFileDataErrsDBService = ntisCwFileDataErrsDBService;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
        this.sprApiKeysDBService = sprApiKeysDBService;
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "NTIS API CALL'S", "NTIS API CALL'S");
        addFormActionCRUD();
    }

    @Override
    public String getFormName() {
        return "NTIS_API";
    }

    /**
     * Retrieves a list of wastewater treatment facilities from the database.
     * The returned list includes information such as the facility's state, type, address, and served objects.
     * 
     * @param conn a Connection object representing the database connection
     * @param usrId a Double representing the ID of the user making the request
     * @param perId a Double representing the ID of the person associated with the user
     * @param orgId a Double representing the ID of the organization associated with the userY
     * @param pageNumber a number representing the page
     * @param pageSize a number representing the size (number of records) of page
     * @param addressFragment part of address for the search
     * @param municipalityCode a code of municipality that matches with Registry of Addresses
     * @param residenceCode a code of residence that matches with Registry of Addresses 
     * @param streetCode a code of street that matches with Registry of Addresses 
     * @param buildingNo the number of building for the looked up objects
     * @param flatNo the number of flat in the building
     * @param municipalityName part/full name of the municipality in which the object is located. Matches with Registry of Addresses
     * @param residenceName part/full name of the residence in which the object is located. Matches with Registry of Addresses
     * @param streetName part/full name of the street in which the object is located. Matches with Registry of Addresses
     * @param realEstateId AOB code
     * @param patCode PAT code
     * @return a String containing the list of wastewater treatment facilities in JSON format
     * @throws Exception if there is an error executing the SELECT statement or formatting the results as JSON
     */

    public List<ApiWastewaterTreatmentFacilityResponse> getApiWtfList(Connection conn, Double usrId, Double orgId, Double perId, Integer pageSize,
            String addressFragment, Integer municipalityCode, Integer residenceCode, Integer streetCode, String buildingNo, String flatNo,
            String municipalityName, String residenceName, String streetName, String realEstateId, Integer aobCode, Integer patCode, Integer pageBefore,
            Integer pageAfter, HttpServletRequest request) throws Exception {
        pageSize = this.getPageSize(pageSize);
        String key = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (key != null) {
            SprApiKeysDAO apiKeysDAO = this.sprApiKeysDBService.loadRecordByParams(conn, "api_key = ?", new SelectParamValue(key));
            if (apiKeysDAO != null) {
                apiKeysDAO.setD01(new Date());
                this.sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
                conn.commit();
            }
        }

        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_READ, "SPR_API_KEYS_BROWSE");

        boolean queryByOwner = (!this.hasUserRole(conn, NtisRolesConstants.MUNICI_SPECIALIST) && !this.hasUserRole(conn, NtisRolesConstants.NTIS_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.ADMIN) && !this.hasUserRole(conn, NtisRolesConstants.INST_ADMIN)
                && !this.hasUserRole(conn, NtisRolesConstants.INST_WORK));

        StringBuilder statement = new StringBuilder();

        if (queryByOwner) {
            if (orgId != null) {
                statement.append(
                        "WITH wtf_assigned AS (SELECT fo.fo_wtf_id wtf_id FROM ntis_facility_owners fo WHERE fo.fo_org_id = ?::int GROUP BY fo.fo_wtf_id) ");
            } else {
                statement.append(
                        "WITH wtf_assigned AS (SELECT fo.fo_wtf_id wtf_id FROM ntis_facility_owners fo WHERE fo.fo_per_id = ?::int GROUP BY fo.fo_wtf_id) ");
            }
        }

        StatementAndParams stmt = new StatementAndParams();
        statement.append("""
                 SELECT
                    wtf.wtf_id as id,
                     --ADDRESS
                    ad.full_address_text as addressLine,
                    ad.aob_code as aobCode,
                    ad.municipality_code as municipalityCode,
                    ad.municipality as municipalityName,
                    ad.residence_code as residenceCode,
                    ad.residence as residenceName,
                    ad.street_code as streetCode,
                    ad.street as streetName,
                    ad.building_no as buildingNumber,
                    ad.ads_housing_nr as buildingUnit,
                    ad.flat_no as flatNumber,
                     --ADDRESS
                    wtf.wtf_state as status,
                    wtf.wtf_type as typeCode,
                    rfc.rfc_meaning as typeTitle,
                    wtf.wtf_facility_latitude as latitude,
                    wtf.wtf_facility_longitude as longitude,
                    coalesce(mnf.rfc_meaning, wtf.wtf_manufacturer) as manufacturer,
                    coalesce(mdl.rfc_meaning, wtf.wtf_model) as model,
                    wtf_capacity as capacity,
                    wtf_distance as distance,
                    to_char(wtf_installation_date, '%s') as installationDate,
                    to_char(wtf_checkout_date, '%s') as checkoutDate,
                    wtf_discharge_type as dischargeType,
                    wtf_discharge_latitude as dischargeLatitude,
                    wtf_discharge_longitude as dischargeLongitude
                FROM NTIS.NTIS_WASTEWATER_TREATMENT_FACI WTF
                LEFT JOIN SPARK.SPR_REF_CODES_VW MNF ON MNF.RFC_CODE = WTF.WTF_MANUFACTURER
                                                    AND MNF.RFC_DOMAIN = 'NTIS_FACIL_MANUFA'
                                                    AND MNF.RFT_LANG = 'lt'
                LEFT JOIN SPARK.SPR_REF_CODES_VW MDL ON MDL.RFC_CODE = WTF.WTF_MODEL
                                                    AND MDL.RFC_DOMAIN = 'NTIS_FACIL_MODEL'
                                                    AND MDL.RFT_LANG = 'lt'
                LEFT JOIN NTIS.NTIS_ADDRESS_VW AS AD ON AD.ADDRESS_ID = WTF.WTF_AD_ID
                LEFT JOIN SPARK.SPR_REF_CODES_VW AS RFC ON RFC.RFC_CODE = WTF.WTF_TYPE
                                                       AND RFC.RFC_DOMAIN = 'NTIS_WTF_TYPE'
                                                       AND RFC.RFT_LANG = 'lt'
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        if (queryByOwner) {
            statement.append(" JOIN wtf_assigned asig ON wtf.wtf_id = asig.wtf_id  ");
            if (orgId != null) {
                stmt.addSelectParam(orgId);
            } else {
                stmt.addSelectParam(perId);
            }
        }
        Double id = null;

        if (realEstateId != null && !"".equals(realEstateId.trim())) {
            NtisBuildingNtrsDAO ntrDAO = ntisBuildingNtrsDBService.loadRecordByParams(conn, " bn_obj_inv_code = ? ", new SelectParamValue(realEstateId));
            if (ntrDAO != null) {
                id = ntrDAO.getBn_id();
                stmt.addParam4WherePart("""
                        WTF.wtf_id in (
                        select so_wtf_id
                          from NTIS.NTIS_BUILDING_NTRS
                          join ntis.ntis_served_objects ON so_bn_id = bn_id
                         where bn_id = ?::int)
                        """, id);
            }
        }
        if (aobCode != null && aobCode.compareTo(0) != 0 && id == null) {
            List<NtisBuildingNtrsDAO> ntrsDAO = ntisBuildingNtrsDBService.loadRecordsByParams(conn, " bn_aob_code = ?::int ",
                    new SelectParamValue(aobCode.doubleValue()));
            if (ntrsDAO != null && !ntrsDAO.isEmpty()) {
                id = ntrsDAO.get(0).getBn_id();
                String conditionalStmt = "";
                boolean first = true;
                for (NtisBuildingNtrsDAO ntr : ntrsDAO) {
                    if (first) {
                        conditionalStmt = ntr.getBn_id() + "::int";
                        first = false;
                    } else {
                        conditionalStmt = conditionalStmt + ", " + ntr.getBn_id() + "::int";
                    }
                }
                stmt.addCondition4WherePart("""
                         WTF.wtf_id in (
                        select so_wtf_id
                          from NTIS.NTIS_BUILDING_NTRS
                          join ntis.ntis_served_objects ON so_bn_id = bn_id
                         where bn_id in ( """ //
                        + conditionalStmt + //
                        """
                                )) """, "where");

            }
        } else if (patCode != null && patCode.compareTo(0) != 0 && id == null) {
            NtisAdrPatLrsDAO aplDAO = ntisAdrPatLrsDBService.loadRecordByParams(conn, " apl_pat_code = ?::int ", new SelectParamValue(patCode.doubleValue()));
            if (aplDAO != null) {
                List<NtisBuildingNtrsDAO> ntrsDAO = ntisBuildingNtrsDBService.loadRecordsByParams(conn, " bn_aob_code = ?::int ",
                        new SelectParamValue(aplDAO.getApl_aob_code()));
                if (ntrsDAO != null && !ntrsDAO.isEmpty()) {
                    id = ntrsDAO.get(0).getBn_id();
                    String conditionalStmt = "";
                    boolean first = true;
                    for (NtisBuildingNtrsDAO ntr : ntrsDAO) {
                        if (first) {
                            conditionalStmt = ntr.getBn_id() + "::int";
                            first = false;
                        } else {
                            conditionalStmt = conditionalStmt + ", " + ntr.getBn_id() + "::int";
                        }
                    }
                    stmt.addCondition4WherePart("""
                             WTF.wtf_id in (
                            select so_wtf_id
                              from NTIS.NTIS_BUILDING_NTRS
                              join ntis.ntis_served_objects ON so_bn_id = bn_id
                             where bn_id in ( """ //
                            + conditionalStmt + //
                            """
                                    )) """, "where");

                }
            }
        }
        if (id == null) {
            if (residenceCode == null && residenceName == null && (addressFragment == null || addressFragment.length() < 5 || addressFragment.trim() == "")) {
                throw new SparkBusinessException(new S2Message("common.error.addressFragment", SparkMessageType.ERROR,
                        "Privaloma užpildyti lauką addressFragment ir jo reikšmė turi būti bent 5 simbolių ilgio"));

            }

            if (municipalityCode != null) {
                stmt.addParam4WherePart(" ad.municipality_code  = ? ", municipalityCode);
            } else if (municipalityName != null && !"".equals(municipalityName.trim())) {
                stmt.addParam4WherePart(" lower(spr_translate_latin(ad.municipality)) like '%'||?||'%'",
                        Utils.replaceNationalCharacters(municipalityName.toLowerCase().trim()));
            } else {
                throw new SparkBusinessException(new S2Message("common.error.municipality", SparkMessageType.ERROR,
                        "Turi būti užpildytas arba laukas municipalityCode arba municipalityName."));
            }
            if (residenceCode != null && residenceCode.compareTo(0) != 0) {
                stmt.addParam4WherePart(" ad.residence_code  = ? ", residenceCode);
            } else if (residenceName != null && !"".equals(residenceName.trim())) {
                stmt.addParam4WherePart(" lower(spr_translate_latin(ad.residence)) like '%'||?||'%'",
                        Utils.replaceNationalCharacters(residenceName.toLowerCase().trim()));
            } else {
                throw new SparkBusinessException(
                        new S2Message("common.error.residence", SparkMessageType.ERROR, "Turi būti užpildytas arba laukas residenceCode arba residenceName."));
            }

            if (streetCode != null && streetCode.compareTo(0) != 0) {
                stmt.addParam4WherePart(" ad.street_code  = ? ", residenceCode);
            } else if (streetName != null && !"".equals(streetName.trim())) {
                stmt.addParam4WherePart(" lower(spr_translate_latin(ad.street)) like '%'||?||'%'",
                        Utils.replaceNationalCharacters(streetName.toLowerCase().trim()));
            } else {
                throw new SparkBusinessException(
                        new S2Message("common.error.street", SparkMessageType.ERROR, "Turi būti užpildytas arba laukas streetCode arba streetName."));
            }

            if (addressFragment != null) {
                stmt.addParam4WherePart(" ad.ad_address_search like '%'||?||'%'", Utils.replaceNationalCharacters(addressFragment.toLowerCase().trim()));
            }
            stmt.addParam4WherePart(" ad.building_no  = ? ", buildingNo);
            stmt.addParam4WherePart(" ad.flat_no  = ? ", flatNo);
        }

        stmt.setStatement(statement.toString());
        setOrderState(stmt, pageAfter, pageBefore, pageSize, "wtf.wtf_id");

        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setAddress", new StatementDataGetter[] { //
                new StatementStringGetter("addressLine"), //
                new StatementStringGetter("aobCode"), //
                new StatementStringGetter("municipalityCode"), //
                new StatementStringGetter("municipalityName"), //
                new StatementStringGetter("residenceCode"), //
                new StatementStringGetter("residenceName"), //
                new StatementStringGetter("streetCode"), //
                new StatementStringGetter("streetName"), //
                new StatementStringGetter("buildingNumber"), //
                new StatementStringGetter("buildingUnit"), //
                new StatementStringGetter("flatNumber"),//
        }));

        methods.add(new MethodCaller("setWtfTechDetails", new StatementDataGetter[] { //
                new StatementStringGetter("typeCode"), //
                new StatementStringGetter("typeTitle"), //
                new StatementStringGetter("latitude"), //
                new StatementStringGetter("longitude"), //
                new StatementStringGetter("manufacturer"), //
                new StatementStringGetter("model"), //
                new StatementStringGetter("capacity"), //
                new StatementStringGetter("distance"), //
                new StatementStringGetter("installationDate"), //
                new StatementStringGetter("checkoutDate") }));

        methods.add(new MethodCaller("setDischargeTechDetails", new StatementDataGetter[] { //
                new StatementStringGetter("dischargeType"), //
                new StatementStringGetter("dischargeLatitude"), //
                new StatementStringGetter("dischargeLongitude") }));
        System.err.println(stmt.logData());
        Data2ObjectProcessor<ApiWastewaterTreatmentFacilityResponse> dataProcessor = new Data2ObjectProcessor<>(ApiWastewaterTreatmentFacilityResponse.class,
                methods);

        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, dataProcessor);

    }

    /**
     * Function will return a list of orders for provided organization id
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public List<ApiTechnicalSupportOrderResponse> getTechnicalSupportOrder(Connection conn, Double usrId, Double orgId,
            ApiTechnicalSupportOrdersListParameters filterParameters, HttpServletRequest request) throws Exception {
        String key = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (key != null) {
            SprApiKeysDAO apiKeysDAO = this.sprApiKeysDBService.loadRecordByParams(conn, "api_key = ?", new SelectParamValue(key));
            if (apiKeysDAO != null) {
                apiKeysDAO.setD01(new Date());
                this.sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
                conn.commit();
            }
        }

        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_READ, "SPR_API_KEYS_BROWSE");
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT ORD_ID orderId,
                       TO_CHAR(ORD_CREATED, 'YYYY-MM-DD') AS orderDate,
                       OM.RFT_DISPLAY_CODE as orderCreatedInNTIS,
                       OS.RFC_CODE as orderStatusCode,
                       OS.RFT_DISPLAY_CODE as orderStatusTitle,
                       COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME) AS customer,
                       COALESCE(ord_email,ORG_email, per_email) customerEmail,
                       COALESCE(ord_phone_number,org_phone, per_phone_number) customerPhone,
                       ocw_completed_works_description as customerComment,
                       TO_CHAR(OCW_COMPLETED_DATE, 'YYYY-MM-DD') AS completionDate,
                       wtf.wtf_id as facilityId,
                       --ADDRESS
                       ad.full_address_text as addressLine,
                       ad.aob_code as addressCode,
                       ad.municipality_code as municipalityCode,
                       ad.municipality as municipalityName,
                       ad.residence_code as residenceCode,
                       ad.residence as residenceName,
                       ad.street_code as streetCode,
                       ad.street as streetName,
                       ad.building_no as buildingNumber,
                       ad.ads_housing_nr as buildingUnit,
                       ad.flat_no as flatNumber
                       --ADDRESS
                FROM NTIS_ORDERS AS ORD
                INNER JOIN NTIS_SERVICES SRV ON SRV_ID = ORD_SRV_ID
                AND SRV_TYPE = 'PRIEZIURA'
                LEFT JOIN NTIS_ORDER_COMPLETED_WORKS ON ORD.ORD_ID = OCW_ORD_ID
                LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG_ID = ORD.ORD_ORG_ID
                LEFT JOIN SPR_PERSONS PER ON PER_ID = ORD.ORD_PER_ID
                LEFT JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON ORD.ORD_WTF_ID = WTF.WTF_ID
                LEFT JOIN NTIS_ADDRESS_VW AD ON WTF.WTF_AD_ID = AD.ADDRESS_ID
                LEFT JOIN SPR_REF_CODES_VW OM ON OM.RFC_CODE  = ORD.ORD_CREATED_IN_NTIS_PORTAL AND OM.RFC_DOMAIN = 'NTIS_ORDER_METHOD' AND OM.RFT_LANG = 'lt'
                LEFT JOIN SPR_REF_CODES_VW OS ON OS.RFC_CODE  = ORD.ORD_STATE AND OS.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND OS.RFT_LANG = 'lt'
                where srv.srv_org_id = ?::int
                """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(orgId);
        stmt.addParam4WherePart(" ORD_CREATED >= ?::date ", filterParameters.getDateFrom());
        stmt.addParam4WherePart(" ORD_CREATED <= ?::date ", filterParameters.getDateTo());
        stmt.addParam4WherePart(" ORD_ID = ?::int ", Utils.getDouble(filterParameters.getOrderId()));
        stmt.addParam4WherePart(" wtf.wtf_id = ?::int ", Utils.getDouble(filterParameters.getFacilityId()));
        stmt.addParam4WherePart(" ORD_STATE = ? ", filterParameters.getOrderStatusCode() != null ? filterParameters.getOrderStatusCode().getCode() : null);

        setOrderState(stmt, filterParameters.getPageAfter(), filterParameters.getPageBefore(), filterParameters.getPageSize(), "ORD_ID");
        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setAddress", new StatementDataGetter[] { //
                new StatementStringGetter("addressLine"), //
                new StatementStringGetter("addressCode"), //
                new StatementStringGetter("municipalityCode"), //
                new StatementStringGetter("municipalityName"), //
                new StatementStringGetter("residenceCode"), //
                new StatementStringGetter("residenceName"), //
                new StatementStringGetter("streetCode"), //
                new StatementStringGetter("streetName"), //
                new StatementStringGetter("buildingNumber"), //
                new StatementStringGetter("buildingUnit"), //
                new StatementStringGetter("flatNumber"),//
        }));

        methods.add(new MethodCaller("setCustomerInfo", new StatementDataGetter[] { //
                new StatementStringGetter("customer"), //
                new StatementStringGetter("customerEmail"), //
                new StatementStringGetter("customerPhone"), //
                new StatementStringGetter("customercomment") }));

        Data2ObjectProcessor<ApiTechnicalSupportOrderResponse> dataProcessor = new Data2ObjectProcessor<>(ApiTechnicalSupportOrderResponse.class, methods);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, dataProcessor);
    }

    /**
     * Function will return a list of disposal orders for provided organization.
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public List<ApiWastewaterRemovalOrderResponse> getNtisDisposalOrders(Connection conn, Double usrId, Double orgId,
            ApiWastewaterRevomalOrdersListParameters filterParameters, HttpServletRequest request) throws Exception {
        String key = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (key != null) {
            SprApiKeysDAO apiKeysDAO = this.sprApiKeysDBService.loadRecordByParams(conn, "api_key = ?", new SelectParamValue(key));
            if (apiKeysDAO != null) {
                apiKeysDAO.setD01(new Date());
                this.sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
                conn.commit();
            }
        }

        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_READ, "SPR_API_KEYS_BROWSE");
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        SELECT ORD_ID as orderId,
                                        TO_CHAR(ORD_CREATED, 'YYYY-MM-DD')  AS orderDate,
                                        OM.RFT_DISPLAY_CODE as orderCreatedInNTIS,
                                        OS.RFC_CODE as orderStatusCode,
                                        OS.RFT_DISPLAY_CODE as orderStatusTitle,
                                        OCW_DISCHARGED_SLUDGE_AMOUNT as removedQuantity,
                                        TO_CHAR(ORD_REMOVED_SEWAGE_DATE, 'YYYY-MM-DD') AS removalDate,
                                        WTF.WTF_ID as facilityId,
                                        WD_STATE,
                                        cr_reg_no as carNumberPlate,
                                        cr_model as carModel,
                                        -- customer
                                        COALESCE(ORG_NAME, PER_NAME || ' ' || PER_SURNAME) AS customer,
                                        COALESCE(ord_email,ORG_email, per_email) customerEmail,
                                        COALESCE(ord_phone_number,org_phone, per_phone_number) customerPhone,
                                        ocw_completed_works_description as customerComment,
                                         -- customer
                                         --ADDRESS
                                        ad.full_address_text as addressLine,
                                        ad.aob_code as addressCode,
                                        ad.municipality_code as municipalityCode,
                                        ad.municipality as municipalityName,
                                        ad.residence_code as residenceCode,
                                        ad.residence as residenceName,
                                        ad.street_code as streetCode,
                                        ad.street as streetName,
                                        ad.building_no as buildingNumber,
                                        ad.ads_housing_nr as buildingUnit,
                                        ad.flat_no as flatNumber
                                        --ADDRESS
                                FROM (select ORD_ID,
                                            ORD_ORG_ID,
                                            SRV_ORG_ID,
                                            ORD_PER_ID,
                                            ORD_STATE,
                                            ORD_EMAIL,
                                            ORD_PHONE_NUMBER,
                                            ORD_CREATED_IN_NTIS_PORTAL,
                                            ORD_CREATED,
                                            ORD_REMOVED_SEWAGE_DATE,
                                            (SELECT FIRST_VALUE(DF_WD_ID) OVER (ORDER BY DF_ID DESC)
                                               FROM NTIS_DELIVERY_FACILITIES
                                              WHERE DF_ORD_ID = ORD_ID
                                              LIMIT 1
                                            ) LAST_DF_WD_ID,
                                            ORD_WTF_ID
                                        FROM NTIS_ORDERS
                                        JOIN NTIS_SERVICES SRV ON SRV_ID = ORD_SRV_ID AND SRV_TYPE = 'VEZIMAS'
                                        where SRV_ORG_ID = ?::int ) as ORD
                                 LEFT JOIN NTIS_ORDER_COMPLETED_WORKS ON ORD.ORD_ID = OCW_ORD_ID
                                 LEFT JOIN NTIS_CARS on cr_id = ocw_cr_id
                                 LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG_ID = ORD.ORD_ORG_ID
                                 LEFT JOIN SPR_PERSONS PER ON PER_ID = ORD.ORD_PER_ID
                                 INNER JOIN SPR_ORG_USERS OU ON SRV_ORG_ID = OU.OU_ORG_ID AND OU.OU_USR_ID = ?::int AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now())
                                 LEFT JOIN NTIS_WASTEWATER_DELIVERIES WD ON WD_ID = ORD.LAST_DF_WD_ID
                                 LEFT JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON ORD.ORD_WTF_ID = WTF.WTF_ID
                                 LEFT JOIN NTIS_ADDRESS_VW AD ON WTF.WTF_AD_ID = AD.ADDRESS_ID
                                 LEFT JOIN SPR_REF_CODES_VW OM ON OM.RFC_CODE  = ORD.ORD_CREATED_IN_NTIS_PORTAL AND OM.RFC_DOMAIN = 'NTIS_ORDER_METHOD' AND OM.RFT_LANG = 'lt'
                                 LEFT JOIN SPR_REF_CODES_VW OS ON OS.RFC_CODE  = ORD.ORD_STATE AND OS.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND OS.RFT_LANG = 'lt'
                                                """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addParam4WherePart(" ORD_CREATED >= ?::date ", filterParameters.getDateFrom());
        stmt.addParam4WherePart(" ORD_CREATED <= ?::date ", filterParameters.getDateTo());
        stmt.addParam4WherePart(" ORD_ID = ?::int ", Utils.getDouble(filterParameters.getOrderId()));
        stmt.addParam4WherePart(" wtf.wtf_id = ?::int ", Utils.getDouble(filterParameters.getFacilityId()));
        stmt.addParam4WherePart(" ORD_STATE = ? ", filterParameters.getOrderStatusCode() != null ? filterParameters.getOrderStatusCode().getCode() : null);
        setOrderState(stmt, filterParameters.getPageAfter(), filterParameters.getPageBefore(), filterParameters.getPageSize(), "ORD_ID");
        ArrayList<MethodCaller> methods = new ArrayList<>();
        methods.add(new MethodCaller("setAddress", new StatementDataGetter[] { //
                new StatementStringGetter("addressLine"), //
                new StatementStringGetter("addressCode"), //
                new StatementStringGetter("municipalityCode"), //
                new StatementStringGetter("municipalityName"), //
                new StatementStringGetter("residenceCode"), //
                new StatementStringGetter("residenceName"), //
                new StatementStringGetter("streetCode"), //
                new StatementStringGetter("streetName"), //
                new StatementStringGetter("buildingNumber"), //
                new StatementStringGetter("buildingUnit"), //
                new StatementStringGetter("flatNumber"),//
        }));

        methods.add(new MethodCaller("setCustomerInfo", new StatementDataGetter[] { //
                new StatementStringGetter("customer"), //
                new StatementStringGetter("customerEmail"), //
                new StatementStringGetter("customerPhone"), //
                new StatementStringGetter("customercomment") }));

        Data2ObjectProcessor<ApiWastewaterRemovalOrderResponse> dataProcessor = new Data2ObjectProcessor<>(ApiWastewaterRemovalOrderResponse.class, methods);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, dataProcessor);
    }

    /**
     * Function will insert finished technical support order. It will be used only by service providers.
     * @param conn - connection to the DB
     * @param parameters - technical support declaration parameters
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public ApiDeclareTechOrderResponse declareTechnicalSupportOrder(Connection conn, Double usrId, Double orgId, ApiTechnicalSupportOrderParameters parameters,
            HttpServletRequest request) throws Exception {
        String key = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (key != null) {
            SprApiKeysDAO apiKeysDAO = this.sprApiKeysDBService.loadRecordByParams(conn, "api_key = ?", new SelectParamValue(key));
            if (apiKeysDAO != null) {
                apiKeysDAO.setD01(new Date());
                this.sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
                conn.commit();
            }
        }

        this.checkIsFormActionAssigned(conn, NtisTechOrdersList.ACTION_CREATE, "SPR_API_KEYS_BROWSE");

        if (parameters.getCompletionDate().before(parameters.getOrderDate())) {
            throw new SparkBusinessException(new S2Message("common.error.date_constraint", SparkMessageType.ERROR,
                    "Lauko completionDate vertė negali būti ankstesnė negu lauko orderDate vertė."));
        }

        if (!sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

        ApiDeclareTechOrderResponse orderResult = new ApiDeclareTechOrderResponse();
        NtisWastewaterTreatmentFaciDAO facilityDAO = ntisWastewaterTreatmentFaciDBService.loadRecordByParams(conn, " wtf_id = ?::int ",
                new SelectParamValue(Utils.getDouble(parameters.getFacilityId())));
        NtisOrdersDAO orderDAO = ntisOrdersDBService.newRecord();
        if (facilityDAO != null) {
            NtisServicesDAO serviceDAO = ntisServicesDBService.loadRecordByParams(conn,
                    " srv_type = ? and srv_org_id = ?::int and ? between rec_create_timestamp and coalesce(srv_date_to, now()) and srv_date_from is not null",
                    new SelectParamValue(NtisServiceItemType.PRIEZIURA.getCode()), new SelectParamValue(orgId),
                    new SelectParamValue(Utils.getDate(parameters.getOrderDate())));
            if (serviceDAO != null && serviceDAO.getSrv_id() != null) {
                // Order data

                orderDAO.setOrd_srv_id(serviceDAO.getSrv_id());
                orderDAO.setOrd_wtf_id(Utils.getDouble(parameters.getFacilityId()));
                orderDAO.setOrd_state(NtisOrderStatus.ORD_STS_FINISHED.getCode());
                orderDAO.setOrd_type(NtisOrderTypeConstants.SERVICE);
                orderDAO.setOrd_created_in_ntis_portal(NtisOrderMethod.API.getCode());
                orderDAO.setOrd_created(Utils.getDate(parameters.getOrderDate()));
                orderDAO.setOrd_email(parameters.getCustomerEmail());
                orderDAO.setOrd_phone_number(parameters.getCustomerPhoneNumber());
                orderDAO.setOrd_additional_description(parameters.getCustomerComment());
                NtisOrdersDAO savedOrderDAO = ntisOrdersDBService.saveRecord(conn, orderDAO);
                // CompletedOrder data
                NtisOrderCompletedWorksDAO completedOrderDAO = ntisOrderCompletedWorksDBService.newRecord();
                completedOrderDAO.setOcw_completed_date(parameters.getCompletionDate());
                completedOrderDAO.setOcw_ord_id(savedOrderDAO.getOrd_id());
                completedOrderDAO.setOcw_completed_works_description(parameters.getCompletionComment());
                ntisOrderCompletedWorksDBService.saveRecord(conn, completedOrderDAO);
                // Set results
                orderResult.setOrderId(savedOrderDAO.getOrd_id().intValue());
                orderResult.setOrderCreatedInNTIS(API);
                orderResult.setOrderStatusCode(NtisOrderStatus.ORD_STS_FINISHED.getCode());
                StatementAndParams stmt = new StatementAndParams();
                stmt.setStatement("""
                                            SELECT rft_display_code
                                            FROM spr_ref_codes_vw
                                            WHERE rfc_domain='NTIS_ORDER_STATUS' and rfc_code= 'ORD_STS_FINISHED' and rft_lang = 'lt'
                        """);
                List<SprRefTranslationsDAO> result = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprRefTranslationsDAO.class);
                if (!result.isEmpty()) {
                    orderResult.setOrderStatusTitle(result.get(0).getRft_display_code());
                } else {
                    orderResult.setOrderStatusTitle("Įvykdytas");
                }
            } else {
                throw new SparkBusinessException(new S2Message("common.error.service_not_exist", SparkMessageType.ERROR,
                        "Pagal organizacijos Id, Nuotekų (dumblo) išvežimo paslauga nerasta"));
            }
        } else {
            throw new SparkBusinessException(new S2Message("common.error.facility", SparkMessageType.ERROR,
                    "Pagal nurodytą nuotekų tvarkymo įrenginio identifikatorių NTIS portale nerastas joks įrašas."));
        }
        ntisFacilityOwnersDBService.manageWtfOwners(conn, orderDAO.getOrd_wtf_id(), orgId, null, null, new Date(), null,
                NtisFacilityOwnerType.SERVICE_PROVIDER);
        NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, orderDAO.getOrd_wtf_id());
        if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode())) {
            facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
            ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
        }

        return orderResult;
    }

    /**
     * Function will insert finished wastewater removal order. It will be used only by service providers.
     * @param conn - connection to the DB
     * @param parameters - wastewater removal declaration parameters
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public ApiDeclareWastewaterRemovalOrderResponse declareWastewaterRemovalOrder(Connection conn, Double usrId, Double orgId,
            ApiWastewaterRemovalOrderParameters parameters, HttpServletRequest request) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE, "SPR_API_KEYS_BROWSE");
        String key = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (key != null) {
            SprApiKeysDAO apiKeysDAO = this.sprApiKeysDBService.loadRecordByParams(conn, "api_key = ?", new SelectParamValue(key));
            if (apiKeysDAO != null) {
                apiKeysDAO.setD01(new Date());
                this.sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
                conn.commit();
            }
        }

        // Constraints START
        if (parameters.getRemovalDate().before(parameters.getOrderDate())) {
            throw new SparkBusinessException(new S2Message("common.error.date_constraint", SparkMessageType.ERROR,
                    "Lauko removalDate vertė negali būti ankstesnė negu lauko orderDate vertė."));
        }

        NtisCarsDAO carsDAO = ntisCarsDBService.loadRecordByParams(conn,
                " cr_reg_no = ? and cr_org_id = ?::int and ? between cr_date_from and coalesce(cr_date_to, now())",
                new SelectParamValue(parameters.getCarNumberPlate()), new SelectParamValue(orgId), new SelectParamValue(parameters.getOrderDate()));
        if (carsDAO == null) {
            throw new SparkBusinessException(new S2Message("common.error.carNumberPlate_constraint", SparkMessageType.ERROR,
                    "Pagal nurodytą valstybinį transporto priemonės numerį NTIS portale nerastas joks įrašas."));
        }

        NtisWastewaterTreatmentFaciDAO facilityDAO = ntisWastewaterTreatmentFaciDBService.loadRecordByParams(conn, " wtf_id = ?::int ",
                new SelectParamValue(Utils.getDouble(parameters.getFacilityId())));
        if (facilityDAO == null) {
            throw new SparkBusinessException(new S2Message("common.error.facility", SparkMessageType.ERROR,
                    "Pagal nurodytą nuotekų tvarkymo įrenginio identifikatorių NTIS portale nerastas joks įrašas."));
        }

        if (!sprOrgUsersDBService.checkIsUserInOrganization(conn, usrId, orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }

        NtisServicesDAO serviceDAO = ntisServicesDBService.loadRecordByParams(conn,
                " srv_type = ? and srv_org_id = ?::int and ? between rec_create_timestamp and coalesce(srv_date_to, now()) and srv_date_from is not null",
                new SelectParamValue(NtisServiceItemType.VEZIMAS.getCode()), new SelectParamValue(orgId),
                new SelectParamValue(Utils.getDate(parameters.getOrderDate())));

        if (serviceDAO == null) {
            throw new SparkBusinessException(new S2Message("common.error.service_not_exist", SparkMessageType.ERROR,
                    "Pagal organizacijos Id, Nuotekų (dumblo) išvežimo paslauga nerasta"));
        }
        // Constraints END

        ApiDeclareWastewaterRemovalOrderResponse orderResult = new ApiDeclareWastewaterRemovalOrderResponse();

        // Order data
        NtisOrdersDAO orderDAO = ntisOrdersDBService.newRecord();
        orderDAO.setOrd_srv_id(serviceDAO.getSrv_id());
        orderDAO.setOrd_wtf_id(Utils.getDouble(parameters.getFacilityId()));
        orderDAO.setOrd_state(NtisOrderStatus.ORD_STS_FINISHED.getCode());
        orderDAO.setOrd_type(NtisOrderTypeConstants.SERVICE);
        orderDAO.setOrd_created_in_ntis_portal(NtisOrderMethod.API.getCode());
        orderDAO.setOrd_created(Utils.getDate(parameters.getOrderDate()));
        orderDAO.setOrd_removed_sewage_date(parameters.getRemovalDate());
        orderDAO.setOrd_email(parameters.getCustomerEmail());
        orderDAO.setOrd_phone_number(parameters.getCustomerPhoneNumber());
        orderDAO.setOrd_additional_description(parameters.getCustomerComment());
        NtisOrdersDAO savedOrderDAO = ntisOrdersDBService.saveRecord(conn, orderDAO);
        // CompletedOrder data
        NtisOrderCompletedWorksDAO completedOrderDAO = ntisOrderCompletedWorksDBService.newRecord();
        completedOrderDAO.setOcw_completed_date(parameters.getRemovalDate());
        completedOrderDAO.setOcw_ord_id(savedOrderDAO.getOrd_id());
        completedOrderDAO.setOcw_completed_works_description(parameters.getCompletionComment());
        completedOrderDAO.setOcw_cr_id(carsDAO.getCr_id());
        completedOrderDAO.setOcw_discharged_sludge_amount(parameters.getRemovedQuantity());
        ntisOrderCompletedWorksDBService.saveRecord(conn, completedOrderDAO);
        // Set results
        orderResult.setOrderId(savedOrderDAO.getOrd_id().intValue());
        orderResult.setOrderCreatedInNTIS(API);
        orderResult.setOrderStatusCode(NtisOrderStatus.ORD_STS_FINISHED.getCode());
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                                    SELECT rft_display_code
                                    FROM spr_ref_codes_vw
                                    WHERE rfc_domain='NTIS_ORDER_STATUS' and rfc_code= 'ORD_STS_FINISHED' and rft_lang = 'lt'
                """);
        List<SprRefTranslationsDAO> result = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprRefTranslationsDAO.class);
        if (!result.isEmpty()) {
            orderResult.setOrderStatusTitle(result.get(0).getRft_display_code());
        } else {
            orderResult.setOrderStatusTitle("Įvykdytas");
        }
        ntisFacilityOwnersDBService.manageWtfOwners(conn, orderDAO.getOrd_wtf_id(), orgId, null, null, new Date(), null,
                NtisFacilityOwnerType.SERVICE_PROVIDER);
        NtisWastewaterTreatmentFaciDAO facility = ntisWastewaterTreatmentFaciDBService.loadRecord(conn, orderDAO.getOrd_wtf_id());
        if (facility.getWtf_state().equalsIgnoreCase(NtisFacilityStatus.REGISTERED.getCode())) {
            facility.setWtf_state(NtisFacilityStatus.CONFIRMED.getCode());
            ntisWastewaterTreatmentFaciDBService.saveRecord(conn, facility);
        }
        return orderResult;
    }

    /**
     * Function insert centralized wastewater records and update treatment types
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public ApiDeclareCentralizedWastewaterResponse declareCentralizedWastewater(Connection conn, Double usrId, Double orgId,
            ApiCentralizedWastewaterPostParameters parameters, HttpServletRequest request) throws Exception {
        String key = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (key != null) {
            SprApiKeysDAO apiKeysDAO = this.sprApiKeysDBService.loadRecordByParams(conn, "api_key = ?", new SelectParamValue(key));
            if (apiKeysDAO != null) {
                apiKeysDAO.setD01(new Date());
                this.sprApiKeysDBService.saveRecord(conn, apiKeysDAO);
                conn.commit();
            }
        }

        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE, "SPR_API_KEYS_BROWSE");
        boolean isUpdated = true;
        ApiDeclareCentralizedWastewaterResponse response = new ApiDeclareCentralizedWastewaterResponse();
        List<NtisCwFileDataErrsDAO> errors = new ArrayList<NtisCwFileDataErrsDAO>();
        NtisCwFilesDAO fileDAO = ntisCwFilesDBService.newRecord();
        fileDAO.setCwf_import_date(Utils.getDate(new Date()));
        fileDAO.setCwf_status(NtisCwFilStatus.CW_FIL_PENDING.getCode());
        fileDAO.setCwf_status_date(Utils.getDate(new Date()));
        if (parameters.getOrgCode() != null) {
            SprOrganizationsDAO organizationDAO = sprOrganizationsDBService.loadRecordByParams(conn,
                    "org_code = ? and current_date between org_date_from and coalesce(org_date_to, now())", new SelectParamValue(parameters.getOrgCode()));
            if (organizationDAO != null) {
                fileDAO.setCwf_org_id(organizationDAO.getOrg_id());
            }
        }
        if (fileDAO.getCwf_org_id() == null) {
            fileDAO.setCwf_org_id(orgId);
        }
        ntisCwFilesDBService.saveRecord(conn, fileDAO);
        NtisCwFileDataDAO fileDataDAO = ntisCwFileDataDBService.newRecord();
        fileDataDAO.setCwfd_pastato_kodas(parameters.getRealEstateId());
        fileDataDAO.setCwfd_pastato_adr_kodas(parameters.getAobCode());
        fileDataDAO.setCwfd_patalpos_kodas(parameters.getPatCode());
        fileDataDAO.setCwfd_savivaldybes_kodas(parameters.getMunicipalityCode());
        fileDataDAO.setCwfd_savivaldybe(parameters.getMunicipalityName());
        fileDataDAO.setCwfd_gyv_vietos_kodas(parameters.getResidenceCode());
        fileDataDAO.setCwfd_gyv_vieta(parameters.getResidenceName());
        fileDataDAO.setCwfd_gatves_kodas(parameters.getStreetCode());
        fileDataDAO.setCwfd_gatve(parameters.getStreetName());
        fileDataDAO.setCwfd_pastato_nr(parameters.getBuildingNumber());
        fileDataDAO.setCwfd_korpuso_nr(parameters.getBuildingUnit());
        fileDataDAO.setCwfd_buto_nr(parameters.getFlatNumber());
        fileDataDAO.setCwfd_vandentvarkos_im_kod(parameters.getOrgCode());
        if (parameters.getWastewaterRemovalType() != null) {
            if (parameters.getWastewaterRemovalType().equalsIgnoreCase("centralizuotas")
                    || parameters.getWastewaterRemovalType().equalsIgnoreCase("CENTRALIZED")) {
                fileDataDAO.setCwfd_nuot_salinimo_budas("CENTRALIZED");
            } else if (parameters.getWastewaterRemovalType().equalsIgnoreCase("vietinis") || parameters.getWastewaterRemovalType().equalsIgnoreCase("LOCAL")) {
                fileDataDAO.setCwfd_nuot_salinimo_budas("LOCAL");
            }
        } else {
            fileDataDAO.setCwfd_nuot_salinimo_budas("CENTRALIZED");
        }
        fileDataDAO.setCwfd_prijungimo_data(parameters.getConnectionDate());
        fileDataDAO.setCwfd_atjungimo_data(parameters.getDisconnectionDate());
        fileDataDAO.setCwfd_cwf_id(fileDAO.getCwf_id());
        ntisCwFileDataDBService.saveRecord(conn, fileDataDAO);

        try {
            StatementAndParams stmt = new StatementAndParams("CALL ntis.validate_data_import(?::integer, ?::integer)");
            stmt.addSelectParam(fileDAO.getCwf_id());
            stmt.addSelectParam(usrId);
            PreparedStatement ps = conn.prepareStatement(stmt.getStatemenWithParams());
            stmt.setValues(ps);
            ps.execute();
            errors = ntisCwFileDataErrsDBService.loadRecordsByParams(conn, "cwfde_cwf_id = ?::int", new SelectParamValue(fileDAO.getCwf_id()));
            if (errors.isEmpty()) {
                try {
                    StatementAndParams stmtUpdate = new StatementAndParams("CALL ntis.update_building_agreements(?::integer, ?::integer)");
                    stmtUpdate.addSelectParam(fileDAO.getCwf_id());
                    stmtUpdate.addSelectParam(usrId);
                    PreparedStatement ps2 = conn.prepareStatement(stmtUpdate.getStatemenWithParams());
                    stmtUpdate.setValues(ps2);
                    ps2.execute();
                } catch (Exception ex) {
                    isUpdated = false;
                    conn.rollback();
                    deleteCentralizedData(conn, fileDAO.getCwf_id());
                    throw new SparkBusinessException(new S2Message("common.error.fileProcessingError", SparkMessageType.ERROR));
                }
            } else {
                isUpdated = false;
            }
        } catch (Exception ex) {
            conn.rollback();
            deleteCentralizedData(conn, fileDAO.getCwf_id());
            throw new SparkBusinessException(new S2Message("common.error.fileProcessingError", SparkMessageType.ERROR));
        }
        if (isUpdated) {
            response.setUpdateStatus("Duomenų atnaujinimas atliktas sėkmingai");
        } else {
            if (errors.isEmpty()) {
                response.setUpdateStatus("Įvyko nenumatyta klaida bandant atnaujinti duomenis");
            } else {
                response.setUpdateStatus("Duomenų atnaujinti nepavyko dėl klaidingų pateiktų parametrų");
                List<ApiCentralizedWastewaterError> errorList = new ArrayList<ApiCentralizedWastewaterError>();
                for (NtisCwFileDataErrsDAO error : errors) {
                    ApiCentralizedWastewaterError cwError = new ApiCentralizedWastewaterError();
                    cwError.setErrorDescription(error.getCwfde_msg_text());
                    cwError.setProvidedValue(error.getCwfde_column_value());
                    errorList.add(cwError);
                }
                response.setErrors(errorList);
            }
        }
        deleteCentralizedData(conn, fileDAO.getCwf_id());
        return response;
    }

    private void setOrderState(StatementAndParams stmt, Integer pageAfter, Integer pageBefore, Integer pageSize, String identifier) {
        if (pageAfter != null) {
            stmt.addParam4WherePart(identifier + " > ?::int ", pageAfter);
            stmt.setStatementOrderPart(" order by " + identifier);
        } else if (pageBefore != null) {
            stmt.addParam4WherePart(identifier + " < ?::int ", pageBefore);
            stmt.setStatementOrderPart(" order by " + identifier + " desc ");
        } else {
            stmt.setStatementOrderPart(" order by  " + identifier);
        }
        stmt.setRecordCountLimitPart(String.format(" LIMIT  %d ", getPageSize(pageSize)));
    }

    private Integer getPageSize(Integer pageSize) {
        return pageSize > 10000 ? 10000 : pageSize;
    }

    private void deleteCentralizedData(Connection conn, Double cwfId) throws Exception {
        StatementAndParams errsStmt = new StatementAndParams("delete from ntis_cw_file_data_errs where cwfde_cwf_id = ?");
        errsStmt.addSelectParam(cwfId);
        baseControllerJDBC.adjustRecordsInDB(conn, errsStmt);
        StatementAndParams dataStmt = new StatementAndParams("delete from ntis_cw_file_data where cwfd_cwf_id = ?");
        dataStmt.addSelectParam(cwfId);
        baseControllerJDBC.adjustRecordsInDB(conn, dataStmt);
        StatementAndParams fileStmt = new StatementAndParams("delete from ntis_cw_files where cwf_id = ?");
        fileStmt.addSelectParam(cwfId);
        baseControllerJDBC.adjustRecordsInDB(conn, fileStmt);
        conn.commit();
    }
}
