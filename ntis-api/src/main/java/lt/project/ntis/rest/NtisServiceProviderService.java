package lt.project.ntis.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.dao.NtisWastewaterTreatmentOrgDAO;
import lt.project.ntis.logic.forms.NtisCarEdit;
import lt.project.ntis.logic.forms.NtisCarsList;
import lt.project.ntis.logic.forms.NtisPriorityFacilitiesList;
import lt.project.ntis.logic.forms.NtisReviewsForSrvList;
import lt.project.ntis.logic.forms.NtisServiceDescriptionEdit;
import lt.project.ntis.logic.forms.NtisServiceManagement;
import lt.project.ntis.logic.forms.NtisServiceProviderSettings;
import lt.project.ntis.logic.forms.NtisWaterManagerFacilitiesList;
import lt.project.ntis.logic.forms.model.NtisServiceDescriptionEditModel;
import lt.project.ntis.models.NtisCar;
import lt.project.ntis.models.NtisPriorityFacilities;
import lt.project.ntis.models.NtisServiceDetails;
import lt.project.ntis.models.NtisServiceManagementItem;
import lt.project.ntis.models.NtisServiceProviderContacts;
import lt.project.ntis.models.NtisServiceProviderSettingsInfo;
import lt.project.ntis.models.NtisWaterManagerFacility;

@RestController
@RequestMapping("/ntis-service-providers")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisServiceProviderService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisServiceProviderSettings ntisServiceProviderSettings;

    @Autowired
    NtisServiceManagement ntisServiceManagement;

    @Autowired
    NtisCarsList ntisCarsList;

    @Autowired
    NtisCarEdit ntisCarEdit;

    @Autowired
    NtisServiceDescriptionEdit ntisServiceDescriptionEdit;

    @Autowired
    NtisWaterManagerFacilitiesList ntisWaterManagerFacilitiesList;

    @Autowired
    NtisPriorityFacilitiesList ntisPriorityFacilitiesList;

    @Autowired
    NtisReviewsForSrvList ntisReviewsForSrvList;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // Service provider settings (start)
    @RequestMapping(value = "/get-profile-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceProviderSettingsInfo> getProfileInfo() throws Exception {
        return okResponse(this.ntisServiceProviderSettings.getInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/save-contacts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceProviderContacts> updateContacts(@RequestBody NtisServiceProviderContacts contacts) throws Exception {
        return okResponse(this.ntisServiceProviderSettings.updateContacts(this.getDBConnection(), contacts, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/deregister-installation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deregisterInstallation() throws Exception {
        this.ntisServiceProviderSettings.deregisterInstallation(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id());
    }
    // Service provider settings (end)

    // Service management (start)
    @RequestMapping(value = "/get-services-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<NtisServiceManagementItem>> getServicesInfo() throws Exception {
        return okResponse(this.ntisServiceManagement.getServicesInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/confirm-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> confirmService(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        this.ntisServiceManagement.confirmService(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), recordIdentifier.getIdAsDouble());
        return okResponse(null);
    }

    @RequestMapping(value = "/deregister-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deregisterService(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        this.ntisServiceManagement.deregisterService(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), recordIdentifier.getIdAsDouble());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-service-details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceDetails> getServiceDetails(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(this.ntisServiceManagement.getServiceDetails(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), recordIdentifier.getIdAsDouble(),
                this.requestContext.getUserSession().getSes_language()));
    }
    // Service management (end)

    // Services for organization cars (start)
    /**
     * Function will return a list of cars for provided organization id
     * @param params - paging and search params
     * @return JSON object
     * @throws Exception
     */
    @RequestMapping(value = "/get-cars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCarsList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisCarsList.getCarsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getUsr_id()));
    }

    /**
     * Function will return NtisCarsDAO object for provided car id
     * @param recordIdentifier - car identifier, which data should be returned
     * @return NtisCarsDAO object
     * @throws Exception
     */
    @RequestMapping(value = "/get-car", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisCar> getOrgCar(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisCarEdit.get(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will save provided object to the DB
     * @param record - object that should be stored in DB
     * @return 
     * @return - saved object.
     * @throws Exception
     */
    @RequestMapping(value = "/set-car", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisCar> setOrgCar(@RequestBody NtisCar record) throws Exception {
        return okResponse(ntisCarEdit.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will delete record in db that has provided identifier.
     * @param recordIdentifier - used for record identification in db that should be deleted.
     * @throws Exception
     */
    @RequestMapping(value = "/del-car", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delOrgCar(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisCarEdit.delete(this.getDBConnection(), recordIdentifier.getIdAsDouble(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }
    // Services for organization cars (end)

    // Services for organization service (start)

    @RequestMapping(value = "/get-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisServiceDescriptionEditModel> getService(@RequestBody String sriId) throws Exception {
        return okResponse(ntisServiceDescriptionEdit.get(this.getDBConnection(), sriId, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/set-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceDescriptionEditModel> setService(@RequestBody NtisServiceDescriptionEditModel record) throws Exception {
        return okResponse(ntisServiceDescriptionEdit.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    // Services for organization service (end)

    // Water manager facilities list (start)
    @RequestMapping(value = "/get-water-facilities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWaterFacilitiesList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisWaterManagerFacilitiesList.getFacilitiesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/update-water-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWastewaterTreatmentOrgDAO> setManagerFacility(@RequestBody NtisWaterManagerFacility record) throws Exception {
        return okResponse(ntisWaterManagerFacilitiesList.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/check-if-facilities-registered", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkIfFacilitiesRegistered() throws Exception {
        return okResponse(this.ntisWaterManagerFacilitiesList.checkIfFacilitiesRegistered(this.getDBConnection(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }
    // Water manager facilities list (end)

    // Priority facilities list (start)

    @RequestMapping(value = "/get-water-managers-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWaterManagersList(@RequestBody SelectRequestParams params) throws Exception {
        String answer = ntisPriorityFacilitiesList.getWaterManagersList(this.getDBConnection(), this.requestContext.getUserSession().getUsr_id(), params,
                this.requestContext.getUserSession().getSes_org_id());
        return okResponse(answer);
    }

    @RequestMapping(value = "/set-priority-facilities-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setPriorityFacilitiesListRest(@RequestBody ArrayList<NtisPriorityFacilities> facility) throws Exception {
        Double orgId = this.requestContext.getUserSession().getSes_org_id();
        Double usrId = this.requestContext.getUserSession().getSes_usr_id();
        ntisPriorityFacilitiesList.setPriorityFacilitiesList(getDBConnection(), orgId, usrId, facility);
    }

    @RequestMapping(value = "/get-srv-reviews-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewsForSrv(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisReviewsForSrvList.getReviewsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getUsr_id()));
    }

    @RequestMapping(value = "/mark-review-as-read-srv", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void markReviewAsReadSrv(@RequestBody String id) throws Exception {
        this.ntisReviewsForSrvList.markReviewAsRead(this.getDBConnection(), id);
    }

    // Priority facilities list (end)

}
