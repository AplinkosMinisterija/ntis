package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprAuditableTablesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SwitchStatus;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprOrganizationsBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprAuditableTablesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Component
public class SprAuditableTablesBrowse extends FormBase {

    public static final String CHANGE_TABLE_AUDIT = "CHANGE_TABLE_AUDIT";

    public static final String CHANGE_TABLE_AUDIT_NAME = "Change table audit";

    private static final Logger log = LoggerFactory.getLogger(SprAuditableTablesBrowse.class);

    @Autowired
    DBStatementManager dbstatementManager;

    public SprAuditableTablesBrowse(BaseControllerJDBC queryController, DBStatementManager dbstatementManager, DBStatementManager dbStatementManager2,
            SprAuditableTablesDBService sprAuditableTablesDBService) {
        super();
        this.queryController = queryController;
        this.dbStatementManager = dbStatementManager2;
        this.sprAuditableTablesDBService = sprAuditableTablesDBService;
    }

    private final BaseControllerJDBC queryController;

    private final DBStatementManager dbStatementManager;

    private final SprAuditableTablesDBService sprAuditableTablesDBService;

    @Override
    public String getFormName() {
        return "SPR_AUDITABLE_TABLES";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark auditable tables  list", "Spark auditable tables list");
        addFormActions(FormBase.ACTION_READ);
        addFormAction(CHANGE_TABLE_AUDIT, CHANGE_TABLE_AUDIT_NAME, CHANGE_TABLE_AUDIT_NAME);
    }

    public String getAuditableTablesList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprAuditableTablesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT aut_id, "//
                + "               aut_table_schema, "//
                + "               aut_table_name, "//
                + "               aut_trigger_enabled "//
                + "          FROM spr_auditable_tables");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("aut_table_name", StatementAndParams.PARAM_STRING, advancedParamList.get("aut_table_name"));
        stmt.addParam4WherePart("aut_table_schema", StatementAndParams.PARAM_STRING, advancedParamList.get("aut_table_schema"));
        stmt.addParam4WherePart("aut_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("aut_id"));
        stmt.addParam4WherePart(dbstatementManager.colNamesToConcatString("aut_table_name", "aut_table_schema", "aut_id"), StatementAndParams.PARAM_STRING,
                advancedParamList.get("quickSearch"));
        SprOrganizationsBrowseSecurityManager sqm = new SprOrganizationsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprAuditableTablesBrowse.ACTION_READ, SprAuditableTablesBrowse.ACTION_UPDATE,
                SprAuditableTablesBrowse.CHANGE_TABLE_AUDIT };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public String getAuditableTablesRecList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprAuditableTablesBrowse.ACTION_READ);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        HashMap<String, String> paramList = params.getParamList();
        SprAuditableTablesDAO tab = sprAuditableTablesDBService.loadRecord(conn, Double.valueOf(paramList.get("p_aut_id")));

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT * "//
                + "FROM  spark_audit.aud_" + tab.getAut_table_name());

        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);

        stmt.addParam4WherePart("rec_aud_timestamp", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("rec_aud_timestamp"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart("rec_userid", StatementAndParams.PARAM_STRING, advancedParamList.get("rec_aud_user_id"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("REC_AUD_ID", "REC_AUD_USER_ID", "REC_AUD_OPERATION_TYPE", "REC_AUD_HOST",
                        "TO_CHAR(REC_AUD_TIMESTAMP, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprOrganizationsBrowseSecurityManager sqm = new SprOrganizationsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprAuditableTablesBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void setAuditEnabled(Connection conn, SwitchStatus params) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, CHANGE_TABLE_AUDIT);
        Double autId = params.getId();
        SprAuditableTablesDAO sprAuditableTableDAO = sprAuditableTablesDBService.loadRecord(conn, autId);
        sprAuditableTableDAO.setAut_trigger_enabled(params.getStatus());
        StatementAndParams stmt = new StatementAndParams();
        if (DBStatementManager.DB_TYPE_POSTGRESQL.equalsIgnoreCase(dbStatementManager.getDbType())) {
            stmt.setStatement(" call spark.spark_change_triger_status(?,?,?);");
            stmt.addSelectParam(sprAuditableTableDAO.getAut_table_schema() + "." + sprAuditableTableDAO.getAut_table_name());
            stmt.addSelectParam(sprAuditableTableDAO.getAut_trigger_name());
            stmt.addSelectParam((YesNo.Y.getCode().equals(params.getStatus()) ? "ENABLE" : "DISABLE"));
        } else {
            stmt.setStatement("ALTER TRIGGER  " + sprAuditableTableDAO.getAut_table_schema() + "." + sprAuditableTableDAO.getAut_trigger_name() + " "
                    + (YesNo.Y.getCode().equals(params.getStatus()) ? "ENABLE" : "DISABLE") + " ;");
        }
        queryController.adjustRecordsInDB(conn, stmt);
        sprAuditableTablesDBService.saveRecord(conn, sprAuditableTableDAO);
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
    public SprAuditableTablesDAO save(Connection conn, SprAuditableTablesDAO record) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getRecordIdentifier() == null ? SprFormsBrowse.ACTION_CREATE : SprFormsBrowse.ACTION_UPDATE);
        return sprAuditableTablesDBService.saveRecord(conn, record);
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
    public SprAuditableTablesDAO get(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprAuditableTablesDAO answerObj = null;
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            this.checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_CREATE);
            answerObj = sprAuditableTablesDBService.newRecord();
            answerObj.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_READ);
            answerObj = sprAuditableTablesDBService.loadRecord(conn, Double.parseDouble(recordIdentifier.getId()));
            answerObj.setFormActions(getFormActions(conn));
        }
        return answerObj;
    }

}
