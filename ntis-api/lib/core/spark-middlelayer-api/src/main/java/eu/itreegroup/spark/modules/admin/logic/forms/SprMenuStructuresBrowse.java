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
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprMenuStructuresBrowseSecurityManager;

@Component
public class SprMenuStructuresBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprFormsBrowse.class);

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
        return "SPR_MENU_STR_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD.
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark menu structure list", "Spark menu structure list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT MST_ID as id,"//
                + "coalesce(RT.RFT_DESCRIPTION, MST_SITE) as MST_SITE, " //
                + "MST_LANG, "//
                + "MST_TITLE, "//
                + "MST_ICON, "//
                + "MST_URI, "//
                + "CASE WHEN rt1.rft_display_code IS NOT NULL THEN rt1.rft_display_code ELSE rc1.rfc_meaning END AS MST_IS_PUBLIC, "//
                + "to_char(MST_DATE_FROM, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as MST_DATE_FROM, "//
                + "to_char(MST_DATE_TO, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as MST_DATE_TO "//
                + "FROM SPR_MENU_STRUCTURES ms " //
                + "INNER JOIN SPR_REF_CODES rc ON rc.rfc_code = ms.MST_SITE AND RC.RFC_DOMAIN = 'SPR_SITE_TYPE' "//
                + "INNER JOIN SPR_REF_CODES rc1 ON rc1.rfc_code = ms.MST_IS_PUBLIC AND RC1.RFC_DOMAIN = 'SPR_IS_PUBLIC_TYPE' "//
                + "INNER JOIN SPR_REF_TRANSLATIONS rt1 ON rc1.rfc_id = rt1.rft_rfc_id and rt1.rft_lang = ? "//
                + "INNER JOIN SPR_REF_TRANSLATIONS rt ON rt.rft_rfc_id = rc.rfc_id and rt.rft_lang = ms.MST_LANG ");
        stmt.addSelectParam(language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);

        stmt.addParam4WherePart("MST_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("mst_id"));
        stmt.addParam4WherePart("RFT_LANG", StatementAndParams.PARAM_STRING, advancedParamList.get("lang"));
        stmt.addParam4WherePart("MST_IS_PUBLIC", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_is_public"));
        stmt.addParam4WherePart("MST_LANG", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_lang"));
        stmt.addParam4WherePart("MST_SITE", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_site"));
        stmt.addParam4WherePart("MST_TITLE", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_title"));
        stmt.addParam4WherePart("MST_URI", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_uri"));

        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("MST_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("mst_date_from"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbStatementManager.getTruncatedColumnCommand("MST_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("mst_date_to"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(dbStatementManager.colNamesToConcatString("MST_ID", "MST_IS_PUBLIC", "MST_LANG", "RT.RFT_DESCRIPTION", "MST_TITLE", "MST_URI"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.addParam4WherePart("MST_SITE", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_site"));
        stmt.addParam4WherePart("MST_IS_PUBLIC", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_is_public"));
        stmt.addParam4WherePart("MST_LANG", StatementAndParams.PARAM_STRING, advancedParamList.get("mst_lang"));

        SprMenuStructuresBrowseSecurityManager sqm = new SprMenuStructuresBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprMenuStructuresBrowse.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
