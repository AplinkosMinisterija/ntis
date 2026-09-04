package lt.project.rest;

import java.util.Map;

public class Verify2faRequest {

    private String username;
    private String token;
    private String code;
    private Map<String, Object> authExtData;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getAuthExtData() {
        return authExtData;
    }

    public void setAuthExtData(Map<String, Object> authExtData) {
        this.authExtData = authExtData;
    }
}
