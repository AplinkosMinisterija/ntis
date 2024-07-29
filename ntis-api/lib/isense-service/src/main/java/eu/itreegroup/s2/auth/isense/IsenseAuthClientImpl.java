package eu.itreegroup.s2.auth.isense;

import eu.itreegroup.s2.auth.isense.data.IdentificationData;
import eu.itreegroup.s2.auth.isense.data.Identity;

/**
 * Integracijos su iSense e-identification posisteme realizacija.
 * 
 */
public class IsenseAuthClientImpl implements IsenseAuthClient {

    protected AuthServiceConfig config;

    protected IsenseHttpRestTemplate restTemplate;

    @Override
    public String startIdentificationSession() throws Exception {
        return restTemplate.startIdentificationSession(restTemplate.createIsenseToken(), IsenseJson.buildMinimalIdentificationBody(config));
    }

    @Override
    public IsenseAuthData getIdentificationSessionInfo(String identificationToken) throws Exception {
        IdentificationData identificationData = restTemplate.getIdentificationSessionInfo(restTemplate.createIsenseToken(), identificationToken);
        IsenseAuthData result = null;
        if (identificationData != null) {
            Identity identity = identificationData.getIdentity();
            result = new IsenseAuthData(identity.getPersonCode(), identity.getGivenName(), identity.getSurname());
        }
        return result;
    }

    @Override
    public void deleteIdentificationSession(String identificationToken) throws Exception {
        restTemplate.deleteIdentificationSession(restTemplate.createIsenseToken(), identificationToken);
    }

    public void setRestTemplate(IsenseHttpRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setConfig(AuthServiceConfig config) {
        this.config = config;
    }

}
