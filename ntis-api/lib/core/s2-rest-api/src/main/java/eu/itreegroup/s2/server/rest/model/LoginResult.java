package eu.itreegroup.s2.server.rest.model;

public class LoginResult<T extends WebSessionInfo> {
    
    protected T session;
    protected String token;
    
    public T getSession() {
        return session;
    }
    
    public void setSession(T session) {
        this.session = session;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResult [session=" + session + ", token=" + token +  "]";
    }

}
