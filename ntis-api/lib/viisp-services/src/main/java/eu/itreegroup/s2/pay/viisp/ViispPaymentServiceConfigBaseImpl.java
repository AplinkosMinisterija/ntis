package eu.itreegroup.s2.pay.viisp;

import eu.itreegroup.s2.BaseCertConfig;

public class ViispPaymentServiceConfigBaseImpl extends BaseCertConfig implements ViispPaymentServiceConfig {

    private String contractId;

    private String senderId;

    private String postUrl;

    private String returnUrlPost;

    private String returnUrlServer;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getReturnUrlPost() {
        return returnUrlPost;
    }

    public void setReturnUrlPost(String returnUrlPost) {
        this.returnUrlPost = returnUrlPost;
    }

    public String getReturnUrlServer() {
        return returnUrlServer;
    }

    public void setReturnUrlServer(String returnUrlServer) {
        this.returnUrlServer = returnUrlServer;
    }

    @Override
    public boolean getSecureValidationEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setSecureValidationEnabled(Boolean enabled) {
        // TODO Auto-generated method stub

    }

}
