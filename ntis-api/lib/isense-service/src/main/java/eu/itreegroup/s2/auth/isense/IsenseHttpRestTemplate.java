package eu.itreegroup.s2.auth.isense;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import eu.itreegroup.s2.auth.isense.data.IdentificationData;
import eu.itreegroup.s2.auth.isense.data.TokenData;

/**
 * Pagalbinė integracijos su iSense e-identification posisteme klasė, naudojama komunikacijai su iSense sistema.
 * 
 */
public class IsenseHttpRestTemplate {

    private static final Logger log = LoggerFactory.getLogger(IsenseHttpRestTemplate.class);

    protected AuthServiceRestConfig config;

    String createIsenseToken() throws Exception {
        String url = String.format("%s/api/oauth/token", config.getIsenseUrl());
        String requestBody = String.format("grant_type=password&username=%s&password=%s&client_id=%s",
                URLEncoder.encode(config.getIsenseUsername(), StandardCharsets.UTF_8.name()),
                URLEncoder.encode(config.getIsensePassword(), StandardCharsets.UTF_8.name()),
                URLEncoder.encode(config.getIsenseClientId(), StandardCharsets.UTF_8.name()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<TokenData> response = getRestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers), TokenData.class);
        checkStatusCode(response, HttpURLConnection.HTTP_OK, "CreateToken", url);
        String result = null;
        if (response.hasBody()) {
            TokenData tokenData = response.getBody();
            result = String.format("%s %s", tokenData.getTokenType(), tokenData.getAccessToken());
        }
        return result;
    }

    String startIdentificationSession(String isenseToken, String requestBody) throws Exception {
        String url = String.format("%s/e-identification/v1/sessions/web-session", config.getIsenseUrl());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", isenseToken);

        ResponseEntity<IdentificationData> response = getRestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers),
                IdentificationData.class);
        checkStatusCode(response, HttpURLConnection.HTTP_CREATED, "StartE-Identification", url);
        return response.hasBody() ? response.getBody().getSessionToken() : null;
    }

    IdentificationData getIdentificationSessionInfo(String isenseToken, String identificationToken) throws Exception {
        String url = String.format("%s/e-identification/v1/sessions/%s", config.getIsenseUrl(), identificationToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", isenseToken);

        ResponseEntity<IdentificationData> response = getRestTemplate().exchange(url, HttpMethod.GET, new HttpEntity<>(headers), IdentificationData.class);
        checkStatusCode(response, HttpURLConnection.HTTP_OK, "GetE-IdentificationSessionInfo", url);
        return response.getBody();
    }

    void deleteIdentificationSession(String isenseToken, String identificationToken) throws Exception {
        String url = String.format("%s/e-identification/v1/sessions/%s", config.getIsenseUrl(), identificationToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", isenseToken);

        ResponseEntity<Void> response = getRestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
        checkStatusCode(response, HttpURLConnection.HTTP_NO_CONTENT, "DeletetE-IdentificationSessionInfo", url);
    }

    private boolean isTestProfile() {
        return "test".equalsIgnoreCase(config.getActiveProfile());
    }

    private RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate = null;
        if (isTestProfile()) {
            log.warn("TEST profile is active - building RestTemplate with very lenient SSLContext.");
            TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

            HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(csf).build();

            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            restTemplate = new RestTemplate(requestFactory);
        } else {
            restTemplate = new RestTemplate();
        }
        restTemplate.setErrorHandler(IsenseBaseHttpRestTemplate.ERROR_HANDLER);
        return restTemplate;
    }

    private void checkStatusCode(ResponseEntity<?> response, int expectedStatusCode, String actionName, String url) throws Exception {
        int statusCode = response.getStatusCodeValue();
        if (statusCode != expectedStatusCode) {
            throw new Exception(String.format("%s returned with response code %d (expected %d); url: %s", actionName, statusCode, expectedStatusCode, url));
        }
    }

    public void setConfig(AuthServiceRestConfig config) {
        this.config = config;
    }

}
