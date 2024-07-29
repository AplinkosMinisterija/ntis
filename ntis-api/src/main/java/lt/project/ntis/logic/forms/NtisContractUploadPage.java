package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisCommonActionsConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisContractServicesDAO;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.enums.NtisContractStatus;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.logic.forms.model.NtisContractEditModel;
import lt.project.ntis.logic.forms.model.NtisContractRequestService;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.logic.forms.processRequests.ContractProcessRequest;
import lt.project.ntis.models.NtisContractUploadRequest;
import lt.project.ntis.service.NtisContractServicesDBService;
import lt.project.ntis.service.NtisContractsDBService;

/**
 * Klasė skirta formos "Įkelti paslaugų teikimo sutartį" (S1220) biznio logikai apibrėžti
 */
@Component
public class NtisContractUploadPage extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    private final NtisContractsDBService ntisContractsDBService;

    private final NtisContractServicesDBService ntisContractServicesDBService;

    private final SprFilesDBService sprFilesDBService;

    private final FileStorageService fileStorageService;

    private final SprOrganizationsDBService sprOrganizationsDBService;

    private final NtisNotificationsManager ntisNotifications;

    private final ContractProcessRequest contractProcessRequest;

    private final SprPersonsDBService sprPersonsDBService;

    private final SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    public NtisContractUploadPage(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, NtisContractsDBService ntisContractsDBService,
            NtisContractServicesDBService ntisContractServicesDBService, SprFilesDBService sprFilesDBService, FileStorageService fileStorageService,
            SprOrganizationsDBService sprOrganizationsDBService, NtisNotificationsManager ntisNotifications, ContractProcessRequest contractProcessRequest,
            SprPersonsDBService sprPersonsDBService, SprOrgUsersDBServiceImpl sprOrgUsersDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ntisContractsDBService = ntisContractsDBService;
        this.ntisContractServicesDBService = ntisContractServicesDBService;
        this.sprFilesDBService = sprFilesDBService;
        this.fileStorageService = fileStorageService;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
        this.ntisNotifications = ntisNotifications;
        this.contractProcessRequest = contractProcessRequest;
        this.sprPersonsDBService = sprPersonsDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;

    }

    @Override
    public String getFormName() {
        return "NTIS_CONTRACT_UPLOAD_PAGE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Įkelti paslaugų teikimo sutartį", "Įkelti paslaugų teikimo sutartį");
        addFormActionCRUD();
        addFormAction(NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC,
                NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS_DESC);
        addFormAction(NtisCommonActionsConstants.INTS_OWNER_ACTIONS, NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC,
                NtisCommonActionsConstants.INTS_OWNER_ACTIONS_DESC);
    }

    /**
    * Metodas grąžins NtisContractEditModel objektą pagal pateiktą NtisContractUploadRequest objektą
    * @param conn - prisijungimas prie DB
    * @param tempContract - NtisContractUploadRequest objektas
    * @param lang - kalba, naudojama vertimams
    * @return NtisContractEditModel objektas
    * @throws Exception
    */
    public NtisContractEditModel loadTempContract(Connection conn, NtisContractUploadRequest tempContract, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        checkFormActions(conn);

        NtisContractEditModel record = new NtisContractEditModel();
        record.setCot_state(NtisContractStatus.SIGNED_BY_BOTH.getCode());
        if (tempContract.getWtfId() != null) {
            record.setCot_wtf_id(tempContract.getWtfId());
            record.setWtf_info(loadWtfInfo(conn, lang, tempContract.getWtfId()));
        }

        record.setSp_info(loadServiceProviderInfo(conn, tempContract.getSpOrgId()));
        SprFilesDAO fileDB = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(tempContract.getFilKey()));
        record.setCot_fil_id(fileDB.getFil_id());
        record.setAttachment(loadFile(conn, fileDB.getFil_id()));
        record.setCot_services(loadServices(conn, tempContract.getSpOrgId(), lang));
        return record;
    }

    /**
     * Metodas išsaugos į duomenų bazę naują sutartį ir grąžins NtisContractEditModel objektą
     * @param conn - prisijungimas prie DB
     * @param record - NtisContractEditModel objektas
     * @param orgId - organizacijos ID
     * @param perId - asmens ID
     * @param lang - kalba
     * @return NtisContractEditModel objektas
     * @throws Exception
     */
    public NtisContractEditModel saveContract(Connection conn, NtisContractEditModel record, Double orgId, Double perId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_CREATE);
        checkFormActions(conn);

        // save contract
        NtisContractsDAO contractDAO = new NtisContractsDAO();
        contractDAO.setCot_state(record.getCot_state());
        contractDAO.setCot_code(record.getCot_code());
        contractDAO.setCot_from_date(record.getCot_from_date());
        contractDAO.setCot_to_date(record.getCot_to_date());
        contractDAO.setCot_created(new Date());
        contractDAO.setCot_created_in_ntis_portal("N");
        contractDAO.setCot_wtf_id(record.getCot_wtf_id());
        SprFilesDAO fileDB = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(record.getAttachment().getFil_key()));
        contractDAO.setCot_fil_id(fileDB.getFil_id());
        contractDAO.setJarOrgId(record.getSp_info().getOrg_id());
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.INTS_OWNER_ACTIONS)) {
            if (orgId != null) {
                contractDAO.setCot_org_id(orgId);
            } else if (perId != null) {
                contractDAO.setCot_per_id(perId);
            }
        }
        contractDAO = ntisContractsDBService.saveRecord(conn, contractDAO);

        for (NtisContractRequestService service : record.getCot_services()) {
            NtisContractServicesDAO ntisContractServicesDAO = ntisContractServicesDBService.newRecord();
            ntisContractServicesDAO.setCs_srv_id(service.getSrv_id());
            ntisContractServicesDAO.setCs_price(service.getPrice());
            ntisContractServicesDAO.setCs_cot_id(contractDAO.getCot_id());
            ntisContractServicesDBService.saveRecord(conn, ntisContractServicesDAO);
            service.setCs_id(ntisContractServicesDAO.getCs_id());
        }

        // confirm uploaded file
        sprFilesDBService.markAsConfirmed(conn, fileDB);

        // update record
        record.setWtf_info(loadWtfInfo(conn, lang, contractDAO.getCot_wtf_id()));
        record.setCot_id(contractDAO.getCot_id());
        ObjectMapper mapper = new ObjectMapper();
        String servicesJSON = mapper.writeValueAsString(record.getCot_services());
        contractDAO.setServicesJson(servicesJSON);
        ntisContractsDBService.saveRecord(conn, contractDAO);

        return record;
    }

    /**
     * Metodas grąžins NtisContractEditModel objektą pagal pateiktą sutarties ID bei sesijos organizacijos arba asmens ID
     * @param conn - prisijungimas prie DB
     * @param cotId - sutarties ID
     * @param orgId - organizacijos ID
     * @param perId - asmens ID
     * @param lang- - kalba
     * @return NtisContractEditModel objektas
     * @throws Exception
     */
    public NtisContractEditModel loadUploadedContract(Connection conn, Double cotId, Double orgId, Double perId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        checkFormActions(conn);
        NtisContractEditModel record = new NtisContractEditModel();
        NtisContractsDAO contractDAO;
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            contractDAO = ntisContractsDBService.loadRecordByParams(conn, " where cot_id = ? and n01 = ? ", new SelectParamValue(cotId),
                    new SelectParamValue(orgId));
        } else {
            contractDAO = ntisContractsDBService.loadRecordByIdAndClientId(conn, orgId, perId, cotId);
        }

        record.setCot_id(contractDAO.getCot_id());
        record.setCot_state(contractDAO.getCot_state());
        record.setCot_code(contractDAO.getCot_code());
        record.setCot_from_date(contractDAO.getCot_from_date());
        record.setCot_to_date(contractDAO.getCot_to_date());
        record.setCot_fil_id(contractDAO.getCot_fil_id());
        record.setAttachment(loadFile(conn, contractDAO.getCot_fil_id()));
        record.setCot_wtf_id(contractDAO.getCot_wtf_id());
        record.setWtf_info(loadWtfInfo(conn, lang, contractDAO.getCot_wtf_id()));
        record.setJarOrgId(contractDAO.getJarOrgId());
        record.setSp_info(loadServiceProviderInfo(conn, contractDAO.getJarOrgId()));

        ObjectMapper mapper = new ObjectMapper();
        List<NtisContractRequestService> serviceList = new ArrayList<>();
        serviceList = Arrays.asList(mapper.readValue(contractDAO.getServicesJson(), NtisContractRequestService[].class));
        record.setCot_services(serviceList);

        return record;
    }

    /**
     * Metodas ištrins sutartį iš duomenų bazės pagal priskirtą form action'ą 
     * ir pateiktus sutarties ID bei organizacijos ir asmens ID
     * @param conn - prisijungimas prie DB
     * @param cotId - sutarties ID
     * @param orgId - sesijos organizacijos ID
     * @param perId - sesijos asmens ID
     * @throws Exception
     */
    public void deleteContract(Connection conn, Double cotId, Double orgId, Double perId) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_DELETE);
        checkFormActions(conn);
        NtisContractsDAO contractDAO;
        if (this.isFormActionAssigned(conn, NtisCommonActionsConstants.SERVICE_PROVIDER_ACTIONS)) {
            contractDAO = ntisContractsDBService.loadRecordByParams(conn, " where cot_id = ? and n01 = ? ", new SelectParamValue(cotId),
                    new SelectParamValue(orgId));
        } else {
            contractDAO = ntisContractsDBService.loadRecordByIdAndClientId(conn, orgId, perId, cotId);
        }
        contractDAO.setCot_state(NtisContractStatus.TERMINATED.getCode());
        contractDAO.setCot_to_date(Utils.getDate(new Date()));
        contractDAO.setCot_rejection_date(Utils.getDate(new Date()));
        ntisContractsDBService.saveRecord(conn, contractDAO);
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

    private SprOrganizationsDAO loadServiceProviderInfo(Connection conn, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        return sprOrganizationsDBService.loadRecord(conn, orgId);
    }

    private NtisWtfInfo loadWtfInfo(Connection conn, String lang, Double wtfId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                SELECT WTF.wtf_id AS id,
                CASE WHEN WTF.WTF_AD_ID IS NULL
                     THEN (
                           SELECT mp.mp_name
                           FROM ntis_wastewater_treatment_faci wtf
                           JOIN ntis_municipalities mp ON wtf.wtf_facility_municipality_code = mp.mp_code
                           WHERE wtf.wtf_id = ?
                          ) || ' (' || WTF.WTF_FACILITY_LATITUDE || ', ' || WTF.WTF_FACILITY_LONGITUDE || ')'
                     ELSE WAV.FULL_ADDRESS_TEXT
                     END AS address,
                COALESCE(TYP.RFT_DISPLAY_CODE, TYP.RFC_MEANING) AS type,
                WTF_TECHNICAL_PASSPORT_ID AS technicalPassport,
                COALESCE(MNF.RFT_DISPLAY_CODE, MNF.RFC_MEANING) AS manufacturer,
                COALESCE(MDL.RFT_DISPLAY_CODE, MDL.RFC_MEANING) AS model,
                WTF_DISTANCE AS distance,
                TO_CHAR(WTF_INSTALLATION_DATE, '%s') AS installationDate
                FROM NTIS_WASTEWATER_TREATMENT_FACI WTF
                LEFT JOIN NTIS_ADDRESS_VW WAV ON WTF.WTF_AD_ID = WAV.ADDRESS_ID
                LEFT JOIN SPR_REF_CODES_VW TYP ON TYP.RFC_CODE = WTF.WTF_TYPE AND TYP.RFC_DOMAIN = 'NTIS_WTF_TYPE' AND TYP.RFT_LANG = ?
                LEFT JOIN SPR_REF_CODES_VW MNF ON MNF.RFC_CODE = WTF.WTF_MANUFACTURER AND MNF.RFC_DOMAIN = 'NTIS_FACIL_MANUFA' AND MNF.RFT_LANG = TYP.RFT_LANG
                LEFT JOIN SPR_REF_CODES_VW MDL ON MDL.RFC_CODE = WTF.WTF_MODEL AND MDL.RFC_DOMAIN = 'NTIS_FACIL_MODEL' AND MDL.RFT_LANG = TYP.RFT_LANG
                WHERE wtf_id = ?
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(wtfId);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wtfId);

        List<NtisWtfInfo> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisWtfInfo.class);
        if (queryResult == null || queryResult.isEmpty()) {
            throw new Exception("No wastewater treatment facility information was found");
        }
        return queryResult.get(0);
    }

    private List<NtisContractRequestService> loadServices(Connection conn, Double orgId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                SELECT srv_type AS type,
                       typ.rfc_meaning AS name,
                       COALESCE(srv_description, '-') AS description,
                       srv_id
                FROM ntis_services srv
                JOIN ntis_service_req_items ON sri_srv_id = srv_id
                JOIN ntis_service_requests sr ON sri_sr_id = sr_id AND sr_status = 'CONFIRMED'
                JOIN spr_ref_codes_vw typ ON typ.rfc_code = srv.srv_type AND typ.rfc_domain = 'NTIS_SRV_ITEM_TYPE' AND typ.rft_lang = ?
                WHERE srv_org_id = ? AND srv_contract_available = '%s'
                """.formatted(DbConstants.BOOLEAN_TRUE));
        stmt.addSelectParam(lang);
        stmt.addSelectParam(orgId);
        List<NtisContractRequestService> queryResult = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisContractRequestService.class);
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
        queryResult.get(0).setFil_key(fileStorageService.encryptFileKey(queryResult.get(0).getFil_key()));
        return queryResult.get(0);
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui pagal įkeltos sutarties ID
     * @param conn - prisijungimas prie DB
     * @param cotId - sutarties id
     * @param usrId - sesijos naudotojo id
     * @param perId - sesijos asmens id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotifications(Connection conn, Double cotId, Double usrId, Double orgId, Double perId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        checkFormActions(conn);

        // contract info
        NtisContractsDAO contractDAO = ntisContractsDBService.loadRecordByIdAndClientId(conn, orgId, perId, cotId);
        String clientName = "";
        if (orgId != null) {
            SprOrganizationsDAO clientOrg = sprOrganizationsDBService.loadRecord(conn, contractDAO.getCot_org_id());
            clientName = clientOrg.getOrg_name();
        } else {
            SprPersonsDAO clientPer = sprPersonsDBService.loadRecord(conn, contractDAO.getCot_per_id());
            clientName = clientPer.getPer_name() + " " + clientPer.getPer_surname();
        }
        SprOrganizationsDAO serviceProvider = sprOrganizationsDBService.loadRecord(conn, contractDAO.getJarOrgId());

        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("clientName", clientName);
        context.put("contractId", cotId.intValue());

        // send emails
        if (serviceProvider.getC02() != null && serviceProvider.getC02().equals(DbConstants.BOOLEAN_TRUE) && serviceProvider.getOrg_email() != null) {
            List<String> serviceProviderEmails = new ArrayList<>();
            serviceProviderEmails = Arrays.asList(serviceProvider.getOrg_email().split("\\s*,\\s*"));
            for (String email : serviceProviderEmails) {
                contractProcessRequest.createContractUploadedByOwner(conn, usrId, cotId, email, Languages.getLanguageByCode(lang), context);
            }
        }
        // send notifications
        String roleCodes = """
                '%s', '%s', '%s'
                """.formatted(NtisRolesConstants.VAND_ADMIN, NtisRolesConstants.PASL_ADMIN, NtisRolesConstants.CONTRACT_MANAGER);
        List<SprUsersDAO> serviceProviderUsers = sprOrgUsersDBService.getOrganizationUsers(conn, serviceProvider.getOrg_id(), roleCodes);
        for (SprUsersDAO user : serviceProviderUsers) {
            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, cotId, "NTIS_CONTRACT_NOTIF", "COT_UPLOADED_BY_OWNER_SUBJECT",
                    "COT_UPLOADED_BY_OWNER_BODY", context, NtisNtfRefType.CONTRACT.getCode(), NtisMessageSubject.MSG_SBJ_AGREEMENT_SIGNED.getCode(), new Date(),
                    user.getUsr_id(), user.getUsr_org_id(), null);
        }
    }
}
