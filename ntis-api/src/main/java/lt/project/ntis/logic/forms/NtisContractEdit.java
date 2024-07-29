package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.util.Value;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.dao.NtisContractCommentsDAO;
import lt.project.ntis.dao.NtisContractServicesDAO;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.enums.NtisContractStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.isense.Isense;
import lt.project.ntis.logic.forms.model.NtisContractEditModel;
import lt.project.ntis.logic.forms.model.NtisContractRequestComment;
import lt.project.ntis.logic.forms.model.NtisContractRequestService;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.processRequests.ContractProcessRequest;
import lt.project.ntis.service.NtisContractCommentsDBService;
import lt.project.ntis.service.NtisContractServicesDBService;
import lt.project.ntis.service.NtisContractsDBService;

/**
 * Klasė skirta formų "Peržiūrėti/redaguoti prašymą/sutartį" (S1040, S1050, S1060, S1170, S1180) biznio logikai apibrėžti
 */
@Component
public class NtisContractEdit extends FormBase {

    public static String DEFAULT_CONTRACT_EXPIRATION_NOTIF_DAYS = "3";

    public static String CONTRACT_EXPIRATION_NOTIF_DAYS = "CONTRACT_EXPIRATION_NOTIF_DAYS";

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    NtisContractsDBService ntisContractsDBService;

    @Autowired
    NtisContractServicesDBService ntisContractServicesDBService;

    @Autowired
    NtisContractCommentsDBService ntisContractCommentsDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ContractProcessRequest contractProcessRequest;

    @Autowired
    NtisNotificationsManager ntisNotificationsManager;

    @Autowired
    Isense isense;

    @Value("${app.host}")
    private String appHostUrl;

    @Override
    public String getFormName() {
        return "NTIS_CONTRACT_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Peržiūrėti/redaguoti prašymą/sutartį", "Peržiūrėti/redaguoti prašymą/sutartį");
        addFormActionCRUD();
        addFormAction(NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC,
                NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC);
        addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC);
    }

    /**
     * Metodas grąžins NtisContractEditModel objektą pagal pateiktą sutarties ID
     * @param conn - prisijungimas prie DB
     * @param lang - kalba, naudojama vertimams
     * @param cotId - sutarties ID
     * @return NtisContractEditModel objektas
     * @throws Exception
     */
    public NtisContractEditModel loadContractInfo(Connection conn, String lang, Double cotId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        checkFormActions(conn);
        // Set contract id if user is a part of the contract
        Double contractId = checkAndSetContractId(conn, orgId, perId, cotId);

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT cot_id,
                    cot_state,
                    COALESCE(CST.RFT_DISPLAY_CODE, CST.RFC_MEANING) AS cot_state_meaning,
                    TO_CHAR(cot_from_date, '%s') AS cot_from_date,
                    TO_CHAR(cot_to_date, '%s') AS cot_to_date,
                    TO_CHAR(cot_created, '%s') AS cot_created,
                    TO_CHAR(cot_rejection_date, '%s') AS cot_rejection_date,
                    TO_CHAR(cot_project_created, '%s') AS cot_project_created,
                    cot_rejection_reason,
                    CASE
                        WHEN cot_state = '%s' THEN cot_sign_1_fil_id
                        WHEN cot_state = '%s' THEN cot_sign_2_fil_id
                        ELSE cot_fil_id
                    END as cot_fil_id,
                    cot_sign_1_fil_id,
                    cot_sign_2_fil_id,
                    org_id,
                    org_name,
                    cot_client_email,
                    cot_client_phone_no,
                    per_name || ' ' || per_surname AS per_name
                FROM NTIS_CONTRACTS COT
                LEFT JOIN SPR_ORGANIZATIONS ORG ON ORG_ID = COT_ORG_ID
                LEFT JOIN SPR_PERSONS PER ON PER_ID = COT_PER_ID
                LEFT JOIN SPR_REF_CODES_VW CST ON CST.RFC_CODE = COT.COT_STATE AND CST.RFC_DOMAIN = 'NTIS_COT_STATE' AND CST.RFT_LANG = ?
                WHERE cot_id = ?::int
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB), NtisContractStatus.SIGNED_BY_SRV_PROV.getCode(),
                NtisContractStatus.SIGNED_BY_BOTH.getCode()));
        stmt.addSelectParam(lang);
        stmt.addSelectParam(contractId);

        List<NtisContractEditModel> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisContractEditModel.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No contract information was found");
        }
        NtisContractEditModel result = queryResult.get(0);
        result.setSp_info(loadServiceProviderInfo(conn, contractId));
        result.setWtf_info(loadWtfInfo(conn, lang, contractId));
        result.setCot_comments(loadComments(conn, contractId, orgId, perId));
        result.setCot_services(loadServices(conn, lang, contractId));
        if (result.getCot_fil_id() != null && result.getCot_fil_id() != 0) {
            result.setAttachment(loadFile(conn, result.getCot_fil_id()));
        }
        return result;
    }

    /**
     * Metodas sukurs naują DB įrašą sutarčių komentarų lentoje pagal pateiktą NtisContractRequestComment objektą
     * @param conn - prisijungimas prie DB
     * @param record - NtisContractRequestComment objektas
     * @param orgId - sesijos organizacijos ID
     * @param perId - sesijos asmens ID
     * @param usrId - sesijos naudotojo ID
     * @throws Exception
     */
    public void addComment(Connection conn, NtisContractRequestComment record, Double orgId, Double perId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        Double contractId = checkAndSetContractId(conn, orgId, perId, record.getContractId());
        if (orgId == null && perId == null) {
            throw new SparkBusinessException(new S2Message("common.error.noPersonInformation", SparkMessageType.ERROR,
                    "No person/organization information was found. Comment cannot be sumbitted."));
        }
        NtisContractCommentsDAO commentDAO = ntisContractCommentsDBService.newRecord();
        commentDAO.setCc_message(record.getText());
        commentDAO.setCc_cot_id(contractId);
        commentDAO.setCc_org_id(orgId);
        commentDAO.setCc_per_id(perId);
        commentDAO.setCc_created(new Date());
        ntisContractCommentsDBService.saveRecord(conn, commentDAO);
        NtisContractsDAO contractDAO = ntisContractsDBService.loadRecord(conn, contractId);
        this.handleSendNotifications(conn, contractId, usrId, perId, orgId, contractDAO, null, commentDAO);
    }

    /**
     * Metodas ištrins DB įrašą pagal nurodytą komentaro ID.
     * @param conn - prisijungimas prie DB
     * @param commentId - komentaro, kurį norima ištrinti, ID
     * @param sessionOrgId - sesijos organizacijos ID
     * @param sessionPerId - sesijos asmens ID
     * @throws NumberFormatException
     * @throws SparkBusinessException
     * @throws Exception
     */
    public void deleteComment(Connection conn, NtisContractRequestComment comment, Double sessionOrgId, Double sessionPerId)
            throws NumberFormatException, SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, ACTION_DELETE);
        Double contractId = checkAndSetContractId(conn, sessionOrgId, sessionPerId, comment.getContractId());
        NtisContractCommentsDAO commentDAO = ntisContractCommentsDBService.loadRecord(conn, comment.getCc_id());
        // Throw error if user tries to delete other user's comment
        if ((sessionOrgId != null && commentDAO.getCc_org_id() != null && Double.compare(sessionOrgId, commentDAO.getCc_org_id()) != 0)
                || (sessionPerId != null && commentDAO.getCc_per_id() != null && Double.compare(sessionPerId, commentDAO.getCc_per_id()) != 0)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));

        }
        ntisContractCommentsDBService.deleteRecord(conn, comment.getCc_id());
    }

    /**
     * Metodas atnaujins sutarties informaciją pagal pateiktą NtisContractEditModel objektą
     * @param conn - prisijungimas prie DB
     * @param record - NtisContractEditModel objektas
     * @param sessionOrgId - sesijos organizacijos ID
     * @param sessionPerId - sesijos asmens ID
     * @param sessionUsrId - sesijos naudotojo ID
     * @throws Exception
     * @throws SparkBusinessException
     */
    public void updateContract(Connection conn, NtisContractEditModel record, Double sessionOrgId, Double sessionPerId, Double sessionUsrId)
            throws Exception, SparkBusinessException {
        this.checkIsFormActionAssigned(conn, ACTION_UPDATE);
        Double contractId = checkAndSetContractId(conn, sessionOrgId, sessionPerId, record.getCot_id());
        NtisContractsDAO contractDAO = ntisContractsDBService.loadRecord(conn, contractId);
        this.handleSendNotifications(conn, record.getCot_id(), sessionUsrId, sessionPerId, sessionOrgId, contractDAO, record, null);
        if (record.getCot_rejection_reason() != null) {
            contractDAO.setCot_rejection_reason(record.getCot_rejection_reason());
            contractDAO.setCot_rejection_date(new Date());
        }
        contractDAO.setCot_state(record.getCot_state());
        if (record.getCot_state().equals(NtisContractStatus.SUBMITTED.getCode())
                && this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            for (NtisContractRequestService service : record.getCot_services()) {
                NtisContractServicesDAO serviceDAO = ntisContractServicesDBService.loadRecord(conn, service.getCs_id());
                serviceDAO.setCs_price(service.getPrice());
                ntisContractServicesDBService.updateRecord(conn, serviceDAO);
            }
            contractDAO.setCot_code(record.getCot_code());
            contractDAO.setCot_from_date(record.getCot_from_date());
            contractDAO.setCot_to_date(record.getCot_to_date());
            contractDAO.setCot_project_created(new Date());

            if (record.getAttachment() != null && record.getAttachment().getFil_key() != null) {
                SprFilesDAO fileDB = new SprFilesDAO();
                fileDB = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(record.getAttachment().getFil_key()));
                sprFilesDBService.markAsConfirmed(conn, fileDB);
                contractDAO.setCot_fil_id(fileDB.getFil_id());
            }
        }
        ntisContractsDBService.updateRecord(conn, contractDAO);
    }

    private SprOrganizationsDAO loadServiceProviderInfo(Connection conn, Double cotId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("SELECT org_id, " //
                + " org_name, " //
                + " org_phone, " //
                + " org_email " //
                + " FROM ntis_contracts COT " //
                + " LEFT JOIN ntis_contract_services CS ON cot_id = cs_cot_id " //
                + " LEFT JOIN ntis_services SRV ON srv_id = cs_srv_id " //
                + " LEFT JOIN spr_organizations ORG ON org_id = srv_org_id " //
                + " WHERE cot_id = ?::int " //
        );
        stmt.addSelectParam(cotId);
        stmt.setStatementGroupPart(" org_id, org_name, org_phone, org_email ");

        List<SprOrganizationsDAO> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprOrganizationsDAO.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No service provider information was found");
        }
        return queryResult.get(0);
    }

    private NtisWtfInfo loadWtfInfo(Connection conn, String lang, Double cotId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("SELECT WTF.wtf_id AS id, " //
                + " CASE WHEN WTF.WTF_AD_ID IS NULL " //
                + "      THEN ( " //
                + "            SELECT mp.mp_name " //
                + "            FROM ntis_wastewater_treatment_faci wtf " //
                + "            JOIN ntis_municipalities mp ON wtf.wtf_facility_municipality_code = mp.mp_code " //
                + "            WHERE wtf.wtf_id = cot_wtf_id " //
                + "           ) || ' (' || WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ')' " //
                + "      ELSE WAV.FULL_ADDRESS_TEXT " //
                + "      END AS address, " //
                + " COALESCE(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) AS type, " //
                + " WTF_TECHNICAL_PASSPORT_ID AS technicalPassport, " //
                + " COALESCE(MNF.RFT_DISPLAY_CODE, MNF.RFC_MEANING) AS manufacturer, " //
                + " COALESCE(MDL.RFT_DISPLAY_CODE, MDL.RFC_MEANING) AS model, " //
                + " WTF.WTF_TYPE AS typeClsf, " //
                + " WTF_DISTANCE AS distance, " //
                + " COALESCE(CAP.RFT_DISPLAY_CODE, CAP.RFC_MEANING) AS capacity, " //
                + " TO_CHAR(WTF_INSTALLATION_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS installationDate " //
                + " FROM NTIS_CONTRACTS COT" //
                + " LEFT JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON WTF_ID = COT_WTF_ID " //
                + " LEFT JOIN NTIS_ADDRESS_VW WAV ON WTF.WTF_AD_ID = WAV.ADDRESS_ID " //
                + " LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ? " //
                + " LEFT JOIN SPR_REF_CODES_VW MNF ON MNF.RFC_CODE = WTF.WTF_MANUFACTURER AND MNF.RFC_DOMAIN = 'NTIS_FACIL_MANUFA' AND MNF.RFT_LANG = ? " //
                + " LEFT JOIN SPR_REF_CODES_VW MDL ON MDL.RFC_CODE = WTF.WTF_MODEL AND MDL.RFC_DOMAIN = 'NTIS_FACIL_MODEL' AND MDL.RFT_LANG = ? " //
                + " LEFT JOIN SPR_REF_CODES_VW CAP ON CAP.RFC_CODE = WTF.WTF_CAPACITY AND CAP.RFC_DOMAIN = 'NTIS_FACIL_CAPACITY' AND CAP.RFT_LANG = TYP.RFT_LANG " //
                + " WHERE cot_id = ?::int " //
        );
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(cotId);

        List<NtisWtfInfo> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWtfInfo.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No wastewater treatment facility information was found");
        }
        return queryResult.get(0);
    }

    /**
     * Metodas grąžins komentarus pagal pateiktą sutarties ID
     * @param conn - prisijungimas prie DB
     * @param cotId - sutarties ID
     * @return NtisContractRequestComment objektų masyvas
     * @throws Exception
     */
    public List<NtisContractRequestComment> loadComments(Connection conn, Double cotId, Double orgId, Double perId) throws Exception {
        Double contractId = checkAndSetContractId(conn, orgId, perId, cotId);
        StatementAndParams stmt = new StatementAndParams(" SELECT " //
                + " cc_id, " + " cc_message AS text, " //
                + " TO_CHAR(cc_created, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') AS time, " //
                + " CASE WHEN cc_org_id IS NULL THEN PER.per_name || ' ' || PER.per_surname " //
                + "      ELSE  per.per_name || ' ' || per.per_surname || ' - ' ||  ORG.org_name END AS author " //
                + " FROM NTIS_CONTRACT_COMMENTS CC " //
                + " LEFT JOIN SPR_ORGANIZATIONS ORG ON org_id = cc_org_id " //
                + " LEFT JOIN SPR_PERSONS PER ON per_id = cc_per_id " //
                + " WHERE CC.cc_cot_id = ? " //
                + " ORDER BY cc_created " //
        );
        stmt.addSelectParam(contractId);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisContractRequestComment.class);
    }

    private List<NtisContractRequestService> loadServices(Connection conn, String lang, Double cotId) throws Exception {
        StatementAndParams stmt = new StatementAndParams(" SELECT cs_id, " //
                + " srv_type AS type, " //
                + " COALESCE(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) AS name, " //
                + " srv_description AS description, " //
                + " srv_id, " //
                + " cs_price AS price " //
                + " FROM NTIS_CONTRACT_SERVICES CS " //
                + " LEFT JOIN NTIS_SERVICES SRV ON srv_id = cs_srv_id " //
                + " LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = SRV.SRV_TYPE AND TYP.RFC_DOMAIN = 'NTIS_SRV_ITEM_TYPE' AND TYP.RFT_LANG = ? " //
                + " WHERE CS.cs_cot_id = ? " //
        );
        stmt.addSelectParam(lang);
        stmt.addSelectParam(cotId);
        List<NtisContractRequestService> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisContractRequestService.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No services information was found");
        }
        return queryResult;
    }

    private SprFile loadFile(Connection conn, Double cotFileId) throws Exception {
        StatementAndParams stmt = new StatementAndParams(" SELECT FIL_ID, " //
                + " FIL_CONTENT_TYPE, " //
                + " FIL_KEY, " //
                + " FIL_NAME, " //
                + " FIL_SIZE, " //
                + " FIL_STATUS, " //
                + " TO_CHAR(FIL_STATUS_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS FIL_STATUS_DATE " //
                + " FROM SPR_FILES " //
                + " WHERE FIL_ID = ? ");
        stmt.addSelectParam(cotFileId);
        List<SprFile> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprFile.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No file information was found");
        }
        return queryResult.get(0);
    }

    private Double checkAndSetContractId(Connection conn, Double orgId, Double perId, Double cotId) throws Exception {
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            return ntisContractsDBService.loadRecordByIdAndClientId(conn, orgId, perId, cotId).getCot_id();
        } else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            return ntisContractsDBService.loadRecordByIdAndServiceProviderId(conn, orgId, cotId).getCot_id();
        } else {
            throw new Exception("User is not authorized to perform this action");
        }
    }

    public void handleSendNotifications(Connection conn, Double cotId, Double sessionUsrId, Double sessionPerId, Double sessionOrgId,
            NtisContractsDAO contractDAO, NtisContractEditModel contractEdit, NtisContractCommentsDAO comment) throws Exception {
        cotId = this.checkAndSetContractId(conn, sessionOrgId, sessionPerId, cotId);
        if (contractDAO == null) {
            contractDAO = ntisContractsDBService.loadRecord(conn, cotId);
        }
        StatementAndParams stmt = new StatementAndParams();
        HashMap<String, String> providerDetails = this.getServiceProvider(conn, cotId);
        String providerName = providerDetails.get("org_name");
        Double providerId = Utils.getDouble(providerDetails.get("org_id"));
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            if (contractDAO.getCot_org_id() != null) {
                stmt.setStatement("""
                        select usr_id
                        from spr_users
                        inner join spr_org_users on ou_usr_id = usr_id and current_date between ou_date_from and coalesce(ou_date_to, now())
                        inner join ntis_contracts on cot_org_id = ou_org_id
                        where cot_org_id = ? and cot_id = ?
                        """);
                stmt.addSelectParam(contractDAO.getCot_org_id());
            } else if (contractDAO.getCot_per_id() != null) {
                stmt.setStatement("""
                        select usr_id
                        from spr_users
                        inner join spr_persons p on usr_per_id = p.per_id
                        inner join ntis_contracts on cot_per_id = per_id
                        where cot_per_id = ? and cot_id = ?
                        """);
                stmt.addSelectParam(contractDAO.getCot_per_id());
            }
        } else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            stmt.setStatement("""
                    select usr_id,
                    usr_email,
                    org_email,
                    cs_cot_id as cot_id,
                    o.c02 as requests_email
                    from spr_users
                    inner join spr_org_users on ou_usr_id = usr_id and current_date between ou_date_from and coalesce(ou_date_to, now())
                    inner join spr_organizations o on ou_org_id = org_id and org_id = ?
                    inner join spr_org_user_roles on our_ou_id = ou_id and current_date between ou_date_from and coalesce(ou_date_to, now())
                    inner join spr_roles on our_rol_id = rol_id and rol_code in ('PASL_ADMIN', 'CONTRACT_MANAGER')
                    inner join ntis_services on srv_org_id = org_id
                    inner join ntis_contract_services on cs_srv_id = srv_id and cs_cot_id = ?
                    group by usr_id, usr_email, org_email, o.c02, cs_cot_id
                           """);
            stmt.addSelectParam(providerId);
        }
        stmt.addSelectParam(cotId);

        List<HashMap<String, String>> users = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        List<String> srvPrvdEmails = new ArrayList<>();
        List<String> intsOrgEmails = new ArrayList<>();
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS) && users != null && users.size() > 0) {
            if (users.get(0).get("requests_email") != null && users.get(0).get("requests_email").equalsIgnoreCase(YesNo.Y.getCode())
                    && users.get(0).get("org_email") != null && !users.get(0).get("org_email").isBlank()) {
                srvPrvdEmails = Arrays.asList(users.get(0).get("org_email").split("\\s*,\\s*"));
            }
        } else {
            srvPrvdEmails = null;
        }
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            intsOrgEmails = Arrays.asList(contractDAO.getCot_client_email().split("\\s*,\\s*"));
        } else {
            intsOrgEmails = null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("homeUrl", this.appHostUrl);
        params.put("contractId", cotId.intValue());
        params.put("contractUrl", cotId.intValue());
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            params.put("srvProvider", providerName);
        }
        if (comment != null) {
            // COMMENTED BY SERVICE PROVIDER
            if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
                for (String email : intsOrgEmails) {
                    this.contractProcessRequest.createContractCommentedByProvider(conn, sessionUsrId, cotId, email, Languages.LT, params);
                }
                for (HashMap<String, String> user : users) {
                    this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                            "COT_COMMENT_PROVIDER_SUBJECT", "COT_COMMENT_PROVIDER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGREEMENT_COMMENTED.getCode(), null, Utils.getDouble(user.get("usr_id")), contractDAO.getCot_org_id(),
                            null);
                }
            }
            // COMMENTED BY INTS OWNER
            else if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                for (String email : srvPrvdEmails) {
                    this.contractProcessRequest.createContractCommentedByOwner(conn, sessionUsrId, cotId, email, Languages.LT, params);
                }
                for (HashMap<String, String> user : users) {
                    this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                            "COT_COMMENT_OWNER_SUBJECT", "COT_COMMENT_OWNER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGREEMENT_COMMENTED.getCode(), null, Utils.getDouble(user.get("usr_id")), providerId, null);
                }
            }
        } else {
            // REJECTED BY SERVICE PROVIDER
            if (contractDAO.getCot_state().equals(NtisContractStatus.SUBMITTED.getCode())
                    && contractEdit.getCot_state().equals(NtisContractStatus.REJECTED.getCode())) {
                if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
                    for (String email : intsOrgEmails) {
                        this.contractProcessRequest.createContractRejectedByProviderRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                    }
                    for (HashMap<String, String> user : users) {
                        this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                                "COT_REJECTED_BY_PROVIDER_SUBJECT", "COT_REJECTED_BY_PROVIDER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                                NtisMessageSubject.MSG_SBJ_AGREEMENT_REJECTED.getCode(), null, Utils.getDouble(user.get("usr_id")), contractDAO.getCot_org_id(),
                                null);
                    }
                }
            }
            // SIGNED BY SERVICE PROVIDER
            else if (contractEdit.getCot_state().equals(NtisContractStatus.SUBMITTED.getCode())
                    && contractDAO.getCot_state().equals(NtisContractStatus.SIGNED_BY_SRV_PROV.getCode())) {
                if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
                    for (String email : intsOrgEmails) {
                        this.contractProcessRequest.createContractSignedByProviderJobRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                    }
                    for (HashMap<String, String> user : users) {
                        this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                                "COT_SIGNED_BY_PROVIDER_SUBJECT", "COT_SIGNED_BY_PROVIDER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                                NtisMessageSubject.MSG_SBJ_AGREEMENT_SIGNED.getCode(), null, Utils.getDouble(user.get("usr_id")), contractDAO.getCot_org_id(),
                                null);
                    }
                }
            }
            // CONTRACT PROJECT SIGNED BY OWNER
            else if (contractEdit.getCot_state().equals(NtisContractStatus.SIGNED_BY_SRV_PROV.getCode())
                    && contractDAO.getCot_state().equals(NtisContractStatus.SIGNED_BY_BOTH.getCode())) {
                if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                    for (String email : srvPrvdEmails) {
                        this.contractProcessRequest.createContractSignedByOwnerRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                    }
                    for (HashMap<String, String> user : users) {
                        this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                                "COT_SIGNED_BY_OWNER_SUBJECT", "COT_SIGNED_BY_OWNER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                                NtisMessageSubject.MSG_SBJ_AGREEMENT_SIGNED.getCode(), null, Utils.getDouble(user.get("usr_id")), providerId, null);
                    }
                }
            }
            // CONTRACT PROJECT REJECTED BY OWNER
            else if (contractDAO.getCot_state().equals(NtisContractStatus.SIGNED_BY_SRV_PROV.getCode())
                    && contractEdit.getCot_state().equals(NtisContractStatus.REJECTED.getCode())) {
                if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
                    for (String email : srvPrvdEmails) {
                        this.contractProcessRequest.createContractRejectedByOwner(conn, sessionUsrId, cotId, email, Languages.LT, params);
                    }
                    for (HashMap<String, String> user : users) {
                        this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                                "COT_REJECTED_BY_OWNER_SUBJECT", "COT_REJECTED_BY_OWNER_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                                NtisMessageSubject.MSG_SBJ_AGREEMENT_REJECTED.getCode(), null, Utils.getDouble(user.get("usr_id")), providerId, null);
                    }
                }

            }
            // TERMINATION REQUEST SUBMITTED BY OWNER
            else if (contractDAO.getCot_state().equals(NtisContractStatus.SIGNED_BY_BOTH.getCode())
                    && !contractEdit.getCot_state().equals(NtisContractStatus.TERMINATED.getCode()) && contractEdit.getCot_rejection_reason() != null) {
                for (String email : srvPrvdEmails) {
                    this.contractProcessRequest.createContractRequestToCancelRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                }
                for (HashMap<String, String> user : users) {
                    this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                            "COT_CANCELLATION_REQUEST_SUBJECT", "COT_CANCELLATION_REQUEST_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGREMENT_APPL_CANCEL.getCode(), null, Utils.getDouble(user.get("usr_id")), providerId, null);
                }
            }
            // TERMINATION REQUEST CONFIRMED BY SERVICE PROVIDER
            else if (contractEdit.getCot_state().equals(NtisContractStatus.TERMINATED.getCode())
                    && !contractDAO.getCot_state().equals(NtisContractStatus.TERMINATED.getCode()) && contractDAO.getCot_rejection_reason() != null) {
                for (String email : intsOrgEmails) {
                    this.contractProcessRequest.createContractRequestToCancelConfirmedRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                }
                for (HashMap<String, String> user : users) {
                    this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                            "COT_CANCELLATION_CONFIRMED_SUBJECT", "COT_CANCELLATION_CONFIRMED_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGREMENT_CANCELLATION_CONF.getCode(), null, Utils.getDouble(user.get("usr_id")),
                            contractDAO.getCot_org_id(), null);
                }
            }
            // CONTRACT TERMINATED BY SERVICE PROVIDER
            else if (contractEdit.getCot_state().equals(NtisContractStatus.TERMINATED.getCode()) && contractDAO.getCot_rejection_reason() == null) {
                for (String email : intsOrgEmails) {
                    this.contractProcessRequest.createContractCancellationInitiatedRequest(conn, sessionUsrId, cotId, email, Languages.LT, params);
                }
                for (HashMap<String, String> user : users) {
                    this.ntisNotificationsManager.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF",
                            "COT_CANCELLATION_INITIATED_SUBJECT", "COT_CANCELLATION_INITIATED_BODY", params, NtisNtfRefType.CONTRACT.getCode(),
                            NtisMessageSubject.MSG_SBJ_AGREEMENT_CANCELLED.getCode(), null, Utils.getDouble(user.get("usr_id")), contractDAO.getCot_org_id(),
                            null);
                }
            }
        }
    }

    private HashMap<String, String> getServiceProvider(Connection conn, Double cotId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                select org_name,
                       org_id
                from spr_organizations
                inner join ntis_services on srv_org_id = org_id
                inner join ntis_contract_services on cs_srv_id = srv_id and cs_cot_id = ?
                """);
        stmt.addSelectParam(cotId);
        List<HashMap<String, String>> data = this.baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        HashMap<String, String> provider = null;
        if (data != null && !data.isEmpty()) {
            provider = data.get(0);
        }
        return provider;
    }

    public String viewAndSignContract(Connection conn, Double cotId, Double perId, Double userId, Double orgId) throws Exception {
        cotId = this.checkAndSetContractId(conn, orgId, perId, cotId);
        Boolean isClient = this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS);
        return isense.startSigning(conn, cotId, perId, userId, orgId, isClient);
    }

    public void handleCallback(Connection conn, String data) throws Exception {
        isense.signingCallback(conn, data);
    }

    public String previewContract(Connection conn, Double cotId, Double perId, Double userId, Double orgId) throws Exception {
        cotId = this.checkAndSetContractId(conn, orgId, perId, cotId);
        Boolean isClient = this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS);
        return isense.startPreview(conn, cotId, perId, userId, orgId, isClient);
    }

    private void checkFormActions(Connection conn) throws Exception {
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)
                && this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            throw new Exception("Both, INTS owner actions and service provider actions cannot be assigned to the same user.");
        } else if (!this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)
                && !this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
    }
}