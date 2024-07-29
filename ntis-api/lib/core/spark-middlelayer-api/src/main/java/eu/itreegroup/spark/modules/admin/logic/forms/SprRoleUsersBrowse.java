package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

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
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprRoleUsersBrowseSecurityManager;

@Component
public class SprRoleUsersBrowse extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    /**
     * Method will return Angular form name. Same name should be defined in DB as well (in case if enabled data synchronisation with DB 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_ROLE_USERS_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) form name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark role users list", "Spark role users list");
        addFormActionCRUD();
    }

    /**
     * Method will return list of defined system parameters. 
     * @param conn - connection to the DB that will be used for data extraction
     * @param params - request parameters received from front end.
     * @return JSON array (string), that hold result of performed queries. 
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double userId) throws Exception {
        this.checkIsFormActionAssigned(conn, SprRoleUsersBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("WITH ROLE_USERS AS (SELECT URO_ID,"//
                + " URO_DATE_FROM," //
                + " URO_DATE_TO," //
                + " URO_ROL_ID," //
                + " URO_USR_ID "//
                + " FROM SPR_USER_ROLES" //
                + " WHERE URO_ROL_ID = ?)" //
                + " SELECT RU.URO_ID," //
                + " TO_CHAR(RU.URO_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as URO_DATE_FROM, "//
                + " TO_CHAR(RU.URO_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as URO_DATE_TO, "//
                + " RU.URO_ROL_ID," //
                + " U.USR_ID," //
                + " U.USR_USERNAME," //
                + " coalesce(RT.RFT_DISPLAY_CODE, U.USR_TYPE) as USR_TYPE, "//
                + " U.USR_PERSON_NAME," //
                + " U.USR_PERSON_SURNAME, " //
                + " O.ORG_NAME, " //
                + " CASE WHEN RU.URO_ROL_ID IS NULL THEN NULL ELSE 'Y' END BELONGS" //
                + " FROM SPR_USERS U " //
                + " LEFT JOIN ROLE_USERS RU ON (U.USR_ID = RU.URO_USR_ID) " //
                + " LEFT JOIN SPR_ORGANIZATIONS O ON (U.USR_ORG_ID = O.ORG_ID) " //
                + " INNER JOIN SPR_REF_CODES RC ON RC.RFC_CODE = USR_TYPE AND RC.RFC_DOMAIN = 'SPR_USER_TYPE'"//
                + " INNER JOIN SPR_REF_TRANSLATIONS RT ON RT.RFT_RFC_ID = RC.RFC_ID");
        stmt.addParam4WherePart("RT.RFT_LANG = ? ", lang);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_rol_id")));
        stmt.addParam4WherePart("U.USR_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("usr_id"));
        stmt.addParam4WherePart("U.USR_USERNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_username"));
        stmt.addParam4WherePart("U.USR_PERSON_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_name"));
        stmt.addParam4WherePart("U.USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_surname"));
        stmt.addParam4WherePart("O.ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("URO_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("uro_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("URO_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("uro_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("USR_ID", "USR_USERNAME", "USR_TYPE", "ORG_NAME", "USR_PERSON_NAME", "USR_PERSON_SURNAME",
                        "TO_CHAR(URO_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(URO_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));
        stmt.addParam4WherePart("U.USR_TYPE = ? ", paramList.get("p_usr_type"));
        SprRoleUsersBrowseSecurityManager sqm = new SprRoleUsersBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprRoleUsersBrowse.ACTION_READ, SprRoleUsersBrowse.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
