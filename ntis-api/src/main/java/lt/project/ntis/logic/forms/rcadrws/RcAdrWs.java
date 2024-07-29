package lt.project.ntis.logic.forms.rcadrws;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Pagalbinė integracijos su Registrų Centro AdrWS sistema klasė, naudojama komunikacijai su AdrWS sistema.
 */
@Component
public class RcAdrWs {

    private static final Logger log = LoggerFactory.getLogger(RcAdrWs.class);

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Value("${rcadrws.url}")
    private String rcadrwsUrl;

    @Value("${rcadrws.callerCode}")
    private String rcadrwsCallerCode;

    @Value("${rcadrws.keystorePath}")
    private File rcadrwsKeystorePath;

    @Value("${rcadrws.keystorePass}")
    private String rcadrwsKeystorePass;

    @Value("${rcadrws.keyAlias}")
    private String rcadrwsKeyAlias;

    @Value("${rcadrws.keyPass}")
    private String rcadrwsKeyPass;

    public String getAdrData(DataType dataType, LocalDate startDate, LocalDate endDate) throws Exception {
        String result = null;
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        String dateFrom = toMillisecondsAsString(startDateTime);
        String dateTo = toMillisecondsAsString(endDateTime);
        String dataTypeCode = dataType.name();
        log.info("DataType: {}; Interval : {} - {}", dataTypeCode, startDateTime, endDateTime);

        long timeAtStartMilliseconds = System.currentTimeMillis();
        String timeNowMilliseconds = Long.toString(timeAtStartMilliseconds);
        String signature = URLEncoder.encode(buildSignature(rcadrwsCallerCode, dataTypeCode, dateFrom, dateTo, timeNowMilliseconds), StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rcadrwsUrl);
        builder.queryParam("CLIENT_NAME", rcadrwsCallerCode);
        builder.queryParam("DATA_TYPE", dataTypeCode);
        builder.queryParam("DATE_FROM", dateFrom);
        builder.queryParam("DATE_TO", dateTo);
        builder.queryParam("TIME", timeNowMilliseconds);
        builder.queryParam("SIGNATURE", signature);

        ResponseEntity<byte[]> response = getRestTemplate().exchange(builder.build(true).toUri(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),
                byte[].class);
        result = new String(response.getBody(), StandardCharsets.UTF_8);
        log.debug("CallDuration : {} ms; StatusCode : {}.", (System.currentTimeMillis() - timeAtStartMilliseconds), response.getStatusCode().value());
        log.debug("Body : {}", result);

        return result;
    }

    private static String toMillisecondsAsString(LocalDateTime dateTime) {
        return Long.toString(dateTime.toEpochSecond(ZoneOffset.UTC) * 1000L);
    }

    private RestTemplate getRestTemplate() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
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
        restTemplate.setErrorHandler(ERROR_HANDLER);
        return restTemplate;
    }

    private boolean isTestProfile() {
        return "test".equalsIgnoreCase(activeProfile);
    }

    private static final ResponseErrorHandler ERROR_HANDLER = new ResponseErrorHandler() {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            int statusCode = response.getStatusCode().value();
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
            String message = String.format("RestTemplate exchange returned with response code %d and body %s.", statusCode, body);
            throw new IOException(message);
        }
    };

    private String buildSignature(String... params) throws CertificateException, InvalidKeyException, IOException, KeyStoreException, NoSuchAlgorithmException,
            SignatureException, UnrecoverableKeyException {

        String data = Arrays.stream(params).collect(Collectors.joining());
        // RSASSA-PKCS1-v1_5 su SHA-1 HASH funkcija
        Signature s = Signature.getInstance("SHA1WithRSA");
        s.initSign(getPrivateKey());
        s.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signedData = s.sign();
        return java.util.Base64.getEncoder().encodeToString(signedData);
    }

    private PrivateKey getPrivateKey() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {

        KeyStore k = KeyStore.getInstance(KeyStore.getDefaultType());
        k.load(new FileInputStream(rcadrwsKeystorePath), rcadrwsKeystorePass.toCharArray());
        return (PrivateKey) k.getKey(rcadrwsKeyAlias, rcadrwsKeyPass.toCharArray());
    }
}
