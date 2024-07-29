package eu.itreegroup.s2.server.rest.model;


public class ClientSideError {
    
    private String user;
    
    private String clientErrorTime;
    
    private String errorCode;
    
    private String errorMessage;
    
    public ClientSideError() {
        
    }
    
    public ClientSideError(String user, String clientErrorTime, String errorCode, String errorMessage) {
        super();
        this.user = user;
        this.clientErrorTime = clientErrorTime;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getUser() {
        return user;
    }

    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getClientErrorTime() {
        return clientErrorTime;
    }

    public void setClientErrorTime(String clientErrorTime) {
        this.clientErrorTime = clientErrorTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    
    public String getErrorMessage() {
        return errorMessage;
    }

    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ClientSideError [user=" + user + ", clientErrorTime=" + clientErrorTime + ", errorCode=" + errorCode
                + ", errorMessage=" + errorMessage + "]";
    }

}
