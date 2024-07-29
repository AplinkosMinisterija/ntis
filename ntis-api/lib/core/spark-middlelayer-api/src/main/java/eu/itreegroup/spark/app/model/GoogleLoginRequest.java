package eu.itreegroup.spark.app.model;

import java.util.Map;

public class GoogleLoginRequest {

    private String credential;

    private Map<String, Object> authExtData;

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public Map<String, Object> getAuthExtData() {
        return authExtData;
    }

    public void setAuthExtData(Map<String, Object> authExtData) {
        this.authExtData = authExtData;
    }

}
