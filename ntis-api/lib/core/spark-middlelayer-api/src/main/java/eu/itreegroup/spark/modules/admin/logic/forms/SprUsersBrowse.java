package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprUsersBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@Component
public class SprUsersBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprUsersBrowse.class);

    public static final String ASSIGN_ROLE_ACTION = "ASSIGN_ROLE";

    public static final String ASSIGN_ROLE_ACTION_NAME = "Assign role to the selected user";

    public static final String ASSIGN_ORG_ACTION = "ASSIGN_ORG";

    public static final String ASSIGN_ORG_ACTION_NAME = "Assign org role to the selected user";

    public static final String INFORM_NEW_USER = "INFORM_NEW_USER";

    public static final String INFORM_NEW_USER_NAME = "Inform new person aboud created user in system";

    public static final String RESET_PASSWORD = "RESET_USER_PASSWORD";

    public static final String RESET_PASSWORD_NAME = "Request user password";

    public static final String DISABLE_USER = "DISABLE_USER";

    public static final String DISABLE_USER_NAME = "Possibility disable/enable user in system";

    public static final String NO_LIMIT_ON_ORG_LEVEL = "NO_LIMIT_ON_ORG_LEVEL";

    public static final String NO_LIMIT_ON_ORG_LEVEL_NAME = "No limitation on user organization level";

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprUsersDBService sprUsersDbService;

    @Autowired
    SprProcessRequest sprProcessRequest;

    /**
     * Method will return Angular form name. Same name should be defined in DB as
     * well (in case if enabled data synchronization with db it name will be used
     * for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_USERS_BROWSE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark users browse", "Spark user users browse");
        addFormActionCRUD();
        addFormAction(DISABLE_USER, DISABLE_USER_NAME, DISABLE_USER_NAME);
        addFormAction(ASSIGN_ROLE_ACTION, ASSIGN_ROLE_ACTION_NAME, ASSIGN_ROLE_ACTION_NAME);
        addFormAction(INFORM_NEW_USER, INFORM_NEW_USER_NAME, INFORM_NEW_USER_NAME);
        addFormAction(RESET_PASSWORD, RESET_PASSWORD_NAME, RESET_PASSWORD_NAME);
        addFormAction(ASSIGN_ORG_ACTION, ASSIGN_ORG_ACTION_NAME, ASSIGN_ORG_ACTION_NAME);
        addFormAction(NO_LIMIT_ON_ORG_LEVEL, NO_LIMIT_ON_ORG_LEVEL_NAME, NO_LIMIT_ON_ORG_LEVEL_NAME);

    }

    /**
     * Function will perform users search id DB and provide result as json string.
     * 
     * @param conn     - connection to the db
     * @param params   - search parameters
     * @param language - language in which should be returned result
     * @return JSON string that represent list of users.
     * @throws Exception
     */
    public String getUsersList(Connection conn, SelectRequestParams params, String language, Double userId, Double orgId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprUsersBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        String statementSQL = " SELECT U.USR_ID, "//
                + "U.OU_ID, "//
                + "U.USR_USERNAME, "//
                + "U.USR_EMAIL, "//
                + "U.USR_PERSON_NAME, "//
                + "U.USR_PERSON_SURNAME, "//
                + "U.ORG_NAME  ORG_NAME, "//
                + "to_char(U.USR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as USR_DATE_FROM, "//
                + "to_char(U.USR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') as USR_DATE_TO,"//
                + "USR_DISPLAY_TYPE USR_TYPE, "//
                + "to_char(U.USR_LOCK_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') USR_LOCK_DATE, "//
                + "NEW_USER, " //
                + "USER_DISABLED, "//
                + "ORG_LIST " //
                + (isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL) ? "FROM SPR_USERS_VW U  " : " FROM SPR_ORG_USERS_VW U  ") //
                + " where  rft_lang=? ";
        stmt.addSelectParam(language);
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            statementSQL = statementSQL + " and u.org_id = ? ";
            stmt.addSelectParam(orgId);
        }
        stmt.setStatement(statementSQL);
        stmt.setWhereExists(true);
        if (!isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {

        }
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);

        stmt.addParam4WherePart("USR_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("usr_id"));
        stmt.addParam4WherePart("RFT_LANG", StatementAndParams.PARAM_STRING, advancedParamList.get("lang"));
        stmt.addParam4WherePart("USR_USERNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_username"));
        stmt.addParam4WherePart("USR_EMAIL", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_email"));
        stmt.addParam4WherePart("USR_PERSON_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_name"));
        stmt.addParam4WherePart("USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_person_surname"));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("USR_TYPE", StatementAndParams.PARAM_STRING, advancedParamList.get("usr_type"));

        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("USR_DATE_FROM"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("usr_date_from"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(dbstatementManager.getTruncatedColumnCommand("USR_DATE_TO"), StatementAndParams.PARAM_DATE,
                advancedParamList.get("usr_date_to"), dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("U.USR_ID", "U.USR_USERNAME", "U.USR_EMAIL", "U.USR_PERSON_NAME", "U.USR_PERSON_SURNAME",
                        "U.USR_PERSON_NAME ||' '|| U.USR_PERSON_SURNAME", "U.ORG_NAME",
                        "to_char(U.USR_DATE_FROM, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "to_char(U.USR_DATE_TO, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        HashMap<String, String> paramList = params.getParamList();
        stmt.addParam4WherePart("U.USR_TYPE = ? ", paramList.get("p_usr_type"));
        SprUsersBrowseSecurityManager sqm = new SprUsersBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = null;
        if (isFormActionAssigned(conn, NO_LIMIT_ON_ORG_LEVEL)) {
            recordActionMenu = new String[] { SprUsersBrowse.ACTION_UPDATE, SprUsersBrowse.ACTION_READ, SprUsersBrowse.ACTION_COPY,
                    SprUsersBrowse.ACTION_DELETE, SprUsersBrowse.ASSIGN_ROLE_ACTION, SprUsersBrowse.ASSIGN_ORG_ACTION, SprUsersBrowse.INFORM_NEW_USER,
                    SprUsersBrowse.RESET_PASSWORD };
        } else {
            recordActionMenu = new String[] { SprUsersBrowse.ACTION_UPDATE, SprUsersBrowse.ACTION_READ, SprUsersBrowse.ACTION_COPY,
                    SprUsersBrowse.ACTION_DELETE, SprUsersBrowse.ASSIGN_ROLE_ACTION, SprUsersBrowse.INFORM_NEW_USER, SprUsersBrowse.RESET_PASSWORD };
        }
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    /**
     * Function will change user lock status. Ins case if user enabled function will
     * lock this user (set current date to the user_lock_date) In case if user
     * locked function will unlock this user and set field usr_lock_date to the
     * null.
     * 
     * @param conn             - connection to the db
     * @param recordIdentifier - user identifier
     * @throws SparkBusinessException
     * @throws Exception
     */
    public void changeStatus(Connection conn, RecordIdentifier recordIdentifier) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, DISABLE_USER);
        Double userId = Double.valueOf(recordIdentifier.getId());
        SprUsersDAO sprUsersDAO = sprUsersDbService.loadRecord(conn, userId);
        if (sprUsersDAO.getUsr_lock_date() == null) {
            sprUsersDAO.setUsr_lock_date(new Date());
        } else {
            sprUsersDAO.setUsr_lock_date(null);
            sprUsersDAO.setUsr_wrong_login_attempts(0d);
        }
        sprUsersDbService.saveRecord(conn, sprUsersDAO);
    }

    public void informSparkNewUser(Connection conn, RecordIdentifier recordIdentifier, String langCode) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, INFORM_NEW_USER);
        Double userId = Double.valueOf(recordIdentifier.getId());
        SprUsersDAO sprUsersDAO = sprUsersDbService.loadRecord(conn, userId);
        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("userName", sprUsersDAO.getUsr_username());

        if (sprUsersDAO.getUsr_email() != null) {
            sprProcessRequest.createNewUserInformRequest(conn, sprUsersDAO.getUsr_id(), sprUsersDAO.getUsr_email(), Languages.getLanguageByCode(langCode),
                    context);
        } else {
            throw new SparkBusinessException(new S2Message("pages.sprUsersBrowse.emaiIsEmpty", SparkMessageType.ERROR, "User do not have e-mail!"));
        }
    }

    public void informSparkUserPasswordReset(Connection conn, RecordIdentifier recordIdentifier, String langCode) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, RESET_PASSWORD);
        Double userId = Double.valueOf(recordIdentifier.getId());
        SprUsersDAO sprUsersDAO = sprUsersDbService.loadRecord(conn, userId);
        // handle context
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("userName", sprUsersDAO.getUsr_username());

        if (sprUsersDAO.getUsr_email() != null) {
            sprProcessRequest.createPasswordChangeRequest(conn, sprUsersDAO.getUsr_id(), sprUsersDAO.getUsr_email(), Languages.getLanguageByCode(langCode),
                    context);
        } else {
            throw new SparkBusinessException(new S2Message("pages.sprUsersBrowse.emaiIsEmpty", SparkMessageType.ERROR, "User do not have e-mail!"));
        }
    }
}
