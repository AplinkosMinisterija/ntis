package eu.itreegroup.s2.auth.viisp;

import static eu.itreegroup.s2.auth.viisp.ViispAuthClient.authRequestParametersToString;
import static eu.itreegroup.s2.auth.viisp.ViispAuthClient.getAuthRequestParametersFromString;

import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import eu.itreegroup.s2.auth.viisp.model.ViispAuthData;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthRequest;
import eu.itreegroup.s2.auth.viisp.model.ViispAuthResult;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationAttributePair;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationDataResponseXml;
import lt.atea.vaiisis.authentication.model.xml.AuthenticationResponseXml;
import lt.atea.vaiisis.authentication.model.xml.UserInformationPair;

public class ViispAuthClientImplV2 implements ViispAuthClient {

    private static final Logger log = LoggerFactory.getLogger(ViispAuthClientImplV2.class);

    private static char[] NAME_DELIMITERS = new char[] { ' ', '-' };

    private ViispAuthRequestGenerator authRequestGenerator;

    private ViispAuthDataRequestGenerator authDataRequestGenerator;

    protected RestTemplate restTemplate = new RestTemplate();

    @Override
    public ViispAuthResult initAuth(ViispAuthRequest authRequest) throws Exception {
        String req = authRequestGenerator.generateRequest(authRequestParametersToString(authRequest.getParameters()));
        HttpEntity<String> request = new HttpEntity<>(req);
        try {
            String strResponse = restTemplate.postForObject(this.getConfig().getServiceEndpointUrl(), request, String.class);
            AuthenticationResponseXml response = authRequestGenerator.getResponse(strResponse);
            ViispAuthResult result = new ViispAuthResult();
            result.setViispUrlWithTicket(authRequestGenerator.getConfig().getLoginUrl() + "?ticket=" + response.getTicket());
            return result;
        } catch (HttpServerErrorException e) {
            log.error("Got error response: {}, {}, {}", e.getStatusCode(), e.getMessage(), e.getResponseBodyAsString());
            throw e;
        }

    }

    @Override
    public ViispAuthData getAuthDataByTicket(String ticket) throws Exception {
        String req = authDataRequestGenerator.generateRequest(ticket);
        HttpEntity<String> request = new HttpEntity<>(req);
        try {
            String strResponse = restTemplate.postForObject(this.getConfig().getServiceEndpointUrl(), request, String.class);
            AuthenticationDataResponseXml response = authDataRequestGenerator.getResponse(strResponse);
            ViispAuthData data = new ViispAuthData();
            data.setCustomData(getAuthRequestParametersFromString(response.getCustomData()));
            data.setTicket(ticket);
            for (AuthenticationAttributePair attr : response.getAuthenticationAttribute()) {
                switch (attr.getAttribute()) {
                    case LT_PERSONAL_CODE:
                        data.setPersonCode(attr.getValue());
                        break;
                    case LT_COMPANY_CODE:
                        data.setCompanyCode(attr.getValue());
                        break;
                    case LT_GOVERNMENT_EMPLOYEE_CODE:
                        data.setGovernmentEmployeeCode(attr.getValue());
                        break;
                    default:
                        break;
                }
            }
            for (UserInformationPair userInfo : response.getUserInformation()) {
                switch (userInfo.getInformation()) {
                    case FIRST_NAME:
                        String firstName = WordUtils.capitalizeFully(userInfo.getValue().getStringValue(), NAME_DELIMITERS);
                        data.setFirstName(firstName);
                        break;
                    case LAST_NAME:
                        String lastName = WordUtils.capitalizeFully(userInfo.getValue().getStringValue(), NAME_DELIMITERS);
                        data.setLastName(lastName);
                        break;

                    case COMPANY_NAME:
                        data.setCompanyName(userInfo.getValue().getStringValue());
                        break;

                    default:
                        break;
                }
            }
            return data;
        } catch (HttpServerErrorException e) {
            log.error("Got error response:", e.getResponseBodyAsString());
            throw e;
        }
    }

    public ViispAuthRequestGenerator getAuthRequestGenerator() {
        return authRequestGenerator;
    }

    public void setAuthRequestGenerator(ViispAuthRequestGenerator authRequestGenerator) {
        this.authRequestGenerator = authRequestGenerator;
    }

    public ViispAuthDataRequestGenerator getAuthDataRequestGenerator() {
        return authDataRequestGenerator;
    }

    public void setAuthDataRequestGenerator(ViispAuthDataRequestGenerator authDataRequestGenerator) {
        this.authDataRequestGenerator = authDataRequestGenerator;
    }

    @Override
    public AuthServiceConfig getConfig() {
        return this.authRequestGenerator.getConfig();
    }
}
