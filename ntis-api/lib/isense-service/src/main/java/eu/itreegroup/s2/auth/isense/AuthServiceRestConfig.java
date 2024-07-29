package eu.itreegroup.s2.auth.isense;

/**
 * Konfigūracija reikalinga kuriant raktą, reikalingą vykdant bet kokią komunikaciją su iSense sistema. 
 * 
 */
public class AuthServiceRestConfig {

    private String activeProfile;

    private String isenseUrl;

    private String isenseUsername;

    private String isensePassword;

    private String isenseClientId;

    public String getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    public String getIsenseUrl() {
        return isenseUrl;
    }

    public void setIsenseUrl(String isenseUrl) {
        this.isenseUrl = isenseUrl;
    }

    public String getIsenseUsername() {
        return isenseUsername;
    }

    public void setIsenseUsername(String isenseUsername) {
        this.isenseUsername = isenseUsername;
    }

    public String getIsensePassword() {
        return isensePassword;
    }

    public void setIsensePassword(String isensePassword) {
        this.isensePassword = isensePassword;
    }

    public String getIsenseClientId() {
        return isenseClientId;
    }

    public void setIsenseClientId(String isenseClientId) {
        this.isenseClientId = isenseClientId;
    }

}
