package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprJobAvailableTemplate;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;

@Component
public class SprJobEdit extends FormBase {

    final private SprJobDefinitionsDBService sprJobDefinitionsDBService;

    public SprJobEdit(SprJobDefinitionsDBService sprJobDefinitionsDBService) {
        this.sprJobDefinitionsDBService = sprJobDefinitionsDBService;
    }

    private static final Logger log = LoggerFactory.getLogger(SprJobEdit.class);

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_JOB_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark job edit", "Spark job edit page");
        addFormActionCRUD();
    }

    public SprJobDefinitionsDAO getJob(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprJobDefinitionsDAO result = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            result = sprJobDefinitionsDBService.newRecord();
            result.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprJobEdit.ACTION_READ);
            result = sprJobDefinitionsDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
            result.setFormActions(getFormActions(conn));
        }
        return result;
    }

    public SprJobDefinitionsDAO setJob(Connection conn, SprJobDefinitionsDAO job) throws Exception {
        this.checkIsFormActionAssigned(conn, job.getRecordIdentifier() == null ? SprJobEdit.ACTION_CREATE : SprJobEdit.ACTION_UPDATE);
        return sprJobDefinitionsDBService.saveRecord(conn, job);
    }

    public ArrayList<SprJobAvailableTemplate> getAvailableTemplatesList(Connection conn) throws Exception {
        ArrayList<SprJobAvailableTemplate> result = new ArrayList<SprJobAvailableTemplate>();
        String stmt = "SELECT tml_id, tml_name FROM spr_templates";
        log.debug("load stmt: " + stmt);
        PreparedStatement preparedStmt = conn.prepareStatement(stmt);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            result.add(new SprJobAvailableTemplate(resultSet.getDouble("tml_id"), resultSet.getString("tml_name")));
        }
        return result;
    }

}
