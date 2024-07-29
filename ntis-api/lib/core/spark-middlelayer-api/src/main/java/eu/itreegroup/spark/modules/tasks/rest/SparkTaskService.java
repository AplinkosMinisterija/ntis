package eu.itreegroup.spark.modules.tasks.rest;

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
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import eu.itreegroup.spark.modules.tasks.logic.forms.SprTaskCardsBrowse;
import eu.itreegroup.spark.modules.tasks.logic.forms.SprTaskEdit;
import eu.itreegroup.spark.modules.tasks.logic.forms.SprTasksBrowse;
import eu.itreegroup.spark.modules.tasks.logic.forms.model.SprTaskEditModel;

@RestController
@RequestMapping("/spark-tasks")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SparkTaskService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprTasksBrowse sprTaskBrowse;

    @Autowired
    SprTaskCardsBrowse sprTaskCardsBrowse;

    @Autowired
    SprTaskEdit sprTaskEdit;

    SprTaskEditModel sprTaskEditModel;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    @RequestMapping(value = "/find-tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkTasks(@RequestBody SelectRequestParams params) throws Exception {
        ResponseEntity<String> answer = okResponse(
                this.sprTaskBrowse.getRecList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language()));
        return answer;
    }

    @RequestMapping(value = "/find-task-cards", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTaskCards(@RequestBody SelectRequestParams params) throws Exception {
        Double userId = this.requestContext.getUserSession().getUsr_id();
        String language = this.requestContext.getUserSession().getSes_language();
        ResponseEntity<String> answer = okResponse(this.sprTaskCardsBrowse.getTaskCards(this.getDBConnection(), userId, language, params));
        return answer;
    }

    @RequestMapping(value = "/get-spark-person-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPersonsList() throws Exception {
        ResponseEntity<String> answer = okResponse(sprTaskEdit.getPersonsList(this.getDBConnection()));
        return answer;
    }

    @RequestMapping(value = "/set-spark-task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprTaskEditModel> setSparkTask(@RequestBody SprTaskEditModel record) throws Exception {
        Double orgId = this.requestContext.getUserSession().getSes_org_id();
        ResponseEntity<SprTaskEditModel> answer = okResponse(sprTaskEdit.save(this.getDBConnection(), record, orgId));
        return answer;
    }

    @RequestMapping(value = "/get-spark-task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SprTaskEditModel> getSparkForm(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        ResponseEntity<SprTaskEditModel> answer = okResponse(sprTaskEdit.get(this.getDBConnection(), recordIdentifier));
        return answer;
    }

    @RequestMapping(value = "/del-spark-task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delSparkParamater(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprTaskEdit.delete(this.getDBConnection(), recordIdentifier);
        return okResponse(null);
    }

}