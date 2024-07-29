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
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;
import eu.itreegroup.spark.modules.admin.service.SprFormActionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprFormsDBService;

@Component
public class SprFormsEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprFormsEdit.class);

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    SprFormsDBService formsDBService;

    @Autowired
    SprFormActionsDBService formActionsDBService;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_FORM_EDIT";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark forms edit", "Spark forms edit");
        addFormActionCRUD();
    }

    /**
     * Function fill get properties record object. In case if recordIdentifier provided with value "NEW" will be constructed new object, otherwise
     * will be object will be constructed from DB
     * @param conn - db connections
     * @param recordIdentifier record ID. In case identifier is equal to "NEW" function will construct new object.
     * @return properties object.
     * @throws NumberFormatException
     * @throws Exception
     */
    public SprFormsDAO get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprFormsDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_CREATE);
            answerObj = formsDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_READ);
            answerObj = formsDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            answerObj.setFormActions(getFormActions(conn));
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setFrm_id(null);
                answerObj.setRec_create_timestamp(null);
                answerObj.setRec_timestamp(null);
                answerObj.setRec_userid(null);
                answerObj.setRec_version(null);
            }
        }
        return answerObj;
    }

    /**
     * Function will save provided object to the db. Before save will be performed below listed actions:
     *      1. check if user has right perform this action (insert or update)
     *      2. validate provided data
     *      3. save data to the db.
     * @param conn - connection to the db, that will be used for data saving.
     * @param record - object that should be stored in db
     * @return - saved object.
     * @throws Exception
     */
    public SprFormsDAO save(Connection conn, SprFormsDAO record) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? SprFormsBrowse.ACTION_CREATE : SprFormsBrowse.ACTION_UPDATE);
        return formsDBService.saveRecord(conn, record);
    }

    /**
     * Function will delete record in db. Function will delete record in db that has provided identifier.
     * @param conn - db connection used for data deletion
     * @param recordIdentifier - used for record identification in db that should be deleted in db.
     * @throws NumberFormatException
     * @throws Exception
     */
    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_DELETE);
        formActionsDBService.deleteActionsForForms(conn, Double.parseDouble(recordIdentifier.getId()));
        formsDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
    }

}
