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
import eu.itreegroup.spark.dto.BrowseData;
import eu.itreegroup.spark.modules.admin.logic.forms.model.list.FormListMapper;
import eu.itreegroup.spark.modules.admin.logic.forms.model.list.FormListRec;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprFormsBrowseSecurityManager;

@Component
public class SprFormsBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprFormsBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    /**
     * Method will return Angular form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_FORMS_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark forms list", "Spark forms list description");
        addFormActionCRUD();
    }

    /**
     * Method will return list of defined system forms. 
     * @param conn - connection to the db that will be used for data extraction
     * @param params - request parameters received from front end.
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public BrowseData<FormListRec> getRecList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprFormsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT FRM_ID, "//
                + "FRM_CODE, "//
                + "FRM_NAME, "//
                + "FRM_DESCRIPTION, "//
                + "TO_CHAR(REC_CREATE_TIMESTAMP, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as REC_CREATE_TIMESTAMP "//
                + "FROM SPR_FORMS ");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);

        stmt.addParam4WherePart("FRM_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("frm_id"));
        stmt.addParam4WherePart("FRM_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("frm_name"));
        stmt.addParam4WherePart("FRM_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("frm_code"));
        stmt.addParam4WherePart("FRM_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("frm_description"));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("REC_CREATE_TIMESTAMP"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("rec_create_timestamp"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("FRM_ID", "FRM_NAME", "FRM_CODE", "FRM_DESCRIPTION",
                        "TO_CHAR(REC_CREATE_TIMESTAMP, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') "),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprFormsBrowseSecurityManager sqm = new SprFormsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprFormsBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsDTO(conn, stmt, params, sqm, new FormListMapper());
    }
}
