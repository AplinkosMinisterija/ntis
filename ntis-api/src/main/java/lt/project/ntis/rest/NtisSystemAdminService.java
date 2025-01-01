package lt.project.ntis.rest;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersNtisDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.ApiKey;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisAddressesList;
import lt.project.ntis.logic.forms.NtisFacilityModelEdit;
import lt.project.ntis.logic.forms.NtisFacilityModelsList;
import lt.project.ntis.logic.forms.NtisInstitutionsEdit;
import lt.project.ntis.logic.forms.NtisInstitutionsList;
import lt.project.ntis.logic.forms.NtisReviewsForAdminList;
import lt.project.ntis.logic.forms.NtisServiceProvidersList;
import lt.project.ntis.logic.forms.NtisServiceRequestCreate;
import lt.project.ntis.logic.forms.NtisServiceRequestReview;
import lt.project.ntis.logic.forms.NtisServiceRequestsList;
import lt.project.ntis.logic.forms.NtisSprApiKeysBrowse;
import lt.project.ntis.logic.forms.NtisSprJobEdit;
import lt.project.ntis.logic.forms.NtisSprOrgUserRolesBrowse;
import lt.project.ntis.logic.forms.NtisSprUsersList;
import lt.project.ntis.logic.forms.NtisSystemWorksEdit;
import lt.project.ntis.logic.forms.model.NtisJobEditModel;
import lt.project.ntis.logic.forms.model.NtisMstAdditionalText;
import lt.project.ntis.logic.forms.model.NtisNewServiceRequest;
import lt.project.ntis.logic.forms.model.NtisServiceReqDetails;
import lt.project.ntis.logic.forms.model.NtisSystemWorksEditModel;
import lt.project.ntis.models.NtisFacilityModelEditModel;
import lt.project.ntis.models.NtisInstitutionEditModel;
import lt.project.ntis.models.NtisServiceProviderRejectionModel;

/**
 * Klasė skirta sistemos administratoriaus servisams apibrėžti
 * 
 */
@RestController
@RequestMapping("/ntis-system-admin")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisSystemAdminService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisServiceRequestsList ntisServiceRequestsList;

    @Autowired
    NtisServiceRequestReview ntisServiceRequestReview;

    @Autowired
    NtisServiceRequestCreate ntisServiceRequestCreate;

    @Autowired
    NtisServiceProvidersList ntisServiceProvidersList;

    @Autowired
    NtisInstitutionsList ntisInstitutionsList;

    @Autowired
    NtisInstitutionsEdit ntisInstitutionsEdit;

    @Autowired
    SprAuthorization<?> sprAuthorizations;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    NtisSprUsersList ntisSprUsersList;

    @Autowired
    NtisSprOrgUserRolesBrowse ntisOrgUserRolesBrowse;

    @Autowired
    NtisSprApiKeysBrowse ntisSprApiKeysBrowse;

    @Autowired
    NtisFacilityModelsList ntisFacilityModelsList;

    @Autowired
    NtisFacilityModelEdit ntisFacilityModelEdit;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Autowired
    NtisReviewsForAdminList ntisReviewsForAdminList;

    @Autowired
    NtisAddressesList ntisAddressesList;

    @Autowired
    NtisSprJobEdit ntisSprJobEdit;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    NtisSystemWorksEdit ntisSystemWorksEdit;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    @RequestMapping(value = "/get-job", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisJobEditModel> getJob(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisSprJobEdit.getJobWithDetails(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/get-job-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> checkToken(@RequestBody String token) throws SparkBusinessException, Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(),
                NtisProcessRequestTypes.SCHEDULER_FAILED_REQUEST, token, false);
        return okResponse(sprProcessRequestsDAO.getPrq_reference_id());
    }

    @RequestMapping(value = "/set-job", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisJobEditModel> setJob(@RequestBody NtisJobEditModel job) throws Exception {
        return okResponse(ntisSprJobEdit.save(this.getDBConnection(), job));
    }

    @RequestMapping(value = "/get-reviews-for-admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewsForAdmin(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisReviewsForAdminList.getReviewsList(this.getDBConnection(), params, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/mark-review-as-read-admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void markReviewAsReadAdmin(@RequestBody String id) throws Exception {
        this.ntisReviewsForAdminList.markReviewAsRead(this.getDBConnection(), id);
    }

    @PostMapping(value = "/get-additional-mst-item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getAdditionalMstItem(@RequestBody String id) throws Exception {
        return okResponse(ntisCommonMethods.getAdditionalMstText(this.getDBConnection(), id));
    }

    @RequestMapping(value = "/set-additional-mst-item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setAdditionalMstItem(@RequestBody NtisMstAdditionalText additionalText) throws Exception {
        this.ntisCommonMethods.saveAdditionalMstText(this.getDBConnection(), additionalText);
    }

    @PostMapping(value = "/get-ntis-api-list")
    public ResponseEntity<String> getApiList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSprApiKeysBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getLanguage(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @PostMapping(value = "/get-api-usr-id")
    public ResponseEntity<Double> getApiUsrId(@RequestBody ApiKey key) throws Exception {
        return okResponse(ntisSprApiKeysBrowse.getApiUsrId(this.getDBConnection(), key.getApi_ou_id(), key.getApi_org_id()));
    }

    // Services for service requests (start)
    /**
     * Function will return a list of service requests
     * @param params - paging and search params
     * @return JSON object
     * @throws Exception
     */
    @RequestMapping(value = "/get-service-requests", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRecList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisServiceRequestsList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getUsr_id()));
    }

    /**
     * Function will return service request details for provided service request id
     * @param srId - service request id
     * @return NtisServiceReqDetails object
     * @throws Exception
     */
    @RequestMapping(value = "/get-service-request-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisServiceReqDetails> getServiceRequestInfo(@RequestBody String srId) throws Exception {
        return okResponse(ntisServiceRequestReview.getServiceRequestInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_language(),
                Utils.getDouble(srId), this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will return service request files for provided service request id
     * @param srId - service request id
     * @return array of SprFile objects
     * @throws Exception
     */
    @RequestMapping(value = "/get-service-request-files", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SprFile>> getServiceRequestFiles(@RequestBody String srId) throws Exception {
        return okResponse(ntisServiceRequestReview.getServiceRequestFiles(this.getDBConnection(), Utils.getDouble(srId),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will update provided service request status
     * @param record - provided NtisServiceReqDetails object
     * @throws Exception
     */
    @RequestMapping(value = "/set-service-request-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceReqDetails> setServiceRequestStatus(@RequestBody NtisServiceReqDetails record) throws Exception {
        ntisServiceRequestReview.setServiceRequestStatus(this.getDBConnection(), record);
        return okResponse(null);
    }

    /**
     * Function will return user and organization information needed for new service request creation
     * @return NtisNewServiceRequest object
     * @throws Exception
     */
    @RequestMapping(value = "/get-new-service-request-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisNewServiceRequest> getNewServiceReqInfo() throws Exception {
        return okResponse(ntisServiceRequestCreate.getNewServiceReqInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will return user and organization information needed for new service request creation
     * @return NtisNewServiceRequest object
     * @throws Exception
     */
    @RequestMapping(value = "/get-active-services-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getActiveServices() throws Exception {
        return okResponse(ntisServiceRequestCreate.getConfirmedServices(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }

    /**
     * Function will save provided NtisServiceRequestsDAO object to the DB
     * @param record - NtisServiceRequestsDAO object
     * @return saved object
     * @throws Exception
     */
    @RequestMapping(value = "/set-service-request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceRequestsDAO> createServiceRequest(@RequestBody NtisServiceReqDetails record) throws Exception {
        Date now = new Date();
        record.setSr_status_date(now);
        record.setSr_registration_date(now);
        record.setSr_usr_id(this.requestContext.getUserSession().getSes_usr_id());
        record.setSr_org_id(this.requestContext.getUserSession().getSes_org_id());
        Connection conn = this.getDBConnection();
        NtisServiceRequestsDAO result = ntisServiceRequestCreate.createServiceRequest(conn, record, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id());
        sprAuthorizations.reloadUserProfile(conn);
        return okResponse(result);
    }

    @RequestMapping(value = "/send-new-service-req-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendNewServiceReqNotifications(@RequestBody String srvReqId) throws Exception {
        this.ntisServiceRequestCreate.sendNotification(this.getDBConnection(), Utils.getDouble(srvReqId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/send-service-req-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendServiceReqNotifications(@RequestBody String srvReqId) throws Exception {
        this.ntisServiceRequestReview.sendNotification(this.getDBConnection(), Utils.getDouble(srvReqId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/get-service-req-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> checkSrvReqToken(@RequestBody String token) throws SparkBusinessException, Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = sprProcessRequest.getIdetifierByToken(this.getDBConnection(), NtisProcessRequestTypes.SRV_REQ_REQUEST,
                token, false);
        return okResponse(sprProcessRequestsDAO.getPrq_reference_id());
    }
    // Services for service requests (end)

    // Service providers list (start)
    @RequestMapping(value = "/get-service-providers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getServiceProvidersList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisServiceProvidersList.getServiceProviders(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/set-org-disabled", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setServiceProviderDisabled(@RequestBody NtisServiceProviderRejectionModel provider) throws Exception {
        ntisServiceProvidersList.setDisabled(this.getDBConnection(), provider);
        return okResponse(null);
    }

    @RequestMapping(value = "/send-sp-list-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendNotifications(@RequestBody String orgId) throws Exception {
        this.ntisServiceProvidersList.sendNotification(this.getDBConnection(), Utils.getDouble(orgId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_language());
    }
    // Service providers list (end)

    // Institutions (start)
    @RequestMapping(value = "/get-institutions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getInstitutionsList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisInstitutionsList.getInstitutionsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-inst-admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisInstitutionEditModel> getInstitutionAdmin(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisInstitutionsEdit.getInstitutionAdmin(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/set-institution", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisInstitutionEditModel> saveInstitution(@RequestBody NtisInstitutionEditModel record) throws Exception {
        return okResponse(ntisInstitutionsEdit.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/invite-institution-person", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> inviteNewPerson(@RequestBody String id) throws Exception {
        ntisInstitutionsList.inviteNewInstitutionAdmin(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/remove-inst-admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeInstAdmin(@RequestBody SelectRequestParams params) throws Exception {
        ntisInstitutionsList.removeInstAdmin(this.getDBConnection(), params.getParamList().get("usrId"), params.getParamList().get("orgId"));
        return okResponse(null);
    }

    @RequestMapping(value = "/check-usr-constraint", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<S2ViolatedConstraint>> checkSparkUserConstraints(@RequestBody NtisInstitutionEditModel record) throws Exception {
        return okResponse(ntisInstitutionsEdit.getViolatedConstraints(this.getDBConnection(), record));
    }

    // Institutions (end)

    // Users (start)
    @RequestMapping(value = "/get-ntis-users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNtisUsers(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.ntisSprUsersList.getUsersList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-users-without-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUsersWithoutRoles() throws Exception {
        return okResponse(this.ntisSprUsersList.getUsersWithoutRoles(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/check-if-roles-assigned", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkIfOrgUserRolesAreAssigned() throws Exception {
        return okResponse(this.ntisSprUsersList.checkIfOrgUserRolesAreAssigned(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/del-ntis-org-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delNtisUser(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisSprUsersList.delete(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/set-ntis-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprUsersNtisDAO> setNtisUser(@RequestBody SprUsersNtisDAO record) throws Exception {
        return okResponse((SprUsersNtisDAO) ntisSprUsersList.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-ntis-user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprUsersDAO> getNtisUser(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisSprUsersList.get(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id()));
    }
    // Users (end)

    // Ntis org user roles start
    @RequestMapping(value = "/get-ntis-org-user-roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNtisOrgUserRoles(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.ntisOrgUserRolesBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    // Facility models list (start)
    @RequestMapping(value = "/get-facility-models", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFacilityModelsList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisFacilityModelsList.getFacilityModels(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/del-facility-model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delFacilityModel(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisFacilityModelsList.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

    @RequestMapping(value = "/set-facility-model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisFacilityModelEditModel> setFacilityModel(@RequestBody NtisFacilityModelEditModel record) throws Exception {
        return okResponse(ntisFacilityModelEdit.save(this.getDBConnection(), record));
    }

    @RequestMapping(value = "/get-facility-model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisFacilityModelEditModel> getFacilityModel(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisFacilityModelEdit.get(this.getDBConnection(), recordIdentifier));
    }

    // Facility models list (end)
    // Adr address list (start)
    @RequestMapping(value = "/get-adr-address-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAddressList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisAddressesList.getAddressList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    // Ntis system works (start)
    @GetMapping(value = "/system-works")
    public ResponseEntity<NtisSystemWorksEditModel> getSystemWorksRecord() throws Exception {
        return okResponse(ntisSystemWorksEdit.get(getDBConnection()));
    }

    @PostMapping(value = "/save-system-works-record")
    public ResponseEntity<NtisSystemWorksEditModel> saveSystemWorksRecord(@RequestBody NtisSystemWorksEditModel record) throws Exception {
        return okResponse(ntisSystemWorksEdit.save(getDBConnection(), record));
    }

    @PostMapping(value = "/update-works-state")
    public void updateSysWorksStatus(@RequestBody Integer id) throws Exception {
        ntisSystemWorksEdit.updateSysWorksStatus(getDBConnection(), Utils.getDouble(id));
    }
    // Ntis system works (end)
}
