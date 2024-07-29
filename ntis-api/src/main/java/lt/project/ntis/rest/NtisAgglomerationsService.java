package lt.project.ntis.rest;

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
import lt.project.ntis.dao.NtisAgglomerationsDAO;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisAggloInfo;
import lt.project.ntis.logic.forms.NtisSubmittedAggloDataList;
import lt.project.ntis.logic.forms.model.NtisAggloRejectRequest;
import lt.project.ntis.logic.forms.model.NtisRejectedAggloVersion;
import lt.project.ntis.models.NtisSubmitAggloData;
import lt.project.ntis.models.NtisSubmittedAggloData;

/**
 * Klasė skirta gauti/saugoti duomenis reikalingus aglomeracijų moduliui
 * 
 * 
 */

@RestController
@RequestMapping("/ntis-agglo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisAgglomerationsService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisSubmittedAggloDataList ntisSubmittedAggloDataList;

    @Autowired
    NtisAggloInfo ntisAggloInfo;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    @RequestMapping(value = "/get-submitted-agglo-data-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSubmittedAggloDataList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSubmittedAggloDataList.getDataList(getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/submit-agglomeration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAgglomerationsDAO> submitAgglomeration(@RequestBody NtisSubmitAggloData data) throws Exception {
        return okResponse(ntisSubmittedAggloDataList.submit(getDBConnection(), data, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/send-submitted-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendNotifications(@RequestBody String aggId) throws Exception {
        ntisSubmittedAggloDataList.sendNotifications(this.getDBConnection(), Utils.getDouble(aggId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getSes_per_id());
    }

    @RequestMapping(value = "/send-agglo-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendAggloNotifications(@RequestBody String aggId) throws Exception {
        ntisAggloInfo.sendNotifications(this.getDBConnection(), Utils.getDouble(aggId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/get-agglo-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisSubmittedAggloData> getAggloByToken(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = sprProcessRequest.getIdetifierByToken(this.getDBConnection(),
                NtisProcessRequestTypes.AGGLOMERATION_REQUEST, token, false);
        return okResponse(ntisAggloInfo.getSubmittedAggloData(getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                sprProcessRequestsDAO.getPrq_reference_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/submit-new-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAgglomerationsDAO> submitNewData(@RequestBody NtisSubmitAggloData data) throws Exception {
        return okResponse(ntisAggloInfo.submitNewData(getDBConnection(), data, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/get-agglo-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisSubmittedAggloData> getAggloData(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisAggloInfo.getSubmittedAggloData(getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                recordIdentifier.getIdAsDouble(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/reject-agglo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisSubmittedAggloData> rejectAgglo(@RequestBody NtisAggloRejectRequest request) throws Exception {
        return okResponse(ntisAggloInfo.rejectAgglo(getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), request, this.requestContext.getUserSession().getSes_language()));
    }

    @PostMapping(value = "/approve-agglo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisSubmittedAggloData> approveAgglo(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisAggloInfo.approveAgglo(getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_usr_id(), recordIdentifier.getIdAsDouble(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-rejection-details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisRejectedAggloVersion> getRejectionDetails(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(ntisAggloInfo.getRejectionDetails(getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                recordIdentifier.getIdAsDouble(), this.requestContext.getUserSession().getSes_language()));
    }

}
