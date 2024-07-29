package lt.project.ntis.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.enums.SAPProcessRequestType;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.dao.NtisAdrResidencesDAO;
import lt.project.ntis.dao.NtisAdrStreetsDAO;
import lt.project.ntis.dao.NtisReviewsDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisNotificationView;
import lt.project.ntis.logic.forms.NtisNotificationsList;
import lt.project.ntis.logic.forms.NtisReviewCreatePage;
import lt.project.ntis.logic.forms.model.NtisAddrSearchRequest;
import lt.project.ntis.logic.forms.model.NtisAddrSearchResult;
import lt.project.ntis.logic.forms.model.NtisNotificationViewModel;
import lt.project.ntis.logic.forms.model.NtisReviewCreationModel;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.models.NtisCheckWtfSelectionRequest;
import lt.project.ntis.models.NtisCheckWtfSelectionResponse;
import lt.project.ntis.service.NtisFacilityOwnersDBService;
import lt.project.ntis.service.NtisSelectedFacilitiesDBService;
import lt.project.ntis.service.NtisWastewaterTreatmentFaciDBService;

/**
 * Klasė skirta gauti/siųsti bendrus duomenis
 * 
 */

@RestController
@RequestMapping("/ntis-common")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisCommonService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisSelectedFacilitiesDBService ntisSelectedFacilitiesDBService;

    @Autowired
    NtisWastewaterTreatmentFaciDBService ntisWastewaterTreatmentFaciDBService;

    @Autowired
    NtisFacilityOwnersDBService ntisFacilityOwnersDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    NtisNotificationsList ntisNotificationsList;

    @Autowired
    NtisNotificationView ntisNotificationView;

    @Autowired
    SprAuthorization<SprBackendUserSession> sprAuthorization;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Autowired
    NtisReviewCreatePage ntisReviewCreatePage;
    
    @Autowired
    SprProcessRequest processRequest;
    

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }
    
    @RequestMapping(value = "/get-review-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisReviewsDAO> getReviewInfo(@RequestBody String revId) throws Exception {
        return okResponse(this.ntisCommonMethods.getReviewInfo(this.getDBConnection(), revId,
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    // ----------- ntis review create page start --------------

    @RequestMapping(value = "/get-review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisReviewCreationModel> getPendingReview(@RequestBody String revId) throws Exception {
        return okResponse(this.ntisReviewCreatePage.getReview(this.getDBConnection(), revId, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }
    

    @RequestMapping(value = "/save-review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisReviewsDAO> setSparkRole(@RequestBody NtisReviewsDAO record) throws Exception {
        return okResponse(ntisReviewCreatePage.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_usr_id()));
    }

    // ----------- ntis review create page end ----------------

    @PostMapping("/delete-user-and-org")
    public void deleteViispIndividual() throws Exception {
        this.ntisCommonMethods.deleteIndividualViisp(getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
    }

    @PostMapping("/check-wtf-selection")
    public ResponseEntity<NtisCheckWtfSelectionResponse> checkWtfSelection(@RequestBody NtisCheckWtfSelectionRequest wtfInfo) throws Exception {
        return okResponse(this.ntisSelectedFacilitiesDBService.checkWtfSelection(getDBConnection(), wtfInfo,
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/select-wtf", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void selectedWtf(@RequestBody String wtfId) throws Exception {
        this.ntisSelectedFacilitiesDBService.selectedWtf(getDBConnection(), wtfId, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id());
    }

    @RequestMapping(value = "/get-selected-wtf", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSelectedWtf() throws Exception {
        if (this.ntisSelectedFacilitiesDBService.getSelecteFacility(getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id()) != null) {
            return okResponse((this.ntisSelectedFacilitiesDBService.getSelecteFacility(getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                    this.requestContext.getUserSession().getSes_org_id())).toString());
        } else {
            return okResponse(null);
        }
    }

    // Notifications (start)
    /**
     * Metodas grąžins pranešimų sąrašą, pagal sesijos naudotojo id, jo organizacijos ir jo rolės id bei pateikiamus paieškos parametrus
     * @param params - paieškos ir filtravimo parametrai
     * @return pranešimų sąrašas, json objektas
     * @throws Exception
     */
    @RequestMapping(value = "/get-notifications-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNotificationsList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(this.ntisNotificationsList.getNotificationsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_rol_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    /**
     * Metodas pažymi pranešimą duomenų bazėje kaip perskaitytą, svarbų arba ištrintą, pagal tai, 
     * koks actionType (read, important, not_important, delete) nurodytas pateikiamame RecordIdentifier įraše. 
     * Pažymėti pranešimą reiškia nustatyti vieno iš datos laukų (ntf_mark_as_read_date, ntf_date_to, d01(is_important) reikšmę.
     * @param recordIdentifier - įrašas su norimo pažymėti pranešimo id ir pažymėjimo veiksmo reikšmėmis
     * @throws Exception
     */
    @RequestMapping(value = "/mark-notification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void markNotification(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisNotificationsList.markNotification(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
    }

    /**
     * Metodas grąžina pranešimo informaciją pagal pateiktą ID ir naudotojo sesijos informaciją.
     * @param ntfId - pranešimo ID
     * @return NtisNotificationViewModel objektas
     * @throws Exception
     */
    @RequestMapping(value = "/load-notification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisNotificationViewModel> loadNotification(@RequestBody String ntfId) throws Exception {
        return okResponse(
                this.ntisNotificationView.loadNotification(this.getDBConnection(), Utils.getDouble(ntfId), this.requestContext.getUserSession().getSes_usr_id(),
                        this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_rol_id()));
    }
    // Notifications (end)

    @PostMapping(value = "/get-faci-info")
    public ResponseEntity<NtisWtfInfo> getWtfInfo(@RequestBody String wtfId) throws Exception {
        return okResponse(ntisCommonMethods.getWtfInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_language(), Utils.getDouble(wtfId)));
    }

    @RequestMapping(value = "/find-facilities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findFacilities(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(ntisCommonMethods.findFacilities(this.getDBConnection(), foreignKeyParams));
    }

    @RequestMapping(value = "/search-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisAddrSearchResult>> onDetailedFaciSearch(@RequestBody NtisAddrSearchRequest params) throws Exception {
        return okResponse(ntisCommonMethods.onDetailedFaciSearch(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-residences", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<NtisAdrResidencesDAO>> getResidencesList(@RequestBody String municipalityCode) throws Exception {
        return okResponse(ntisCommonMethods.getResidencesList(this.getDBConnection(), Utils.getDouble(municipalityCode)));
    }

    @RequestMapping(value = "/get-streets", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<NtisAdrStreetsDAO>> getStreetsList(@RequestBody String residenceCode) throws Exception {
        return okResponse(ntisCommonMethods.getStreetsList(this.getDBConnection(), Utils.getDouble(residenceCode)));
    }

    @RequestMapping(value = "/request-confirm-email", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> createRequestConfirmEmailRequest(@RequestBody String email) throws SparkBusinessException, Exception {
        sprAuthorization.request4EmailVerification(this.getDBConnection(), Utils.getDouble(this.requestContext.getUserSession().getSes_usr_id()), email,
                this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-wtf-list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWtfList() throws Exception {
        Double personId = this.requestContext.getUserSession().getSes_per_id();
        Double organizationId = this.requestContext.getUserSession().getSes_org_id();
        String language = this.requestContext.getUserSession().getSes_language();
        return okResponse(ntisCommonMethods.getWtfList(this.getDBConnection(), personId, organizationId, language));
    }

    @RequestMapping(value = "/get-sen-list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSenList() throws Exception {
        return okResponse(ntisCommonMethods.getSenList(this.getDBConnection()));
    }

    @RequestMapping(value = "/get-sen-muni", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getSenList(@RequestBody String mnCode) throws Exception {
        return okResponse(ntisCommonMethods.getSenList(this.getDBConnection(), mnCode));
    }

    @RequestMapping(value = "/get-res-muni", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getResList(@RequestBody String mnCode) throws Exception {
        return okResponse(ntisCommonMethods.getResMuniList(this.getDBConnection(), mnCode));
    }

    @RequestMapping(value = "/get-streets-res", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getSteetResList(@RequestBody String reId) throws Exception {
        return okResponse(ntisCommonMethods.getStreetResList(this.getDBConnection(), reId));
    }

    @RequestMapping(value = "/get-res-sen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getReSenList(@RequestBody String snId) throws Exception {
        return okResponse(ntisCommonMethods.getResSenList(this.getDBConnection(), snId));
    }

    @RequestMapping(value = "/get-organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrganizations() throws Exception {
        return okResponse(ntisCommonMethods.getOrganizations(this.getDBConnection()));
    }

    @RequestMapping(value = "/check-person-has-email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkPersonHasEmail() throws Exception {
        return okResponse(ntisCommonMethods.checkIfPersonHasEmail(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-selected-wtf-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSelectedWtfInfo(@RequestBody String id) throws Exception {
        return okResponse(
                ntisCommonMethods.getSelectedWtfInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_language(), Utils.getDouble(id)));
    }
    
    @RequestMapping(value = "/check-if-new-user-link-valid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void checkIfNewUserLinkValid(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), SAPProcessRequestType.NEW_USER_REQUEST,
                token, false);
    }
    
    @RequestMapping(value = "/check-if-change-password-valid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void checkIfChangePasswordLinkValid(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), SAPProcessRequestType.CHANGE_PASSWORD_REQUEST,
                token, false);
    }
    
    
}
