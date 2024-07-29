package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.jasper.SprJasperReportService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.enums.JasperReportOutputType;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

@Component
public class SprPersonsEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPersonsEdit.class);

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    SprPersonsDBService personsDBService;

    // /**
    // * Reference to jasper request.
    // */
    // @Autowired
    // JasperReportRequest jasperReportRequest;

    @Autowired
    SprJasperReportService jasperService;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_PERSON_EDIT";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark persons edit", "Spark persons edit");
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
    public SprPersonsDAO get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprPersonsDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_CREATE);
            answerObj = personsDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
            answerObj = personsDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            answerObj.setFormActions(getFormActions(conn));
            if ("copy".equalsIgnoreCase(recordIdentifier.getActionType())) {
                answerObj.setPer_id(null);
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
    public SprPersonsDAO save(Connection conn, SprPersonsDAO record) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? SprPropertiesBrowse.ACTION_CREATE : SprPropertiesBrowse.ACTION_UPDATE);
        return personsDBService.saveRecord(conn, record);
    }

    /**
     * Function will delete record in db. Function will delete record in db that has provided identifier.
     * @param conn - db connection used for data deletion
     * @param recordIdentifier - used for record identification in db that should be deleted in db.
     * @throws NumberFormatException
     * @throws Exception
     */
    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_DELETE);
        personsDBService.deleteRecord(conn, Double.parseDouble(recordIdentifier.getId()));
    }

    /**
     * Function will generate and return report
     * @param conn - DB connection used for data deletion
     * @param ses_usr_id - User ID
     * @param ses_language - Language
     * @throws NumberFormatException
     * @throws Exception
     */
    public SprFilesDAO generateProfileReport(Connection dbConnection, Double ses_usr_id, String ses_language) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("USR_ID", ses_usr_id.toString());
        params.put("LANG", ses_language);
        return jasperService.generateReport(dbConnection, "test-rep", "test-rep", JasperReportOutputType.FILE_TYPE_PDF, params);
    }

}
