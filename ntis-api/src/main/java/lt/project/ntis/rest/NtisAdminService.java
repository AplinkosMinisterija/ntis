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
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprOrganizationsEdit;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.logic.forms.NtisSprOrganizationsEdit;

/**
 * Klasė skirta saugoti duomenis apie organizacijas
 * 
 */

@RestController
@RequestMapping("/ntis-admin")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisAdminService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisSprOrganizationsEdit ntisSprOrganizationsEdit;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    @RequestMapping(value = "/set-spark-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprOrganizationsNtisDAO> setSparkOrg(@RequestBody SprOrganizationsNtisDAO record) throws Exception {
        return okResponse((SprOrganizationsNtisDAO) ntisSprOrganizationsEdit.saveRecord(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_usr_id()));
    }

}
