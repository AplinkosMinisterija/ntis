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
import eu.itreegroup.spark.modules.admin.dao.SprRefDictionariesDAO;
import eu.itreegroup.spark.modules.admin.service.SprRefDictionariesDBService;

@Component
public class SprRefDictionariesEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprRefDictionariesBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    protected SprRefDictionariesDBService dictionariesDBService;

    @Override
    public String getFormName() {
        return "SPR_REF_DICTIONARY_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark classifier edit", "Spark classifier edit");
        addFormActionCRUD();
    }

    public SprRefDictionariesDAO get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprRefDictionariesDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, SprRefDictionariesBrowse.ACTION_CREATE);
            answerObj = dictionariesDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprRefDictionariesBrowse.ACTION_READ);
            answerObj = dictionariesDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            answerObj.setFormActions(getFormActions(conn));
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setRfd_id(null);
                answerObj.setRec_create_timestamp(null);
                answerObj.setRec_timestamp(null);
                answerObj.setRec_userid(null);
                answerObj.setRec_version(null);
            }
        }
        return answerObj;
    }

    public SprRefDictionariesDAO save(Connection conn, SprRefDictionariesDAO record) throws Exception {
        this.checkIsFormActionAssigned(conn,
                record.getRecordIdentifier() == null ? SprRefDictionariesBrowse.ACTION_CREATE : SprRefDictionariesBrowse.ACTION_UPDATE);
        return dictionariesDBService.saveRecord(conn, record);
    }

    public String getDictionaries(Connection conn) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprRefDictionariesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT RFD_TABLE_NAME, RFD_NAME||' ('||RFD_TABLE_NAME||')' as RFD_NAME FROM SPR_REF_DICTIONARIES");
        return queryController.selectQueryAsJSON(conn, stmt);
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprRefDictionariesBrowse.ACTION_DELETE);
        dictionariesDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
    }
}
