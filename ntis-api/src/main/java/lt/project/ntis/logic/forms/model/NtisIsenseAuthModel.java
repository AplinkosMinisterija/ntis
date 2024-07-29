package lt.project.ntis.logic.forms.model;

/**
 * Klasė skirta iSense darbo sesijai pradėti reikalingos informacijos modeliui apibrėžti
 */
public class NtisIsenseAuthModel {

    private String token;

    private String host;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
