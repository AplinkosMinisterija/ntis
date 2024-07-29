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
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprPersonsBrowseSecurityManager;

@Component
public class SprPersonList extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPersonList.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_PERSON_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark person list", "Spark person list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT PER_ID, "//
                + "PER_NAME, "//
                + "PER_SURNAME, "//
                + "TO_CHAR(PER_DATE_OF_BIRTH, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as PER_DATE_OF_BIRTH, "//
                + "PER_EMAIL, "//
                + "PER_LRT_RESIDENT, "//
                + "TO_CHAR(REC_CREATE_TIMESTAMP, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as REC_CREATE_TIMESTAMP "//
                + "FROM SPR_PERSONS");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);

        stmt.addParam4WherePart("PER_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("per_id"));
        stmt.addParam4WherePart("PER_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("per_name"));
        stmt.addParam4WherePart("PER_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("per_surname"));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("PER_DATE_OF_BIRTH"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("per_date_of_birth"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("PER_EMAIL", StatementAndParams.PARAM_STRING, advancedParamList.get("per_email"));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("REC_CREATE_TIMESTAMP"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("rec_create_timestamp"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("PER_ID", "PER_NAME", "PER_SURNAME", "PER_EMAIL",
                        "TO_CHAR(PER_DATE_OF_BIRTH, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(REC_CREATE_TIMESTAMP, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprPersonsBrowseSecurityManager sqm = new SprPersonsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprPersonList.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
