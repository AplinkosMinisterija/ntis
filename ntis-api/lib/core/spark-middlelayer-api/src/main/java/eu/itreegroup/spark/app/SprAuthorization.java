package eu.itreegroup.spark.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.model.MenuStructure;
import eu.itreegroup.spark.app.model.RequestToken;
import eu.itreegroup.spark.app.model.RoleFormsActions;
import eu.itreegroup.spark.app.model.UserRole;
import eu.itreegroup.spark.app.password.PasswordManagerService;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.app.tools.RoleManager;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.SAPProcessRequestType;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprApiKeysDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprSessionClosedDAO;
import eu.itreegroup.spark.modules.admin.dao.SprSessionOpenDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprApiKeysDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprSessionClosedDBService;
import eu.itreegroup.spark.modules.admin.service.SprSessionOpenDBService;
import eu.itreegroup.spark.modules.admin.service.SprUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@Component
public class SprAuthorization<BUS extends SprBackendUserSession> {

    public static final String SINGLE_ROLE_MODE = "SINGLE_ROLE_MODE";

    public static final String MERGED_ROLE_MODE = "MERGED_ROLE_MODE";

    public static final int MERGED_ROLE_ID = -1;

    public static final String MERGED_ROLE_CODE = "MERGED";

    public static final String MERGED_ROLE_NAME = "Merged user role";

    public static final int USER_NAME_PASSWORD_AUTHENTICATION = 1;

    public static final int EXTERNAL_AUTHENTICATION = 0;

    public static final int API_AUTHENTICATION = 3;

    public static final String PASSOWORD_EXPIRED = "common.error.passwordExpired";

    public static final String SESSION_EXPIRED = "common.error.sessionExpired";

    public static final int ANONYMOUS_USER_ID = -42;

    public static final int PWD_REQ_CODE_DEFAULT_LENGTH = 50;

    public static final String APP_TIMEOUT_IN_MINUTES = "APP_TIMEOUT_IN_MINUTES";

    public static final String PUBLIC = "PUBLIC";

    private static final Logger log = LoggerFactory.getLogger(SprAuthorization.class);

    @Autowired
    protected S2RestRequestContext<BUS> requestContext;

    @Autowired
    SprUsersDBService sprUsersDBservice;

    @Autowired
    SprApiKeysDBService sprApiKeysDBService;

    @Autowired
    SprSessionOpenDBService sprSessionOpenDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    SprSessionOpenDBService sprOpenSessionService;

    @Autowired
    SprSessionClosedDBService sprCloseSessionService;

    @Autowired
    SprRolesDBService sprRolesDBService;

    @Autowired
    SprUserRolesDBService sprUserRolesDBService;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    private SprAuthorization<BUS> thisObject;

    @Autowired
    PasswordManagerService passwordManager;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    @Value("${app.user.role.mode:SINGLE_ROLE_MODE}")
    private String roleMode;

    @Value("${app.timeout.in.minutes:#{null}}")
    private String defaultTimeoutInMinutes;

    @Value("${multipleUserSessionsAllowed:#{true}}")
    private Boolean multipleUserSessionsAllowed;

    @Value("${app.default.language}")
    private String defaultLanguage;

    @Value("${app.host:#{null}}")
    private String appHost;

    private Double publicRoleID = null;

    private String publicRoleJson = null;

    public String getRandomKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 25;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        return generatedString;
    }

    private void manageOldActiveSessions(Connection conn, BUS sessionObj) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select ses_id from SPR_USER_SESSIONS_OPEN ");
        stmt.addParam4WherePart(" ses_username = ? ", sessionObj.getUsername());
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, String> row = data.get(i);
            SprSessionOpenDAO opensessionDAO = sprOpenSessionService.loadRecord(conn, Double.parseDouble(row.get("ses_id")));
            SprSessionClosedDAO closedSessionDAO = new SprSessionClosedDAO();
            closedSessionDAO.initFromOpenSession(opensessionDAO);
            closedSessionDAO.setSec_logout_cause("O");
            closedSessionDAO.setSec_logout_time(new Date());
            sprCloseSessionService.insertRecord(conn, closedSessionDAO);
            sprOpenSessionService.deleteRecord(conn, opensessionDAO);
            sprOpenSessionService.removeCachedSession(opensessionDAO.getSes_key());
        }
    }

    protected BUS createDBSession(Connection conn, BUS sessionObj) throws Exception {
        SprSessionOpenDAO session = sprOpenSessionService.newRecord();
        session.setSes_key(getRandomKey());
        session.setSes_username(sessionObj.getUsername());
        session.setSes_usr_id(sessionObj.getUsr_id().doubleValue());
        Date currentDate = new Date();
        session.setSes_login_time(currentDate);
        session.setSes_last_action_time(currentDate);
        session.setSes_ip(sessionObj.getIp());
        session.setSes_browser(sessionObj.getBrowser());
        session.setSes_language(sessionObj.getSes_language());
        session.setSes_terms_accepted(sessionObj.getSes_terms_accepted());
        session.setSes_terms_accepted_date(sessionObj.getSes_terms_accepted_date());
        if (isSingleRoleMode()) {
            session.setSes_rol_id(sessionObj.getSes_rol_id());
            session.setSes_rol_type(sessionObj.getSes_rol_type());
        }
        session.setSes_per_id(sessionObj.getSes_per_id());
        session.setSes_org_id(sessionObj.getSes_org_id());
        session.setSes_usr_type(sessionObj.getSes_usr_type());
        session.setSes_usr_id(sessionObj.getSes_usr_id());
        session.setSes_person_name(sessionObj.getSes_person_name());
        session.setSes_person_surname(sessionObj.getSes_person_surname());
        session.setSes_profile_token(sessionObj.getSes_profile_token());
        session.setSes_password_reset_req_date(sessionObj.getSes_password_reset_req_date());
        if (!multipleUserSessionsAllowed) {
            manageOldActiveSessions(conn, sessionObj);
        }
        session = sprOpenSessionService.insertRecord(conn, session);
        sessionObj.setSesKey(session.getSes_key());
        sessionObj.setSesId(session.getSes_id());
        return sessionObj;
    }

    private String getUserProfile(Connection conn, Double orgId, Double userId) throws Exception {
        String answer = null;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("profileBuilder"));
        stmt.addSelectParam(userId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(userId);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        if (!data.isEmpty()) {
            answer = data.get(0).get("profile");
        }
        return answer;
    }

    private BUS setUserDataToSession(Connection conn, BUS userSession, SprUsersDAO sprUserDAO, SprRolesDAO sprRoleDAO, int loginMode) throws Exception {
        userSession.setUsername(sprUserDAO.getUsr_username());
        userSession.setUsr_id(sprUserDAO.getUsr_id());
        userSession.setPer_id(sprUserDAO.getUsr_per_id());
        if (userSession.getPer_id() == null) {
            userSession.setPerson_name(sprUserDAO.getUsr_person_name());
            userSession.setPerson_surname(sprUserDAO.getUsr_person_surname());
        }
        if (isSingleRoleMode()) {
            if (sprRoleDAO != null) {
                userSession.setRole(sprRoleDAO.getRol_code());
                userSession.setRole_name(sprRoleDAO.getRol_name());
                userSession.setRole_type(sprRoleDAO.getRol_type());
                userSession.setSes_rol_id(sprRoleDAO.getRol_id());
            }
        } else {
            userSession.setSes_rol_id(Double.valueOf(MERGED_ROLE_ID));
            userSession.setRole(MERGED_ROLE_CODE);
            userSession.setRole_name(MERGED_ROLE_NAME);
        }
        userSession.setSes_per_id(sprUserDAO.getUsr_per_id());
        userSession.setSes_terms_accepted(sprUserDAO.getUsr_terms_accepted());
        userSession.setSes_terms_accepted_date(sprUserDAO.getUsr_terms_accepted_date());
        userSession.setUsr_terms_accepted(sprUserDAO.getUsr_terms_accepted());
        userSession.setUsr_terms_accepted_date(sprUserDAO.getUsr_terms_accepted_date());
        userSession.setUsr_valid_from(sprUserDAO.getUsr_date_from());
        userSession.setUsr_valid_to(sprUserDAO.getUsr_date_to());
        userSession.setSes_person_name(sprUserDAO.getUsr_person_name());
        userSession.setSes_person_surname(sprUserDAO.getUsr_person_surname());
        userSession.setSes_usr_type(sprUserDAO.getUsr_type());
        userSession.setSes_org_id(sprUserDAO.getUsr_org_id());
        if (!isSingleRoleMode()) {
            userSession.setSes_profile_token(getUserProfile(conn, sprUserDAO.getUsr_org_id(), sprUserDAO.getUsr_id()));
        }
        if (sprUserDAO.getUsr_per_id() != null) {
            SprPersonsDAO sprPersonsDAO = sprPersonsDBService.loadRecord(conn, sprUserDAO.getUsr_per_id());
            userSession.setPerson_name(sprPersonsDAO.getPer_name());
            userSession.setPerson_surname(sprPersonsDAO.getPer_surname());
        } else {
            userSession.setPerson_name(sprUserDAO.getUsr_person_name());
            userSession.setPerson_surname(sprUserDAO.getUsr_person_surname());
        }
        userSession.setSes_password_reset_req_date(sprUserDAO.getUsr_password_reset_req_date());
        Date currentDate = new Date();
        if (loginMode == USER_NAME_PASSWORD_AUTHENTICATION) {
            if (sprUserDAO.getUsr_password_next_change_date() != null) {
                if (currentDate.after(sprUserDAO.getUsr_password_next_change_date())) {
                    if (userSession.getSes_password_reset_req_date() == null
                            || userSession.getSes_password_reset_req_date().after(sprUserDAO.getUsr_password_next_change_date())) {
                        userSession.setSes_password_reset_req_date(sprUserDAO.getUsr_password_next_change_date());
                    }
                }
            }
            if (userSession.getSes_password_reset_req_date() != null) {
                if (currentDate.after(userSession.getSes_password_reset_req_date())) {
                    userSession.setUserPasswordChangeToken(PASSOWORD_EXPIRED);
                }
            }
        }
        if (sprUserDAO.getUsr_language() != null) {
            userSession.setSes_language(sprUserDAO.getUsr_language());
        } else {
            sprUserDAO.setUsr_language(defaultLanguage);
            userSession.setSes_language(defaultLanguage);
        }
        return userSession;
    }

    private BUS setUserDataToSession(Connection conn, BUS userSession, SprUsersDAO sprUserDAO, int loginMode) throws Exception {
        SprRolesDAO sprRoleDAO = null;
        if (isSingleRoleMode() && sprUserDAO.getUsr_rol_id() != null) {
            sprRoleDAO = sprRolesDBService.loadRecord(conn, sprUserDAO.getUsr_rol_id());
        }
        return setUserDataToSession(conn, userSession, sprUserDAO, sprRoleDAO, loginMode);

    }

    private BUS loginUser(Connection conn, SprUsersDAO usersDAO, BUS userSession, int loginMode) throws SparkBusinessException, Exception {
        if (usersDAO.getUsr_lock_date() != null && usersDAO.getUsr_lock_date().before(new Date(System.currentTimeMillis()))) {
            throw new SparkBusinessException(new S2Message("common.message.accountIsBlocked", SparkMessageType.ERROR,
                    "User is blocked by administrator. Contact system administrator!"));
        } else {
            if ((usersDAO.getUsr_date_from() != null && !usersDAO.getUsr_date_from().before(new Date(System.currentTimeMillis())))
                    || (usersDAO.getUsr_date_to() != null && !usersDAO.getUsr_date_to().after(new Date(System.currentTimeMillis())))) {
                throw new SparkBusinessException(new S2Message("pages.adminLogin.userNotActivated", SparkMessageType.ERROR, "User is not activated."));
            } else {
                userSession = setUserDataToSession(conn, userSession, usersDAO, loginMode);
                requestContext.setUserSession(userSession);
                userSession = createDBSession(conn, userSession);
                if (userSession.getSes_org_id() != null) {
                    SprOrganizationsDAO organization = sprOrganizationsDBService.loadRecord(conn, userSession.getSes_org_id());
                    userSession.setOrgName(organization.getOrg_name());
                }
                usersDAO.setUsr_wrong_login_attempts((double) 0);
                sprUsersDBservice.saveRecord(conn, usersDAO);
            }
        }
        return userSession;
    }

    private void handleWrongLoginAttempt(Connection conn, SprUsersDAO usersDAO) throws SparkBusinessException, Exception {
        if (usersDAO != null) {
            usersDAO.setUsr_wrong_login_attempts(usersDAO.getUsr_wrong_login_attempts() == null ? 1 : usersDAO.getUsr_wrong_login_attempts() + 1);
            String param = dbPropertyManager.getPropertyByName("MAX_WRONG_PASSWORD_ATTEMPT", "5");
            Double paramValue = Utils.getDouble(param);
            if (usersDAO.getUsr_lock_date() != null && usersDAO.getUsr_lock_date().after(new Date())) {
                throw new SparkBusinessException(new S2Message("common.message.accountIsBlocked", SparkMessageType.ERROR,
                        "User is blocked by administrator. Contact system administrator!"));
            } else if (usersDAO.getUsr_wrong_login_attempts() != null && usersDAO.getUsr_wrong_login_attempts() >= paramValue) {
                usersDAO.setUsr_lock_date(new Date());
            }
            sprUsersDBservice.saveRecord(conn, usersDAO);
            conn.commit();
        }
        throw new SparkBusinessException(
                new S2Message("pwd.wrongCredentials", SparkMessageType.ERROR, "Sorry, your credentials are incorrect. Please try again."));
    }

    /**
     * Function will return user session form request context;
     * @return user session.
     */
    public BUS getUserSessionFromConetext() {
        return requestContext.getUserSession();
    }

    public SprUsersDAO attemptToLogin(Connection conn, String userName, String userPassword, Map<String, Object> authExtData) throws Exception {
        SprUsersDAO sprUsersDAO = null;
        try {
            sprUsersDAO = sprUsersDBservice.loadRecordByIdentifier(conn, userName, authExtData);
        } catch (Exception ex) {
            ex.printStackTrace();
            handleWrongLoginAttempt(conn, sprUsersDAO);
        }
        // TODO SAP-223 implement logic for password Algorithm identification.
        if (sprUsersDAO == null || sprUsersDAO.getUsr_password_hash() == null
                || !sprUsersDAO.getUsr_password_hash().equals(passwordManager.getPasswordHash(null, userPassword, null))) {
            handleWrongLoginAttempt(conn, sprUsersDAO);
        }
        return sprUsersDAO;
    }

    /**
     * Function will create user session for user that try perform authentication process. 
     * @param conn - connection to the db
     * @param userSession - user session that used for current transaction 
     * @param userName - user name that will be used for user identification
     * @param userPassword - user password provided by user
     * @param authExtData - additional data provided from authentication process
     * @return - user session that will contain information about authentication user
     * @throws Exception will be raised in case if authentication process was not success.
     */
    public BUS createUserSession(Connection conn, BUS userSession, String userName, String userPassword, Map<String, Object> authExtData) throws Exception {
        SprUsersDAO sprUserDAO = this.attemptToLogin(conn, userName, userPassword, authExtData);
        return loginUser(conn, sprUserDAO, userSession, USER_NAME_PASSWORD_AUTHENTICATION);
    }

    public BUS createUserSession(Connection conn, BUS userSession, SprUsersDAO usersDAO, int loginMode) throws Exception {
        SprUsersDAO loadedUsersDAO = null;
        try {
            loadedUsersDAO = sprUsersDBservice.loadRecord(conn, usersDAO.getRecordIdentifier());
        } catch (Exception ex) {
            ex.printStackTrace();
            handleWrongLoginAttempt(conn, loadedUsersDAO);
        }
        if (loadedUsersDAO.getUsr_username().equals(usersDAO.getUsr_username())
                && (loadedUsersDAO.getUsr_password_hash() == null || loadedUsersDAO.getUsr_password_hash().equals(usersDAO.getUsr_password_hash()))) {
            userSession = loginUser(conn, loadedUsersDAO, userSession, loginMode);
        } else {
            handleWrongLoginAttempt(conn, loadedUsersDAO);
        }
        return userSession;
    }

    public void logout(Connection conn, String sessionKey, String operation) throws Exception {
        SprSessionOpenDAO opensessionDAO = sprOpenSessionService.loadRecordByKey(conn, sessionKey);
        SprSessionClosedDAO closedSessionDAO = new SprSessionClosedDAO();
        closedSessionDAO.initFromOpenSession(opensessionDAO);
        closedSessionDAO.setSec_logout_cause(operation);
        closedSessionDAO.setSec_logout_time(new Date());
        sprCloseSessionService.insertRecord(conn, closedSessionDAO);
        sprOpenSessionService.deleteRecord(conn, opensessionDAO);
        sprOpenSessionService.removeCachedSession(sessionKey);
    }

    public void initDBUserSession(Connection conn, String sessionKey) {

    }

    public void clearDBUserSessionState(Connection conn) {

    }

    public BUS initSessionFromDB(Connection conn, BUS sessionObj) throws Exception {
        SprSessionOpenDAO opensessionDAO = null;
        log.debug("===========================================================");
        log.debug("==============  initSessionFromDB for  key: " + sessionObj.getSesKey());
        log.debug("===========================================================");
        opensessionDAO = sprOpenSessionService.loadRecordByKey(conn, sessionObj.getSesKey());
        if (opensessionDAO.getSes_key() == null) {
            sprOpenSessionService.removeCachedSession(sessionObj.getSes_key());
            opensessionDAO = sprOpenSessionService.loadRecordByKey(conn, sessionObj.getSesKey());
        }

        String timeoutInMinutes = dbPropertyManager.getPropertyByName(APP_TIMEOUT_IN_MINUTES, defaultTimeoutInMinutes);
        if (timeoutInMinutes != null) {
            Date currentDate = new Date();
            long difference_In_Minutes = -1;
            if (opensessionDAO.getSes_last_action_time() != null) {
                long difference_In_Time = currentDate.getTime() - opensessionDAO.getSes_last_action_time().getTime();
                difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            }
            log.debug("date from: " + Utils.getStringFromDate(opensessionDAO.getSes_last_action_time(), "YYYY-MM-dd HH:mm:ss") + " difference_In_Minutes: "
                    + difference_In_Minutes);
            if (difference_In_Minutes < 0 || difference_In_Minutes > Long.valueOf(timeoutInMinutes)) {
                SprSessionClosedDAO closedSessionDAO = new SprSessionClosedDAO();
                closedSessionDAO.initFromOpenSession(opensessionDAO);
                closedSessionDAO.setSec_logout_cause("O");
                closedSessionDAO.setSec_logout_time(new Date());
                sprCloseSessionService.insertRecord(conn, closedSessionDAO);
                sprOpenSessionService.deleteRecord(conn, opensessionDAO);
                throw new SparkBusinessException(new S2Message("pages.adminLogin.userNotActivated", SparkMessageType.ERROR, "User is not activated."));
            } else {
                opensessionDAO.setSes_last_action_time(new Date());
                opensessionDAO = sprOpenSessionService.saveRecord(conn, opensessionDAO);
            }
        }

        log.debug("===========================================================");
        log.debug("==============  initSessionFromDB session info  ============");
        log.debug("====session.getSes_rol_id(): " + opensessionDAO.getSes_rol_id() + " ===");
        log.debug("====session.getSes_org_id(): " + opensessionDAO.getSes_org_id() + " ===");
        log.debug("====session.getUsername(): " + opensessionDAO.getSes_username() + " ===");
        log.debug("====session.getSes_key(): " + opensessionDAO.getSes_key() + " ===");
        log.debug("====session.getProfileToken(): " + opensessionDAO.getSes_profile_token() + " ===");
        log.debug("===========================================================");
        sessionObj.setUsr_id(opensessionDAO.getSes_usr_id());
        sessionObj.setPer_id(opensessionDAO.getSes_per_id());
        sessionObj.setSes_org_id(opensessionDAO.getSes_org_id());
        sessionObj.setSes_id(opensessionDAO.getSes_id());
        sessionObj.setSes_language(opensessionDAO.getSes_language());
        if (isSingleRoleMode()) {
            sessionObj.setSes_rol_id(opensessionDAO.getSes_rol_id());
            sessionObj.setSes_rol_type(opensessionDAO.getSes_rol_type());
        } else {
            sessionObj.setSes_rol_id(Double.valueOf(MERGED_ROLE_ID));
            sessionObj.setRole(MERGED_ROLE_CODE);
            sessionObj.setRole_name(MERGED_ROLE_NAME);
        }
        sessionObj.setSes_usr_type(opensessionDAO.getSes_usr_type());
        sessionObj.setSes_terms_accepted(opensessionDAO.getSes_terms_accepted());
        sessionObj.setSes_terms_accepted_date(opensessionDAO.getSes_terms_accepted_date());
        sessionObj.setSes_profile_token(opensessionDAO.getSes_profile_token());
        sessionObj.setSes_password_reset_req_date(opensessionDAO.getSes_password_reset_req_date());
        sessionObj.setSes_org_id(opensessionDAO.getSes_org_id());
        sessionObj.setPerson_name(opensessionDAO.getSes_person_name());
        sessionObj.setPerson_surname(opensessionDAO.getSes_person_surname());
        sessionObj.setSes_person_name(opensessionDAO.getSes_person_name());
        sessionObj.setSes_person_surname(opensessionDAO.getSes_person_surname());
        sessionObj.sessionIsLoaded();
        return sessionObj;
    }

    /**
     * This function check and initializes the API user session. 
     * The method checks if the user has an open session, and if not, it creates one.
     * @param conn - connection to the DB
     * @param sessionObj - user sesssion object
     * @return user sesssion object.
     * @throws Exception
     */
    public BUS initApiSessionFromDB(Connection conn, BUS sessionObj) throws Exception {
        SprSessionOpenDAO openSessionDAO = null;
        log.debug("===========================================================");
        log.debug("Initializing session from the database for apiId: {}", sessionObj.getApiId());
        log.debug("===========================================================");
        Date currentDate = new Date();
        SprApiKeysDAO apiKeysRec = sprApiKeysDBService.loadRecord(conn, sessionObj.getApiId());

        // Checking if token is valid
        if ((apiKeysRec.getApi_date_from() == null || currentDate.compareTo(apiKeysRec.getApi_date_from()) >= 0)
                && (apiKeysRec.getApi_date_to() == null || currentDate.compareTo(apiKeysRec.getApi_date_to()) <= 0)) {
            Double usrId = apiKeysRec.getApi_usr_id();
            Double orgId = null;

            if (apiKeysRec.getApi_ou_id() != null) {
                SprOrgUsersDAO orgUser = sprOrgUsersDBService.loadRecord(conn, apiKeysRec.getApi_ou_id());
                usrId = orgUser.getOu_usr_id();
                orgId = orgUser.getOu_org_id();
            }
            log.debug(" usrId: {} orgId:  {} ouID: {} ", usrId, orgId, apiKeysRec.getApi_ou_id());
            openSessionDAO = sprOpenSessionService.loadRecordByParams(conn, "  WHERE SES_USR_ID = ?  order by 1 desc", new SelectParamValue(usrId));
            if (openSessionDAO == null || openSessionDAO.getSes_id() == null) {
                SprUsersDAO usersDAO = sprUsersDBservice.loadRecord(conn, usrId);
                usersDAO.setUsr_org_id(orgId);
                sessionObj = loginUser(conn, usersDAO, sessionObj, API_AUTHENTICATION);
                sessionObj.setSes_org_id(orgId);
                sessionObj.setUsr_id(usrId);
            } else {
                sessionObj.setUsr_id(usrId);
                sessionObj.setPer_id(openSessionDAO.getSes_per_id());
                sessionObj.setSes_org_id(orgId);
                sessionObj.setSes_id(openSessionDAO.getSes_id());
                sessionObj.setSes_language(openSessionDAO.getSes_language());
                if (isSingleRoleMode()) {
                    sessionObj.setSes_rol_id(openSessionDAO.getSes_rol_id());
                    sessionObj.setSes_rol_type(openSessionDAO.getSes_rol_type());
                } else {
                    sessionObj.setSes_rol_id(Double.valueOf(MERGED_ROLE_ID));
                    sessionObj.setRole(MERGED_ROLE_CODE);
                    sessionObj.setRole_name(MERGED_ROLE_NAME);
                }
                sessionObj.setSes_usr_type(openSessionDAO.getSes_usr_type());
                sessionObj.setSes_terms_accepted(openSessionDAO.getSes_terms_accepted());
                sessionObj.setSes_terms_accepted_date(currentDate);
                sessionObj.setSes_profile_token(openSessionDAO.getSes_profile_token());
                sessionObj.setSes_password_reset_req_date(openSessionDAO.getSes_password_reset_req_date());
                sessionObj.setSes_org_id(openSessionDAO.getSes_org_id());
                sessionObj.setPerson_name(openSessionDAO.getSes_person_name());
                sessionObj.setPerson_surname(openSessionDAO.getSes_person_surname());
                sessionObj.setSes_person_name(openSessionDAO.getSes_person_name());
                sessionObj.setSes_person_surname(openSessionDAO.getSes_person_surname());
                sessionObj.sessionIsLoaded();
            }
            return sessionObj;
        } else {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized perform this action"));
        }

    }

    /**
     * Function will check if user has right assign provided role or not. In case if user has provided role function
     * will return true otherwise false. 
     * @param conn - connection to the DB
     * @param userId - reference to the user, for which should be performed validation
     * @param roleId - reference to the role. This role will be checked if user has this role or not
     * @return in case if role available for this user will return true, otherwise false.
     * @throws Exception
     */
    private boolean checkIfRoleBelongsToUser(Connection conn, Double userId, Double roleId, Double orgId) throws Exception {
        boolean answer = false;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("Select count(t.rol_id) cnt from " //
                + " (select uro.uro_rol_id rol_id" + //
                "  from spr_user_roles uro " //
                + " where uro.uro_usr_id = ?" //
                + "   and uro.uro_rol_id = ?" //
                + "   and " + dbStatementManager.getPeriodValidationForCurrentDateStr("uro.uro_date_from", "uro.uro_date_to") //
                + "  union all " //
                + "  select ur.our_rol_id as rol_id " //
                + "    from spr_org_users ou " //
                + "     join spr_org_user_roles ur on (ou.ou_id = ur.our_ou_id) " //
                + " where " + dbStatementManager.getPeriodValidationForCurrentDateStr("ur.our_date_from", "ur.our_date_to") //
                + "   and ou.ou_org_id = ? " //
                + "   and ur.our_rol_id = ? " //
                + "   and ou.ou_usr_id = ?) t ");

        stmt.addSelectParam(userId);
        stmt.addSelectParam(roleId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(roleId);
        stmt.addSelectParam(userId);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, String> rec = data.get(i);
            if (!"0".equals(rec.get("cnt"))) {
                answer = true;
            }
        }
        return answer;

    }

    private boolean checkIfOrganizationBelongsToUser(Connection conn, Double userId, Double orgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("Select count(1) cnt " //
                + "  from spr_org_users ou " //
                + " where ou.ou_usr_id = ?" //
                + "   and ou.ou_org_id = ?" //
                + "   and " + dbStatementManager.getPeriodValidationForCurrentDateStr("ou.ou_date_from", "ou.ou_date_to"));
        stmt.addSelectParam(userId);
        stmt.addSelectParam(orgId);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        return !data.isEmpty() && data.get(0).get("cnt") != null && !data.get(0).get("cnt").equals("0");
    }

    public void setUserRole(Connection conn, String roleId, Double orgId) throws Exception {
        SprUsersDAO sprUserDAO = sprUsersDBservice.loadRecord(conn, requestContext.getUserSession().getUsrId());
        SprRolesDAO sprRolesDAO = sprRolesDBService.loadRecord(conn, Double.parseDouble(roleId));
        if (!checkIfRoleBelongsToUser(conn, sprUserDAO.getUsr_id(), sprRolesDAO.getRol_id(), orgId)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized perform this action"));
        }
        BUS userSession = requestContext.getUserSession();
        if (sprUserDAO.getUsr_rol_id() == null || sprUserDAO.getUsr_rol_id().longValue() != sprRolesDAO.getRol_id().longValue()) {
            sprUserDAO.setUsr_rol_id(sprRolesDAO.getRol_id());
            sprUsersDBservice.saveRecord(conn, sprUserDAO);

            userSession.setRole(sprRolesDAO.getRol_code());
            userSession.setRole_name(sprRolesDAO.getRol_name());
            userSession.setRole_type(sprRolesDAO.getRol_type());
            userSession.setSes_rol_id(sprRolesDAO.getRol_id());
            userSession.setSes_login_time(new Date());
            userSession.setSes_last_action_time(new Date());
            sprOpenSessionService.saveRecord(conn, userSession);
            requestContext.setUserSession(userSession);
        }
    }

    public void setUserOrganization(Connection conn, String orgId) throws Exception {
        SprUsersDAO sprUserDAO = sprUsersDBservice.loadRecord(conn, requestContext.getUserSession().getUsrId());
        SprOrganizationsDAO organization = sprOrganizationsDBService.loadRecord(conn, Double.parseDouble(orgId));
        if (!checkIfOrganizationBelongsToUser(conn, sprUserDAO.getUsr_id(), organization.getOrg_id())) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized to perform this action"));
        }
        BUS userSession = requestContext.getUserSession();
        thisObject.clearUserRoleManagerCashe(userSession.getSes_org_id(), userSession.getSes_usr_id());
        if (sprUserDAO.getUsr_org_id() == null || sprUserDAO.getUsr_org_id().longValue() != organization.getOrg_id().longValue()) {
            sprUserDAO.setUsr_org_id(organization.getOrg_id());
            sprUserDAO.setUsr_rol_id(null);
            sprUsersDBservice.saveRecord(conn, sprUserDAO);
        }
        if ((sprUserDAO.getUsr_org_id() == null && userSession.getSes_org_id() != null)
                || (sprUserDAO.getUsr_org_id() != null && !sprUserDAO.getUsr_org_id().equals(userSession.getSes_org_id()))) {
            userSession.setSes_org_id(organization.getOrg_id());
            userSession.setOrgName(organization.getOrg_name());
            if (!isSingleRoleMode()) {
                userSession.setSes_profile_token(getUserProfile(conn, sprUserDAO.getUsr_org_id(), sprUserDAO.getUsr_id()));
            }
            userSession.setRole(null);
            userSession.setRole_name(null);
            userSession.setRole_type(null);
            userSession.setSes_rol_id(null);
            userSession.setSes_login_time(new Date());
            userSession.setSes_last_action_time(new Date());
            sprOpenSessionService.saveRecord(conn, userSession);
            if (!isSingleRoleMode()) {
                userSession.setRole(MERGED_ROLE_CODE);
                userSession.setRole_name(MERGED_ROLE_NAME);
                userSession.setSes_rol_id(Double.valueOf(MERGED_ROLE_ID));
            }
            requestContext.setUserSession(userSession);
        }

    }

    /**
     * Function will rebuild user profile token and set new value to the user session.
     * @param conn
     * @throws Exception
     */
    public void reloadUserProfile(Connection conn) throws Exception {
        if (!isSingleRoleMode()) {
            BUS userSession = requestContext.getUserSession();
            log.debug("old profile token is: " + userSession.getSes_profile_token());
            userSession.setSes_profile_token(getUserProfile(conn, userSession.getSes_org_id(), userSession.getSes_usr_id()));
            log.debug("new profile token is: " + userSession.getSes_profile_token());
            if (userSession.isRecordChanged()) {
                userSession.setForceUpdate(true);
                sprOpenSessionService.saveRecord(conn, userSession);
                requestContext.setUserSession(userSession);
            }
        }
    }

    public String getRoleList4User(Connection conn, BUS sessionObj) throws Exception {
        if (sessionObj.getUsr_id() != null && sessionObj.getUsr_id().intValue() == ANONYMOUS_USER_ID) {
            return getPublicRoleData(conn);
        } else {
            if (isSingleRoleMode()) {
                StatementAndParams stmt = new StatementAndParams();
                stmt.setStatement(dbStatementManager.getStatementByName("roleList4User"));
                stmt.setWhereExists(true);
                stmt.addSelectParam(sessionObj.getUsr_id());
                stmt.addSelectParam(sessionObj.getSes_org_id());
                stmt.addSelectParam(sessionObj.getUsr_id());
                return baseControllerJDBC.selectRefCodesAsJSON(conn, stmt);
            } else {
                return "[{\"rol_id\":\"" + MERGED_ROLE_ID + "\", \"rol_code\":\"" + MERGED_ROLE_CODE + "\", \"rol_name\":\"" + MERGED_ROLE_NAME + "\"}]";
            }
        }
    }

    /**
     * Function will load user roles to the userRoleManager. Function will work as wrapper for function 
     * getUserRoleManager(Connection conn, Double orgId, Double userId) This function will take information from session 
     * object and will call function getUserRoleManager.
     * @param conn - connection to the db
     * @return created user role manager object.
     * @throws Exception
     */
    public RoleManager getUserRoleManager(Connection conn) throws Exception {
        BUS sessionObj = requestContext.getUserSession();
        return thisObject.getUserRoleManager(conn, sessionObj.getSes_org_id(), sessionObj.getUsr_id());

    }

    @Cacheable(value = "userRolesManager", key = "{#orgId, #userId}")
    public RoleManager getUserRoleManager(Connection conn, Double orgId, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("roleList4User"));
        stmt.setWhereExists(true);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(userId);
        List<UserRole> userRoles = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, UserRole.class);
        return new RoleManager(userRoles);
    }

    @CacheEvict(value = "userRolesManager", key = "{#orgId, #userId}")
    public void clearUserRoleManagerCashe(Double orgId, Double userId) {
        log.debug("Will remove cache for OrdgId: " + orgId + " userid: " + userId);
    }

    public String getOrganizationList4User(Connection conn, BUS sessionObj) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select o.org_id, o.org_name from spr_org_users ou, spr_organizations o where ou.ou_org_id = o.org_id and ou.ou_usr_id = ? and "
                + dbStatementManager.getPeriodValidationForCurrentDateStr("ou.ou_date_from", "ou.ou_date_to"));
        stmt.setWhereExists(true);
        if (sessionObj.getUsr_id() != null) {
            stmt.addSelectParam(sessionObj.getUsr_id());
        } else {
            stmt.addSelectParam(Double.valueOf(-1));
        }
        return baseControllerJDBC.selectRefCodesAsJSON(conn, stmt);
    }

    /**
     * Function will return data for public role.
     * @param conn - connection to the db
     * @return - json object that presents public role data
     * @throws Exception
     */
    public String getPublicRoleData(Connection conn) throws Exception {
        if (publicRoleJson == null) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement(dbStatementManager.getStatementByName("roleList4Public"));
            publicRoleJson = baseControllerJDBC.selectRefCodesAsJSON(conn, stmt);
        }
        return publicRoleJson;
    }

    public Double getPublicRoleId(Connection conn) throws Exception {
        if (publicRoleID == null) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement(dbStatementManager.getStatementByName("roleList4Public"));
            List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
            String roleIdStr = null;
            for (int i = 0; i < data.size(); i++) {
                HashMap<String, String> rec = data.get(i);
                roleIdStr = rec.get("rol_id");
            }
            publicRoleID = Utils.getDouble(roleIdStr);
        }
        return publicRoleID;
    }

    /**
     * Performs menu structure hierarchy construction. From flat structure transforms data to the hierarchical structure
     * @param sqlResult - flat menu structure retrieved from DB
     * @return  Constructed hierarchical menu structure. Menu hierarchy is implemented by eu.itreegroup.logic.spark.model.MenuStructure object.
     */
    private List<MenuStructure> constructMenuStructure(List<HashMap<String, String>> sqlResult) {
        ArrayList<MenuStructure> menuStructure = new ArrayList<MenuStructure>();
        int level = 0;
        if (sqlResult.size() > 0) {
            level = Integer.parseInt(sqlResult.get(0).get("level"));
        } else {
            return menuStructure;
        }
        boolean end = false;
        int i = 0;
        while (!end) {
            MenuStructure menuElement = new MenuStructure();
            Map<String, String> rec = sqlResult.get(i);
            menuElement.setIconName(rec.get("icon"));
            menuElement.setLevel(rec.get("level"));
            menuElement.setLink(rec.get("uri"));
            menuElement.setName(rec.get("title"));
            if (rec.get("form") == null) {
                menuElement.setType(MenuStructure.MENU_STRUCTURE_CATEGORY);
                for (int j = i; j >= 0; j--) {
                    sqlResult.remove(j);
                }
                i = 0;
                if (sqlResult.size() > 0 && Integer.parseInt(sqlResult.get(0).get("level")) > level) {
                    menuElement.setRow(constructMenuStructure(sqlResult));
                }
                int j = 0;
                for (int k = i; (k < sqlResult.size() && !end); k++) {
                    if (level == Integer.parseInt(sqlResult.get(k).get("level"))) {
                        end = true;
                        j = k - 1;
                    }
                }
                end = false;
                for (int k = j; (k >= 0 && sqlResult.size() > 0); k--) {
                    sqlResult.remove(k);
                }
            } else {
                menuElement.setType(MenuStructure.MENU_STRUCTURE_LINK);
                i++;
            }
            if (MenuStructure.MENU_STRUCTURE_LINK.equals(menuElement.getType()) || !menuElement.getRows().isEmpty()) {
                menuStructure.add(menuElement);
            }
            if (i >= sqlResult.size() || level != Integer.parseInt(sqlResult.get(i).get("level"))) {
                end = true;
            }
        }
        return menuStructure;
    }

    public List<MenuStructure> getInternalMenu(Connection conn, Double roleId, Double userId, Double orgId, String language) throws Exception {
        BUS session = requestContext.getUserSession();
        if (!isSingleRoleMode()) {
            if (session.getSes_profile_token() == null) {
                return getMenu4User(conn, userId, orgId, language);
            } else {
                return thisObject.getMenu4User(conn, userId, orgId, session.getSes_profile_token(), language);
            }
        } else {
            if (roleId == null) {
                return new ArrayList<MenuStructure>();
            } else {
                if (!checkIfRoleBelongsToUser(conn, userId, roleId, orgId)) {
                    throw new SparkBusinessException(
                            new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "User is not authorized perform this action"));

                }
                return getMenu4Role(conn, roleId, language);
            }
        }
    }

    @Cacheable(value = "roleMenu", key = "{#roleId, #language}")
    public List<MenuStructure> getMenu4Role(Connection conn, Double roleId, String language) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("roleMenu"));
        stmt.setWhereExists(true);
        stmt.addSelectParam(roleId);
        stmt.addSelectParam(language);
        log.debug(stmt.logData());
        List<HashMap<String, String>> result = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        List<MenuStructure> menuStructure = constructMenuStructure(result);
        return menuStructure;
    }

    @Cacheable(value = "userMenu", key = "{#profileToken, #language}")
    public List<MenuStructure> getMenu4User(Connection conn, Double userId, Double orgId, String profileToken, String language) throws Exception {
        return getMenu4User(conn, userId, orgId, language);
    }

    public List<MenuStructure> getMenu4User(Connection conn, Double userId, Double orgId, String language) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("userMenu"));
        stmt.setWhereExists(true);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(language);
        log.debug(stmt.logData());
        List<HashMap<String, String>> result = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        List<MenuStructure> menuStructure = constructMenuStructure(result);
        return menuStructure;
    }

    @Cacheable(value = "publicMenu", key = "{#language}")
    public List<MenuStructure> getPublicMenu(Connection conn, String language) throws Exception, Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("publicMenu"));
        stmt.addSelectParam(language);
        log.debug(stmt.logData());
        List<HashMap<String, String>> result = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        List<MenuStructure> menuStructure = constructMenuStructure(result);
        return menuStructure;
    }

    public RoleFormsActions getForms4Role(Connection conn) throws SQLException, Exception {
        BUS session = requestContext.getUserSession();
        log.debug("===========================================================");
        log.debug("==============  getForms4Role  ============================");
        log.debug("===========================================================");
        if (session.getUsr_id() != null && session.getUsr_id().intValue() == ANONYMOUS_USER_ID) {
            return thisObject.getForms4Role(conn, session.getSes_rol_id(), session.getSes_language());
        } else {
            if (!isSingleRoleMode()) {
                log.debug("session.getSes_profile_token(): " + session.getSes_profile_token());
                if (session.getSes_profile_token() == null) {
                    return thisObject.getForms4User(conn, session.getUsr_id(), session.getSes_org_id(), session.getSes_language());
                } else {
                    return thisObject.getForms4User(conn, session.getUsr_id(), session.getSes_org_id(), session.getSes_profile_token(),
                            session.getSes_language());
                }
            } else {
                if (session.getSes_rol_id() != null) {
                    return thisObject.getForms4Role(conn, session.getSes_rol_id(), session.getSes_language());
                } else {
                    return new RoleFormsActions();
                }
            }
        }

    }

    private RoleFormsActions constructFormActionStructure(Connection conn, List<HashMap<String, String>> data, String lang) throws Exception {
        RoleFormsActions roleFormsActions = new RoleFormsActions();
        if (data.size() > 0) {
            roleFormsActions.setRoleName(data.get(0).get("rol_name"));
            String formCode = null;
            String rolName = data.get(0).get("rol_name");
            boolean defaultForm = false;
            FormActions defForm = null;
            List<String> allAvailableActions = null;
            List<String> allActions = null;
            for (int i = 0; i < data.size(); i++) {
                Map<String, String> record = data.get(i);
                if (formCode == null || !formCode.equals(record.get("frm_code"))) {
                    if (formCode != null) {
                        FormActions form = new FormActions(formCode, allActions, allAvailableActions);
                        roleFormsActions.addForm(formCode, form);
                        if (defaultForm) {
                            defForm = form;
                        }
                    }
                    formCode = record.get("frm_code");
                    String defaultFormVal = record.get("default_form");
                    defaultForm = (defaultFormVal != null && !defaultFormVal.isBlank() && YesNo.valueOf(defaultFormVal).getBoolean());
                    allAvailableActions = new ArrayList<String>();
                    allActions = new ArrayList<String>();
                }
                allActions.add(record.get("frm_action"));
                if ("N".equalsIgnoreCase(record.get("disabled_action"))) {
                    allAvailableActions.add(record.get("frm_action"));
                }
            }
            FormActions form = new FormActions(formCode, allActions, allAvailableActions);
            roleFormsActions.addForm(formCode, form);
            if (defaultForm) {
                defForm = form;
            }
            if (defForm != null) {
                StatementAndParams stmt = new StatementAndParams();
                stmt.setStatement("select ms.mst_uri as MST_URI " + //
                        "  from spr_forms frm " + //
                        "  join spr_menu_structures ms on frm.frm_id = ms.mst_frm_id and ms.mst_lang = ? and ms.mst_is_public = ? " + //
                        " where frm.frm_code = ?");
                stmt.setWhereExists(true);
                stmt.addSelectParam(lang);
                if (rolName.equalsIgnoreCase(PUBLIC)) {
                    stmt.addSelectParam(YesNo.Y.getCode());
                } else {
                    stmt.addSelectParam(YesNo.N.getCode());
                }
                stmt.addSelectParam(defForm.getFormName());
                List<HashMap<String, String>> data1 = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
                if (data1 != null && !data1.isEmpty()) {
                    roleFormsActions.setDefaultFormUrl(data1.get(0).get("mst_uri"));
                }

            }
        }
        return roleFormsActions;
    }

    @Cacheable(value = "roleActions", key = "#roleId")
    public RoleFormsActions getForms4Role(Connection conn, Double roleId, String lang) throws SQLException, Exception {
        log.debug("Will get data from db for roleId: " + roleId);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("forms4Role"));
        stmt.setWhereExists(true);
        stmt.addSelectParam(roleId);
        RoleFormsActions roleFormsActions = constructFormActionStructure(conn, baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt), lang);
        roleFormsActions.setRoleId("" + roleId.intValue());
        return roleFormsActions;
    }

    @Cacheable(value = "userActions", key = "#profileToken")
    public RoleFormsActions getForms4User(Connection conn, Double userId, Double orgId, String profileToken, String lang) throws SQLException, Exception {
        log.debug("cache for profileToken: " + profileToken + " is empty will load from db");
        return getForms4User(conn, userId, orgId, lang);
    }

    public RoleFormsActions getForms4User(Connection conn, Double userId, Double orgId, String lang) throws SQLException, Exception {
        log.debug("Will get data from db for user: " + userId);
        // RoleFormsActions roleFormsActions = new RoleFormsActions();
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("forms4User"));
        stmt.setWhereExists(true);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(userId);
        stmt.addSelectParam(orgId);
        RoleFormsActions roleFormsActions = constructFormActionStructure(conn, baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt), lang);
        roleFormsActions.setRoleId("" + MERGED_ROLE_ID);
        roleFormsActions.setRoleName(MERGED_ROLE_CODE);
        return roleFormsActions;
    }

    public void changeLanguage(Connection conn, String language) throws Exception {
        SprUsersDAO sprUserDAO = sprUsersDBservice.loadRecord(conn, requestContext.getUserSession().getUsrId());
        sprUserDAO.setUsr_language(language);
        sprUsersDBservice.saveRecord(conn, sprUserDAO);
        BUS userSession = requestContext.getUserSession();
        userSession.setForceUpdate(true);
        userSession.setSes_language(sprUserDAO.getUsr_language());
        sprOpenSessionService.saveRecord(conn, userSession);
        sprOpenSessionService.removeCachedSession(userSession.getSes_key());
        requestContext.setUserSession(userSession);
    }

    public void acceptTerms(Connection conn, String answer) throws Exception {
        SprUsersDAO sprUserDAO = sprUsersDBservice.loadRecord(conn, requestContext.getUserSession().getUsrId());
        sprUserDAO.setUsr_terms_accepted(answer);
        sprUserDAO.setUsr_terms_accepted_date(new Date());
        sprUsersDBservice.saveRecord(conn, sprUserDAO);
        BUS userSession = requestContext.getUserSession();
        userSession.setForceUpdate(true);
        userSession.setSes_terms_accepted("Y");
        userSession.setSes_terms_accepted_date(new Date());
        sprOpenSessionService.saveRecord(conn, userSession);
        sprOpenSessionService.removeCachedSession(userSession.getSes_key());
        requestContext.setUserSession(userSession);
    }

    /**
     * Function will perform management of password request initiation. Function will check if user with provided email exists in system and this user is not locked.
     * If such user exists password request string will be generated and email request will be created. 
     * @param conn - connection to the db
     * @param userName - user name for which the password should be changed
     * @throws SQLException
     * @throws Exception
     */
    public void request4forgotPassword(Connection conn, String userName, String langCode) throws SQLException, Exception {
        StatementAndParams stmt = new StatementAndParams("""
                SELECT usr_id,
                       usr_username,
                       to_char(usr_lock_date, '%s') usr_lock_date,
                       usr_email,
                       usr_language
                 FROM SPR_USERS
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addParam4WherePart(" usr_username = ? ", userName.trim());

        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        if (data.size() > 0) {
            Map<String, String> row = data.get(0);
            Date currentDate = new Date();
            if (langCode == null) {
                langCode = row.get("usr_language");
            }
            if (row.get("usr_lock_date") == null || currentDate
                    .after(Utils.getDateFromString(row.get("usr_lock_date"), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)))) {
                // handle context
                Map<String, Object> context = new HashMap<String, Object>();
                context.put("userName", row.get("usr_username"));
                sprProcessRequest.createPasswordChangeRequest(conn, Utils.getDouble(row.get("usr_id")), row.get("usr_email"),
                        Languages.getLanguageByCode(langCode), context);
            }
        }
    }

    /**
     * Function will perform management of password request initiation. 
     * If such user exists password request string will be generated and email request will be created. 
     * @param conn - connection to the db
     * @param usrId - user ID for which the password should be created
     * @throws SQLException
     * @throws Exception
     */
    public void requestForCreatePassword(Connection conn, Double usrId, String langCode) throws SQLException, Exception {
        StatementAndParams stmt = new StatementAndParams("""
                SELECT usr_username,
                       usr_email,
                       usr_language
                 FROM SPR_USERS
                WHERE usr_id = ?
                """);
        stmt.addSelectParam(usrId);

        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        if (data.size() > 0) {
            Map<String, String> row = data.get(0);
            if (langCode == null) {
                langCode = row.get("usr_language");
            }
            // handle context
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("userName", row.get("usr_username"));
            sprProcessRequest.createPasswordCreateRequest(conn, usrId, row.get("usr_email"), Languages.getLanguageByCode(langCode), context);
        }
    }

    public void request4EmailVerification(Connection conn, Double userId, String email, String langCode) throws SQLException, Exception {
        SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, userId);
        if (email != null) {
            if (userDAO.getUsr_email() == null && !userDAO.getUsr_email().equalsIgnoreCase(email)) {
                userDAO.setUsr_email(email);
                sprUsersDBservice.saveRecord(conn, userDAO);
            }
        }
        sprProcessRequest.createCheckEmailRequest(conn, userId, email, Languages.getLanguageByCode(langCode));
    }

    /**
     * Function will validate new password and if provided password fits to the requirements it will be stored in DB.
     * @param conn
     * @param userDAO
     * @param password
     * @throws SQLException
     * @throws Exception
     */
    private void changeUserPassword(Connection conn, SprUsersDAO userDAO, String password, SAPProcessRequestType processRequestType)
            throws SQLException, Exception {
        // TODO SAP-223 implement logic for password Algorithm identification.
        String passwordAlgorithmCode = null;
        passwordManager.checkPassword(passwordAlgorithmCode, password, userDAO.getUsr_password_hash(), userDAO.getUsr_password_history());
        String hashedPassword = passwordManager.getPasswordHash(passwordAlgorithmCode, password, null);
        userDAO.setUsr_password_hash(hashedPassword);
        userDAO.setUsr_password_history(passwordManager.getPasswordHistory(passwordAlgorithmCode, userDAO.getUsr_password_history(), hashedPassword));
        Date currentDate = new Date();
        userDAO.setUsr_password_change_date(currentDate);
        userDAO = sprUsersDBservice.saveRecord(conn, userDAO);
        Double validInDays = passwordManager.getPasswordValidInDays(passwordAlgorithmCode);
        if (validInDays != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, validInDays.intValue());
            userDAO.setUsr_password_next_change_date(c.getTime());
            userDAO.setUsr_password_reset_req_date(c.getTime());
        } else {
            userDAO.setUsr_password_next_change_date(null);
            userDAO.setUsr_password_reset_req_date(null);
        }
        userDAO = sprUsersDBservice.saveRecord(conn, userDAO);
        if (processRequestType == SAPProcessRequestType.CHANGE_PASSWORD) {
            BUS userSession = requestContext.getUserSession();
            userSession.setForceUpdate(true);
            userSession.setSes_password_reset_req_date(userDAO.getUsr_password_reset_req_date());
            userSession.setUserPasswordChangeToken(null);
            sprOpenSessionService.saveRecord(conn, userSession);
            sprOpenSessionService.removeCachedSession(userSession.getSes_key());
            requestContext.setUserSession(userSession);
        }
    }

    /**
     * Function will find user by provided password request string and change password for this user. Password will be changed if user is not locked 
     * @param conn - connection to the db
     * @param changePasswordRequest - password request string (random generated string) that will be used for user identification, that perform request of password change
     * @param password - new password, that should be stored in system.
     * @throws SQLException
     * @throws Exception
     */
    public void changePasswordByRequest(Connection conn, SAPProcessRequestType processRequestType, String changePasswordRequest, String password,
            String langCode) throws SQLException, Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = sprProcessRequest.getIdetifierByToken(conn, processRequestType, changePasswordRequest, true);
        SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, sprProcessRequestsDAO.getPrq_reference_id());
        if (userDAO.getUsr_lock_date() == null) {
            changeUserPassword(conn, userDAO, password, processRequestType);
            return;
        }
        throw new SparkBusinessException(
                new S2Message("pwd.wrongCredentials", SparkMessageType.ERROR, "Sorry, your credentials are incorrect. Please try again."));

    }

    /**
     * Function will check if provided old passwords is equal to the stored password. If yeas will be performed new password validation and storing in DB.
     * @param conn - connection to the db
     * @param userId - user identifier for which should be changed password
     * @param oldPassword - old password.
    * @param newPassword - new password.
     * @throws SQLException
     * @throws Exception
     */
    public void changePassword(Connection conn, Double userId, String oldPassword, String newPassword, String langCode) throws SQLException, Exception {
        SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, userId);
        // TODO SAP-223 implement logic for password Algorithm identification.
        String passwordAlgorithmCode = null;
        String oldPasswordHash = passwordManager.getPasswordHash(passwordAlgorithmCode, oldPassword, null);
        if (userDAO.getUsr_lock_date() == null) {
            if (oldPasswordHash != null && oldPasswordHash.equals(userDAO.getUsr_password_hash())) {
                changeUserPassword(conn, userDAO, newPassword, SAPProcessRequestType.CHANGE_PASSWORD);
                return;
            }
        }
        throw new SparkBusinessException(
                new S2Message("pages.changePassword.wrongPassword", SparkMessageType.ERROR, "Sorry, your password is incorrect. Please try again."));

    }

    public void validateEmailRequest(Connection conn, SAPProcessRequestType processRequestType, String token, String langCode) throws Exception {
        SprProcessRequestsDAO sprProcessRequestsDAO = sprProcessRequest.getIdetifierByToken(conn, processRequestType, token, true);
        String processRequestEmail = sprProcessRequestsDAO.getC01();
        if (processRequestEmail == null) {
            throw new Error("Email address is not set for process request of " + processRequestType + " type with token " + token);
        }
        SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, sprProcessRequestsDAO.getPrq_reference_id());
        if (userDAO.getUsr_lock_date() == null) {
            SprPersonsDAO personsDAO = null;
            if (userDAO.getUsr_per_id() != null) {
                personsDAO = this.sprPersonsDBService.loadRecord(conn, userDAO.getUsr_per_id());
                if (!YesNo.Y.getCode().equals(personsDAO.getPer_email_confirmed()) && processRequestEmail.equals(personsDAO.getPer_email())) {
                    personsDAO.setPer_email_confirmed(YesNo.Y.getCode());
                    userDAO.setUsr_email(personsDAO.getPer_email());
                    this.sprPersonsDBService.saveRecord(conn, personsDAO);
                }
            }
            if (processRequestEmail.equals(userDAO.getUsr_email())) {
                userDAO.setUsr_email_confirmed(YesNo.Y.getCode());
            } else if (personsDAO == null || !YesNo.Y.getCode().equals(personsDAO.getPer_email_confirmed())) {
                throw new Exception("The specified email address (" + processRequestEmail + ") for token (" + token
                        + ") do not match with user and/or person email address");
            }
            this.sprUsersDBservice.saveRecord(conn, userDAO);
            return;
        }
        throw new Exception("No process request found of " + processRequestType + " type with token " + token);
    }

    /**
     * Function will return true if application works in single role mode
     * @return if application works in single role mode then true otherwise false;
     */
    public boolean isSingleRoleMode() {
        return SprAuthorization.SINGLE_ROLE_MODE.equals(roleMode);
    }

    /**
     * 
     */
    /**
     * Function wrapper for password manager function, that should return information about password parameters. In case if provided
     * process request identifier then using this identifier will be extracted user, that request password change algorithm, otherwise
     * (if process request identifier not provided) user will be extracted from session.
     * @param conn - connection to the db
     * @param requestToken - process request identifier.
     * @return - list of requirements for password.
     * @throws SQLException
     * @throws Exception
     */
    public HashMap<String, String> getPasswordParameters(Connection conn, RequestToken requestToken, boolean isUserAuthenticated) throws Exception {
        String passwordAlgorithmCode = null;
        if (!isUserAuthenticated) {
            if (requestToken.getToken() == null || requestToken.getToken().isBlank()) {
                if (requestContext.getUserSession() != null) {
                    SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, requestContext.getUserSession().getSes_usr_id());
                    passwordAlgorithmCode = userDAO.getUsr_password_algorithm();
                }
            } else {
                SprProcessRequestsDAO sprProcessRequestsDAO = sprProcessRequest.getIdetifierByToken(conn, requestToken.getProcessRequestType(),
                        requestToken.getToken(), false);
                SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, sprProcessRequestsDAO.getPrq_reference_id());
                passwordAlgorithmCode = userDAO.getUsr_password_algorithm();
            }
        }
        return passwordManager.getPasswordProperties(passwordAlgorithmCode);
    }

    /**
     * Function will check the user have password
     * @param conn - connection to the db
     * @param userId - user ID
     * @return boolean - true/false
     * @throws SQLException
     * @throws Exception
     */
    public boolean checkUserHavePassword(Connection conn, Double userId) throws Exception {
        SprUsersDAO userDAO = sprUsersDBservice.loadRecord(conn, userId);
        return userDAO.getUsr_password_hash() != null && !userDAO.getUsr_password_hash().isEmpty();
    }

    /**
     * Function will check if user has confirmed email
     * @param conn - connection to the db
     * @param userId - user ID
     * @return boolean - true/false
     * @throws SQLException
     * @throws Exception
     */
    public boolean checkUserHasConfirmedEmail(Connection conn, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                SELECT 1
                 FROM SPR_USERS u
                 LEFT JOIN SPR_PERSONS p ON u.usr_per_id = p.per_id AND u.usr_email = p.per_email
                 WHERE (u.usr_email_confirmed = ? OR p.per_email_confirmed = ?)
                   AND u.usr_id = ?
                """);
        stmt.addSelectParam(DbConstants.BOOLEAN_TRUE);
        stmt.addSelectParam(DbConstants.BOOLEAN_TRUE);
        stmt.addSelectParam(userId);

        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        return data.size() > 0;
    }

}