package lt.project.rest;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.s2.server.rest.model.LoginResult;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.logic.auth.NtisTwoFactorHandler;

// 2FA antras žingsnis. Atskiro login endpoint'o čia NĖRA sąmoningai — kad nebūtų 2FA apėjimo;
// pirmas žingsnis vyksta framework /auth/login per SprAuthorization + NtisTwoFactorHandler.
@RestController
@RequestMapping("/auth")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TwoFactorAuthService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    private SprAuthorization sprAuthorization;

    @Autowired
    private NtisTwoFactorHandler twoFactorHandler;

    @Autowired
    private SprUsersDBService sprUsersDBService;

    @PostMapping(value = "/verify-2fa", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> verify2fa(@RequestBody Verify2faRequest req) throws Exception {
        Connection conn = this.getDBConnection();
        SprUsersDAO userDAO = sprUsersDBService.loadRecordByIdentifier(conn, req.getUsername(), req.getAuthExtData());
        requireUser(userDAO);

        twoFactorHandler.verifyAndConsume(conn, userDAO, req.getToken(), req.getCode());

        // Kodą patvirtinus — užbaigiam prisijungimą be pakartotinio slaptažodžio
        SprBackendUserSession session = beforeSessionCreated(requestContext);
        session = sprAuthorization.createUserSession(conn, session, userDAO, SprAuthorization.USER_NAME_PASSWORD_AUTHENTICATION);
        return okResponse(createLoginResult(new SprBackendWebSessionInfo(session), session));
    }

    @PostMapping(value = "/resend-2fa", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resend2fa(@RequestBody Resend2faRequest req) throws Exception {
        Connection conn = this.getDBConnection();
        SprUsersDAO userDAO = sprUsersDBService.loadRecordByIdentifier(conn, req.getUsername(), req.getAuthExtData());
        requireUser(userDAO);
        twoFactorHandler.resendCode(conn, userDAO, req.getToken());
        return okResponse(null);
    }

    // Nežinomas username → tas pats bendras iššūkis (neatskleidžia, ar naudotojas egzistuoja) vietoj NPE/500
    private void requireUser(SprUsersDAO userDAO) throws Exception {
        if (userDAO == null) {
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.noChallenge", SparkMessageType.ERROR,
                    "No active 2FA challenge"));
        }
    }

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }
}
