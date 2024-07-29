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
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.dao.NtisOrdersDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisInspectorResearchOrdersList;
import lt.project.ntis.logic.forms.NtisOwnerResearchOrdersList;
import lt.project.ntis.logic.forms.NtisResearchNormsHistory;
import lt.project.ntis.logic.forms.NtisResearchNormsManagement;
import lt.project.ntis.logic.forms.NtisResearchOrderPage;
import lt.project.ntis.logic.forms.NtisResearchProvidersList;
import lt.project.ntis.logic.forms.NtisSpResearchOrdersList;
import lt.project.ntis.logic.forms.ResearchOutsideNtisCreate;
import lt.project.ntis.logic.forms.model.NtisWtfInfo;
import lt.project.ntis.models.NtisResearchNormEditModel;
import lt.project.ntis.models.NtisResearchOrderModel;
import lt.project.ntis.models.ResearchCriteriaResultsModel;

/**
 * Klasė skirta Tyrimų modulio rest metodams
 */

@RestController
@RequestMapping("/ntis-research")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

public class NtisResearchService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisSpResearchOrdersList ntisSpResearchOrdersList;

    @Autowired
    NtisResearchNormsManagement ntisResearchNormsManagement;

    @Autowired
    NtisResearchProvidersList ntisResearchProvidersList;

    @Autowired
    NtisResearchNormsHistory ntisResearchNormsHistory;

    @Autowired
    NtisOwnerResearchOrdersList ownerResearchOrdersList;

    @Autowired
    NtisResearchOrderPage researchOrderPage;

    @Autowired
    ResearchOutsideNtisCreate researchOutsideNtisCreate;

    @Autowired
    SprProcessRequest processRequest;
    
    @Autowired
    NtisInspectorResearchOrdersList ntisInspectorResearchOrdersList;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // SP RESEARCH ORDERS LIST START
    @RequestMapping(value = "/get-sp-research-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSpResearchOrdersList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSpResearchOrdersList.getResearchOrdersList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_language()));
    }
    // SP RESEARCH ORDERS LIST END

    // RESEARCH NORMS MANAGEMENT START
    @RequestMapping(value = "/get-research-norms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getResearchNorms(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(
                ntisResearchNormsManagement.getResearchNormsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/set-research-norm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setResearchNorm(@RequestBody NtisResearchNormEditModel researchNorm) throws Exception {
        ntisResearchNormsManagement.saveResearchNorm(this.getDBConnection(), researchNorm, this.requestContext.getUserSession().getUsr_id());
        return okResponse(null);
    }
    // RESEARCH NORMS MANAGEMENT END

    // RESEARCH OUTSIDE NTIS CREATE START
    @RequestMapping(value = "/check-if-has-research", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkIfHasResearch() throws Exception {
        return okResponse(researchOutsideNtisCreate.checkIfHasResearchService(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getUsrId()));
    }

    @RequestMapping(value = "/check-research-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> checkResearchId() throws Exception {
        return okResponse(researchOutsideNtisCreate.checkLabSrvId(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getUsrId()));
    }

    @RequestMapping(value = "/get-current-norms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ArrayList<ResearchCriteriaResultsModel>> getCurrentNorms(@RequestBody String wtfId) throws Exception {
        return okResponse(researchOutsideNtisCreate.getResearchNorms(this.getDBConnection(), Utils.getDouble(wtfId),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/save-new-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisOrdersDAO> saveNewOrder(@RequestBody NtisResearchOrderModel order) throws Exception {
        return okResponse(researchOutsideNtisCreate.saveNewOrder(this.getDBConnection(), order, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/send-outside-order-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendOutsideOrderNotifications(@RequestBody String orderId) throws Exception {
        this.researchOutsideNtisCreate.sendNotification(this.getDBConnection(), Utils.getDouble(orderId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }
    // RESEARCH OUTSIDE NTIS CREATE END

    // OWNER RESEARCH ORDERS START
    @RequestMapping(value = "/get-selected-wtf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWtfInfo> getSelectedWtf() throws Exception {
        return okResponse(ntisResearchProvidersList.getSelectedWtfInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id()));
    }

    @RequestMapping(value = "/get-research-providers-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLaboratoriesList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisResearchProvidersList.getLaboratoriesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_usr_id()));
    }

    // RESEARCH NORMS HISTORY START

    @RequestMapping(value = "/get-research-norms-history", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getResearchNormsHistory(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(
                ntisResearchNormsHistory.getResearchNormsHistory(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language()));
    }

    // RESEARCH NORMS HISTORY END

    // OWNER RESEARCH ORDERS START
    @RequestMapping(value = "/get-owner-research-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getResearchOrdersList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ownerResearchOrdersList.getResearchOrdersList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }
    // OWNER RESEARCH ORDERS END

    // RESEARCH ORDER PAGE START

    @RequestMapping(value = "/get-order-info-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Double> checkOrdReqToken(@RequestBody String token) throws SparkBusinessException, Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), NtisProcessRequestTypes.ORDER_REQUEST, token,
                false);
        return okResponse(sprProcessRequestsDAO.getPrq_reference_id());
    }

    @RequestMapping(value = "/get-order-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisResearchOrderModel> getResearchOrderDetails(@RequestBody String ordId) throws Exception {
        return okResponse(researchOrderPage.getOrder(this.getDBConnection(), Utils.getDouble(ordId), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/update-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateOrder(@RequestBody NtisResearchOrderModel order) throws Exception {
        researchOrderPage.updateOrder(this.getDBConnection(), order, this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getPer_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/send-order-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendOrderNotifications(@RequestBody String orderId) throws Exception {
        this.researchOrderPage.sendNotification(this.getDBConnection(), Utils.getDouble(orderId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_language(), this.requestContext.getUserSession().getSes_org_id());
    }
    // RESEARCH ORDER PAGE END
    
    // INSPECTOR RESEARCH ORDERS LIST START
    @RequestMapping(value = "/get-inspector-research-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getInspectorResearchOrders(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisInspectorResearchOrdersList.getResearchOrdersList(this.getDBConnection(), params, this.requestContext.getUserSession().getUsr_id(), this.requestContext.getUserSession().getSes_language(),
                Utils.getDouble(params.getParamList().get("wtfId"))));
    }
    // INSPECTOR RESEARCH ORDERS LIST END
}
