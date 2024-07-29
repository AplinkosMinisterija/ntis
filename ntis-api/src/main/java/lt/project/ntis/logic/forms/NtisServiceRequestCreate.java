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

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
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
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprOrgAvailableRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBServiceImpl;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.logic.forms.SprNotifications;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisOrgStateConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.dao.NtisServiceReqFilesDAO;
import lt.project.ntis.dao.NtisServiceReqItemsDAO;
import lt.project.ntis.dao.NtisServiceReqStatusLogsDAO;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
import lt.project.ntis.enums.NtisMessageSubject;
import lt.project.ntis.enums.NtisNtfRefType;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.enums.NtisServiceReqStatus;
import lt.project.ntis.enums.NtisServiceType;
import lt.project.ntis.logic.forms.model.NtisNewServiceRequest;
import lt.project.ntis.logic.forms.model.NtisServiceReqDetails;
import lt.project.ntis.logic.forms.processRequests.SrvReqProcessRequest;
import lt.project.ntis.service.NtisServiceReqFilesDBService;
import lt.project.ntis.service.NtisServiceReqItemsDBService;
import lt.project.ntis.service.NtisServiceReqStatusLogsDBService;
import lt.project.ntis.service.NtisServiceRequestsDBService;

/**
 * Klasė skirta formų "Paslaugų teikimo prašymas" ir "Paslaugų teikimo prašymas (vandentvarka)" biznio logikai apibrėžti
 */
@Component
public class NtisServiceRequestCreate extends FormBase {

    public static String MINISTRY_ORG_ID = "MINISTRY_ORG_ID";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisServiceRequestsDBService ntisServiceRequestsDBService;

    @Autowired
    NtisServiceReqStatusLogsDBService ntisServiceReqStatusLogsDBService;

    @Autowired
    NtisServiceReqItemsDBService ntisServiceReqItemsDBService;

    @Autowired
    NtisServiceReqFilesDBService ntisServiceReqFilesDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService;

    @Autowired
    SprOrgUserRolesDBService sprOrgUserRolesDBService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    SrvReqProcessRequest srvReqProcessRequest;

    @Autowired
    NtisNotificationsManager ntisNotifications;

    @Autowired
    SprOrgUsersDBServiceImpl sprOrgUsersDBService;

    @Autowired
    SprRolesDBService sprRolesDBService;

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_REQUEST_CREATE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Paslaugų teikimo prašymas", "Prašymas tapti nuotekų tvarkymo paslaugų teikėju");
        addFormActions(FormBase.ACTION_READ);
        addFormActions(FormBase.ACTION_UPDATE);
        addFormActions(FormBase.ACTION_CREATE);
    }

    /**
     * Function will return NtisNewServiceRequest object for provided organization and user ids
     * @param conn - connection to the DB
     * @param orgId - organization id
     * @param usrId - user id
     * @return NtisNewServiceRequest object
     * @throws Exception
     */
    public NtisNewServiceRequest getNewServiceReqInfo(Connection conn, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceRequestCreate.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT USR_ID, " //
                + " PER_NAME, " //
                + " PER_SURNAME, " //
                + " COALESCE(ORG_TYPE || ' ','') || ORG_NAME AS ORG_NAME, " //
                + " ORG_CODE, " //
                + " CONCAT_WS(', ', NULLIF(TRIM(ORG_ADDRESS), ''), NULLIF(TRIM(ORG_HOUSE_NUMBER), ''), NULLIF(TRIM(ORG_REGION), '')) AS ORG_ADDRESS, " //
                + " ORG_EMAIL, " //
                + " ORG_PHONE, " //
                + " ORG.c03 AS ORG_WEBSITE, " //
                + " TO_CHAR(ORG.D03, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS org_installation_from " //
                + " FROM SPR_USERS " //
                + " INNER JOIN SPR_ORG_USERS OU ON OU.OU_USR_ID = USR_ID AND CURRENT_DATE BETWEEN OU.OU_DATE_FROM AND COALESCE(OU.OU_DATE_TO, now()) " //
                + " INNER JOIN SPR_ORGANIZATIONS ORG ON OU.OU_ORG_ID = ORG_ID " //
                + " INNER JOIN SPR_PERSONS ON PER_ID = USR_PER_ID " //
                + " WHERE USR_ID = ?::int " //
                + "   AND ORG_ID = ?::int ");
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(orgId);
        Data2ObjectProcessor<NtisNewServiceRequest> dataProcessor = new Data2ObjectProcessor<NtisNewServiceRequest>(NtisNewServiceRequest.class);
        List<NtisNewServiceRequest> queryResult = queryController.selectQueryAsObjectArrayList(conn, stmt, dataProcessor);
        if (queryResult != null && !queryResult.isEmpty()) {
            return queryResult.get(0);
        } else {
            throw new Exception("No information was found");
        }
    }

    /**
     * Function will save provided NtisServiceRequestsDAO object to the DB.
     * Before save function will check if user has right to perform this action (insert or update)
     * @param conn - connection to the DB
     * @param record - NtisServiceRequestsDAO object
     * @param lang - session language
     * @param orgId - session organization id
     * @param usrId - session user id
     * @return saved object
     * @throws Exception
     */
    public NtisServiceRequestsDAO createServiceRequest(Connection conn, NtisServiceReqDetails record, String lang, Double orgId, Double usrId)
            throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceRequestCreate.ACTION_CREATE);
        // save service request
        NtisServiceRequestsDAO requestDAO = new NtisServiceRequestsDAO();
        if (record.getSr_org_id().compareTo(orgId) != 0 && record.getSr_usr_id().compareTo(usrId) != 0) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        requestDAO.setSr_type(record.getSr_type());
        requestDAO.setSr_resp_person_description(record.getSr_resp_person_description());
        requestDAO.setSr_email(record.getSr_email());
        requestDAO.setSr_phone(record.getSr_phone());
        requestDAO.setSr_homepage(record.getSr_homepage());
        requestDAO.setSr_data_is_correct(record.getSr_data_is_correct());
        requestDAO.setSr_rules_accepted(record.getSr_rules_accepted());
        requestDAO.setSr_email_verified(record.getSr_email_verified());
        requestDAO.setSr_status(record.getSr_status());
        requestDAO.setSr_status_date(record.getSr_status_date());
        requestDAO.setSr_registration_date(record.getSr_registration_date());
        requestDAO.setSr_usr_id(record.getSr_usr_id());
        requestDAO.setSr_org_id(record.getSr_org_id());
        requestDAO = ntisServiceRequestsDBService.insertRecord(conn, requestDAO);
        // save srvReq items
        for (NtisServiceReqItemsDAO service : record.getReqItemsDAO()) {
            service.setSri_sr_id(requestDAO.getSr_id());
            service.setSri_status_date(new Date());
            service.setSri_registration_date(new Date());
            ntisServiceReqItemsDBService.insertRecord(conn, service);
        }
        // save srvReq status log
        NtisServiceReqStatusLogsDAO statusLogDAO = new NtisServiceReqStatusLogsDAO();
        statusLogDAO.setSrs_sr_id(requestDAO.getSr_id());
        statusLogDAO.setSrs_status(requestDAO.getSr_status());
        statusLogDAO.setSrs_status_date(requestDAO.getSr_status_date());
        ntisServiceReqStatusLogsDBService.insertRecord(conn, statusLogDAO);
        // save srvReq files
        record.setSr_id(requestDAO.getSr_id());
        saveServiceReqFiles(conn, record);
        // change organization state
        if (record.getSr_status().equalsIgnoreCase(NtisServiceReqStatus.CONFIRMED.getCode())
                || record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.PRIEZIURA.getCode()))
                || record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.VEZIMAS.getCode()))
                || record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode()))) {
            SprOrganizationsNtisDAO orgDao = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, requestDAO.getSr_org_id());
            if (orgDao.getOrgState() == null || (orgDao.getOrgState().equals(NtisOrgStateConstants.DEREGISTERED))) {
                orgDao.setOrgState(NtisOrgStateConstants.REGISTERED);
                orgDao.setOrgRegisteredDate(Utils.getDate(new Date()));
                orgDao.setOrgDeregisteredDate(null);
                orgDao.setManageOrgRoles(1.0);
                if (record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode()))) {
                    orgDao.setFacilityInstallerDateFrom(new Date());
                }
                sprOrganizationsDBService.saveRecord(conn, orgDao);
            }
            if (record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode()))) {
                if (orgDao.getOrgState() == null || orgDao.getOrgState().equals(NtisOrgStateConstants.DEREGISTERED)
                        || (orgDao.getOrgState().equals(NtisOrgStateConstants.REGISTERED) && orgDao.getOrgRegisteredDate() == null)) {
                    orgDao.setOrgState(NtisOrgStateConstants.REGISTERED);
                    orgDao.setOrgRegisteredDate(Utils.getDate(new Date()));
                }
                orgDao.setFacilityInstallerDateFrom(new Date());
                orgDao.setManageOrgRoles(1.0);
                sprOrganizationsDBService.saveRecord(conn, orgDao);
            }
            // set organization type
            setOrgType(conn, requestDAO);
            // update organization roles
            Double applicationUserId = record.getSr_usr_id();
            Double adminOrgUsrId = sprOrgUsersDBService.loadRecordByParams(conn,
                    " WHERE ou_usr_id = ?::int AND ou_org_id = ?::int and current_date between ou_date_from and coalesce(ou_date_to, now()) ",
                    new SelectParamValue(applicationUserId), new SelectParamValue(orgId)).getOu_id();
            List<NtisServiceReqItemsDAO> reqServices = record.getReqItemsDAO();

            String newRoleCode = "";
            for (NtisServiceReqItemsDAO service : reqServices) {
                if (service.getSri_service_type().equals(NtisServiceItemType.PRIEZIURA.getCode())
                        && !hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.TECH_SPECIALIST)) {
                    newRoleCode = NtisRolesConstants.TECH_SPECIALIST;
                    List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ", new SelectParamValue(newRoleCode));
                    setOrgAvailableRoles(conn, orgId, newRolesIds);
                } else if (service.getSri_service_type().equals(NtisServiceItemType.VEZIMAS.getCode())
                        && !hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.CAR_SPECIALIST)) {
                    newRoleCode = NtisRolesConstants.CAR_SPECIALIST;
                    List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ", new SelectParamValue(newRoleCode));
                    setOrgAvailableRoles(conn, orgId, newRolesIds);
                } else if (service.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode())
                        && !hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.INSTALLATION_SPECIALIST)) {
                    newRoleCode = NtisRolesConstants.INSTALLATION_SPECIALIST;
                    List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ", new SelectParamValue(newRoleCode));
                    setOrgAvailableRoles(conn, orgId, newRolesIds);
                } else if (service.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode())
                        && !hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.INSTALLATION_ADMIN)) {
                    newRoleCode = NtisRolesConstants.INSTALLATION_ADMIN;
                    List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ", new SelectParamValue(newRoleCode));
                    setOrgAvailableRoles(conn, orgId, newRolesIds);
                }
            }
            if (!record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode())) || (record
                    .getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode()))
                    && (record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.TYRIMAI.getCode()))
                            || record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.PRIEZIURA.getCode()))
                            || record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.VEZIMAS.getCode()))))) {
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
                if (!hasOrgUsrRole(conn, adminOrgUsrId, NtisRolesConstants.PASL_ADMIN)) {
                    SprRolesDAO newRoleId = this.sprRolesDBService.loadRecordByParams(conn, " rol_code = ? ",
                            new SelectParamValue(NtisRolesConstants.PASL_ADMIN));
                    setOrgUserRole(conn, adminOrgUsrId, newRoleId.getRol_id());
                }
            } else if (record.getReqItemsDAO().stream().anyMatch(s -> s.getSri_service_type().equals(NtisServiceItemType.MONTAVIMAS.getCode()))) {
                if (!hasOrganizationRole(conn, record.getSr_org_id(), NtisRolesConstants.INSTALLATION_ADMIN)) {
                    List<SprRolesDAO> newRolesIds = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code = ? ",
                            new SelectParamValue(NtisRolesConstants.INSTALLATION_ADMIN));
                    setOrgAvailableRoles(conn, orgId, newRolesIds);
                }
                if (!hasOrgUsrRole(conn, adminOrgUsrId, NtisRolesConstants.INSTALLATION_ADMIN)) {
                    SprRolesDAO newRoleId = this.sprRolesDBService.loadRecordByParams(conn, " rol_code = ? ",
                            new SelectParamValue(NtisRolesConstants.INSTALLATION_ADMIN));
                    setOrgUserRole(conn, adminOrgUsrId, newRoleId.getRol_id());
                }
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
        return requestDAO;
    }

    private boolean hasOrganizationRole(Connection conn, Double orgId, String roleCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT oar_id as id
                FROM spr_org_available_roles oar
                JOIN spr_roles rol ON rol_code = ? AND oar_rol_id = rol_id
                WHERE oar_org_id = ?::int
                  AND now()  between oar_date_from and COALESCE(oar_date_to, now())
                """);
        stmt.addSelectParam(roleCode);
        stmt.addSelectParam(orgId);
        List<RecordIdentifier> oarIds = queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
        return !oarIds.isEmpty();
    }

    private boolean hasOrgUsrRole(Connection conn, Double orgUserId, String roleCode) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT our_id as id
                FROM spr_org_user_roles our
                JOIN spr_roles rol ON rol_code = ? AND our_rol_id = rol_id
                WHERE our_ou_id = ?::int
                  AND now() between our_date_from and COALESCE(our_date_to, now())
                """);
        stmt.addSelectParam(roleCode);
        stmt.addSelectParam(orgUserId);
        List<RecordIdentifier> ourIds = queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
        return !ourIds.isEmpty();
    }

    private void setOrgUserRole(Connection conn, Double adminOrgUsrId, Double newRoleId) throws Exception {
        SprOrgUserRolesDAO orgUserRoleDAO = new SprOrgUserRolesDAO();
        orgUserRoleDAO.setOur_date_from(Utils.getDate());
        orgUserRoleDAO.setOur_ou_id(adminOrgUsrId);
        orgUserRoleDAO.setOur_rol_id(newRoleId);
        sprOrgUserRolesDBService.saveRecord(conn, orgUserRoleDAO);
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

    private void setOrgType(Connection conn, NtisServiceRequestsDAO record) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT " //
                + " CASE WHEN count(distinct sr_type) > 1 THEN '" + NtisOrgType.PASLAUG_VANDEN + "' " //
                + "      ELSE max(sr_type) END as org_type" //
                + " FROM ntis_service_requests " //
                + " WHERE sr_org_id = ? ");
        stmt.addSelectParam(Utils.getDouble(record.getSr_org_id()));
        HashMap<String, String> orgType = queryController.selectQueryAsDataArrayList(conn, stmt).get(0);
        SprOrganizationsNtisDAO orgDAO = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, Utils.getDouble(record.getSr_org_id()));
        if (orgDAO.getOrgType() == null || !orgDAO.getOrgType().equals(orgType.get("org_type"))) {
            orgDAO.setOrgType(orgType.get("org_type"));
            orgDAO.setManageOrgRoles(1.0);
            sprOrganizationsDBService.updateRecord(conn, orgDAO);
        }
    }

    private List<RecordIdentifier> getOrgUsers(Connection conn, Double orgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT OU_ID AS ID " //
                + " FROM SPR_ORG_USERS " //
                + " WHERE OU_ORG_ID = ? ");
        stmt.addSelectParam(orgId);
        List<RecordIdentifier> usersIds = queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
        return usersIds;
    }

    private void deleteOrgNewRecords(Connection conn, Double orgAvailableRoleId, List<RecordIdentifier> orgUsersIds, Double currentRoleId) throws Exception {
        sprOrgAvailableRolesDBService.deleteRecord(conn, orgAvailableRoleId);
        Iterator<RecordIdentifier> i = orgUsersIds.iterator();
        while (i.hasNext()) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement(" DELETE FROM SPR_ORG_USER_ROLES WHERE OUR_OU_ID = ? AND OUR_ROL_ID = ? ");
            stmt.addSelectParam(i.next().getIdAsDouble());
            stmt.addSelectParam(currentRoleId);
            queryController.adjustRecordsInDB(conn, stmt);
        }
    }

    /**
     * Function return a list of registered services for specific service provider
     * @param conn - connection to the DB
     * @param orgId - organisation id
     * @param usrId - session user id
     * @return json
     * @throws Exception
     */
    public String getConfirmedServices(Connection conn, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                             rfc.rfc_code as code,
                            coalesce(rfc.rfc_meaning, sri.sri_service_type) as name,
                            case
                                when sri.sri_registration_date is not null
                                then 'Y'
                                else 'N'
                            end as exists
                             from ntis_service_req_items sri
                             inner join ntis_service_requests sr on sr.sr_id = sri.sri_sr_id and sr.sr_org_id = ?::int
                             inner join spr_org_users on ou_org_id = sr_org_id and ou_usr_id = ?::int and CURRENT_DATE between ou_date_from and COALESCE(ou_date_to, now())
                             inner join spr_organizations org on ou_org_id = org.org_id
                             and now() between sri.sri_registration_date and coalesce(sri.sri_removal_date, now())
                             and sr.sr_status not in (?)
                            right join spr_Ref_codes_vw rfc on sri.sri_service_Type = rfc.rfc_code
                            WHERE rfc_domain = 'NTIS_SRV_ITEM_TYPE' and rfc.rft_lang = ?
                        """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(NtisServiceReqStatus.REJECTED.getCode());
        stmt.addSelectParam(lang);
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    private void saveServiceReqFiles(Connection conn, NtisServiceReqDetails record) throws Exception {
        if (record.getAttachments() != null) {
            for (SprFile reqFile : record.getAttachments()) {
                SprFilesDAO fileDB = new SprFilesDAO();
                fileDB = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(reqFile.getFil_key()));
                sprFilesDBService.markAsConfirmed(conn, fileDB);
                NtisServiceReqFilesDAO fileDAO = ntisServiceReqFilesDBService.newRecord();
                fileDAO.setSrf_fil_id(fileDB.getFil_id());
                fileDAO.setSrf_sr_id(Utils.getDouble(record.getSr_id()));
                ntisServiceReqFilesDBService.saveRecord(conn, fileDAO);
            }
        }
        return;
    }

    /**
     * Metodas išsiųs pranešimus ir email'us sistemos administratoriui, vandentvarkos įmonei arba paslaugų teikėjui pagal pateiktą prašymo id
     * @param conn - prisijungimas prie DB
     * @param srId - užsakymo id
     * @param usrId - sesijos naudotojo id
     * @param orgId - sesijos organizacijos id
     * @param lang - sesijos kalba
     * @throws Exception
     */
    public void sendNotification(Connection conn, Double srId, Double usrId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);

        // system admin info
        Double ministryOrgId = Utils.getDouble(dbPropertyManager.getPropertyByName(MINISTRY_ORG_ID, null));
        SprOrganizationsDAO ministryOrg = sprOrganizationsDBService.loadRecord(conn, ministryOrgId);
        String ministryRoleCodes = """
                '%s'
                """.formatted(NtisRolesConstants.NTIS_ADMIN);
        List<SprUsersDAO> ministryOrgUsers = getOrganizationUsers(conn, ministryOrg.getOrg_id(), ministryRoleCodes);

        // service request info
        NtisServiceRequestsDAO requestDAO = ntisServiceRequestsDBService.loadRecordByIdAndOrgId(conn, srId, orgId);
        SprOrganizationsNtisDAO serviceOrg = (SprOrganizationsNtisDAO) sprOrganizationsDBService.loadRecord(conn, orgId);
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
        List<SprUsersDAO> serviceOrgUsers = getOrganizationUsers(conn, serviceOrg.getOrg_id(), roleCodes);
        String requestUrl = requestDAO.getSr_type().toLowerCase() + "/email?token=";

        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("serviceType", getSrvReqItems(conn, srId, lang));
        context.put("requestId", srId.intValue());
        context.put("serviceOrg", serviceOrg.getOrg_name());
        context.put("requestUrl", requestUrl);
        context.put("requestType", requestDAO.getSr_type().toLowerCase());

        // send notifications and emails
        for (String email : serviceOrgEmails) {
            srvReqProcessRequest.createSrvReqSubmittedRequest(conn, usrId, srId, email, Languages.getLanguageByCode(lang), context);
        }
        for (SprUsersDAO user : serviceOrgUsers) {
            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, srId, "NTIS_SRV_REQ_NOTIF", "SRV_REQ_SUBMIT_SUBJECT",
                    "SRV_REQ_SUBMIT_BODY", context, NtisNtfRefType.SRV_REQ.getCode(), NtisMessageSubject.MSG_SBJ_APPLICATION_SUBMITTED.getCode(), new Date(),
                    user.getUsr_id(), serviceOrg.getOrg_id(), null);
        }

        if (ministryOrg.getOrg_email() != null && ministryOrg.getC02() != null && ministryOrg.getC02().equals(DbConstants.BOOLEAN_TRUE)) {
            srvReqProcessRequest.createSrvReqSubmittedSysRequest(conn, usrId, srId, ministryOrg.getOrg_email(), Languages.getLanguageByCode(lang), context);
        }
        for (SprUsersDAO user : ministryOrgUsers) {
            ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, srId, "NTIS_SRV_REQ_NOTIF", "SRV_REQ_SUBMIT_SUBJECT_SYS",
                    "SRV_REQ_SUBMIT_BODY_SYS", context, NtisNtfRefType.SRV_REQ.getCode(), NtisMessageSubject.MSG_SBJ_APPLICATION_SUBMITTED.getCode(),
                    new Date(), user.getUsr_id(), ministryOrgId, null);
        }

        // send additional notifications and emails if the request was automatically confirmed
        if (requestDAO.getSr_status().equals(NtisServiceReqStatus.CONFIRMED.getCode())) {
            for (String email : serviceOrgEmails) {
                srvReqProcessRequest.createSrvReqConfirmedRequest(conn, usrId, srId, email, Languages.getLanguageByCode(lang), context);
            }
            for (SprUsersDAO user : serviceOrgUsers) {
                ntisNotifications.saveNotification(conn, SprNotifications.NOTIFICATION_TYPE_MESSAGE, srId, "NTIS_SRV_REQ_NOTIF", "SRV_REQ_CONFIRM_SUBJECT",
                        "SRV_REQ_CONFIRM_BODY", context, NtisNtfRefType.SRV_REQ.getCode(), NtisMessageSubject.MSG_SBJ_APPLICATION_ACCEPTED.getCode(),
                        new Date(), user.getUsr_id(), serviceOrg.getOrg_id(), null);
            }
        }
    }

    private String getSrvReqItems(Connection conn, Double srId, String lang) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT srv_type.rfc_meaning as rfc_meaning
                FROM ntis.ntis_service_req_items sri
                LEFT JOIN spr_ref_codes_vw srv_type ON srv_type.rfc_code = sri.sri_service_type AND srv_type.rfc_domain = 'NTIS_SRV_ITEM_TYPE'
                AND srv_type.rft_lang = ?
                WHERE sri_sr_id = ?
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

    private List<SprUsersDAO> getOrganizationUsers(Connection conn, Double orgId, String roleCodes) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT distinct usr_id,
                       usr_email
                FROM spr_users usr
                JOIN spr_org_users ou ON usr_id = ou_usr_id
                JOIN spr_roles rol ON rol_code in (%s)
                JOIN spr_org_user_roles our ON our_ou_id = ou_id AND our_rol_id = rol.rol_id
                WHERE ou_org_id = ?::int
                AND now() BETWEEN ou_date_from AND COALESCE(ou_date_to, now())
                AND now() BETWEEN our_date_from AND COALESCE(our_date_to, now())
                                                """.formatted(roleCodes));
        stmt.addSelectParam(orgId);
        return queryController.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
    }
}