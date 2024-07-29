package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprMenuStructuresDAO;
import eu.itreegroup.spark.modules.admin.service.SprMenuStructuresDBService;

@Component
public class SprMenuStructuresEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprFormsEdit.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    SprMenuStructuresDBService menuStructuresDBService;

    @Override
    public String getFormName() {
        return "SPR_MENU_STR_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark menu structure edit", "Spark menu structure edit");
        addFormActionCRUD();
    }

    public SprMenuStructuresDAO get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprMenuStructuresDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, SprMenuStructuresBrowse.ACTION_CREATE);
            answerObj = menuStructuresDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprMenuStructuresBrowse.ACTION_READ);
            answerObj = menuStructuresDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            answerObj.setFormActions(getFormActions(conn));
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setMst_id(null);
                answerObj.setRec_create_timestamp(null);
                answerObj.setRec_timestamp(null);
                answerObj.setRec_userid(null);
                answerObj.setRec_version(null);
            }
        }
        return answerObj;
    }

    public SprMenuStructuresDAO save(Connection conn, SprMenuStructuresDAO record) throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getRecordIdentifier() == null ? SprMenuStructuresBrowse.ACTION_CREATE : SprMenuStructuresBrowse.ACTION_UPDATE);
        return menuStructuresDBService.saveRecord(conn, record);
    }

    public String getForms(Connection conn) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT FRM_ID, FRM_CODE, FRM_NAME FROM SPR_FORMS");
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprMenuStructuresBrowse.ACTION_DELETE);
        menuStructuresDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
    }
}