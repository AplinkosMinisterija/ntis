package lt.project.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.auth.isense.IsenseAuthClient;
import eu.itreegroup.s2.auth.isense.IsenseAuthData;
import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.s2.server.rest.model.LoginResult;
import eu.itreegroup.spark.app.SprExternalAuthorization;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.service.SprPropertiesDBServiceImpl;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.logic.forms.model.NtisIsenseAuthModel;

/**
 * Integracijos su iSense e-identification posisteme klasė, apdorojanti naudotojo agento užklausas susijusias su iSense identifikacija.
 * 
 */
@RestController
@RequestMapping("/auth/isense")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IsenseAuthService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    private static final Logger log = LoggerFactory.getLogger(IsenseAuthService.class);

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${isense.url:}")
    private String isenseUrl;

    @Autowired
    private DBPropertyManager dbPropertyManager;

    @Autowired
    private IsenseAuthClient isenseClient;

    @Autowired
    private SprExternalAuthorization sprExternalAuthorization;

    /**
     * Pradeda naudotojo identifikacijos sesiją iSense sistemoje.
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/start-isense-auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisIsenseAuthModel> startIsenseAuth(@RequestBody IsenseAuthRequest request) throws Exception {

        ResponseEntity<NtisIsenseAuthModel> response = null;
        if (!isIdentificationEnabled()) {
            response = ResponseEntity.notFound().build();
        } else {
            try {
                String iSenseSessionToken = isenseClient.startIdentificationSession();
                NtisIsenseAuthModel authData = new NtisIsenseAuthModel();
                authData.setToken(iSenseSessionToken);
                authData.setHost(isenseUrl);
                response = okResponse(authData);

            } catch (Exception e) {
                response = handleException("Failed to start iSense identification session.", e);
            }
        }
        return response;
    }

    /**
     * Gauna naudotojo identifikacijos duomenis iš iSense sistemos ir sukuria db sesiją.
     * @param isenseSessionToken
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/complete-isense-auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult<SprBackendWebSessionInfo>> completeIsenseAuth(@RequestParam("isenseSessionToken") String isenseSessionToken)
            throws Exception {

        ResponseEntity<LoginResult<SprBackendWebSessionInfo>> response = null;
        if (!isIdentificationEnabled()) {
            response = ResponseEntity.notFound().build();
        } else {
            try {
                SprBackendUserSession userSparkSession = beforeSessionCreated(requestContext);
                IsenseAuthData iSenseAuthData = isenseClient.getIdentificationSessionInfo(isenseSessionToken);

                deleteIsenseSession(isenseSessionToken);

                Map<String, Object> authExtData = new HashMap<>();
                authExtData.put("AUTH_TYPE", "ISENSE_AUTH");
                authExtData.put("USER_TYPE", "PRIVATE");
                userSparkSession = sprExternalAuthorization.createOtherExternalUserSession(this.getDBConnection(), userSparkSession,
                        iSenseAuthData.personName(), iSenseAuthData.personSurname(), iSenseAuthData.personCode(), authExtData);

                response = okResponse(createLoginResult(new SprBackendWebSessionInfo(userSparkSession), userSparkSession));

            } catch (Exception e) {
                response = handleException("Failed to retrieve iSense identification session information.", e);
            }
        }
        return response;
    }

    private void deleteIsenseSession(String isenseSessionToken) {
        try {
            isenseClient.deleteIdentificationSession(isenseSessionToken);

        } catch (Exception e) {
            log.error("Failed to delete iSense identification session.", e);
        }
    }

    private boolean isIdentificationEnabled() throws Exception {
        return YesNo.getEnumByCode(dbPropertyManager.getPropertyByName(SprPropertiesDBServiceImpl.IS_IDENTIFICATION_ENABLED, YesNo.Y.getCode())).getBoolean();
    }

    protected <T> ResponseEntity<T> handleException(String message, Exception e) {
        log.error(message, e);
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

}
