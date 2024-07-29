package eu.itreegroup.spark.app;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.EidType;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrgAvailableRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUserExtIdentificationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgAvailableRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUserExtIdentificationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

public class SprExternalAuthorization {

    private static final Logger log = LoggerFactory.getLogger(SprExternalAuthorization.class);

    protected final SprUserExtIdentificationsDBService sprUserExtIdentificationsDBService;

    protected final SprUsersDBService sprUsersDBService;

    protected final SprPersonsDBService sprPersonsDBService;

    protected final SprRolesDBService sprRolesDBService;

    protected final DBPropertyManager dBPropertyManager;

    protected final SprUserRolesDBService sprUserRolesDBService;

    protected final SprAuthorization<SprBackendUserSession> sprAuthorization;

    protected final SprOrganizationsDBService sprOrganizationsDBService;

    protected final SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService;

    protected final SprOrgUserRolesDBService sprOrgUserRolesDBService;

    protected final SprOrgUsersDBService sprOrgUsersDBService;

    protected final BaseControllerJDBC baseControllerJDBC;

    @Value("${app.default.language}")
    private String defaultLanguage;

    public SprExternalAuthorization(SprUserExtIdentificationsDBService sprUserExtIdentificationsDBService, //
            SprUsersDBService sprUsersDBService, //
            SprRolesDBService sprRolesDBService, //
            SprUserRolesDBService sprUserRolesDBService, //
            DBPropertyManager dBPropertyManager, //
            SprAuthorization<SprBackendUserSession> sprAuthorization, //
            BaseControllerJDBC baseControllerJDBC, //
            SprPersonsDBService sprPersonsDBService, //
            SprOrganizationsDBService sprOrganizationsDBService, //
            SprOrgUsersDBService sprOrgUsersDBService, //
            SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService, //
            SprOrgUserRolesDBService sprOrgUserRolesDBService) {
        super();
        this.sprUserExtIdentificationsDBService = sprUserExtIdentificationsDBService;
        this.sprUsersDBService = sprUsersDBService;
        this.sprRolesDBService = sprRolesDBService;
        this.sprUserRolesDBService = sprUserRolesDBService;
        this.dBPropertyManager = dBPropertyManager;
        this.sprAuthorization = sprAuthorization;
        this.baseControllerJDBC = baseControllerJDBC;
        this.sprPersonsDBService = sprPersonsDBService;
        this.sprOrganizationsDBService = sprOrganizationsDBService;
        this.sprOrgUsersDBService = sprOrgUsersDBService;
        this.sprOrgAvailableRolesDBService = sprOrgAvailableRolesDBService;
        this.sprOrgUserRolesDBService = sprOrgUserRolesDBService;
    }

    @Deprecated
    public SprBackendUserSession loginByGoogle(Connection conn, SprBackendUserSession backendUserSession, String credential)
            throws Exception, SparkBusinessException {
        return loginByGoogle(conn, backendUserSession, credential, null);
    }

    public SprBackendUserSession loginByGoogle(Connection conn, SprBackendUserSession backendUserSession, String credential, Map<String, Object> authExtData)
            throws Exception, SparkBusinessException {

        String googleClientId = dBPropertyManager.getPropertyByName("GOOGLE_AUTH_CLIENT_ID", null);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                // Or, if multiple clients access the backend: .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(credential);
        } catch (Exception e) {
            throw new SparkBusinessException(
                    new S2Message("pwd.wrongCredentials", SparkMessageType.ERROR, "Sorry, your credentials are incorrect. Please try again."));
        }

        Payload payload = idToken.getPayload();

        log.debug("loginByGoogle: \n" + payload.toPrettyString());

        String userId = payload.getSubject();
        String email = payload.getEmail();
        Boolean emailVerified = payload.getEmailVerified();
        // String name = (String) payload.get("name");
        // String pictureUrl = (String) payload.get("picture");
        // String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        if (!emailVerified) {
            log.debug("loginByGoogle: Google account " + email + " (user id: " + userId + ") is not verified.");
            throw new SparkBusinessException(new S2Message("common.error.unexpected", SparkMessageType.ERROR,
                    "Sorry, something went wrong. Try again or contact the system administrator."));
        }

        SprUsersDAO usersDAO = null;
        try {
            usersDAO = this.findUser(conn, EidType.GOOGLE, userId);
        } catch (Exception exception) {
            log.debug(exception.getMessage());
        }

        if (usersDAO == null) {
            try {
                usersDAO = sprUsersDBService.loadRecordByEmail(conn, email);
            } catch (Exception exception) {
                log.debug(exception.getMessage());
            }

            if (usersDAO != null) {
                if (usersDAO.getUsr_email_confirmed() == null || !usersDAO.getUsr_email_confirmed().equals(DbConstants.BOOLEAN_TRUE)) {
                    usersDAO = null;
                    throw new SparkBusinessException(new S2Message("common.error.emailNotConfirmed", SparkMessageType.ERROR, "Email is not confirmed!"));
                }
            } else {
                SprPersonsDAO personDAO = sprPersonsDBService.loadRecordByParams(conn, "where per_name = ? and per_surname = ? and per_email = ? ",
                        new SelectParamValue(givenName), new SelectParamValue(familyName), new SelectParamValue(email));
                if (personDAO == null) {
                    personDAO = createNewPerson(conn, givenName, familyName, null, email, emailVerified, null);
                }
                usersDAO = this.createUser(conn, EidType.GOOGLE, userId, email, email, emailVerified, givenName, familyName, personDAO, null);
            }

        }
        return sprAuthorization.createUserSession(conn, backendUserSession, usersDAO, SprAuthorization.EXTERNAL_AUTHENTICATION);
    }

    public SprBackendUserSession createVIISPUserSession(Connection conn, SprBackendUserSession userSession, String name, String lastname, String personCode)
            throws Exception {
        return createVIISPUserSession(conn, userSession, name, lastname, personCode, null);
    }

    public SprBackendUserSession createVIISPUserSession(Connection conn, SprBackendUserSession userSession, String name, String lastname, String personCode,
            Map<String, Object> authExtData) throws Exception {
        return createExternalUserSession(conn, EidType.VIISP, userSession, name, lastname, personCode, authExtData);
    }

    public SprBackendUserSession createOtherExternalUserSession(Connection conn, SprBackendUserSession userSession, String name, String lastname,
            String personCode, Map<String, Object> authExtData) throws Exception {
        return createExternalUserSession(conn, EidType.OTHER_EXTERNAL, userSession, name, lastname, personCode, authExtData);
    }

    public SprBackendUserSession createExternalUserSession(Connection conn, EidType eidType, SprBackendUserSession userSession, String name, String lastname,
            String personCode, Map<String, Object> authExtData) throws Exception {
        SprUsersDAO sprUserDAO = null;
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select per_name, " + //
                "                 per_surname, " + //
                "                 per_id " + //
                "  from SPR_PERSONS PER\r\n " + //
                "  where PER.PER_CODE = ? ");
        stmt.setWhereExists(true);
        stmt.addSelectParam(personCode);
        stmt.setStatementOrderPart(" per_id DESC ");
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        String perName = null;
        String perSurName = null;
        String perId = null;
        SprPersonsDAO person = null;
        if (data.size() != 0) {
            HashMap<String, String> rec = data.get(0);
            perName = rec.get("per_name");
            perSurName = rec.get("per_surname");
            perId = rec.get("per_id");
            person = sprPersonsDBService.loadRecord(conn, Utils.getDouble(perId));
            if ((perName == null || !perName.equals(name)) || (perSurName == null || !perSurName.equals(lastname))) {
                person.setPer_name(name);
                person.setPer_surname(lastname);
                person = sprPersonsDBService.saveRecord(conn, person);
            }
            sprUserDAO = sprUsersDBService.loadRecordByIdentifier(conn, person.getPer_id(), authExtData);
            if (sprUserDAO != null && authExtData.get("LANGUAGE") != null && !((String) authExtData.get("LANGUAGE")).equals(sprUserDAO.getUsr_language())) {
                sprUserDAO.setUsr_language((String) authExtData.get("LANGUAGE"));
                sprUserDAO = sprUsersDBService.saveRecord(conn, sprUserDAO);
            }
            if (sprUserDAO != null && ((sprUserDAO.getUsr_person_name() == null || !sprUserDAO.getUsr_person_name().equals(name))
                    || (sprUserDAO.getUsr_person_surname() == null || !sprUserDAO.getUsr_person_surname().equals(lastname)))) {
                sprUserDAO.setUsr_person_name(name);
                sprUserDAO.setUsr_person_surname(lastname);
                sprUserDAO = sprUsersDBService.saveRecord(conn, sprUserDAO);
            }
        }

        if (sprUserDAO == null) {
            if (person == null) {
                person = createNewPerson(conn, name, lastname, personCode, null, false, authExtData);
            }
            String userName = ((Utils.replaceNationalCharacters(name.substring(0, Math.min(name.length(), 80))) + "."
                    + Utils.replaceNationalCharacters(lastname.substring(0, Math.min(lastname.length(), 80)))).replaceAll("[^a-zA-Z-\\.]", "") + "."
                    + person.getPer_id().intValue()).toLowerCase();
            List<SprUsersDAO> usrNameCheck = this.sprUsersDBService.loadRecordsByParams(conn, "usr_username like ? order by usr_id desc",
                    new SelectParamValue(userName + "%"));
            if (usrNameCheck != null && !usrNameCheck.isEmpty()) {
                String lastUserName = usrNameCheck.get(0).getUsr_username();
                userName = lastUserName + "1";
            }
            sprUserDAO = createUser(conn, eidType, null, userName, person.getPer_email(), false, name, lastname, person, authExtData);
        }
        return sprAuthorization.createUserSession(conn, userSession, sprUserDAO, SprAuthorization.EXTERNAL_AUTHENTICATION);
    }

    /**
     * Create user session for VIISP authenticated user. Session will be created for user that was authenticated as responsible person of organization.
     * In case if such user and organization do not exists in system they will be created. For new created user will be added role that are assigned to the
     * parameter by name DEFAULT_NEW_ORG_ROLE_CODE
     * @param conn - connection to the db
     * @param userSession - back end user session
     * @param name - user first name
     * @param lastname - user last name
     * @param personCode - user person code
     * @param orgName - organization name
     * @param orgCode - organization code
     * @return back end session for provided data.
     * @throws Exception
     */
    @Deprecated
    public SprBackendUserSession createVIISPOrganizationUserSession(Connection conn, SprBackendUserSession userSession, String name, String lastname,
            String personCode, String orgName, String orgCode) throws Exception {
        return createVIISPOrganizationUserSession(conn, userSession, name, lastname, personCode, orgName, orgCode, null);
    }

    /**
     * Create user session for VIISP authenticated user. Session will be created for user that was authenticated as responsible person of organization.
     * In case if such user and organization do not exists in system they will be created. For new created user will be added role that are assigned to the
     * parameter by name DEFAULT_NEW_ORG_ROLE_CODE
     * @param conn - connection to the db
     * @param userSession - back end user session
     * @param name - user first name
     * @param lastname - user last name
     * @param personCode - user person code
     * @param orgName - organization name
     * @param orgCode - organization code
     * @param authExtData - external authorization data that will be provided from front end.
     * @return back end session for provided data.
     * @throws Exception
     */
    public SprBackendUserSession createVIISPOrganizationUserSession(Connection conn, SprBackendUserSession userSession, String name, String lastname,
            String personCode, String orgName, String orgCode, Map<String, Object> authExtData) throws Exception {
        SprBackendUserSession localUserSession = createVIISPUserSession(conn, userSession, name, lastname, personCode, authExtData);
        SprUsersDAO userDAO = sprUsersDBService.loadRecord(conn, localUserSession.getSes_usr_id());
        SprOrganizationsDAO organizationDAO = sprOrganizationsDBService.loadRecordByParams(conn, "org_code = ? and org_name = ? ",
                new SelectParamValue(orgCode), new SelectParamValue(orgName));
        SprRolesDAO roleDAO = null;
        if (organizationDAO == null) {
            organizationDAO = sprOrganizationsDBService.newRecord();
            organizationDAO.setOrg_name(orgName);
            organizationDAO.setOrg_code(orgCode);
            organizationDAO.setOrg_date_from(Utils.getDate());
            organizationDAO = sprOrganizationsDBService.saveRecord(conn, organizationDAO);
            roleDAO = loadDefaultRoleData(conn, "DEFAULT_NEW_ORG_ROLE_CODE");
            if (roleDAO != null) {
                SprOrgAvailableRolesDAO availableRole = sprOrgAvailableRolesDBService.newRecord();
                availableRole.setOar_org_id(organizationDAO.getOrg_id());
                availableRole.setOar_rol_id(roleDAO.getRol_id());
                availableRole.setOar_date_from(Utils.getDate());
                availableRole = sprOrgAvailableRolesDBService.saveRecord(conn, availableRole);
            }
        } else {
            if (userDAO.getUsr_org_id() != null && userDAO.getUsr_org_id().doubleValue() == organizationDAO.getOrg_id().doubleValue()) {
                return localUserSession;
            }
        }
        SprOrgUsersDAO orgUser = sprOrgUsersDBService.loadRecordByParams(conn, "ou_org_id = ? and ou_usr_id = ? ",
                new SelectParamValue(organizationDAO.getOrg_id()), new SelectParamValue(userDAO.getUsr_id()));
        if (orgUser == null) {
            orgUser = sprOrgUsersDBService.newRecord();
            orgUser.setOu_org_id(organizationDAO.getOrg_id());
            orgUser.setOu_usr_id(userDAO.getUsr_id());
            orgUser.setOu_date_from(Utils.getDate());
            orgUser = sprOrgUsersDBService.saveRecord(conn, orgUser);
            if (roleDAO != null) {
                SprOrgUserRolesDAO orgUserRole = sprOrgUserRolesDBService.newRecord();
                orgUserRole.setOur_ou_id(orgUser.getOu_id());
                orgUserRole.setOur_rol_id(roleDAO.getRol_id());
                orgUserRole.setOur_date_from(Utils.getDate());
                orgUserRole = sprOrgUserRolesDBService.saveRecord(conn, orgUserRole);
            }
        }
        sprAuthorization.setUserOrganization(conn, organizationDAO.getOrg_id().toString());
        return sprAuthorization.getUserSessionFromConetext();

    }

    protected SprUsersDAO findUser(Connection conn, EidType identificationType, String identificationCode) throws Exception {
        Double userId = sprUserExtIdentificationsDBService.getUserId(conn, identificationType, identificationCode);
        if (userId != null) {
            return sprUsersDBService.loadRecord(conn, userId);
        }
        log.info(
                "findUser: User with identificationType " + identificationType.getValue() + " and identificationCode " + identificationCode + " was not found");
        return null;
    }

    protected SprPersonsDAO createNewPerson(Connection conn, String name, String lastname, String personCode, String email, boolean emailConfirmed,
            Map<String, Object> authExtData) throws Exception {
        SprPersonsDAO sprPersonsDAO = sprPersonsDBService.newRecord();
        sprPersonsDAO.setPer_name(name);
        sprPersonsDAO.setPer_surname(lastname);
        sprPersonsDAO.setPer_code_exists("Y");
        sprPersonsDAO.setPer_code(personCode);
        sprPersonsDAO.setPer_lrt_resident("Y");
        String sDate1 = "01/01/1900";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormatter.parse(sDate1);
        sprPersonsDAO.setPer_date_of_birth(date1);
        sprPersonsDAO.setPer_email(email);
        sprPersonsDAO.setPer_email_confirmed(emailConfirmed ? YesNo.Y.getCode() : YesNo.N.getCode());
        sprPersonsDAO = sprPersonsDBService.saveRecord(conn, sprPersonsDAO);
        return sprPersonsDAO;
    }

    protected SprUsersDAO createUser(Connection conn, EidType identificationType, String identificationCode, String username, String email,
            Boolean emailConfirmed, String name, String surname, SprPersonsDAO personDAO, Map<String, Object> authExtData) throws Exception {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFrom = dateFormatter.parse(dateFormatter.format(new Date()));

        SprUsersDAO usersDAO = sprUsersDBService.newRecord();
        usersDAO.setUsr_email(email);
        usersDAO.setUsr_email_confirmed(emailConfirmed ? DbConstants.BOOLEAN_TRUE : DbConstants.BOOLEAN_FALSE);
        usersDAO.setUsr_person_name(name);
        usersDAO.setUsr_person_surname(surname);
        String userType = "EXTERNAL";
        String userLang = defaultLanguage;
        if (authExtData != null) {
            if (authExtData.get("USER_TYPE") != null) {
                userType = (String) authExtData.get("USER_TYPE");
            }
            if (authExtData.get("LANGUAGE") != null) {
                userLang = (String) authExtData.get("LANGUAGE");
            }
        }
        usersDAO.setUsr_type(userType);
        usersDAO.setUsr_language(userLang);
        if (identificationType == EidType.VIISP || identificationType == EidType.OTHER_EXTERNAL) {
            usersDAO.setUsr_username(username);
        } else {
            usersDAO.setUsr_username(email);
        }
        usersDAO.setUsr_date_from(dateFrom);
        if (personDAO != null) {
            usersDAO.setUsr_per_id(personDAO.getPer_id());
        }
        SprUsersDAO insertedUsersDAO = sprUsersDBService.insertRecord(conn, usersDAO);
        if (identificationType != EidType.VIISP && identificationType != EidType.OTHER_EXTERNAL) {
            SprUserExtIdentificationsDAO userExtIdentificationsDAO = sprUserExtIdentificationsDBService.newRecord();
            userExtIdentificationsDAO.setEid_type(identificationType.getValue());
            userExtIdentificationsDAO.setEid_token(identificationCode);
            userExtIdentificationsDAO.setEid_usr_id(insertedUsersDAO.getUsr_id());
            sprUserExtIdentificationsDBService.insertRecord(conn, userExtIdentificationsDAO);
        }
        return insertedUsersDAO;
    }

    protected SprRolesDAO loadDefaultRoleData(Connection conn, String parameter4DefaultRoleName) throws Exception {
        String defaultRoleCode = dBPropertyManager.getPropertyByName(parameter4DefaultRoleName, null);
        log.debug("createUser: " + parameter4DefaultRoleName + ": " + defaultRoleCode);
        SprRolesDAO rolesDAO = null;
        if (defaultRoleCode != null) {
            try {
                rolesDAO = sprRolesDBService.loadRecordByRoleCode(conn, defaultRoleCode);
            } catch (Exception ex) {
                log.error("Role by name '" + defaultRoleCode + "' not found ", ex);
            }
        }
        return rolesDAO;
    }
}
