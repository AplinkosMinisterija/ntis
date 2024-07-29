package lt.project.ntis.rest;

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
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.logic.forms.NtisSpWmServiceRequestsList;

/**
 * Klasė skirta paslaugų teikėjo ir vandentvarkos įmonės servisams apibrėžti
 * 
 */
@RestController
@RequestMapping("/ntis-sp-wm-shared")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisSpWmSharedService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisSpWmServiceRequestsList ntisSpWmServiceRequestsList;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // Services for service requests (start)
    /**
     * Function will return a list of service requests for provided organization id
     * @param params - paging and search params
     * @return JSON object
     * @throws Exception
     */
    @RequestMapping(value = "/get-service-requests", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRecList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSpWmServiceRequestsList.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language(),
                this.requestContext.getUserSession().getUsr_id(), this.requestContext.getUserSession().getSes_org_id()));
    }
    // Services for service requests (end)
}