package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprPropertiesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprPropertiesModel;
import eu.itreegroup.spark.modules.admin.service.SprPropertiesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Component
public class SprPropertiesEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPropertiesEdit.class);

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    /**
     * Reference to the properties DB service instance.
     */
    @Autowired
    SprPropertiesDBService propertiesDBService;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    FileStorageService fileStorageService;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_PROPERTY_EDIT";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD.
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark properties edit", "Spark properties edit");
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
    public SprPropertiesModel get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprPropertiesModel result = new SprPropertiesModel();
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_CREATE);
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getIdAsDouble());
            this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
            SprPropertiesDAO prpDAO = propertiesDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            result.setPrp_id(prpDAO.getPrp_id());
            result.setPrp_name(prpDAO.getPrp_name());
            result.setPrp_description(prpDAO.getPrp_description());
            result.setPrp_type(prpDAO.getPrp_type());
            result.setPrp_value(prpDAO.getPrp_value());
            result.setPrp_fil_id(prpDAO.getPrp_fil_id());
            result.setPrp_guid(prpDAO.getPrp_guid());
            result.setPrp_install_instance(prpDAO.getPrp_install_instance());
            if (prpDAO.getPrp_fil_id() != null) {
                result.setAttachment(this.getFile(conn, prpDAO.getPrp_fil_id()));
            }
            if (ACTION_COPY.equalsIgnoreCase(recordIdentifier.getActionType())) {
                if (prpDAO.getPrp_fil_id() != null) {
                    SprFilesDAO fileCopy = fileStorageService.saveFile(fileStorageService.getFileByFileEncryptedKey(result.getAttachment().getFil_key(), conn),
                            result.getAttachment().getFil_name(), result.getAttachment().getFil_content_type(), conn);
                    result.setAttachment(this.getFile(conn, fileCopy.getFil_id()));
                }
                result.setPrp_id(null);
                result.setRec_create_timestamp(null);
                result.setRec_timestamp(null);
                result.setRec_userid(null);
                result.setRec_version(null);
            }
        }
        result.setFormActions(getFormActions(conn));
        return result;
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
    public SprPropertiesModel save(Connection conn, SprPropertiesModel record) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        if (record.getAttachment() != null) {
            record.setPrp_fil_id(this.getFileId(conn, record.getAttachment()));
            sprFilesDBService.markAsConfirmed(conn, this.getFileId(conn, record.getAttachment()));
        }
        if (record.getPrp_id() != null && record.getAttachment() == null) {
            SprPropertiesDAO prpDAO = propertiesDBService.loadRecord(conn, record.getPrp_id());
            if (prpDAO.getPrp_fil_id() != null) {
                sprFilesDBService.markAsDeleted(conn, prpDAO.getPrp_fil_id());
            }
        }
        dbPropertyManager.updateCache(record.getPrp_name());
        propertiesDBService.saveRecord(conn, record);
        this.clearAppData(record.getPrp_name());
        return record;
    }

    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprPropertiesModel record) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? FormBase.ACTION_CREATE : FormBase.ACTION_UPDATE);
        return propertiesDBService.getViolatedConstraints(conn, record, null);
    }

    /**
     * Function will delete record in db. Function will delete record in db that has provided identifier.
     * @param conn - db connection used for data deletion
     * @param recordIdentifier - used for record identification in db that should be deleted in db.
     * @throws NumberFormatException
     * @throws Exception
     */
    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        SprPropertiesDAO record = propertiesDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
        dbPropertyManager.updateCache(record.getPrp_name());
        propertiesDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
        if (record.getPrp_fil_id() != null) {
            sprFilesDBService.markAsDeleted(conn, record.getPrp_fil_id());
        }
    }

    private Double getFileId(Connection conn, SprFile file) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        SprFilesDAO fileDB = new SprFilesDAO();
        fileDB = sprFilesDBService.loadRecordByKey(conn, fileStorageService.decryptFileKey(file.getFil_key()));
        return fileDB.getFil_id();
    }

    private SprFile getFile(Connection conn, Double fileId) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT FIL_ID, " //
                + " FIL_CONTENT_TYPE, " //
                + " FIL_KEY, " //
                + " FIL_NAME, " //
                + " FIL_SIZE, " //
                + " FIL_STATUS, " //
                + " TO_CHAR(FIL_STATUS_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') AS FIL_STATUS_DATE " //
                + " FROM SPR_FILES " //
                + " WHERE FIL_ID = ? ");
        stmt.addSelectParam(fileId);
        List<SprFile> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprFile.class);
        SprFile queryResult = null;
        if (!data.isEmpty()) {
            queryResult = data.get(0);
            queryResult.setFil_key(fileStorageService.encryptFileKey(queryResult.getFil_key()));
        }
        return queryResult;
    }

    /**
     * Clear cached app data if changed property is public
     * 
     * @param changedProperty - name of changed property
     * @throws Exception
     */
    private void clearAppData(String changedProperty) throws Exception {
        if (Arrays.stream(this.propertiesDBService.getPublicPropertyNames()).anyMatch(property -> property.equals(changedProperty))) {
            log.debug(String.format("Property with name %s has been changed, clearing cached app data.", changedProperty));
            this.dbPropertyManager.clearAppData();
        }
    }
}
