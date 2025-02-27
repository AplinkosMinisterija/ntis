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
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.dao.NtisCarsDAO;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
import lt.project.ntis.dao.NtisWastewaterDeliveriesDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisDisposalOrdersList;
import lt.project.ntis.logic.forms.NtisINTSOwnerDashboardPage;
import lt.project.ntis.logic.forms.NtisInspectorDisposalOrdersList;
import lt.project.ntis.logic.forms.NtisOrderOutsideNtisCreate;
import lt.project.ntis.logic.forms.NtisOwnerDisposalOrdersList;
import lt.project.ntis.logic.forms.NtisOwnerTechOrdersList;
import lt.project.ntis.logic.forms.NtisServiceOrderEdit;
import lt.project.ntis.logic.forms.NtisServiceOrderPage;
import lt.project.ntis.logic.forms.NtisSewageDeliveriesList;
import lt.project.ntis.logic.forms.NtisSewageDeliveryEdit;
import lt.project.ntis.logic.forms.NtisSewageDeliveryViewPage;
import lt.project.ntis.logic.forms.NtisSludgeTreatmentDeliveriesList;
import lt.project.ntis.logic.forms.NtisSludgeTreatmentDelivery;
import lt.project.ntis.logic.forms.NtisSpDashboard;
import lt.project.ntis.logic.forms.NtisTechOrdersList;
import lt.project.ntis.logic.forms.NtisWmDashboard;
import lt.project.ntis.logic.forms.model.NtisSludgeDeliveryDetails;
import lt.project.ntis.models.NtisINTSDashboardModel;
import lt.project.ntis.models.NtisOrderCarSelection;
import lt.project.ntis.models.NtisOrderCompletedWorksRequest;
import lt.project.ntis.models.NtisOrderDetails;
import lt.project.ntis.models.NtisOrdersRequest;
import lt.project.ntis.models.NtisServiceManagementItem;
import lt.project.ntis.models.NtisServiceOrderRequest;
import lt.project.ntis.models.NtisSewageOriginFacility;
import lt.project.ntis.models.NtisUsedSewageFacility;
import lt.project.ntis.models.NtisWastewaterDeliveryEditModel;
import lt.project.ntis.models.NtisWaterManagerSelectionModel;

/**
 * Klasė skirta techninės priežiūros modulio rest metodams
 *  
 */
@RestController
@RequestMapping("/ntis-tech-supp")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisTechSupportService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisSpDashboard spDashboard;

    @Autowired
    NtisWmDashboard wmDashboard;

    @Autowired
    NtisTechOrdersList ntisTechOrdersList;

    @Autowired
    NtisSludgeTreatmentDeliveriesList ntisSludgeTreatmentDeliveriesList;

    @Autowired
    NtisSludgeTreatmentDelivery ntisSludgeTreatmentDelivery;

    @Autowired
    NtisOwnerTechOrdersList ownerTechOrdersList;

    @Autowired
    NtisOwnerDisposalOrdersList ownerDisposalOrdersList;

    @Autowired
    NtisSewageDeliveryEdit sewageDeliveryEdit;

    @Autowired
    NtisSewageDeliveriesList sewageDeliveriesList;

    @Autowired
    NtisSewageDeliveryViewPage sewageDeliveryView;

    @Autowired
    NtisDisposalOrdersList ntisDisposalOrdersList;

    @Autowired
    NtisOrderOutsideNtisCreate ntisOrderOutsideNtisCreate;

    @Autowired
    NtisServiceOrderEdit ntisServiceOrderEdit;

    @Autowired
    NtisServiceOrderPage ntisServiceOrderPage;

    @Autowired
    NtisINTSOwnerDashboardPage ntisINTSOwnerDashboardPage;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    NtisInspectorDisposalOrdersList ntisInspectorDisposalOrdersList;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // service provider dashboard start
    @RequestMapping(value = "/get-sp-dashboard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSpDashboard() throws Exception {
        return okResponse(spDashboard.getOrders(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-sp-finished-ord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getConfirmedOrders(@RequestBody String period) throws Exception {
        return okResponse(spDashboard.getFinishedOrders(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(), period));
    }

    @RequestMapping(value = "/get-sp-sludge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getSludge(@RequestBody String period) throws Exception {
        return okResponse(spDashboard.getDischargedSludge(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(), period));
    }
    // service provider dashboard end

    // water manager dashboard start
    @PostMapping(value = "/get-wm-dashboard")
    public ResponseEntity<String> getWmDashboard(@RequestBody(required = false) String id) throws Exception {
        return okResponse(wmDashboard.getDeliveryOrdersBySp(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(), Utils.getDouble(id)));
    }

    @RequestMapping(value = "/get-wm-confirmed-del", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getConfirmedDeliveries(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(wmDashboard.getConfirmedDeliveries(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                params.getParamList().get("period"), Utils.getDouble(params.getParamList().get("wtoId"))));
    }

    @RequestMapping(value = "/get-wm-sludge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWmSludge(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(wmDashboard.getAcceptedSludge(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                params.getParamList().get("period"), Utils.getDouble(params.getParamList().get("wtoId"))));
    }

    @RequestMapping(value = "/get-wm-facilities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWmFacilities() throws Exception {
        return okResponse(wmDashboard.getWastewaterFacilities(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id()));
    }
    // water manager dashboard end

    // Services for tech orders (start)
    /**
     * Function will return a list of orders for provided organization id
     * @param params - paging and search params
     * @return JSON object
     * @throws Exception
     */

    @RequestMapping(value = "/get-tech-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTechOrdersList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisTechOrdersList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getUsr_id()));
    }

    /**
     * Function will save new completed order object to the DB
     * @param record - NtisOrderCompletedWorksRequest object
     * @throws Exception
     */
    @RequestMapping(value = "/set-tech-order-completion-date", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisOrderCompletedWorksRequest> setOrderCompletionDate(@RequestBody NtisOrderCompletedWorksRequest record) throws Exception {
        ntisTechOrdersList.setOrderCompletionDate(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    /**
     * Function will update order state in DB for provided order.
     * @param record - NtisOrdersRequest object
     * @throws Exception
     */
    @RequestMapping(value = "/set-tech-order-state", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisOrdersRequest> setOrderState(@RequestBody NtisOrdersRequest record) throws Exception {
        ntisTechOrdersList.setOrderState(this.getDBConnection(), record, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    // Services for Sludge Treatment Deliveries (start)
    /**
     * Function will return a list of sludge deliveries for provided organization.
     * @param params - paging and search params
     * @return JSON object
     * @throws Exception
     */
    @RequestMapping(value = "/get-sludge-deliveries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSludgeDeliveries(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSludgeTreatmentDeliveriesList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will update wastewater delivery data.
     * @param record - NtisWastewaterDeliveriesDAO object
     * @throws Exception
     */
    @RequestMapping(value = "/set-delivery-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setDeliveryData(@RequestBody NtisWastewaterDeliveriesDAO record) throws Exception {
        this.ntisSludgeTreatmentDeliveriesList.setDeliveryData(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    /**
     * Metodas grąžins NtisSludgeDeliveryDetails objektą pagal nurodytą pristatymo ID
     * @param wdId - pristatymo ID
     * @return - NtisSludgeDeliveryDetails objektas
     * @throws Exception
     */
    @RequestMapping(value = "/get-sludge-delivery-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisSludgeDeliveryDetails> getSludgeDeliveryInfo(@RequestBody String wdId) throws Exception {
        return okResponse(ntisSludgeTreatmentDelivery.getSludgeDeliveryInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(), Utils.getDouble(wdId)));
    }

    @RequestMapping(value = "/send-sludge-delivery-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendSludgeDeliveryNotifications(@RequestBody String wdId) throws Exception {
        this.ntisSludgeTreatmentDeliveriesList.sendNotification(this.getDBConnection(), Utils.getDouble(wdId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }

    // sewage deliveries list start
    @RequestMapping(value = "/get-sewage-deliveries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSewageDeliveries(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sewageDeliveriesList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/del-sewage-delivery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSewageDelivery(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sewageDeliveriesList.delete(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    /**
    Cancellation of a Sewage Delivery.
    @param recordIdentifier The identifier of the record for which to cancel.
    @return A ResponseEntity object representing the response of the request.
    @throws Exception if an error occurs during the process.
    */
    @PostMapping(value = "/cancel-sewage-delivery")
    public ResponseEntity<Void> cancelWfAgreement(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sewageDeliveriesList.cancelSewageDelivery(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }
    // sewage deliveries list end

    // sewage delivery edit start
    @RequestMapping(value = "/get-sewage-delivery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWastewaterDeliveryEditModel> getSewageDelivery(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sewageDeliveryEdit.getRecord(this.getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/save-delivery-record", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWastewaterDeliveryEditModel> saveDeliveryRecord(@RequestBody NtisWastewaterDeliveryEditModel record) throws Exception {
        return okResponse(sewageDeliveryEdit.saveDeliveryRecord(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/send-sewage-delivery-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendSewageDeliveryNotifications(@RequestBody String wdId) throws Exception {
        this.sewageDeliveryEdit.sendNotification(this.getDBConnection(), Utils.getDouble(wdId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/send-auto-accept-delivery-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendAutoAcceptDeliveryNotifications(@RequestBody String wdId) throws Exception {
        this.sewageDeliveryEdit.sendNotificationAutoAccept(this.getDBConnection(), Utils.getDouble(wdId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }

    // sewage delivery edit - SEWAGE ORIGIN FACILITIES RELATED
    @RequestMapping(value = "/get-origin-facilities-orders-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisWaterManagerSelectionModel>> getOriginFacilitiesOrders() throws Exception {
        return okResponse(this.sewageDeliveryEdit.getOriginFacilitiesOrdersList(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-portable-facilities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisWaterManagerSelectionModel>> getPortableWtfs() throws Exception {
        return okResponse(this.sewageDeliveryEdit.getPortableFacilities(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-new-sewage-origin-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisSewageOriginFacility> getNewOriginFacility(@RequestBody String id) throws Exception {
        return okResponse(sewageDeliveryEdit.getNewOriginFacilityOrder(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-portable-facility-for-delivery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisSewageOriginFacility> getPortableFacilityForDelivery(@RequestBody String id) throws Exception {
        return okResponse(sewageDeliveryEdit.getPortableFacilityForDelivery(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    // sewage delivery edit - SEWAGE USED FACILITIES RELATED
    @RequestMapping(value = "/get-new-delivery-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisUsedSewageFacility> getSewageDelivery(@RequestBody String id) throws Exception {
        return okResponse(sewageDeliveryEdit.getNewDeliveryFacility(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_language()));
    }

    // sewage delivery edit - SELECT COMPONENT RELATED
    @RequestMapping(value = "/get-available-water-manager-facilities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWaterManagerFacilities() throws Exception {
        return okResponse(this.sewageDeliveryEdit.getWaterManagerFacilitiesList(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-available-org-cars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisOrderCarSelection>> getOrgCars() throws Exception {
        return okResponse(this.sewageDeliveryEdit.getOrgCars(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }
    // sewage delivery edit end

    // sewage delivery view page start
    @RequestMapping(value = "/get-sewage-delivery-view", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisSludgeDeliveryDetails> getDeliveryRecordView(@RequestBody String id) throws Exception {
        return okResponse(
                sewageDeliveryView.getDeliveryRecordView(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_org_id(),
                        this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-delivery-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> checkDeliveryToken(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), NtisProcessRequestTypes.DELIVERY_REQUEST,
                token, false);
        return okResponse(sprProcessRequestsDAO.getPrq_reference_id());
    }
    // sewage delivery view page end

    // Services for disposal orders (start)
    /**
     * Function will return a list of disposal orders for provided organization.
     * @param params - paging and search params
     * @return JSON object
     * @throws Exception
     */
    @RequestMapping(value = "/get-disposal-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDisposalOrders(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisDisposalOrdersList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will return a list of provided organization's cars.
     * @return array of NtisCarsDAO objects
     * @throws Exception
     */
    @RequestMapping(value = "/get-org-cars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisCarsDAO>> getOrgCarsList() throws Exception {
        return okResponse(ntisDisposalOrdersList.getOrgCarsList(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Function will update Order state and save provided NtisOrderCompletedWorksDAO object to the DB.
     * @param record - object that should be stored in DB.
     * @throws Exception
     */
    @RequestMapping(value = "/set-disposal-order-completion-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisOrderCompletedWorksDAO> setOrderCompletionData(@RequestBody NtisOrderCompletedWorksDAO record) throws Exception {
        this.ntisDisposalOrdersList.setOrderCompletionData(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    // Owner orders list (start)
    @RequestMapping(value = "/get-owner-tech-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOwnerTechOrders(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ownerTechOrdersList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-owner-disposal-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOwnerDisposalOrders(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ownerDisposalOrdersList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }
    // Owner orders list (end)

    // Services for orders received outside NTIS (start)
    /**
     * Metodas grąžins teikiamų paslaugų sąrašą
     * @return NtisServiceManagementItem objektų masyvas
     * @throws Exception
     */
    @RequestMapping(value = "/get-org-services", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisServiceManagementItem>> getOrgServices() throws Exception {
        return okResponse(ntisOrderOutsideNtisCreate.getOrgServices(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }

    /**
     * Pagal nurodytą NtisOrderDetails objektą, metodas į duomenų bazę išsaugos NtisOrdersDAO objektą 
     *  ir (nebūtinai - priklauso nuo paduoto objekto ord_state) NtisOrderCompletedWorksDAO objektą
     * @param record - NtisOrderDetails objektas
     * @throws Exception
     */
    @RequestMapping(value = "/set-order-outside-ntis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisOrderDetails> setOrderOutsideNtis(@RequestBody NtisOrderDetails record) throws Exception {
        return okResponse(this.ntisOrderOutsideNtisCreate.save(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Metodas išsiųs pranešimus ir email'us INTS savininkui, pagal pateiktą užsakymo id
     * @param orderId - užsakymo id
     * @throws Exception
     */
    @RequestMapping(value = "/send-outside-order-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendOutsideOrderNotifications(@RequestBody String orderId) throws Exception {
        this.ntisOrderOutsideNtisCreate.sendNotification(this.getDBConnection(), Utils.getDouble(orderId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }

    // Service order (start)
    @RequestMapping(value = "/get-service-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceOrderRequest> getOrder(@RequestBody NtisOrdersRequest record) throws Exception {
        return okResponse(this.ntisServiceOrderEdit.getOrder(this.getDBConnection(), record, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/save-service-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceOrderRequest> saveOrder(@RequestBody NtisServiceOrderRequest order) throws Exception {
        return okResponse(this.ntisServiceOrderEdit.saveOrder(this.getDBConnection(), order, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-order-details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisServiceOrderRequest> getOrderDetails(@RequestBody String ordId) throws Exception {
        return okResponse(ntisServiceOrderPage.getOrder(this.getDBConnection(), ordId, this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/update-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisOrderDetails> updateOrder(@RequestBody NtisOrderDetails order) throws Exception {
        return okResponse(this.ntisServiceOrderPage.updateOrder(this.getDBConnection(), order, this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui arba INTS savininkui, pagal pateiktą užsakymo id
     * @param orderId - užsakymo id
     * @throws Exception
     */
    @RequestMapping(value = "/send-order-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendOrderNotifications(@RequestBody String orderId) throws Exception {
        this.ntisServiceOrderEdit.sendNotification(this.getDBConnection(), Utils.getDouble(orderId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_language());
    }

    /**
     * Metodas išsiųs pranešimus ir email'us paslaugų teikėjui arba INTS savininkui, pagal pateiktą užsakymo id
     * @param orderId - užsakymo id
     * @throws Exception
     */
    @RequestMapping(value = "/send-order-edit-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendOrderNotifications(@RequestBody RecordIdentifier orderId) throws Exception {
        this.ntisServiceOrderPage.sendNotification(this.getDBConnection(), orderId, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/get-order-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> checkToken(@RequestBody String token) throws SparkBusinessException, Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), NtisProcessRequestTypes.ORDER_REQUEST, token,
                false);
        return okResponse(sprProcessRequestsDAO.getPrq_reference_id());
    }
    // Service order (end)

    // INTS owner dashboard (start)
    @RequestMapping(value = "/get-ints-dashboard", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisINTSDashboardModel> getINTSDashboard() throws Exception {
        return okResponse(ntisINTSOwnerDashboardPage.getDashboardInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/remove-facility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void removeFacility(@RequestBody String id) throws Exception {
        ntisINTSOwnerDashboardPage.removeFacility(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_usr_id());
    }

    @RequestMapping(value = "/update-selected-wtf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void updateSelectedWtf(@RequestBody String selectedWtf) throws Exception {
        ntisINTSOwnerDashboardPage.updateSelectedWtf(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), Utils.getDouble(selectedWtf));
    }
    // INTS owner dashboard (end)

    // INSPECTOR DISPOSAL ORDERS LIST START
    @RequestMapping(value = "/get-inspector-disposal-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getInspectorDisposalOrders(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisInspectorDisposalOrdersList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_language(), Utils.getDouble(params.getParamList().get("wtfId"))));
    }
    // INSPECTOR DISPOSAL ORDERS LIST END
}
