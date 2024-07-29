package lt.project.ntis.logic.forms.isense;

import static lt.project.ntis.logic.forms.isense.Isense.MDC_COT_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_DATA_FILE_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_EDOC_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_FIL_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_OAUTH_TOKEN;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_ORG_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_PER_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_SESSION_INFO;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_SESSION_TOKEN;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_USR_ID;
import static lt.project.ntis.logic.forms.isense.IsenseJson.getJsonFieldValue;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import eu.itreegroup.s2.auth.isense.IsenseBaseHttpRestTemplate;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

/**
 * Pagalbinė integracijos su iSense klasė, naudojama komunikacijai su iSense sistema.
 */
@Component
public class IsenseHttpRestTemplate extends IsenseBaseHttpRestTemplate {

    private static final Logger log = LoggerFactory.getLogger(IsenseHttpRestTemplate.class);

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${isense.url}")
    private String isenseUrl;

    @Value("${isense.username}")
    private String isenseUsername;

    @Value("${isense.password}")
    private String isensePassword;

    @Value("${isense.clientId}")
    private String isenseClientId;

    String createNtisToken() throws Exception {
        log.info("Creating OAuth token for cotId:{}; perId:{}; usrId:{}; orgId:{}.", MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID),
                MDC.get(MDC_ORG_ID));

        String url = String.format("%s/api/oauth/token", isenseUrl);
        String requestBody = String.format("grant_type=password&username=%s&password=%s&client_id=%s",
                URLEncoder.encode(isenseUsername, StandardCharsets.UTF_8.name()), URLEncoder.encode(isensePassword, StandardCharsets.UTF_8.name()),
                URLEncoder.encode(isenseClientId, StandardCharsets.UTF_8.name()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_OK, "CreateToken", url);

        String json = response.getBody();
        String token = String.format("%s %s", getJsonFieldValue(json, "token_type"), getJsonFieldValue(json, "access_token"));
        MDC.put(MDC_OAUTH_TOKEN, token);
        log.info("Created OAuth token for cotId:{}; perId:{}; usrId:{}; orgId:{}, token is '{}'.", MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID),
                MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), token);
        return token;
    }

    String uploadDataFile(String ntisToken, SprFilesDAO file, InputStream dataFile) throws Exception {
        log.info("Uploading dataFile for cotId:{}; perId:{}; usrId:{}; orgId:{}; filId:{}.", MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID),
                MDC.get(MDC_ORG_ID), MDC.get(MDC_FIL_ID));

        String url = String.format("%s/e-documents/v1/datafiles", isenseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", ntisToken);

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition.builder("form-data").name("file").filename(file.getFil_name()).build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(dataFile.readAllBytes(), fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_CREATED, "UploadDataFile", url);

        String dataFileId = getJsonFieldValue(response.getBody(), "datafileId");
        MDC.put(MDC_DATA_FILE_ID, dataFileId);
        log.info("Uploaded dataFile for cotId:{}; perId:{}; usrId:{}; orgId:{}; filId:{}, assigned dataFileId is '{}'.", MDC.get(MDC_COT_ID),
                MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_FIL_ID), dataFileId);
        return dataFileId;
    }

    String createEdoc(String ntisToken, String dataFileId, String requestBody) throws Exception {
        log.info("Creating edoc for cotId:{}; perId:{}; usrId:{}; orgId:{} with dataFileId:'{}' with request body {}.", MDC.get(MDC_COT_ID),
                MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), dataFileId, requestBody);

        String url = String.format("%s/e-documents/v1/edocs", isenseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", ntisToken);

        HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_CREATED, "CreateEdoc", url);

        String edocId = getJsonFieldValue(response.getBody(), "edocId");
        MDC.put(MDC_EDOC_ID, edocId);
        log.info("Created edoc for cotId:{}; perId:{}; usrId:{}; orgId:{} with dataFileId:'{}', assigned edocId is '{}'.", MDC.get(MDC_COT_ID),
                MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), dataFileId, edocId);
        return edocId;
    }

    InputStream downloadEdoc(String ntisToken, String edocId) throws Exception {
        log.info("Downloading edoc '{}' with OAuthToken:'{}'; sessionToken:'{}'.", edocId, MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_SESSION_TOKEN));

        String url = String.format("%s/e-documents/v1/edocs/%s", isenseUrl, edocId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", ntisToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Resource> response = getRestTemplate().exchange(url, HttpMethod.GET, request, Resource.class);
        checkStatusCode(response, HttpURLConnection.HTTP_OK, "DownloadEdoc", url);

        log.info("Edoc '{}' downloaded with OAuthToken:'{}'; sessionToken:'{}'.", edocId, MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_SESSION_TOKEN));
        return response.getBody().getInputStream();
    }

    String uploadEdoc(String ntisToken, InputStream edoc) throws Exception {
        log.info("Uploading edoc {} for cotId:{}; perId:{}; usrId:{}; orgId:{} with OAuthToken:'{}'.", MDC.get(MDC_FIL_ID), MDC.get(MDC_OAUTH_TOKEN),
                MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID));

        String url = String.format("%s/e-documents/v1/edocs/load", isenseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", ntisToken);

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition.builder("form-data").name("file").filename("Sutartis.adoc").build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(edoc.readAllBytes(), fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_CREATED, "UploadEdoc", url);

        String edocId = getJsonFieldValue(response.getBody(), "edocId");
        MDC.put(MDC_EDOC_ID, edocId);
        log.info("Uploaded edoc {} for cotId:{}; perId:{}; usrId:{}; orgId:{} with OAuthToken:'{}', assigned edocId is '{}'.", MDC.get(MDC_FIL_ID),
                MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), edocId);
        return edocId;
    }

    String doSigning(String ntisToken, String requestBody) throws Exception {
        log.info("Starting signing for cotId:{}; perId:{}; usrId:{}; orgId:{} with OAuthToken:'{}'; dataFileId:'{}'; edocId:'{}'.", MDC.get(MDC_COT_ID),
                MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_DATA_FILE_ID), MDC.get(MDC_EDOC_ID));

        String url = String.format("%s/e-documents/v1/sessions/signer", isenseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", ntisToken);

        HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_CREATED, "StartSigning", url);

        String sessionToken = getJsonFieldValue(response.getBody(), "sessionToken");
        MDC.put(MDC_SESSION_TOKEN, sessionToken);
        log.info(
                "Started signing for cotId:{}; perId:{}; usrId:{}; orgId:{} with OAuthToken:'{}'; dataFileId:'{}'; edocId:'{}', assigned sessionToken is '{}'.",
                MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_DATA_FILE_ID),
                MDC.get(MDC_EDOC_ID), sessionToken);
        return sessionToken;
    }

    String doViewing(String ntisToken, String requestBody) throws Exception {
        log.info("Starting preview for cotId:{}; perId:{}; usrId:{}; orgId:{} with OAuthToken:'{}'; edocId:'{}'.", MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID),
                MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_EDOC_ID));

        String url = String.format("%s/e-documents/v1/sessions/viewer", isenseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", ntisToken);

        HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_CREATED, "StartViewing", url);

        String sessionToken = getJsonFieldValue(response.getBody(), "sessionToken");
        MDC.put(MDC_SESSION_TOKEN, sessionToken);
        log.info("Started preview for cotId:{}; perId:{}; usrId:{}; orgId:{} with OAuthToken:'{}'; edocId:'{}', assigned sessionToken is '{}'.",
                MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_EDOC_ID),
                sessionToken);
        return sessionToken;
    }

    void deleteSession(String ntisToken, String sessionToken) throws Exception {
        log.info("Deleting session '{}' with OAuthToken:'{}'; cotId:{}; filId:{}.", sessionToken, MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_COT_ID),
                MDC.get(MDC_FIL_ID));

        String url = String.format("%s/e-documents/v1/sessions/%s?deleteAssociatedFiles=true", isenseUrl, sessionToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ntisToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Void> response = getRestTemplate().exchange(url, HttpMethod.DELETE, request, Void.class);
        checkStatusCode(response, HttpURLConnection.HTTP_NO_CONTENT, "DeleteSession", url);

        log.info("Session '{}' deleted with OAuthToken:'{}'; cotId:{}; filId:{}.", sessionToken, MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_COT_ID),
                MDC.get(MDC_FIL_ID));
    }

    String getSessionInfo(String ntisToken, String sessionToken) throws Exception {
        String url = String.format("%s/e-documents/v1/sessions/%s", isenseUrl, sessionToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ntisToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        checkStatusCode(response, HttpURLConnection.HTTP_OK, "GetSessionInfo", url);

        log.info("Session info downloaded with OAuthToken:'{}'; sessionToken:'{}'.", MDC.get(MDC_OAUTH_TOKEN), MDC.get(MDC_SESSION_TOKEN));
        String sessionInfo = response.getBody();
        MDC.put(MDC_SESSION_INFO, sessionInfo);
        return sessionInfo;
    }

    private void checkStatusCode(ResponseEntity<?> response, int expectedStatusCode, String actionName, String url) throws Exception {
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.value() != expectedStatusCode) {
            throw new Exception(String.format("%s returned with response code %d (expected %d); url: %s", actionName, statusCode, expectedStatusCode, url));
        }
    }

    @Override
    protected boolean isTestProfile() {
        return "test".equalsIgnoreCase(activeProfile);
    }

}
