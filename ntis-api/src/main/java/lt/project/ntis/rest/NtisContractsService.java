package lt.project.ntis.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.model.ForeignKeyParams;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.logic.forms.NtisCarEdit;
import lt.project.ntis.logic.forms.NtisCarsList;
import lt.project.ntis.logic.forms.NtisContractEdit;
import lt.project.ntis.logic.forms.NtisContractRequest;
import lt.project.ntis.logic.forms.NtisContractUploadPage;
import lt.project.ntis.logic.forms.NtisContractsList;
import lt.project.ntis.logic.forms.NtisServiceProviderSettings;
import lt.project.ntis.logic.forms.NtisServiceSearch;
import lt.project.ntis.logic.forms.model.NtisContractEditModel;
import lt.project.ntis.logic.forms.model.NtisContractRequestComment;
import lt.project.ntis.logic.forms.model.NtisContractSignReq;
import lt.project.ntis.logic.forms.model.NtisNewContractRequest;
import lt.project.ntis.logic.forms.model.NtisNewContractRequestInfo;
import lt.project.ntis.logic.forms.model.NtisSubmitContractRequestInfo;
import lt.project.ntis.models.NtisAddressSuggestion;
import lt.project.ntis.models.NtisContractUploadRequest;
import lt.project.ntis.models.NtisServiceSearchRequest;
import lt.project.ntis.models.NtisServiceSearchResult;

@RestController
@RequestMapping("/ntis-contracts")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisContractsService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisServiceProviderSettings ntisServiceProviderSettings;

    @Autowired
    NtisCarsList ntisCarsList;

    @Autowired
    NtisCarEdit ntisCarEdit;

    @Autowired
    NtisContractRequest ntisContractRequest;

    @Autowired
    NtisServiceSearch ntisServiceSearch;

    @Autowired
    NtisContractsList ntisContractsList;

    @Autowired
    NtisContractEdit ntisContractEdit;

    @Autowired
    NtisContractUploadPage ntisContractUpload;

    @Autowired
    SprProcessRequest processRequest;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // Service search (start)

    @RequestMapping(value = "/search-address/{searchText}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NtisAddressSuggestion>> getCities(@PathVariable(value = "searchText") String searchText) throws Exception {
        return okResponse(ntisServiceSearch.getAddressSuggestions(getDBConnection(), searchText));
    }

    @RequestMapping(value = "/get-selected-wtf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceSearchRequest> getSelectedWtf() throws Exception {
        return okResponse(ntisServiceSearch.getSelectedWtfInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id()));
    }

    @RequestMapping(value = "/service-search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<NtisServiceSearchResult>> getProfileInfo(@RequestBody NtisServiceSearchRequest request) throws Exception {
        return okResponse(ntisServiceSearch.getSearchResult(this.getDBConnection(), request));
    }

    // Service search (end)
    // Services for contracts (start)
    @RequestMapping(value = "/get-contracts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRecList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisContractsList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getUsr_id(), this.requestContext.getUserSession().getPer_id(),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/find-service-providers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findServiceProviders(@RequestBody ForeignKeyParams foreignKeyParams) throws Exception {
        return okResponse(ntisContractsList.findServiceProviders(this.getDBConnection(), foreignKeyParams));
    }

    @RequestMapping(value = "/check-sp-services", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkSpServices() throws Exception {
        return okResponse(ntisContractsList.checkSpServices(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id()));
    }
    // Services for contracts (end)

    // Contract requests (start)
    @RequestMapping(value = "/load-new-contract-request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisNewContractRequestInfo> loadNewContractRequest(@RequestBody NtisNewContractRequest record) throws Exception {
        return okResponse(ntisContractRequest.loadNewRequestInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getUsr_id(), record,
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/submit-contract-request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitContractRequest(@RequestBody NtisSubmitContractRequestInfo requestInfo) throws Exception {
        return okResponse(ntisContractRequest.submitContractRequest(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_usr_id(), requestInfo));
    }
    // Contract requests (end)

    // Contract edit (start)
    /**
     * Metodas grąžins NtisContractEditModel objektą pagal pateiktą sutarties ID
     * @param cotId - sutarties ID
     * @return NtisContractEditModel objektas
     * @throws Exception
     */
    @RequestMapping(value = "/load-contract", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisContractEditModel> loadContractInfo(@RequestBody String cotId) throws Exception {
        return okResponse(ntisContractEdit.loadContractInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_language(),
                Utils.getDouble(cotId), this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id()));
    }

    @RequestMapping(value = "/get-contract-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisContractEditModel> checkToken(@RequestBody String token) throws SparkBusinessException, Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), NtisProcessRequestTypes.CONTRACT_REQUEST,
                token, false);
        return okResponse(ntisContractEdit.loadContractInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_language(),
                sprProcessRequestsDAO.getPrq_reference_id(), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id()));
    }

    /**
     * Metodas atnaujins sutarties informaciją pagal pateiktą NtisContractEditModel objektą
     * @param record - NtisContractEditModel objektas
     * @throws Exception
     */
    @RequestMapping(value = "/update-contract", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateContract(@RequestBody NtisContractEditModel record) throws Exception {
        ntisContractEdit.updateContract(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_usr_id());
    }

    /**
     * Metodas grąžins komentarus pagal pateiktą sutarties ID
     * @param cotId - sutarties ID
     * @return NtisContractRequestComment objektų masyvas
     * @throws Exception
     */
    @RequestMapping(value = "/load-contract-comments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<NtisContractRequestComment>> loadComments(@RequestBody String cotId) throws Exception {
        return okResponse(ntisContractEdit.loadComments(this.getDBConnection(), Utils.getDouble(cotId), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id()));
    }

    /**
     * Metodas sukurs naują DB įrašą sutarčių komentarų lentoje pagal pateiktą NtisContractRequestComment objektą
     * @param record - NtisContractRequestComment objektas
     * @throws Exception
     */
    @RequestMapping(value = "/save-contract-comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addComment(@RequestBody NtisContractRequestComment record) throws Exception {
        ntisContractEdit.addComment(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_usr_id());
    }

    /**
     * Metodas ištrins DB įrašą pagal nurodytą komentaro ID
     * @param commentId - komentaro, kurį norima ištrinti, ID
     * @throws Exception
     */
    @RequestMapping(value = "/delete-contract-comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteComment(@RequestBody NtisContractRequestComment comment) throws Exception {
        ntisContractEdit.deleteComment(this.getDBConnection(), comment, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_per_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/sign-contract", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisContractSignReq> signContract(@RequestBody String cotId) throws Exception {
        String signingUrl = ntisContractEdit.viewAndSignContract(this.getDBConnection(), Utils.getDouble(cotId),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
        NtisContractSignReq record = new NtisContractSignReq();
        record.setUrl(signingUrl);
        return okResponse(record);
    }

    @RequestMapping(value = "/contract-callback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleCallback(@RequestBody String callbackData) throws Exception {
        ntisContractEdit.handleCallback(this.getDBConnection(), callbackData);
    }

    @RequestMapping(value = "/preview-contract", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisContractSignReq> previewContract(@RequestBody String cotId) throws Exception {
        String previewUrl = ntisContractEdit.previewContract(this.getDBConnection(), Utils.getDouble(cotId),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_usr_id(),
                this.requestContext.getUserSession().getSes_org_id());
        NtisContractSignReq record = new NtisContractSignReq();
        record.setUrl(previewUrl);
        return okResponse(record);
    }

    @RequestMapping(value = "/contract-sign-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleSendNotifications(@RequestBody NtisContractEditModel contractBeforeSign) throws Exception {
        this.ntisContractEdit.handleSendNotifications(this.getDBConnection(), contractBeforeSign.getCot_id(), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_org_id(), null, contractBeforeSign, null);
    }

    // Contract edit (end)
    // Contract upload (start)
    @RequestMapping(value = "/save-contract", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisContractEditModel> saveContract(@RequestBody NtisContractEditModel record) throws Exception {
        return okResponse(ntisContractUpload.saveContract(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/load-temp-contract", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisContractEditModel> loadTempContract(@RequestBody NtisContractUploadRequest request) throws Exception {
        return okResponse(ntisContractUpload.loadTempContract(this.getDBConnection(), request, this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/load-uploaded-contract", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisContractEditModel> loadUploadedContract(@RequestBody String cotId) throws Exception {
        return okResponse(
                ntisContractUpload.loadUploadedContract(this.getDBConnection(), Utils.getDouble(cotId), this.requestContext.getUserSession().getSes_org_id(),
                        this.requestContext.getUserSession().getSes_per_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/delete-contract", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteContract(@RequestBody String cotId) throws Exception {
        ntisContractUpload.deleteContract(this.getDBConnection(), Utils.getDouble(cotId), this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getPer_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/send-notifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendNotifications(@RequestBody String cotId) throws Exception {
        this.ntisContractUpload.sendNotifications(this.getDBConnection(), Utils.getDouble(cotId), this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/load-uploaded-contract-by-token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisContractEditModel> checkContractToken(@RequestBody String token) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = processRequest.getIdetifierByToken(this.getDBConnection(), NtisProcessRequestTypes.CONTRACT_REQUEST,
                token, false);
        return okResponse(this.ntisContractUpload.loadUploadedContract(this.getDBConnection(), sprProcessRequestsDAO.getPrq_reference_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_per_id(),
                this.requestContext.getUserSession().getSes_language()));

    }
    // Contract upload (end)
}
