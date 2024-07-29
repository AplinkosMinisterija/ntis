package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
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
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.OrgUserRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprOrgUsersListSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;

@Component
public class SprUserOrgsList extends FormBase {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprUserOrgsList.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_USER_ORGS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark user organizations list", "Spark user organizations list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUserOrgsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("WITH USER_ORGS AS (SELECT OU_ID, "//
                + "OU_POSITION, "//
                + "OU_DATE_FROM, "//
                + "OU_DATE_TO, "//
                + "OU_USR_ID, "//
                + "OU_ORG_ID " //
                + "FROM SPR_ORG_USERS "//
                + "WHERE OU_USR_ID = ?)" //
                + "SELECT OU_ID, " //
                + "OU_USR_ID, " //
                + "OU_POSITION AS OU_POSITION_CODE, " //
                + "CASE WHEN RFT.RFT_DISPLAY_CODE IS NOT NULL THEN RFT.RFT_DISPLAY_CODE ELSE RC.RFC_MEANING END AS OU_POSITION," //
                + "TO_CHAR(OU_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as OU_DATE_FROM, "//
                + "TO_CHAR(OU_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as OU_DATE_TO, "//
                + "SO.ORG_ID, " //
                + "SO.ORG_NAME, " //
                + "SO.ORG_CODE, " //
                + "SO.ORG_EMAIL, " //
                + "SO.ORG_PHONE, " //
                + "CASE WHEN OU_ORG_ID IS NULL THEN NULL ELSE 'Y' END BELONGS " //
                + "FROM SPR_ORGANIZATIONS SO " //
                + "LEFT JOIN USER_ORGS ON (SO.ORG_ID = OU_ORG_ID) " //
                + "LEFT JOIN SPR_REF_CODES RC ON RC.RFC_CODE = OU_POSITION AND RC.RFC_DOMAIN = 'SPR_PERSON_POSITION'" //
                + "LEFT JOIN SPR_REF_TRANSLATIONS RFT ON RC.RFC_ID = RFT.RFT_RFC_ID AND RFT.RFT_LANG = ? ");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_usr_id")));
        stmt.addSelectParam(language);
        stmt.addParam4WherePart("ORG_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("org_id"));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ORG_CODE", StatementAndParams.PARAM_STRING, advancedParamList.get("org_code"));
        stmt.addParam4WherePart("ORG_EMAIL", StatementAndParams.PARAM_STRING, advancedParamList.get("org_email"));
        stmt.addParam4WherePart("ORG_PHONE", StatementAndParams.PARAM_STRING, advancedParamList.get("org_phone"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OU_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("ou_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OU_DATE_TO"), StatementAndParams.PARAM_DATE, advancedParamList.get("ou_date_to"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("OU_POSITION = ? ", paramList.get("p_ou_position"));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("ORG_ID", "ORG_NAME", "ORG_CODE", "ORG_EMAIL", "ORG_PHONE",
                        "TO_CHAR(OU_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(OU_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        String orderParamsStr = " BELONGS ";
        params.getPagingParams().setOrder_clause(orderParamsStr);
        SprOrgUsersListSecurityManager sqm = new SprOrgUsersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprUserOrgsList.ACTION_READ, SprUserOrgsList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void updateUserOrgs(Connection conn, ArrayList<OrgUserRequest> records) throws Exception {
        for (OrgUserRequest record : records) {
            if (record.getIsSelected()) {
                if (record.getOu_id() == null || record.getOu_id().length() == 0) {
                    this.checkIsFormActionAssigned(conn, SprUserOrgsList.ACTION_CREATE);
                    SprOrgUsersDAO userOrg = sprOrgUsersDBService.newRecord();
                    userOrg.setOu_org_id(Utils.getDouble(record.getOrg_id()));
                    userOrg.setOu_usr_id(Utils.getDouble(record.getOu_usr_id()));
                    userOrg.setOu_position(record.getOu_position());
                    userOrg.setOu_date_to(record.getOu_date_to());
                    if (record.getOu_date_from() != null) {
                        userOrg.setOu_date_from(Utils.getDate(record.getOu_date_from()));
                    } else {
                        userOrg.setOu_date_from(Utils.getDate(new java.util.Date()));
                    }
                    sprOrgUsersDBService.saveRecord(conn, userOrg);
                }
            } else {
                if (record.getOu_id() != null && record.getOu_id().length() != 0) {
                    this.checkIsFormActionAssigned(conn, SprUserOrgsList.ACTION_DELETE);
                    sprOrgUsersDBService.deleteRecord(conn, Utils.getDouble(record.getOu_id()));
                }
            }

        }
    }

    public void setUserOrgDate(Connection conn, OrgUserRequest record) throws Exception {
        this.checkIsFormActionAssigned(conn, SprUserOrgsList.ACTION_UPDATE);
        SprOrgUsersDAO userOrg = sprOrgUsersDBService.loadRecord(conn, Utils.getDouble(record.getOu_id()));
        userOrg.setOu_position(record.getOu_position());
        userOrg.setOu_date_to(Utils.getDate(record.getOu_date_to()));
        userOrg.setOu_date_from(Utils.getDate(record.getOu_date_from()));
        sprOrgUsersDBService.saveRecord(conn, userOrg);
    }

}
