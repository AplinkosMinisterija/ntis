package lt.project.rest;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestApiService;
import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.modules.admin.logic.forms.model.Request4RefCode;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.project.common.ProjectCommonLists;

@RestController
@RequestMapping("/project-common")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProjectCommonService extends S2RestApiService<SprBackendUserSession> {

    @Value("${jwt.secret}")
    private String secret;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ProjectCommonService.class);

    public ProjectCommonService(ProjectCommonLists projectCommontLists) {
        super();
        this.projectCommontLists = projectCommontLists;
    }

    private final ProjectCommonLists projectCommontLists;

    @PostMapping(value = "/get-list-of-values")
    public ResponseEntity<ArrayList<SprListIdKeyValue>> getcustomLisByCode(@RequestBody Request4RefCode domainParams) throws Exception {
        Double usrId = this.requestContext.getUserSession().getSes_usr_id();
        String recId = domainParams.getParameters().get("usr_id");
        if (recId != null) {
            usrId = Double.parseDouble(recId);
        }
        return okResponse(projectCommontLists.getListValuesByListCode(this.getDBConnection(), domainParams.getCode(), usrId, domainParams.getLang()));
    }

}
