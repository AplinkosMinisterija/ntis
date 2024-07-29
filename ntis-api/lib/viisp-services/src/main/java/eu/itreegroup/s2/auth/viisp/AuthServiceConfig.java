package eu.itreegroup.s2.auth.viisp;

import eu.itreegroup.s2.CertConfig;

public interface AuthServiceConfig extends CertConfig {

    String getLoginUrl();

    String getPostbackUrl();

    String getContractId();

    String getServiceEndpointUrl();

    String getAppSuccessUrl();

    void setPostbackUrl(String postbackUrl);

    void setContractId(String contractId);

    void setServiceEndpointUrl(String serviceEndpointUrl);

    void setLoginUrl(String loginUrl);

    void setAppSuccessUrl(String appSuccessUrl);

}