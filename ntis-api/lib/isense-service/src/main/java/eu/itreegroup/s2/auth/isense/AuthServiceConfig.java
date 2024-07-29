package eu.itreegroup.s2.auth.isense;

/**
 * Konfigūracija reikalinga pradedant naudotojo identifikacijos sesiją iSense sistemoje.
 * 
 */
public class AuthServiceConfig {

    private String localeName;

    private boolean callbackAsyncEnabled;

    private String defaultCountryName;

    private String message;

    private String returnUrl;

    private String callbackUrl;

    public String getLocaleName() {
        return localeName;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }

    public boolean isCallbackAsyncEnabled() {
        return callbackAsyncEnabled;
    }

    public void setCallbackAsyncEnabled(boolean callbackAsyncEnabled) {
        this.callbackAsyncEnabled = callbackAsyncEnabled;
    }

    public String getDefaultCountryName() {
        return defaultCountryName;
    }

    public void setDefaultCountryName(String defaultCountryName) {
        this.defaultCountryName = defaultCountryName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

}
