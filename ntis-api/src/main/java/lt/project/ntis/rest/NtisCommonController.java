package lt.project.ntis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.logic.forms.model.NtisSystemWorksInfo;

@RestController
@RequestMapping("/ntis-common")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisCommonController extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {
    
    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }
    
    @GetMapping(value = "/works-info")
    public ResponseEntity<NtisSystemWorksInfo> getSystemWorksInfo() throws Exception {
        return okResponse(ntisCommonMethods.getSystemWorksInfo(getDBConnection()));
    }



}
