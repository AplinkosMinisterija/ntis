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
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprRolesBrowseSecurityManager;

@Component
public class SprRolesBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprRolesBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_ROLES_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark roles list", "Spark roles list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ROL_ID as id, "//
                + "ROL_CODE, "//
                + "coalesce(RT.RFT_DISPLAY_CODE, ROL_TYPE) as ROL_TYPE, " //
                + "ROL_NAME, "//
                + "ROL_DESCRIPTION, "//
                + "TO_CHAR(ROL_DATE_FROM,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as ROL_DATE_FROM, "//
                + "TO_CHAR(ROL_DATE_TO,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as ROL_DATE_TO "//
                + "FROM SPR_ROLES "//
                + "INNER JOIN SPR_REF_CODES rc ON rc.rfc_code = rol_type AND RC.RFC_DOMAIN = 'SPR_ROLE_TYPE' "//
                + "INNER JOIN SPR_REF_TRANSLATIONS rt ON rt.rft_rfc_id = rc.rfc_id");

        stmt.addParam4WherePart("RT.RFT_LANG = ? ", language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("ROL_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rol_id"));
        stmt.addParam4WherePart("RFT_LANG", StatementAndParams.PARAM_STRING, advancedParamList.get("lang"));
        stmt.addParam4WherePart("ROL_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_code"));
        stmt.addParam4WherePart("ROL_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_type"));
        stmt.addParam4WherePart("ROL_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_name"));
        stmt.addParam4WherePart("ROL_DESCRIPTION", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_description"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("ROL_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("rol_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("ROL_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("rol_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ROL_ID", "ROL_CODE", "ROL_NAME", "RT.RFT_DISPLAY_CODE", "ROL_DESCRIPTION",
                        "TO_CHAR(ROL_DATE_FROM,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(ROL_DATE_TO,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.addParam4WherePart("ROL_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("rol_type"));
        SprRolesBrowseSecurityManager sqm = new SprRolesBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprRolesBrowse.ACTION_UPDATE, SprRolesBrowse.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
