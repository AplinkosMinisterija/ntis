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
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;

@Component
public class SprOrgUsersList extends FormBase {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprOrgUsersList.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprOrgUserRolesDBService sprOrgUserRolesDBService;

    @Override
    public String getFormName() {
        return "SPR_ORG_USERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark organization users list", "Spark organization users list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        this.checkIsFormActionAssigned(conn, SprOrgUsersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("WITH ORG_USERS AS (SELECT OU_ID, "//
                + "OU_POSITION, "//
                + "OU_DATE_FROM, "//
                + "OU_DATE_TO, "//
                + "OU_USR_ID, "//
                + "OU_ORG_ID " //
                + "FROM SPR_ORG_USERS "//
                + "WHERE OU_ORG_ID = ?)" //
                + "SELECT OU_ID, " //
                + "OU_ORG_ID, " //
                + "CASE WHEN RFT.RFT_DISPLAY_CODE IS NOT NULL THEN RFT.RFT_DISPLAY_CODE ELSE RC.RFC_MEANING END AS OU_POSITION," //
                + "OU_POSITION AS OU_POSITION_CODE," //
                + "TO_CHAR(OU_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as OU_DATE_FROM, "//
                + "TO_CHAR(OU_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as OU_DATE_TO, "//
                + "SU.USR_ID, " //
                + "SU.USR_USERNAME, " //
                + "SU.USR_EMAIL, " //
                + "SU.USR_PERSON_NAME, " //
                + "SU.USR_PERSON_SURNAME, " //
                + "CASE WHEN OU_USR_ID IS NULL THEN NULL ELSE 'Y' END BELONGS " //
                + "FROM SPR_USERS SU " //
                + "LEFT JOIN ORG_USERS ON (SU.USR_ID = OU_USR_ID) " //
                + "LEFT JOIN SPR_REF_CODES RC ON RC.RFC_CODE = OU_POSITION AND RC.RFC_DOMAIN = 'SPR_PERSON_POSITION'" //
                + "LEFT JOIN SPR_REF_TRANSLATIONS RFT ON RC.RFC_ID = RFT.RFT_RFC_ID AND RFT.RFT_LANG = ? ");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(Utils.getDouble(paramList.get("p_org_id")));
        stmt.addSelectParam(language);
        stmt.addParam4WherePart("USR_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("usr_id"));
        stmt.addParam4WherePart("USR_USERNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_username"));
        stmt.addParam4WherePart("USR_EMAIL", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_email"));
        stmt.addParam4WherePart("USR_PERSON_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_name"));
        stmt.addParam4WherePart("USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_surname"));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OU_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("ou_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("OU_DATE_TO"), StatementAndParams.PARAM_DATE, advancedParamList.get("ou_date_to"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("OU_POSITION = ? ", paramList.get("p_ou_position"));
        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("USR_ID", "USR_USERNAME", "USR_EMAIL", "USR_PERSON_NAME", "USR_PERSON_SURNAME",
                        "TO_CHAR(OU_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')",
                        "TO_CHAR(OU_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        if (params.getPagingParams().getOrder_clause() == null) {
            String orderParamsStr = " BELONGS ";
            params.getPagingParams().setOrder_clause(orderParamsStr);
        }
        SprOrgUsersListSecurityManager sqm = new SprOrgUsersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { SprOrgUsersList.ACTION_READ, SprOrgUsersList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void updateOrgUsers(Connection conn, ArrayList<OrgUserRequest> records) throws Exception {
        for (OrgUserRequest record : records) {
            if (record.getIsSelected()) {
                if (record.getOu_id() == null || record.getOu_id().length() == 0) {
                    Double orgId = Utils.getDouble(record.getOu_org_id());
                    Double usrId = Utils.getDouble(record.getUsr_id());
                    this.checkIsFormActionAssigned(conn, SprOrgUsersList.ACTION_CREATE);
                    SprOrgUsersDAO orgUser = sprOrgUsersDBService.newRecord();
                    orgUser.setOu_org_id(orgId);
                    orgUser.setOu_usr_id(usrId);
                    orgUser.setOu_position(record.getOu_position());
                    if (record.getOu_date_from() != null) {
                        orgUser.setOu_date_from(Utils.getDate(record.getOu_date_from()));
                    } else {
                        orgUser.setOu_date_from(Utils.getDate(new java.util.Date()));
                    }
                    orgUser.setOu_date_to(record.getOu_date_to());
                    sprOrgUsersDBService.saveRecord(conn, orgUser);
                    sprOrgUserRolesDBService.refreshUserOrgProfile(conn, orgId, usrId);
                }
            } else {
                if (record.getOu_id() != null && record.getOu_id().length() != 0) {
                    this.checkIsFormActionAssigned(conn, SprOrgUsersList.ACTION_DELETE);
                    sprOrgUsersDBService.deleteRecord(conn, Utils.getDouble(record.getOu_id()));
                }
            }

        }
    }

    public void setOrgUserDate(Connection conn, OrgUserRequest record) throws Exception {
        this.checkIsFormActionAssigned(conn, SprOrgUsersList.ACTION_UPDATE);
        SprOrgUsersDAO orgUser = sprOrgUsersDBService.loadRecord(conn, Utils.getDouble(record.getOu_id()));
        orgUser.setOu_position(record.getOu_position());
        orgUser.setOu_date_to(Utils.getDate(record.getOu_date_to()));
        orgUser.setOu_date_from(Utils.getDate(record.getOu_date_from()));
        sprOrgUsersDBService.saveRecord(conn, orgUser);
    }

}
