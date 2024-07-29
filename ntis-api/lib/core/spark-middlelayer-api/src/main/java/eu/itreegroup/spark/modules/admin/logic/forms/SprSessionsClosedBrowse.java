package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprSessionsClosedBrowseSecurityManager;

@Component
public class SprSessionsClosedBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprSessionsClosedBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_USR_SES_CLSD_LST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark user sessions closed", "Spark user sessions closed");
        addFormActions(FormBase.ACTION_READ);
    }

    public String getSessionList(Connection conn, SelectRequestParams params, Double userId) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprSessionsClosedBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT SEC_ID, "//
                + "SEC_USERNAME, "//
                + "U.USR_PERSON_NAME, "//
                + "U.USR_PERSON_SURNAME, "//
                + "TO_CHAR(SEC_LOGIN_TIME,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') AS SEC_LOGIN_TIME,"//
                + "TO_CHAR(SEC_LOGOUT_TIME,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') AS SEC_LOGOUT_TIME,"//
                + "TO_CHAR(SEC_LAST_ACTION_TIME,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB)
                + "') AS SEC_LAST_ACTION_TIME,"//
                + "SEC_LOGOUT_CAUSE, "//
                + "O.ORG_NAME "//
                + "FROM SPR_USER_SESSIONS_CLOSED S "//
                + "JOIN SPR_USERS U ON S.SEC_USR_ID = U.USR_ID "//
                + "LEFT JOIN SPR_ORGANIZATIONS O ON U.USR_ORG_ID = O.ORG_ID");

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("SEC_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ses_id"));
        stmt.addParam4WherePart("SEC_USERNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("user_name"));
        stmt.addParam4WherePart("USR_PERSON_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("person_name"));
        stmt.addParam4WherePart("USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("person_surname"));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));

        stmt.addParam4WherePart("SEC_LOGIN_TIME", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("login_time"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
        stmt.addParam4WherePart("SEC_LOGOUT_TIME", StatementAndParams.PARAM_DATE_TIME, advancedParamList.get("logout_time"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("SEC_ID", "SEC_USERNAME", "USR_PERSON_NAME", "USR_PERSON_SURNAME",
                        "TO_CHAR(SEC_LOGIN_TIME,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')",
                        "TO_CHAR(SEC_LOGOUT_TIME,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')", "ORG_NAME"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.addParam4WherePart("SEC_LOGOUT_CAUSE", StatementAndParams.PARAM_STRING, advancedParamList.get("logout_cause"));

        SprSessionsClosedBrowseSecurityManager sqm = new SprSessionsClosedBrowseSecurityManager();
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
