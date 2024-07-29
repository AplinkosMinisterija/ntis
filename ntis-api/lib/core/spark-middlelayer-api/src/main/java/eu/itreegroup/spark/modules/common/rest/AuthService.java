package eu.itreegroup.spark.modules.common.rest;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.s2.server.rest.model.ChangePasswordRequest;
import eu.itreegroup.s2.server.rest.model.LoginRequest;
import eu.itreegroup.s2.server.rest.model.LoginResult;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.SprExternalAuthorization;
import eu.itreegroup.spark.app.model.GoogleLoginRequest;
import eu.itreegroup.spark.app.model.MenuStructure;
import eu.itreegroup.spark.app.model.RequestToken;
import eu.itreegroup.spark.app.model.RoleFormsActions;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.SAPProcessRequestType;
import eu.itreegroup.spark.modules.common.rest.model.ChangePasswordByEmailRequest;
import eu.itreegroup.spark.modules.common.rest.model.ChangePasswordWithToken;
import eu.itreegroup.spark.modules.common.rest.model.EmailVerificationRequest;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@RestController
@RequestMapping("/auth")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AuthService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final SprAuthorization<SprBackendUserSession> sprAuthorization;

    private final SprExternalAuthorization sprExternalAuthorization;

    public AuthService(SprAuthorization<SprBackendUserSession> sprAuthorization, SprExternalAuthorization sprExternalAuthorization) {
        super();
        this.sprAuthorization = sprAuthorization;
        this.sprExternalAuthorization = sprExternalAuthorization;
    }

    @GetMapping(value = "/check-session", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> isSessionValid() throws Exception {
        return okResponse(OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> login(@RequestBody LoginRequest loginRequest) throws SparkBusinessException, Exception {
        SprBackendUserSession userSparkSession = beforeSessionCreated(requestContext);
        userSparkSession = sprAuthorization.createUserSession(this.getDBConnection(), userSparkSession, loginRequest.getUsername(), loginRequest.getPassword(),
                loginRequest.getAuthExtData());
        return okResponse(createLoginResult(new SprBackendWebSessionInfo(userSparkSession), userSparkSession));
    }

    @RequestMapping(value = "/google-login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> googleLogin(@RequestBody GoogleLoginRequest request) throws SparkBusinessException, Exception {
        SprBackendUserSession userSparkSession = beforeSessionCreated(requestContext);
        userSparkSession = sprExternalAuthorization.loginByGoogle(getDBConnection(), userSparkSession, request.getCredential(), request.getAuthExtData());
        return okResponse(createLoginResult(new SprBackendWebSessionInfo(userSparkSession), userSparkSession));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> logout() throws SparkBusinessException, Exception {
        sprAuthorization.logout(this.getDBConnection(), requestContext.getUserSession().getSesKey(), "U");
        return okResponse(null);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkUserRoles() throws Exception {
        return okResponse(sprAuthorization.getRoleList4User(this.getDBConnection(), this.requestContext.getUserSession()));
    }

    @RequestMapping(value = "/organizations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSparkUserOrganizations() throws Exception {
        return okResponse(sprAuthorization.getOrganizationList4User(this.getDBConnection(), this.requestContext.getUserSession()));
    }

    @RequestMapping(value = "/set-role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> setRole(@RequestBody RecordIdentifier recordIdentifier)
            throws SparkBusinessException, Exception {
        sprAuthorization.setUserRole(this.getDBConnection(), recordIdentifier.getId(), requestContext.getUserSession().getSes_org_id());
        SprBackendWebSessionInfo sprBackendWebSessionInfo = new SprBackendWebSessionInfo(requestContext.getUserSession());
        sprBackendWebSessionInfo.setUsrTermsAccepted(requestContext.getUserSession().getSes_terms_accepted());
        return okResponse(createLoginResult(sprBackendWebSessionInfo, requestContext.getUserSession()));
    }

    @RequestMapping(value = "/set-organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> setOrganization(@RequestBody RecordIdentifier recordIdentifier)
            throws SparkBusinessException, Exception {
        sprAuthorization.setUserOrganization(this.getDBConnection(), recordIdentifier.getId());
        SprBackendWebSessionInfo sprBackendWebSessionInfo = new SprBackendWebSessionInfo(requestContext.getUserSession());
        sprBackendWebSessionInfo.setUsrTermsAccepted(requestContext.getUserSession().getSes_terms_accepted());
        return okResponse(createLoginResult(sprBackendWebSessionInfo, requestContext.getUserSession()));
    }

    @RequestMapping(value = "/accept-user-terms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> acceptTerms(@RequestBody String answer) throws SparkBusinessException, Exception {
        sprAuthorization.acceptTerms(this.getDBConnection(), answer);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-role-forms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleFormsActions> getRoleForms() throws SparkBusinessException, Exception {
        RoleFormsActions roleFormActiosn = sprAuthorization.getForms4Role(this.getDBConnection());
        return okResponse(roleFormActiosn);
    }

    @RequestMapping(value = "/get-internal-menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuStructure>> setRoleMenu() throws SparkBusinessException, Exception {
        Double roleId = this.requestContext.getUserSession().getSes_rol_id();
        return okResponse(sprAuthorization.getInternalMenu(this.getDBConnection(), roleId, this.requestContext.getUserSession().getUsr_id(),
                this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-public-menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuStructure>> getPublicRoleMenu(@RequestBody RecordIdentifier recordIdentifier) throws SparkBusinessException, Exception {
        String language = null;
        if (recordIdentifier.getId() != null && !"".equalsIgnoreCase(recordIdentifier.getId())) {
            language = recordIdentifier.getId();
        } else {
            language = Languages.EN.toString();
        }
        return okResponse(sprAuthorization.getPublicMenu(this.getDBConnection(), language));
    }

    @RequestMapping(value = "/change-language", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> changeLanguage(@RequestBody RecordIdentifier recordIdentifier)
            throws SparkBusinessException, Exception {
        sprAuthorization.changeLanguage(this.getDBConnection(), recordIdentifier.getId());
        return okResponse(null);
    }

    @RequestMapping(value = "/request-new-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> request4forgotPassword(@RequestBody ChangePasswordByEmailRequest changePasswordRequest)
            throws SparkBusinessException, Exception {
        sprAuthorization.request4forgotPassword(this.getDBConnection(), changePasswordRequest.getEmail(), changePasswordRequest.getLang());
        return okResponse(null);
    }

    @RequestMapping(value = "/request-create-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> requestForCreatePassword() throws SparkBusinessException, Exception {
        sprAuthorization.requestForCreatePassword(this.getDBConnection(), requestContext.getUserSession().getUsr_id(),
                requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest passwordData) throws SparkBusinessException, Exception {
        sprAuthorization.changePassword(this.getDBConnection(), requestContext.getUserSession().getSes_usr_id(), passwordData.getOldPassword(),
                passwordData.getNewPassword(), this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePasswordByRequest(@RequestBody ChangePasswordWithToken newPasswordData) throws SparkBusinessException, Exception {
        sprAuthorization.changePasswordByRequest(this.getDBConnection(), SAPProcessRequestType.CHANGE_PASSWORD_REQUEST, newPasswordData.getToken(),
                newPasswordData.getPassword(), this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/reset-new-user-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeNewUserPasswordByRequest(@RequestBody ChangePasswordWithToken newPasswordData) throws SparkBusinessException, Exception {
        sprAuthorization.changePasswordByRequest(this.getDBConnection(), SAPProcessRequestType.NEW_USER_REQUEST, newPasswordData.getToken(),
                newPasswordData.getPassword(), this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/create-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewUserPasswordByRequest(@RequestBody ChangePasswordWithToken newPasswordData) throws SparkBusinessException, Exception {
        sprAuthorization.changePasswordByRequest(this.getDBConnection(), SAPProcessRequestType.CREATE_PASSWORD_REQUEST, newPasswordData.getToken(),
                newPasswordData.getPassword(), this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-password-params", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> getPasswordParams(RequestToken requestToken) throws SparkBusinessException, Exception {
        return okResponse(sprAuthorization.getPasswordParameters(this.getDBConnection(), requestToken, true));
    }

    @RequestMapping(value = "/get-user-password-params", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> getPasswordUserParams(RequestToken requestToken) throws SparkBusinessException, Exception {
        return okResponse(sprAuthorization.getPasswordParameters(this.getDBConnection(), requestToken, false));
    }

    @RequestMapping(value = "/check-user-have-password", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkUserHavePassword(RequestToken requestToken) throws SparkBusinessException, Exception {
        return okResponse(sprAuthorization.checkUserHavePassword(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/check-user-has-confirmed-email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkConfirmedEmail() throws Exception {
        return okResponse(sprAuthorization.checkUserHasConfirmedEmail(this.getDBConnection(), this.requestContext.getUserSession().getSes_usr_id()));
    }

    @RequestMapping(value = "/request-confirm-email", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createRequestConfirmEmailRequest(@RequestBody EmailVerificationRequest emailVerificationRequest)
            throws SparkBusinessException, Exception {
        sprAuthorization.request4EmailVerification(this.getDBConnection(), Utils.getDouble(emailVerificationRequest.getUserId()),
                emailVerificationRequest.getEmail(), this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @RequestMapping(value = "/confirm-email", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> confirmEmailRequest(@RequestBody String validateEmailRequest) throws SparkBusinessException, Exception {
        sprAuthorization.validateEmailRequest(this.getDBConnection(), SAPProcessRequestType.CHECK_EMAIL_REQUEST, validateEmailRequest,
                this.requestContext.getUserSession().getSes_language());
        return okResponse(null);
    }

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

}
