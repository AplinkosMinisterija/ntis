package eu.itreegroup.spark.modules.common.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.auth.viisp.ViispAuthClient;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthData;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthRequest;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthResult;
import eu.itreegroup.s2.auth.viisp.model.ViispMockAuthRequest;
import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.s2.server.rest.model.LoginResult;
import eu.itreegroup.spark.app.SprExternalAuthorization;
import eu.itreegroup.spark.modules.common.model.PayloadWrapper;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@RestController
@RequestMapping("/auth/viisp")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ViispAuthService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    private static final String TICKET_PREFIX_TEST = "TEST-";

    private static final String TICKET_PREFIX_MOCK = "MOCK-";

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${viisp.login.enabled:false}")
    private boolean viispLoginEnabled;

    @Autowired(required = false)
    @Qualifier("viispMockClient")
    private ViispAuthClient viispMockClient;

    @Autowired(required = false)
    @Qualifier("viispAuthClient")
    @Lazy
    private ViispAuthClient viispClient;

    @Autowired(required = false)
    @Qualifier("viispAuthClientTest")
    @Lazy
    private ViispAuthClient viispClientTest;

    @Autowired
    private SprExternalAuthorization sprExternalAuthorization;

    @RequestMapping(value = "/init-viisp-auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ViispAuthResult> initViisp(@RequestBody ViispAuthRequest request) throws Exception {
        if (!viispLoginEnabled) {
            return ResponseEntity.notFound().build();
        }
        ViispAuthResult ticket = viispClient.initAuth(request);
        return okResponse(ticket);
    }

    @RequestMapping(value = "/finalize-viisp-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ViispAuthData> finalizeViisp(@RequestParam("ticket") String ticket) throws Exception {
        if (!viispLoginEnabled) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("location", viispClient.getConfig().getAppSuccessUrl() + "?ticket=" + ticket)
                .body(null);
    }

    @RequestMapping(value = "/init-test-viisp-auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ViispAuthResult> initTestViisp(@RequestBody ViispAuthRequest request) throws Exception {
        if (!viispLoginEnabled || !"test".equalsIgnoreCase(activeProfile)) {
            return ResponseEntity.notFound().build();
        }
        ViispAuthResult ticket = viispClientTest.initAuth(request);
        return okResponse(ticket);
    }

    @RequestMapping(value = "/finalize-test-viisp-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ViispAuthData> finalizeTestViisp(@RequestParam("ticket") String ticket) throws Exception {
        if (!viispLoginEnabled || !"test".equalsIgnoreCase(activeProfile)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("location", viispClientTest.getConfig().getAppSuccessUrl() + "?ticket=" + TICKET_PREFIX_TEST + ticket).body(null);
    }

    @RequestMapping(value = "/init-mock-viisp-auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ViispAuthResult> initMockViisp(@RequestBody ViispMockAuthRequest request) throws Exception {
        if (!viispLoginEnabled || !"test".equalsIgnoreCase(activeProfile)) {
            return ResponseEntity.notFound().build();
        }
        ViispAuthResult result = viispMockClient.initAuth(request);
        log.debug("result={}", result);
        return okResponse(result);
    }

    @RequestMapping(value = "/finalize-mock-viisp-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ViispAuthData> finalizeMockViisp(@RequestParam("ticket") String ticket) throws Exception {
        if (!viispLoginEnabled || !"test".equalsIgnoreCase(activeProfile)) {
            return ResponseEntity.notFound().build();
        }
        // only when test mode enabled, to allow working with different hostnames
        ViispAuthData viispAuthData = viispMockClient.getAuthDataByTicket(ticket);
        String redirectUrl = "test".equalsIgnoreCase(activeProfile) ? viispAuthData.getOrigin() : "";
        redirectUrl = redirectUrl.concat(viispMockClient.getConfig().getAppSuccessUrl() + "?ticket=" + TICKET_PREFIX_MOCK + ticket);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("location", redirectUrl).body(null);
    }

    @PostMapping(value = "/create-session-viisp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> createSessionViisp(@RequestBody PayloadWrapper<String> ticket) throws Exception {
        if (!viispLoginEnabled) {
            return ResponseEntity.notFound().build();
        }
        log.debug("Viisp ticket={}", ticket);
        ViispAuthData viispAuthData = getViispAuthDataByTicket(ticket.get());
        log.debug("viispauthData={}", viispAuthData);
        SprBackendUserSession userSparkSession = beforeSessionCreated(requestContext);

        if (viispAuthData == null) {
            throw new SparkBusinessException(
                    new S2Message("pwd.wrongCredentials", SparkMessageType.ERROR, "Sorry, your credentials are incorrect. Please try again."));
        }

        if (viispAuthData.getCompanyCode() != null && !viispAuthData.getCompanyCode().isEmpty()) {
            sprExternalAuthorization.createVIISPOrganizationUserSession(this.getDBConnection(), userSparkSession, viispAuthData.getFirstName(),
                    viispAuthData.getLastName(), viispAuthData.getPersonCode(), viispAuthData.getCompanyName(), viispAuthData.getCompanyCode(),
                    viispAuthData.getCustomData());
        } else {
            userSparkSession = sprExternalAuthorization.createVIISPUserSession(this.getDBConnection(), userSparkSession, viispAuthData.getFirstName(),
                    viispAuthData.getLastName(), viispAuthData.getPersonCode(), viispAuthData.getCustomData());
        }
        if (viispAuthData.getCustomData() != null) {
            userSparkSession.setDefaultRoute((String) viispAuthData.getCustomData().get("RETURN_URL"));
        }
        return okResponse(createLoginResult(new SprBackendWebSessionInfo(userSparkSession), userSparkSession));
    }

    protected ViispAuthData getViispAuthDataByTicket(String ticket) throws Exception {
        ViispAuthClient client = viispClient;
        String theTicket = ticket;
        if (ticket.startsWith(TICKET_PREFIX_MOCK)) {
            client = viispMockClient;
            theTicket = ticket.replaceFirst("^" + TICKET_PREFIX_MOCK, "");
        } else if (ticket.startsWith(TICKET_PREFIX_TEST)) {
            client = viispClientTest;
            theTicket = ticket.replaceFirst("^" + TICKET_PREFIX_TEST, "");
        }
        return client.getAuthDataByTicket(theTicket);
    }

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

}
