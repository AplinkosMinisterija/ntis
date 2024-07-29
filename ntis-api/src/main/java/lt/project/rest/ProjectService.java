package lt.project.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;

@RestController
@RequestMapping("/project")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProjectService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

}