package eu.itreegroup.s2.server.rest.model;

import java.util.Map;

public class LoginRequest {

    public LoginRequest() {
        super();
    }

    public LoginRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    private String username;

    private String password;

    private Map<String, Object> authExtData;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getAuthExtData() {
        return this.authExtData;
    }

    public void setAuthExtData(Map<String, Object> authExtData) {
        this.authExtData = authExtData;
    }
}
