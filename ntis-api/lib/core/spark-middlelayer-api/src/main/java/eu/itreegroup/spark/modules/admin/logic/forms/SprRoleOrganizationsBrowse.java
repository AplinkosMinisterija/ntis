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
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprRoleOrganizationsBrowseSecurityManager;

@Component
public class SprRoleOrganizationsBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprRoleOrganizationsBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_ROLE_ORGANIZATIONS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark role organizations list", "Spark role organizations list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extracting organizations assigned to role from db");
        this.checkIsFormActionAssigned(conn, SprRoleOrganizationsBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();

        stmt.setStatement("WITH ROLE_ORGANIZATIONS AS (SELECT OAR_ID, " //
                + "OAR_DATE_FROM, "//
                + "OAR_DATE_TO, "//
                + "OAR_ROL_ID, "//
                + "OAR_ORG_ID " //
                + "FROM SPR_ORG_AVAILABLE_ROLES "//
                + "WHERE OAR_ROL_ID = ?) " //
                + "SELECT SO.ORG_ID, "//
                + "OAR_ID, "//
                + "TO_CHAR(OAR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS OAR_DATE_FROM, "//
                + "TO_CHAR(OAR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS OAR_DATE_TO, "//
                + "SO.ORG_CODE, " //
                + "SO.ORG_NAME, " //
                + "SO.ORG_EMAIL, "//
                + "SO.ORG_PHONE, " //
                + "CASE WHEN OAR_ROL_ID IS NULL THEN NULL ELSE 'Y' END BELONGS " //
                + "FROM SPR_ORGANIZATIONS SO LEFT JOIN ROLE_ORGANIZATIONS ON (SO.ORG_ID = OAR_ORG_ID)");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_rol_id")));
        stmt.addParam4WherePart("ORG_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("org_id"));
        stmt.addParam4WherePart("ORG_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("org_code"));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ORG_EMAIL", StatementAndParams.PARAM_STRING, advancedParamList.get("org_email"));
        stmt.addParam4WherePart("ORG_PHONE", StatementAndParams.PARAM_STRING, advancedParamList.get("org_phone"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OAR_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("OAR_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OAR_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("OAR_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ORG_ID", "ORG_CODE", "ORG_NAME", "ORG_EMAIL", "ORG_PHONE",
                        "TO_CHAR(OAR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(OAR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprRoleOrganizationsBrowseSecurityManager sqm = new SprRoleOrganizationsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprRoleOrganizationsBrowse.ACTION_READ, SprRoleOrganizationsBrowse.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}