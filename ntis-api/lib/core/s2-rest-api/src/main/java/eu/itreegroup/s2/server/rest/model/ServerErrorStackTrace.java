package eu.itreegroup.s2.server.rest.model;


public class ServerErrorStackTrace {

    private String cause;
    private String stackTrace;
    
    public ServerErrorStackTrace() {

    }
    
    public ServerErrorStackTrace(String cause, String stackTrace) {
        this.cause = cause;
        this.stackTrace = stackTrace;
    }

    public String getCause() {
        return cause;
    }
    
    public void setCause(String cause) {
        this.cause = cause;
    }
    
    public String getStackTrace() {
        return stackTrace;
    }
    
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "ServerErrorStackTrace [cause=" + cause + ", stackTrace=" + stackTrace + "]";
    }
    
}
