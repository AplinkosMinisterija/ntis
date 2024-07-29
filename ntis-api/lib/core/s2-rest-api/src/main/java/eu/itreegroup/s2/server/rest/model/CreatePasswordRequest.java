package eu.itreegroup.s2.server.rest.model;


public class CreatePasswordRequest {
	
    private String token;
    private String password;
    
    public CreatePasswordRequest() {
    }
    
    public CreatePasswordRequest(String token, String password) {
        super();
        this.token = token;
        this.password = password;
    }

    
    public String getToken() {
        return token;
    }

    
    public void setToken(String key) {
        this.token = key;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "LbcCreatePasswordRequest [key=" + token + ", password=" + password + "]";
    }

}
