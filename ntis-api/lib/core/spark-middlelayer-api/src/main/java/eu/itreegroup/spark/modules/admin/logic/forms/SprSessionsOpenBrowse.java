package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprSessionsOpenBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprSessionOpenDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

@Component
public class SprSessionsOpenBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprSessionsOpenBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprSessionOpenDBService sprSessionOpenDBService;

    @Autowired
    SprAuthorization<SprBackendUserSession> sprAuthorization;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_USR_SES_OPN_LST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark user sessions open", "Spark user sessions open");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_DELETE);
    }

    public String getSessionList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprSessionsOpenBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT SES_ID, "//
                + "SES_USERNAME, "//
                + "U.USR_PERSON_NAME, "//
                + "U.USR_PERSON_SURNAME, "//
                + "TO_CHAR(SES_LOGIN_TIME,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') AS SES_LOGIN_TIME,"//
                + "TO_CHAR(SES_LAST_ACTION_TIME,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB)
                + "') AS SES_LAST_ACTION_TIME,"//
                + "O.ORG_NAME, "//
                + "SES_KEY "//
                + "FROM SPR_USER_SESSIONS_OPEN S "//
                + "JOIN SPR_USERS U ON S.SES_USR_ID = U.USR_ID "//
                + "LEFT JOIN SPR_ORGANIZATIONS O ON U.USR_ORG_ID = O.ORG_ID");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("SES_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ses_id"));
        stmt.addParam4WherePart("SES_USERNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("ses_username"));
        stmt.addParam4WherePart("USR_PERSON_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("person_name"));
        stmt.addParam4WherePart("USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("person_surname"));
        stmt.addParam4WherePart("SES_LOGIN_TIME", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("login_time"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("SES_ID", "SES_USERNAME", "USR_PERSON_NAME", "USR_PERSON_SURNAME",
                        "TO_CHAR(SES_LOGIN_TIME,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')",
                        "TO_CHAR(SES_LAST_ACTION_TIME,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')", "ORG_NAME "),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        SprSessionsOpenBrowseSecurityManager sqm = new SprSessionsOpenBrowseSecurityManager();
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void killSessions(Connection conn, List<String> sesList) throws Exception {
        checkIsFormActionAssigned(conn, SprSessionsOpenBrowse.ACTION_DELETE);
        for (var s : sesList) {
            sprAuthorization.logout(conn, s, "A");
        }
    }

    // @TODO add filtering by params ?
    public void killAllSessions(Connection conn) throws Exception {
        checkIsFormActionAssigned(conn, SprSessionsOpenBrowse.ACTION_READ);
        checkIsFormActionAssigned(conn, SprSessionsOpenBrowse.ACTION_DELETE);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT SES_KEY FROM SPR_USER_SESSIONS_OPEN ");
        List<HashMap<String, String>> sessions = queryController.selectQueryAsDataArrayList(conn, stmt);
        for (var s : sessions) {
            sprAuthorization.logout(conn, s.get("ses_key"), "A");
        }
    }

}
