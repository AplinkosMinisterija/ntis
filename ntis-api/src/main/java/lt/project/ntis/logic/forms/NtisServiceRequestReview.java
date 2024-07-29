package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.Data2ObjectProcessor;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprOrgAvailableRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgAvailableRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import lt.project.ntis.constants.NtisOrgStateConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.dao.NtisServiceReqStatusLogsDAO;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.enums.NtisServiceReqStatus;
import lt.project.ntis.enums.NtisServiceType;
import lt.project.ntis.logic.forms.model.NtisServiceReqDetails;
import lt.project.ntis.logic.forms.processRequests.SrvReqProcessRequest;
import lt.project.ntis.service.NtisServiceReqItemsDBService;
import lt.project.ntis.service.NtisServiceReqStatusLogsDBService;
import lt.project.ntis.service.NtisServiceRequestsDBService;

/**
 * Klasė skirta formų "Prašymo teikti paslaugas peržiūra (sistemos administratoriui)", "Prašymo teikti paslaugas peržiūra (vandentvarka) (sistemos administratoriui)",
 * "Prašymo teikti paslaugas peržiūra (paslaugų teikėjams)" ir "Prašymo teikti paslaugas peržiūra (vandentvarka) (vandentvarkos įmonėms)" biznio logikai apibrėžti
 */
@Component
public class NtisServiceRequestReview extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisServiceRequestsDBService ntisServiceRequestsDBService;

    @Autowired
    NtisServiceReqStatusLogsDBService ntisServiceReqStatusLogsDBService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SrvReqProcessRequest srvReqProcessRequest;

    @Autowired
    NtisNotificationsManager ntisNotifications;

    @Autowired
    SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService;

    @Autowired
    SprOrgUserRolesDBService sprOrgUserRolesDBService;

    @Autowired
    SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    @Autowired
    SprRolesDBService sprRolesDBService;

    @Autowired
    NtisServiceReqItemsDBService ntisServiceReqItemsDBService;

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_REQUEST_REVIEW";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Prašymo teikti paslaugas peržiūra", "Prašymo teikti paslaugas peržiūra");
        addFormActions(FormBase.ACTION_CREATE);
        addFormActions(FormBase.ACTION_READ);
        addFormActions(FormBase.ACTION_UPDATE);
    }

    /**
     * Function will return service request details for provided service request id
     * @param conn - connection to the DB
     * @param lang - language code, used for translation
     * @param srId - provided service request id
     * @return NtisServiceReqDetails object
     * @throws Exception
     */
    public NtisServiceReqDetails getServiceRequestInfo(Connection conn, String lang, Double srId, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceRequestReview.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT SR_ID,
                     SR_RESP_PERSON_DESCRIPTION,
                     TO_CHAR(SR_REGISTRATION_DATE, '%s') AS SR_REGISTRATION_DATE,
                     SR_STATUS,
                     COALESCE(SRS.RFT_DISPLAY_CODE, SRS.RFC_MEANING) AS SR_STATUS_MEANING,
                     TO_CHAR(SR_STATUS_DATE, '%s') AS SR_STATUS_DATE,
                     SR_REMOVAL_REASON,
                     (
                         SELECT STRING_AGG(SRI_SERVICE_TYPE, ', ')
                         FROM NTIS_SERVICE_REQ_ITEMS
                         WHERE SRI_SR_ID = SR_ID
                     ) AS REGISTERED_SERVICES,
                     COALESCE(ORG_TYPE || ' ','') || ORG_NAME AS ORG_NAME,
                     ORG_CODE,
                     CONCAT_WS(', ', NULLIF(TRIM(ORG_ADDRESS), ''), NULLIF(TRIM(ORG_HOUSE_NUMBER), ''), NULLIF(TRIM(ORG_REGION), '')) AS ORG_ADDRESS,
                     SR_ORG_ID,
                     SR_EMAIL,
                     SR_PHONE,
                     SR_HOMEPAGE,
                     SR_TYPE,
                     SR_USR_ID
                FROM NTIS_SERVICE_REQUESTS
                JOIN SPR_ORGANIZATIONS ORG ON SR_ORG_ID = ORG_ID
                JOIN SPR_REF_CODES_VW SRS ON SRS.RFC_CODE = SR_STATUS AND SRS.RFC_DOMAIN = 'NTIS_SRV_REQ_STATUS' AND SRS.RFT_LANG = ?
                WHERE SR_ID = ?::int
                              """.formatted(dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(lang);
        stmt.addSelectParam(srId);
        stmt.setWhereExists(true);
        if (!this.isFormActionAssigned(conn, ACTION_UPDATE)) {
            stmt.addParam4WherePart(" SR_ORG_ID = ?::int ", orgId);
        }
        Data2ObjectProcessor<NtisServiceReqDetails> dataProcessor = new Data2ObjectProcessor<NtisServiceReqDetails>(NtisServiceReqDetails.class);
        List<NtisServiceReqDetails> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, dataProcessor);
        if (queryResult != null && !queryResult.isEmpty()) {
            NtisServiceReqDetails result = queryResult.get(0);
            result.setReqItemsDAO((ArrayList<NtisServiceReqItemsDAO>) ntisServiceReqItemsDBService.loadRecordsByParams(conn, " WHERE sri_sr_id = ?::int ",
                    new SelectParamValue(srId)));
            return result;
        } else {
            throw new Exception("No information for service request with id " + srId + " was found");
        }
    }

    /**
     * Function will return service request files for provided service request id
     * @param conn - connection to the DB
     * @param srId - provided service request id
     * @param orgId - session organization id
     * @param usrId - session user id
     * @return array of SprFile objects
     * @throws Exception
     */
    public List<SprFile> getServiceRequestFiles(Connection conn, Double srId, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceRequestReview.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT FIL_ID, " //
                + " FIL_CONTENT_TYPE, " //
                + " FIL_KEY, " //
                + " FIL_NAME, " //
                + " FIL_SIZE, " //
                + " FIL_STATUS, " //
                + " TO_CHAR(FIL_STATUS_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS FIL_STATUS_DATE " //
                + " FROM NTIS_SERVICE_REQ_FILES " //
                + " INNER JOIN NTIS_SERVICE_REQUESTS ON SR_ID = SRF_SR_ID " //
                + " INNER JOIN SPR_ORG_USERS ON SR_ORG_ID = OU_ORG_ID AND CURRENT_DATE BETWEEN OU_DATE_FROM AND COALESCE(OU_DATE_TO, now()) " //
                + " LEFT JOIN SPR_FILES ON SRF_FIL_ID = FIL_ID " //
                + " WHERE SRF_SR_ID = ?::int ");
        stmt.addSelectParam(srId);
        stmt.setWhereExists(true);
        stmt.setRecordCountLimitPart("LIMIT 1");
        if (!this.isFormActionAssigned(conn, ACTION_UPDATE)) {
            stmt.addParam4WherePart(" ou_org_id = ?::int ", orgId);
            stmt.addParam4WherePart("ou_usr_id = ?::int", usrId);
        }

        Data2ObjectProcessor<SprFile> dataProcessor = new Data2ObjectProcessor<SprFile>(SprFile.class);
        List<SprFile> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, dataProcessor);

        return queryResult;
    }

    /**
     * Function will update provided service request status and save NtisServiceReqStatusLogsDAO object to the DB. 
     * Before update function will check if user has right to perform this action (update)
     * @param conn - connection to the DB
     * @param record - provided NtisServiceReqDetails object
     * @throws Exception
     */
    public void setServiceRequestStatus(Connection conn, NtisServiceReqDetails record) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceRequestReview.ACTION_UPDATE);
        Date status_date = new Date();
        NtisServiceRequestsDAO serviceRequest = ntisServiceRequestsDBService.loadRecord(conn, Utils.getDouble(record.getSr_id()));
        serviceRequest.setSr_status(record.getSr_status());
        serviceRequest.setSr_status_date(status_date);
        serviceRequest.setSr_removal_reason(record.getSr_removal_reason());
        ntisServiceRequestsDBService.updateRecord(conn, serviceRequest);

        NtisServiceReqStatusLogsDAO statusLog = ntisServiceReqStatusLogsDBService.newRecord();
        statusLog.setSrs_sr_id(Utils.getDouble(record.getSr_id()));
        statusLog.setSrs_status(record.getSr_status());
        statusLog.setSrs_status_date(status_date);
        ntisServiceReqStatusLogsDBService.saveRecord(conn, statusLog);

        if (record.getSr_status().equalsIgnoreCase(NtisServiceReqStatus.CONFIRMED.getCode())) {
            // change organization state
            SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, record.getSr_org_id());
            if (orgDao.getOrgState() == null || (orgDao.getOrgState().equals(NtisOrgStateConstants.DEREGISTERED))) {
                orgDao.setOrgState(NtisOrgStateConstants.REGISTERED);
                orgDao.setOrgRegisteredDate(Utils.getDate(new Date()));
                orgDao.setOrgDeregisteredDate(null);
                orgDao.setManageOrgRoles(1.0);
                sprOrganizationsDBService.saveRecord(conn, orgDao);
            }
            // set organization type
            setOrgType(conn, record.getSr_org_id());
            // update organization roles
            updateOrgRoles(conn, record);
        } else if (record.getSr_status().equalsIgnoreCase(NtisServiceReqStatus.REJECTED.getCode())) {
            if(record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode()))) {
                SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, record.getSr_org_id());
                orgDao.setFacilityInstallerDateFrom(null);
                orgDao.setOrgDeregisteredDate(null);
                orgDao.setManageOrgRoles(1.0);
                sprOrganizationsDBService.saveRecord(conn, orgDao);
            }
        }
    }

    private void updateOrgRoles(Connection conn, NtisServiceReqDetails record) throws Exception {
        Double orgId = record.getSr_org_id();
        Double applicationUserId = record.getSr_usr_id();
        Double adminOrgUsrId = sprOrgUsersDBService.loadRecordByParams(conn, " WHERE ou_usr_id = ?::int AND ou_org_id = ?::int ",
                new SelectParamValue(applicationUserId), new SelectParamValue(orgId)).getOu_id();
        if (record.getSr_type().equals(NtisOrgType.PASLAUG.getCode())) {
            if (!hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.LAB_SPECIALIST)) {
                List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ",
                        new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST));
                setOrgAvailableRoles(conn, orgId, newRolesIds);
            }
            if (!hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.CONTRACT_MANAGER)) {
                List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ",
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER));
                setOrgAvailableRoles(conn, orgId, newRolesIds);
            }
            if (!hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.PASL_ADMIN)) {
                List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN));
                setOrgAvailableRoles(conn, orgId, newRolesIds);
            }
            // set admin role for applicant user
            SprRolesDAO adminRoleId = this.sprRolesDBService.loadRecordByParams(conn, " rol_code = ? ", new SelectParamValue(NtisRolesConstants.PASL_ADMIN));
            setOrgUserRole(conn, adminOrgUsrId, adminRoleId.getRol_id());
        } else if (record.getSr_type().equals(NtisOrgType.VANDEN.getCode())) {
            if (!hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.PLANT_SPECIALIST)) {
                List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ",
                        new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST));
                setOrgAvailableRoles(conn, orgId, newRolesIds);
            }
            if (!hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.VAND_ADMIN)) {
                List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ",
                        new SelectParamValue(NtisRolesConstants.VAND_ADMIN));
                setOrgAvailableRoles(conn, orgId, newRolesIds);
            }
            // set admin role for applicant user
            SprRolesDAO adminRoleId = this.sprRolesDBService.loadRecordByParams(conn, " rol_code = ? ", new SelectParamValue(NtisRolesConstants.VAND_ADMIN));
            setOrgUserRole(conn, adminOrgUsrId, adminRoleId.getRol_id());
        } else {
            throw new Exception("Invalid service type");
        }
        if (hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.ORG_NEW)) {
            Double currentRoleId = this.sprRolesDBService.loadRecordByParams(conn, " rol_code = ? ", new SelectParamValue(NtisRolesConstants.ORG_NEW))
                    .getRol_id();
            Double orgAvailableRoleId = sprOrgAvailableRolesDBService.loadRecordByParams(conn, " WHERE oar_rol_id = ?::int AND oar_org_id = ?::int ",
                    new SelectParamValue(currentRoleId), new SelectParamValue(orgId)).getOar_id();
            List<RecordIdentifier> orgUsersIds = getOrgUsers(conn, orgId);
            deleteOrgNewRecords(conn, orgAvailableRoleId, orgUsersIds, currentRoleId);
        }
        sprOrgUserRolesDBService.refreshUserOrgProfile(conn, orgId, applicationUserId);
    }

    private boolean hasOrganizationRole(Connection conn, Double orgId, String roleCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT oar_id as id
                FROM spr_org_available_roles oar
                JOIN spr_roles rol ON rol_code = ? AND oar_rol_id = rol_id
                WHERE oar_org_id = ?::int
                """);
        stmt.addSelectParam(roleCode);
        stmt.addSelectParam(orgId);
        List<RecordIdentifier> oarIds = queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
        return !oarIds.isEmpty();
    }

    private void setOrgType(Connection conn, Double srOrgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT " //
                + " CASE WHEN count(distinct sr_type) > 1 THEN '" + NtisOrgType.PASLAUG_VANDEN + "' " //
                + "      ELSE max(sr_type) END as org_type" //
                + " FROM ntis_service_requests " //
                + " WHERE sr_org_id = ?::int ");
        stmt.addSelectParam(srOrgId);
        HashMap<String, String> orgType = queryController.selectQueryAsDataArrayList(conn, stmt).get(0);
        SprOrganizationsNtisDAO orgDAO = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, srOrgId);
        if (orgDAO.getOrgType() == null || !orgDAO.getOrgType().equals(orgType.get("org_type"))) {
            orgDAO.setOrgType(orgType.get("org_type"));
            orgDAO.setManageOrgRoles(1.0);
            sprOrganizationsDBService.updateRecord(conn, orgDAO);
        }
    }

    private void setOrgAvailableRoles(Connection conn, Double orgId, List<SprRolesDAO> newRolesIds) throws Exception {
        for (SprRolesDAO roleId : newRolesIds) {
            SprOrgAvailableRolesDAO availableRoleDAO = new SprOrgAvailableRolesDAO();
            availableRoleDAO.setOar_date_from(Utils.getDate());
            availableRoleDAO.setOar_org_id(orgId);
            availableRoleDAO.setOar_rol_id(roleId.getRol_id());
            sprOrgAvailableRolesDBService.saveRecord(conn, availableRoleDAO);
        }
    }

    private void setOrgUserRole(Connection conn, Double adminOrgUsrId, Double newRoleId) throws Exception {
        SprOrgUserRolesDAO existingOrgUserRole = sprOrgUserRolesDBService.loadRecordByParams(conn, " WHERE our_ou_id = ?::int AND our_rol_id = ?::int ",
                new SelectParamValue(adminOrgUsrId), new SelectParamValue(newRoleId));
        if (existingOrgUserRole == null) {
            SprOrgUserRolesDAO orgUserRoleDAO = new SprOrgUserRolesDAO();
            orgUserRoleDAO.setOur_date_from(Utils.getDate());
            orgUserRoleDAO.setOur_ou_id(adminOrgUsrId);
            orgUserRoleDAO.setOur_rol_id(newRoleId);
            sprOrgUserRolesDBService.saveRecord(conn, orgUserRoleDAO);
        }
    }

    private List<RecordIdentifier> getOrgUsers(Connection conn, Double orgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT OU_ID AS ID " //
                + " FROM SPR_ORG_USERS " //
                + " WHERE OU_ORG_ID = ?::int ");
        stmt.addSelectParam(orgId);
        List<RecordIdentifier> usersIds = queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
        return usersIds;
    }

    private void deleteOrgNewRecords(Connection conn, Double orgAvailableRoleId, List<RecordIdentifier> orgUsersIds, Double currentRoleId) throws Exception {
        sprOrgAvailableRolesDBService.deleteRecord(conn, orgAvailableRoleId);
        Iterator<RecordIdentifier> i = orgUsersIds.iterator();
        while (i.hasNext()) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement(" DELETE FROM SPR_ORG_USER_ROLES WHERE OUR_OU_ID = ?::int AND OUR_ROL_ID = ?::int ");
            stmt.addSelectParam(i.next().getIdAsDouble());
            stmt.addSelectParam(currentRoleId);
            queryController.adjustRecordsInDB(conn, stmt);
        }
    }

    /**
     * Metodas išsiųs pranešimus ir email'us vandentvarkos įmonei arba paslaugų teikėjui pagal pateiktą prašymo id
     * @param conn - prisijungimas prie DB
     * @param srId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double srId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);

        // service request info
        NtisServiceRequestsDAO requestDAO = ntisServiceRequestsDBService.loadRecord(conn, srId);
        SprOrganizationsNtisDAO serviceOrg = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, requestDAO.getSr_org_id());
        List<String> serviceOrgEmails = new ArrayList<>();
        if (requestDAO.getSr_email() != null && !requestDAO.getSr_email().isBlank()) {
            serviceOrgEmails.add(requestDAO.getSr_email());
        } else if (serviceOrg.getC02() != null && serviceOrg.getC02().equals(DbConstants.BOOLEAN_TRUE) && serviceOrg.getOrg_email() != null
                && !serviceOrg.getOrg_email().isBlank()) {
            serviceOrgEmails = Arrays.asList(serviceOrg.getOrg_email().split("\\s*,\\s*"));
        }
        String roleCodes = """
                '%s', '%s'
                """.formatted(NtisRolesConstants.ORG_NEW,
                requestDAO.getSr_type().equals(NtisServiceType.VANDEN.getCode()) ? NtisRolesConstants.VAND_ADMIN : NtisRolesConstants.PASL_ADMIN);
        if (requestDAO.getSr_type().equals(NtisServiceType.VANDEN.getCode()) && serviceOrg.getOrgType() != null
                && serviceOrg.getOrgType().equals(NtisOrgType.PASLAUG.getCode())) {
            roleCodes += ", '" + NtisRolesConstants.PASL_ADMIN + "'";
        } else if (requestDAO.getSr_type().equals(NtisServiceType.PASLAUG.getCode()) && serviceOrg.getOrgType() != null
                && serviceOrg.getOrgType().equals(NtisOrgType.VANDEN.getCode())) {
            roleCodes += ", '" + NtisRolesConstants.VAND_ADMIN + "'";
        }
        List<SprUsersDAO> serviceOrgUsers = sprOrgUsersDBService.getOrganizationUsers(conn, serviceOrg.getOrg_id(), roleCodes);
        String requestUrl = requestDAO.getSr_type().toLowerCase() + "/email?token=";

        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("serviceType", getSrvReqItems(conn, srId, lang));
        context.put("requestId", srId.intValue());
        context.put("requestUrl", requestUrl);
        context.put("requestType", requestDAO.getSr_type().toLowerCase());

        // send notifications and emails
        String templateSubject = "";
        String templateBody = "";
        String messageSubject = "";

        if (requestDAO.getSr_status().equals(NtisServiceReqStatus.CONFIRMED.getCode())) {
            templateSubject = "SRV_REQ_CONFIRM_SUBJECT";
            templateBody = "SRV_REQ_CONFIRM_BODY";
            messageSubject = NtisMessageSubject.MSG_SBJ_APPLICATION_ACCEPTED.getCode();
            for (String email : serviceOrgEmails) {
                srvReqProcessRequest.createSrvReqConfirmedRequest(conn, usrId, srId, email, Languages.getLanguageByCode(lang), context);
            }
        } else if (requestDAO.getSr_status().equals(NtisServiceReqStatus.REJECTED.getCode())) {
            templateSubject = "SRV_REQ_REJECT_SUBJECT";
            templateBody = "SRV_REQ_REJECT_BODY";
            messageSubject = NtisMessageSubject.MSG_SBJ_APPLICATION_REJECTED.getCode();
            for (String email : serviceOrgEmails) {
                srvReqProcessRequest.createSrvReqRejectedRequest(conn, usrId, srId, email, Languages.getLanguageByCode(lang), context);
            }
        }
        for (SprUsersDAO user : serviceOrgUsers) {
            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, srId, "NTIS_SRV_REQ_NOTIF", templateSubject, templateBody,
                    context, NtisNtfRefType.SRV_REQ.getCode(), messageSubject, new Date(), user.getUsr_id(), serviceOrg.getOrg_id(), null);
        }
    }

    private String getSrvReqItems(Connection conn, Double srId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT srv_type.c01 as rfc_meaning
                FROM ntis.ntis_service_req_items sri
                JOIN spr_ref_codes srv_type ON srv_type.rfc_code = sri.sri_service_type and srv_type.rfc_domain = 'NTIS_SRV_ITEM_TYPE'
                JOIN spr_ref_translations rft ON rft.rft_rfc_id = srv_type.rfc_id and rft.rft_lang = ?
                WHERE sri_sr_id = ?::int
                                        """);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(srId);
        List<HashMap<String, String>> queryResult = queryController.selectQueryAsDataArrayList(conn, stmt);
        List<String> serviceItems = new ArrayList<>();
        for (HashMap<String, String> item : queryResult) {
            serviceItems.add(item.get("rfc_meaning"));
        }
        String result = String.join(", ", serviceItems).toLowerCase();
        return result;
    }
}