package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.modules.admin.dao.SprTemplateTextsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprTemplatesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprTemplate;
import eu.itreegroup.spark.modules.admin.service.SprTemplateTextsDBService;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Component
public class SprTemplateEdit extends FormBase {

    final private SprTemplatesDBService sprTemplatesDBService;

    final private SprTemplateTextsDBService sprTemplateTextsDBService;

    public SprTemplateEdit(SprTemplatesDBService sprTemplatesDBService, SprTemplateTextsDBService sprTemplateTextsDBService) {
        this.sprTemplatesDBService = sprTemplatesDBService;
        this.sprTemplateTextsDBService = sprTemplateTextsDBService;
    }

    private static final Logger log = LoggerFactory.getLogger(SprTemplateEdit.class);

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_TEMPLATE_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark template edit", "Spark template edit page");
        addFormActionCRUD();
    }

    public SprTemplate getTemplate(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprTemplate template = new SprTemplate();
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            SprTemplatesDAO templatesDAO = sprTemplatesDBService.newRecord();
            template.copyValues(templatesDAO);
            template.setFormActions(getFormActions(conn));
            template.setTexts(new ArrayList<SprTemplateTextsDAO>());
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprTemplateEdit.ACTION_READ);
            SprTemplatesDAO templatesDAO = sprTemplatesDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            template.copyValues(templatesDAO);
            template.setFormActions(getFormActions(conn));
            template.setTexts(sprTemplateTextsDBService.loadRecordsByParent(conn, recordIdentifier.getIdAsDouble()));
        }
        return template;
    }

    public SprTemplate setTemplate(Connection conn, SprTemplate template) throws Exception {
        this.checkIsFormActionAssigned(conn, template.getRecordIdentifier() == null ? SprTemplateEdit.ACTION_CREATE : SprTemplateEdit.ACTION_UPDATE);
        SprTemplatesDAO templatesDAO = sprTemplatesDBService.saveRecord(conn, template);
        List<SprTemplateTextsDAO> loadedTexts = sprTemplateTextsDBService.loadRecordsByParent(conn, templatesDAO.getTml_id());
        List<SprTemplateTextsDAO> templateTexts = template.getTexts();
        List<SprTemplateTextsDAO> resultTexts = new ArrayList<SprTemplateTextsDAO>();
        for (int i = 0; i < loadedTexts.size(); i++) {
            Boolean exists = false;
            for (SprTemplateTextsDAO templateText : templateTexts) {
                if (loadedTexts.get(i).getTmt_id().equals(templateText.getTmt_id())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                sprTemplateTextsDBService.deleteRecord(conn, loadedTexts.get(i).getTmt_id());
            }
        }
        for (SprTemplateTextsDAO templateText : templateTexts) {
            templateText.setTmt_tml_id(templatesDAO.getTml_id());
            resultTexts.add(sprTemplateTextsDBService.saveRecord(conn, templateText));
        }
        SprTemplate result = new SprTemplate();
        result.copyValues(templatesDAO);
        result.setTexts(resultTexts);
        return result;
    }

    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprTemplate record) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        return sprTemplatesDBService.getViolatedConstraints(conn, record, null);
    }
}
