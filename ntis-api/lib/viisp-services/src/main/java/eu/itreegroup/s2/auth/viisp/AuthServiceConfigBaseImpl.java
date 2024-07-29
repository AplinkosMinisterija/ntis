package eu.itreegroup.s2.auth.viisp;

import eu.itreegroup.s2.BaseCertConfig;

public class AuthServiceConfigBaseImpl extends BaseCertConfig implements AuthServiceConfig {

    private String postbackUrl;

    private String contractId;

    private String serviceEndpointUrl;

    private String loginUrl;

    private String appSuccessUrl;

    public String getPostbackUrl() {
        return postbackUrl;
    }

    public void setPostbackUrl(String postbackUrl) {
        this.postbackUrl = postbackUrl;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getServiceEndpointUrl() {
        return serviceEndpointUrl;
    }

    public void setServiceEndpointUrl(String serviceEndpointUrl) {
        this.serviceEndpointUrl = serviceEndpointUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getAppSuccessUrl() {
        return appSuccessUrl;
    }

    public void setAppSuccessUrl(String appSuccessUrl) {
        this.appSuccessUrl = appSuccessUrl;
    }

}
