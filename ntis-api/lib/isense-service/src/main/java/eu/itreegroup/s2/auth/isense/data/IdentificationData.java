package eu.itreegroup.s2.auth.isense.data;

import java.util.Date;

/**
 * Naudotojo identifikavimo iSense sistemoje rezultatas.
 * 
 */
public class IdentificationData {

    /**
     * Identifikavimo sesijos būsena.
     * Galimos reikšmės paimtos iš iSense sistemos dokumentacijos.
     */
    public static enum SessionStatus {
        INITIATED, FINISHED
    }

    private Identity identity;

    private String sessionToken;

    private SessionStatus sessionStatus;

    private Date expiresIn;

    private String returnUrl;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
