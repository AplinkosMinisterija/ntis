package lt.project.ntis.rest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisWastewaterBuildingUpdate;
import lt.project.ntis.logic.forms.NtisWastewaterFacilityEdit;
import lt.project.ntis.logic.forms.NtisWastewaterFacilityList;
import lt.project.ntis.logic.forms.NtisWastewaterFacilityView;
import lt.project.ntis.logic.forms.NtisWfAgreementsLists;
import lt.project.ntis.logic.forms.NtisWfManagersEdit;
import lt.project.ntis.logic.forms.NtisWfManagersList;
import lt.project.ntis.models.AddressSearch;
import lt.project.ntis.models.AddressSearchResponse;
import lt.project.ntis.models.FacilityModelAdditionalDetails;
import lt.project.ntis.models.NtisCheckWtfSelectionRequest;
import lt.project.ntis.models.NtisFacilityManagerEditModel;
import lt.project.ntis.models.NtisFacilityModel;
import lt.project.ntis.models.NtisServedBuildingUpdateModel;
import lt.project.ntis.models.NtisWastewaterTreatmentFaci;
import lt.project.ntis.models.NtisWastewaterTreatmentFacility;
import lt.project.ntis.models.NtrOwnerModel;

@RestController
@RequestMapping("/ntis-building-data")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisBuildingDataService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisWastewaterFacilityEdit ntisWastewaterFacilityEdit;

    @Autowired
    NtisWastewaterFacilityList ntisWastewaterFacilityList;

    @Autowired
    NtisWastewaterFacilityView ntisWastewaterFacilityView;

    @Autowired
    NtisWastewaterBuildingUpdate ntisWastewaterBuildingUpdate;

    @Autowired
    NtisWfManagersEdit ntisWfManagersEdit;

    @Autowired
    NtisWfManagersList ntisWfManagersList;

    @Autowired
    NtisWfAgreementsLists ntisWfAgreementsLists;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprProcessRequest processRequest;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }
    
    @RequestMapping(value = "/check-identification-number", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> checkIdentificationNumber(@RequestBody String identificationNumber) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.checkIdentificationNumber(this.getDBConnection(), identificationNumber, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id()));
    }

    /**
    
    Saves a wastewater facility record to the database.
    @param record a NtisWastewaterTreatmentFaci object containing the information to save
    @return a ResponseEntity object with a NtisWastewaterTreatmentFaci object as the body, representing the saved record
    @throws Exception if there is an error executing the insert or update statement
    */
    @RequestMapping(value = "/set-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWastewaterTreatmentFaci> saveFacility(@RequestBody NtisWastewaterTreatmentFaci record) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
    
    Retrieves a list of facility models based on the specified code.
    @param code a String containing the code to use for the query
    @return a ResponseEntity object with a Vector of NtisFacilityModel objects as the body, representing the retrieved list of models
    @throws Exception if there is an error executing the select statement
    */
    @RequestMapping(value = "/get-facility-models", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<NtisFacilityModel>> getFacilityModels(@RequestBody String code) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.getFacilityModels(this.getDBConnection(), code));
    }

    /**
    
    Retrieves a list of wastewater facilities from the database based on the specified parameters.
    @param params a SelectRequestParams object containing the parameters for the select request
    @return a ResponseEntity object with a String as the body, containing the list of wastewater facilities in JSON format
    @throws Exception if there is an error executing the select statement or formatting the results as JSON
    */
    @RequestMapping(value = "/get-facility-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFacilityList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisWastewaterFacilityList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    /**
    
    Removes a wastewater facility from the list, setting the wtf_deleted field to true.
    @param recordIdentifier a RecordIdentifier object containing the ID of the facility to remove
    @return a ResponseEntity object with a null body
    @throws Exception if there is an error executing the update statement
    */
    @RequestMapping(value = "/remove-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeFacility(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisWastewaterFacilityList.removeFacility(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id());
        return okResponse(null);
    }

    /**
     * 
     * Approves a wastewater facility, setting the wtf_state field to 'CONFIRMED'.
     * @param recordIdentifier a RecordIdentifier object containing the ID of the facility to approve
     * @return a ResponseEntity object with a null body
     * @throws Exception if there is an error executing the update statement
     */
    @RequestMapping(value = "/approve-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> approveFacility(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisWastewaterFacilityList.approveFacility(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id());
        return okResponse(null);
    }

    /**
     * 
     * Endpoint for retrieving data about a wastewater treatment facility.
     * @param recordIdentifier a RecordIdentifier object containing the ID of the facility to retrieve data for
     * @return a ResponseEntity object with a JSON string as the body, containing the data for the specified facility
     * @throws Exception if there is an error executing the database query or creating the response
     */
    @RequestMapping(value = "/get-treatment-facility-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTreatmentFacility(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisWastewaterFacilityView.getTreatmentFacility(this.getDBConnection(), recordIdentifier.getIdAsDouble(),
                this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id()));

    }

    @RequestMapping(value = "/find-address", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressSearchResponse>> findAddress(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(
                ntisWastewaterFacilityEdit.findAddress(this.getDBConnection(), foreignKeyParams, this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/find-address-by-coordinates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressSearchResponse>> findAddressByCoorinates(@RequestBody ArrayList<Double> coordinates) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.findAddressByCoorinates(this.getDBConnection(), coordinates));
    }

    /**
     *
     *
     * 
     * Function will return a list of provided organization's cars.
     * @return array of NtisCarsDAO objects
     * @throws Exception
     */
    @RequestMapping(value = "/search-address", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> detailedAddressSearch(@RequestBody AddressSearch params) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.detailedAddressSearch(this.getDBConnection(), params));
    }

    /**
     *
     *
     *
     *
     * 
     * Function will return a full address.
     * @return AddressSearch
     * @throws Exception
     */
    @RequestMapping(value = "/find-address-by-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressSearch>> detailedAddressSearch(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.findAddressById(this.getDBConnection(), recordIdentifier));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/get-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWastewaterTreatmentFaci> getFacility(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.getFacility(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id()));
    }
    
    @RequestMapping(value = "/get-facility-model-details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacilityModelAdditionalDetails> getFacilityModelDetails(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.getFacilityModelAdditionalDetails(this.getDBConnection(), recordIdentifier.getIdAsDouble()));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/verify-address", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> verifyAddress(@RequestBody NtisCheckWtfSelectionRequest params) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.verifyAddress(this.getDBConnection(), Utils.getDouble(params.getAdId()), params.getWtfType()));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/find-ntr-address", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressSearchResponse>> findNtrAddress(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(
                ntisWastewaterFacilityEdit.findNtrAddress(this.getDBConnection(), foreignKeyParams, this.requestContext.getUserSession().getSes_language()));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/check-ntr-objects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<AddressSearchResponse>> checkNtrObjects(@RequestBody String addressId) throws Exception {
        return okResponse(ntisWastewaterFacilityEdit.checkNtrObjects(this.getDBConnection(), Utils.getDouble(addressId)));
    }

    /**
    *
    *
    * 
    */
    @RequestMapping(value = "/assign-existing-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> assignExistingFacility(@RequestBody NtisWastewaterTreatmentFaci record) throws Exception {
        return okResponse(
                ntisWastewaterFacilityEdit.assignExistingFacility(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_usr_id(),
                        this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id()));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/get-ntr-objects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getNtrObjects(@RequestBody String soId) throws Exception {
        return okResponse(
                ntisWastewaterFacilityView.getNtrObjects(this.getDBConnection(), Utils.getDouble(soId), this.requestContext.getUserSession().getPer_id(),
                        this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language()));
    }

    /**
    *
    *
    * 
    */
    @RequestMapping(value = "/load-ntr-owners", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<NtrOwnerModel>> loadNTROwners(@RequestBody String bnRegNr) throws Exception {
        return okResponse(ntisWastewaterFacilityView.loadNTROwners(this.getDBConnection(), bnRegNr));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/get-building-agreement", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisServedBuildingUpdateModel> getBuildingAgreement(@RequestBody String soId) throws Exception {
        return okResponse(ntisWastewaterBuildingUpdate.getBuildingAgreement(this.getDBConnection(), Utils.getDouble(soId),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/update-building-agreement", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateBuildingAgreement(@RequestBody NtisServedBuildingUpdateModel formData) throws Exception {
        return okResponse(ntisWastewaterBuildingUpdate.updateBuildingAgreement(this.getDBConnection(), formData,
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
     *
     *
     * 
     */
    @RequestMapping(value = "/get-water-management-companies", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SprOrganizationsDAO>> updateServedBuilding() throws Exception {
        return okResponse(ntisWastewaterBuildingUpdate.getWaterManagementCompany(this.getDBConnection()));
    }

    // WF MANAGERS LIST (START)
    @RequestMapping(value = "/get-wf-managers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWfManagers(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisWfManagersList.getWfManagersList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-wf-manager-wtf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisWastewaterTreatmentFacility> getSelectedWtf(@RequestBody String id) throws Exception {
        return okResponse(ntisWfManagersList.getSelectedWtf(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/update-manager-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateManagerDate(@RequestBody NtisFacilityManagerEditModel manager) throws Exception {
        ntisWfManagersList.updateFacilityManagerDate(this.getDBConnection(), manager, this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_org_id());
    }

    @RequestMapping(value = "/check-is-owner", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Boolean> checkIsOwner(@RequestBody String id) throws Exception {
        return okResponse(ntisWfManagersList.checkIsOwner(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }
    // WF MANAGERS LIST (END)

    // Wastewater facility managers edit (start)
    @RequestMapping(value = "/get-selected-manager-wtf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisWastewaterTreatmentFacility> getSelectedManagerWtf(@RequestBody String id) throws Exception {
        return okResponse(ntisWfManagersEdit.getSelectedWtf(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getUsr_id()));
    }

    @RequestMapping(value = "/save-new-manager", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisFacilityManagerEditModel> saveNewManager(@RequestBody NtisFacilityManagerEditModel manager) throws Exception {
        return okResponse(ntisWfManagersEdit.saveManager(this.getDBConnection(), manager, this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }
    // Wastewater facility managers edit (end)

    /**
    
    Retrieves a list of wastewater facilities agrrements from the database based on the specified parameters.
    @param params a SelectRequestParams object containing the parameters for the select request
    @return a ResponseEntity object with a String as the body, containing the list of wastewater facilities in JSON format
    @throws Exception if there is an error executing the select statement or formatting the results as JSON
    */
    @PostMapping(value = "/get-wf-agreements-list")
    public ResponseEntity<String> getWfAgreementsList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisWfAgreementsLists.getList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
    
    Retrieves a list of wastewater facilities agrrements from the database based on the specified parameters.
    @param params a SelectRequestParams object containing the parameters for the select request
    @return a ResponseEntity object with a String as the body, containing the list of wastewater facilities in JSON format
    @throws Exception if there is an error executing the select statement or formatting the results as JSON
    */
    @PostMapping(value = "/get-wf-agreement-confirmation-view")
    public ResponseEntity<String> getWfConfirmatinData(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisWfAgreementsLists.getWfConfirmationInfo(this.getDBConnection(), recordIdentifier.getIdAsDouble(),
                this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
    
    Retrieves a list of wastewater facilities agrrements from the database based on the specified parameters.
    @param params a SelectRequestParams object containing the parameters for the select request
    @return a ResponseEntity object with a String as the body, containing the list of wastewater facilities in JSON format
    @throws Exception if there is an error executing the select statement or formatting the results as JSON
    */
    @PostMapping(value = "/get-wf-obj-agreement-confirmation-view")
    public ResponseEntity<String> getWfObjConfirmatinData(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisWfAgreementsLists.getWfObjConfirmationInfo(this.getDBConnection(), recordIdentifier.getIdAsDouble(),
                this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    /**
    Cancellation of a WF agreement.
    @param recordIdentifier The identifier of the record for which to retrieve the confirmation.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/cancel-wf-agreement")
    public ResponseEntity<Void> cancelWfAgreement(@RequestBody IdKeyValuePair result) throws Exception {
        ntisWfAgreementsLists.cancelAgreement(this.getDBConnection(), result, this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    /**
    Cancellation of a WF obj agreement.
    @param recordIdentifier The identifier of the record for which to retrieve the confirmation.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/cancel-wf-obj-agreement")
    public ResponseEntity<Void> cancelWfobjAgreement(@RequestBody IdKeyValuePair result) throws Exception {
        ntisWfAgreementsLists.cancelObjAgreement(this.getDBConnection(), result, this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    /**
    Confirmation of a WF agreement.
    @param recordIdentifier The identifier of the record for which to retrieve the confirmation.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/confirm-wf-agreement")
    public ResponseEntity<Void> confirmationWfAgreement(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisWfAgreementsLists.confirmAgreement(this.getDBConnection(), recordIdentifier.getIdAsDouble(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    /**
    Confirmation of a WF obj agreement.
    @param recordIdentifier The identifier of the record for which to retrieve the confirmation.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/confirm-wf-obj-agreement")
    public ResponseEntity<Void> confirmationWfobjAgreement(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ntisWfAgreementsLists.confirmObjAgreement(this.getDBConnection(), recordIdentifier.getIdAsDouble(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    /**
    Handles a POST request to reject the reject of a WF agreement.
    @param recordIdentifier The identifier of the record for which to retrieve the confirmation.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/reject-wf-obj-agreement")
    public ResponseEntity<Void> rejectWfObjAgreementConfirmation(@RequestBody IdKeyValuePair result) throws Exception {
        ntisWfAgreementsLists.rejectObjAgreement(this.getDBConnection(), result, this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    /**
    Handles a POST request to reject the reject of a WF agreement.
    @param recordIdentifier The identifier of the record for which to retrieve the confirmation.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/reject-wf-agreement")
    public ResponseEntity<Void> rejectWfAgreementConfirmation(@RequestBody IdKeyValuePair result) throws Exception {
        ntisWfAgreementsLists.rejectAgreement(this.getDBConnection(), result, this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-agreement-file/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttachment(@PathVariable(name = "id") Double id) throws Exception {
        if (id != null) {
            InputStream fileContent = ntisWfAgreementsLists.agreementFile(getDBConnection(), id, this.requestContext.getUserSession().getPer_id(),
                    this.requestContext.getUserSession().getSes_org_id());
            HttpHeaders headers = new HttpHeaders();
            headers.add(X_S2_STATUS, OK);
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(fileContent));
        } else {
            return okResponse(null);
        }
    }

    @RequestMapping(value = "/get-obj-fua-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkObjFuaToken(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(),
                NtisProcessRequestTypes.FACI_UPDATE_AGREEMENT_REQUEST, token, false);
        return okResponse(ntisWfAgreementsLists.getWfObjConfirmationInfo(this.getDBConnection(), sprProcessRequestsDAO.getPrq_reference_id(),
                this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-fua-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkFuaToken(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(),
                NtisProcessRequestTypes.FACI_UPDATE_AGREEMENT_REQUEST, token, false);
        return okResponse(ntisWfAgreementsLists.getWfConfirmationInfo(this.getDBConnection(), sprProcessRequestsDAO.getPrq_reference_id(),
                this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/del-fua-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delFuaFile(@RequestBody SprFile file) throws Exception {
        ntisWastewaterBuildingUpdate.deleteFacilityUpdateAgreementFile(this.getDBConnection(), file);
        return okResponse(null);
    }
}
