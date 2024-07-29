package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.util.JwtUtil;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.s2.server.rest.model.CreateNewUserRequest;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprApiKeysDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprProfile;
import eu.itreegroup.spark.modules.admin.service.SprApiKeysDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprSessionOpenDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@Component
public class SprProfileSettings<BUS extends SprBackendUserSession> extends FormBase {

    public static String ACTION_CODE_CLEAR_LOCAL_CACHE = "CLEAR_LOCAL_CACHE";

    public static String ACTION_NAME_CLEAR_LOCAL_CACHE = "Clearing local cache right";

    public static String ACTION_DESC_CLEAR_LOCAL_CACHE = "Right of clearing some locally stored cache on Angular app (stored in localStorage, sessionStorage, services, etc.)";

    public static String ACTION_CODE_LINK_ACCOUNTS = "LINK_ACCOUNTS";

    public static String ACTION_NAME_LINK_ACCOUNTS = "Merge two accounts into one";

    public static String ACTION_DESC_LINK_ACCOUNTS = "Right of merging two accounts into one";

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprProfileSettings.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    protected S2RestRequestContext<BUS> requestContext;

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    @Autowired
    SprSessionOpenDBService sprOpenSessionService;

    @Autowired
    SprApiKeysDBService sprApiKeysDBService;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDBService;

    @Autowired
    SprAuthorization<SprBackendUserSession> sprAuthorization;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public String getFormName() {
        return "SPR_PROFILE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark user profile", "Spark user profile settings");
        addFormActions(ACTION_READ, ACTION_UPDATE);
        addFormAction(ACTION_CODE_CLEAR_LOCAL_CACHE, ACTION_NAME_CLEAR_LOCAL_CACHE, ACTION_DESC_CLEAR_LOCAL_CACHE);
        addFormAction(ACTION_CODE_LINK_ACCOUNTS, ACTION_NAME_LINK_ACCOUNTS, ACTION_DESC_LINK_ACCOUNTS);
    }

    public SprProfile getProfileSettings(Connection conn, Double sessionOrgId, Double sessionUsrId) throws Exception {
        checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        SprProfile profile = new SprProfile();
        Double per_id = requestContext.getUserSession().getPer_id();
        if (per_id != null) {
            // person exists, get data from persons table:
            SprPersonsDAO personsDAO = sprPersonsDBService.loadRecord(conn, per_id);
            profile.setPer_code(personsDAO.getPer_code());
            profile.setPer_name(personsDAO.getPer_name());
            profile.setPer_surname(personsDAO.getPer_surname());
            profile.setPer_phone_number(personsDAO.getPer_phone_number());
            profile.setPer_email(personsDAO.getPer_email());
            if (personsDAO.getPer_email_confirmed() != null) {
                profile.setPer_email_confirmed(YesNo.getEnumByCode(personsDAO.getPer_email_confirmed()).getBoolean());
            } else {
                profile.setPer_email_confirmed(false);
            }
            profile.setEmailNotifications(("Y").equals(personsDAO.getC01()));
            profile.setPer_address_country_code(personsDAO.getPer_address_country_code());
            profile.setPer_address_post_code(personsDAO.getPer_address_post_code());
            profile.setPer_address_city(personsDAO.getPer_address_city());
            profile.setPer_address_street(personsDAO.getPer_address_street());
            profile.setPer_address_house_number(personsDAO.getPer_address_house_number());
            profile.setPer_address_flat_number(personsDAO.getPer_address_flat_number());
            profile.setLinkAccountsAvailable(
                    YesNo.Y.getCode().equals(personsDAO.getPer_data_confirmed()) && YesNo.Y.getCode().equals(personsDAO.getPer_email_confirmed()));
        } else {
            // person doesn't exist, get name and surname from user table:
            Double usrId = requestContext.getUserSession().getUsrId();
            SprUsersDAO usersDAO = sprUsersDBService.loadRecord(conn, usrId);
            profile.setPer_name(usersDAO.getUsr_person_name());
            profile.setPer_surname(usersDAO.getUsr_person_surname());
            profile.setPer_email_confirmed(YesNo.getEnumByCode(usersDAO.getUsr_email_confirmed()).getBoolean());
            profile.setLinkAccountsAvailable(false);
        }
        SprApiKeysDAO apiKeysDAO = null;
        if (sessionOrgId == null) {
            apiKeysDAO = sprApiKeysDBService.loadRecordByParams(conn, "api_usr_id = ? and ? between api_date_from and coalesce(api_date_to, ?)",
                    new SelectParamValue(sessionUsrId), new SelectParamValue(new Date()), new SelectParamValue(new Date()));
        } else {
            SprOrgUsersDAO orgUserDAO = sprOrgUsersDBService.loadRecordByParams(conn,
                    "ou_usr_id = ? and ou_org_id = ? and ? between ou_date_from and coalesce(ou_date_to, ?)", new SelectParamValue(sessionUsrId),
                    new SelectParamValue(sessionOrgId), new SelectParamValue(new Date()), new SelectParamValue(new Date()));
            if (orgUserDAO != null && orgUserDAO.getOu_id() != null) {
                apiKeysDAO = sprApiKeysDBService.loadRecordByParams(conn, "api_ou_id = ? and ? between api_date_from and coalesce(api_date_to, ?)",
                        new SelectParamValue(orgUserDAO.getOu_id()), new SelectParamValue(new Date()), new SelectParamValue(new Date()));
            }
        }
        if (apiKeysDAO != null && apiKeysDAO.getApi_key() != null) {
            profile.setApi_key(apiKeysDAO.getApi_key());
            profile.setApi_id(apiKeysDAO.getApi_id());
        }
        return profile;
    }

    public void updateProfileSettings(Connection conn, SprProfile profile) throws Exception {
        checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        BUS userSession = requestContext.getUserSession();
        Double per_id = userSession.getPer_id();
        SprPersonsDAO personsDAO = null;
        Boolean isNewRecord = false;
        SprUsersDAO usersDAO = null;
        try {
            personsDAO = sprPersonsDBService.loadRecord(conn, per_id);
        } catch (Exception ex) {
            // person doesn't exist, create a new person record:
            personsDAO = sprPersonsDBService.newRecord();
            isNewRecord = true;
            // get name and surname from users table:
            Double usr_id = userSession.getSes_usr_id();
            usersDAO = sprUsersDBService.loadRecord(conn, usr_id);
            personsDAO.setPer_name(usersDAO.getUsr_person_name());
            personsDAO.setPer_surname(usersDAO.getUsr_person_surname());
            // set date of birth to 1800-01-01, code exists to N and lrt resident to N:
            personsDAO.setPer_date_of_birth(new SimpleDateFormat("yyyy-MM-dd").parse("1800-01-01"));
            personsDAO.setPer_code_exists(DbConstants.BOOLEAN_FALSE);
            personsDAO.setPer_lrt_resident(DbConstants.BOOLEAN_FALSE);
        }

        if (isNewRecord || !Objects.equals(personsDAO.getPer_email(), profile.getPer_email())) {
            personsDAO.setPer_email_confirmed(YesNo.N.getCode());
        }
        personsDAO.setPer_phone_number(profile.getPer_phone_number());
        personsDAO.setPer_email(profile.getPer_email());
        personsDAO.setC01(profile.getEmailNotifications() ? DbConstants.BOOLEAN_TRUE : DbConstants.BOOLEAN_FALSE);
        personsDAO.setPer_address_street(profile.getPer_address_street());
        personsDAO.setPer_address_house_number(profile.getPer_address_house_number());
        personsDAO.setPer_address_flat_number(profile.getPer_address_flat_number());
        personsDAO.setPer_address_city(profile.getPer_address_city());
        personsDAO.setPer_address_post_code(profile.getPer_address_post_code());
        personsDAO = sprPersonsDBService.saveRecord(conn, personsDAO);

        if (isNewRecord) {
            usersDAO.setUsr_per_id(personsDAO.getPer_id());
            usersDAO = sprUsersDBService.saveRecord(conn, usersDAO);
            userSession.setForceUpdate(true);
            userSession.setSes_per_id(personsDAO.getPer_id());
            sprOpenSessionService.saveRecord(conn, userSession);
            sprOpenSessionService.removeCachedSession(userSession.getSes_key());
            requestContext.setUserSession(userSession);
        }

    }

    public void linkAccounts(Connection conn, Double usrId, Double perId, String usrType, CreateNewUserRequest data) throws Exception {
        this.checkIsFormActionAssigned(conn, SprProfileSettings.ACTION_CODE_LINK_ACCOUNTS);
        Map<String, Object> authExtData = new HashMap<String, Object>();
        authExtData.put("AUTH_TYPE", "USER_PASSWORD_AUTH");
        authExtData.put("USER_TYPE", usrType);
        SprUsersDAO sprUsersDAO = this.sprAuthorization.attemptToLogin(conn, data.getUsername(), data.getPassword(), null);
        if (usrId.equals(sprUsersDAO.getUsr_id())) {
            throw new SparkBusinessException(new S2Message("pages.sprProfile.error.linkSameUser", SparkMessageType.ERROR));
        }
        if (perId == null) {
            throw new SparkBusinessException(new S2Message("pages.sprProfile.error.linkNoPerson", SparkMessageType.ERROR));
        }
        SprPersonsDAO currentPerson = this.sprPersonsDBService.loadRecord(conn, perId);
        if (!YesNo.Y.getCode().equals(currentPerson.getPer_email_confirmed())) {
            throw new SparkBusinessException(new S2Message("pages.sprProfile.error.linkEmailNotConfirmed", SparkMessageType.ERROR));
        }
        SprPersonsDAO linkPerson = null;
        if (sprUsersDAO.getUsr_per_id() != null) {
            linkPerson = this.sprPersonsDBService.loadRecord(conn, sprUsersDAO.getUsr_per_id());
            if (YesNo.Y.getCode().equals(linkPerson.getPer_data_confirmed())) {
                if (!Objects.equals(currentPerson.getPer_code(), linkPerson.getPer_code())
                        || !Objects.equals(currentPerson.getPer_lrt_resident(), linkPerson.getPer_lrt_resident())) {
                    throw new SparkBusinessException(new S2Message("pages.sprProfile.error.linkPerCode", SparkMessageType.ERROR));
                }
            }
            if (YesNo.Y.getCode().equals(linkPerson.getPer_email_confirmed()) && !Objects.equals(currentPerson.getPer_email(), linkPerson.getPer_email())) {
                throw new SparkBusinessException(new S2Message("pages.sprProfile.error.linkEmailsDoNotMatch", SparkMessageType.ERROR));
            }
        } else if (YesNo.Y.getCode().equals(sprUsersDAO.getUsr_email_confirmed())
                && !Objects.equals(currentPerson.getPer_email(), sprUsersDAO.getUsr_email())) {
            throw new SparkBusinessException(new S2Message("pages.sprProfile.error.linkEmailsDoNotMatch", SparkMessageType.ERROR));
        }
        StatementAndParams statementAndParams = new StatementAndParams("CALL spark.spr_join_users(?, ?)");
        statementAndParams.addSelectParam(usrId);
        statementAndParams.addSelectParam(sprUsersDAO.getUsr_id());
        this.baseControllerJDBC.adjustRecordsInDB(conn, statementAndParams);
        sprUsersDAO.setUsr_date_to(new Date());
        this.sprUsersDBService.saveRecord(conn, sprUsersDAO);
        if (linkPerson != null) {
            this.sprPersonsDBService.deleteRecord(conn, linkPerson.getPer_id());
        }
    }

    public void sendConfirmationLink(Connection conn, Double usrId, String lang) throws Exception {
        checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        SprUsersDAO sprUsersDAO = this.sprUsersDBService.loadRecord(conn, usrId);
        String email = sprUsersDAO.getUsr_email();
        if (sprUsersDAO.getUsr_per_id() != null) {
            SprPersonsDAO sprPersonsDAO = this.sprPersonsDBService.loadRecord(conn, sprUsersDAO.getUsr_per_id());
            email = sprPersonsDAO.getPer_email();
        }
        this.sprProcessRequest.createCheckEmailRequest(conn, usrId, email, Languages.getLanguageByCode(lang));
    }

    /**
     * Method generates new API key
     *
     * @param conn             The database connection to use for the retrieval.
     * @param sessionUsrId session user id
     * @param sessionOrgId organization id of the session user
     * @return newly generated api key
     * @throws Exception If an error occurs during the database retrieval.
     */
    public SprApiKeysDAO saveApiKey(Connection conn, Double sessionUsrId, Double sessionOrgId) throws Exception {
        checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        SprApiKeysDAO sprKeysDAO = sprApiKeysDBService.newRecord();
        sprKeysDAO.setApi_date_from(Utils.getDate(new Date()));
        if (sessionOrgId != null) {
            SprOrgUsersDAO orgUserDAO = sprOrgUsersDBService.loadRecordByParams(conn,
                    "ou_usr_id = ? and ou_org_id = ? and ? between ou_date_from and coalesce(ou_date_to, ?)", new SelectParamValue(sessionUsrId),
                    new SelectParamValue(sessionOrgId), new SelectParamValue(new Date()), new SelectParamValue(new Date()));
            if (orgUserDAO != null && orgUserDAO.getOu_id() != null) {
                sprKeysDAO.setApi_ou_id(orgUserDAO.getOu_id());
                sprKeysDAO.setApi_type_code("ORG");
            }
        } else {
            sprKeysDAO.setApi_usr_id(sessionUsrId);
            sprKeysDAO.setApi_type_code("USR");
        }
        sprKeysDAO.setApi_date_from(Utils.getDate(new Date()));
        SprUsersDAO userDAO = sprUsersDBService.loadRecord(conn, sessionUsrId);
        sprKeysDAO.setApi_key(generateApiJwtsToken(userDAO.getUsr_username(), sprKeysDAO.getApi_id()));
        sprApiKeysDBService.saveRecord(conn, sprKeysDAO);
        return sprKeysDAO;
    }

    /**
     * Method sets end date to an existing api key and generates a new one
     *
     * @param conn             The database connection to use for the retrieval.
     * @param apiId api identificator
     * @param sessionUsrId session user id
     * @param sessionOrgId organization id of the session user
     * @return newly generated api key
     * @throws Exception If an error occurs during the database retrieval.
     */
    public SprApiKeysDAO regenerateApiKey(Connection conn, Double apiId, Double sessionUsrId, Double sessionOrgId) throws Exception {
        checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        SprApiKeysDAO sprKeysDAO = sprApiKeysDBService.loadRecord(conn, apiId);
        if (sprKeysDAO != null) {
            sprKeysDAO.setApi_date_to(Utils.getDate(new Date()));
        }
        sprApiKeysDBService.saveRecord(conn, sprKeysDAO);
        return this.saveApiKey(conn, sessionUsrId, sessionOrgId);
    }

    private String generateApiJwtsToken(String username, Double apiId) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("apiId", apiId);
        return jwtUtil.generateJwtTokenByClaimsMap(username, claimsMap);
    }

}
