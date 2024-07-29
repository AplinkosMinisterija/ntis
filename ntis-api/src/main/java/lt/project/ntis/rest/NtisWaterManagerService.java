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
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.logic.forms.NtisWaterManagerSettings;
import lt.project.ntis.models.NtisServiceProviderContacts;
import lt.project.ntis.models.NtisServiceProviderSettingsInfo;

@RestController
@RequestMapping("/ntis-water-managers")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisWaterManagerService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    NtisWaterManagerSettings ntisWaterManagerSettings;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // Water manager settings (start)
    @RequestMapping(value = "/get-profile-info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceProviderSettingsInfo> getProfileInfo() throws Exception {
        return okResponse(this.ntisWaterManagerSettings.getInfo(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/save-contacts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisServiceProviderContacts> updateContacts(@RequestBody NtisServiceProviderContacts contacts) throws Exception {
        return okResponse(this.ntisWaterManagerSettings.updateContacts(this.getDBConnection(), contacts, this.requestContext.getUserSession().getUsr_id()));
    }
    // Water manager settings (end)

}
