package eu.itreegroup.s2.server.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface BackendUserSession extends UserDetails {

    boolean isAuthenticated();

    // El. pašto 2FA žymos. Numatytai no-op (upstream-safe); realų elgesį suteikia SprBackendUserSession.
    default void setTwoFactorRequired(boolean twoFactorRequired) {}

    default boolean isTwoFactorRequired() { return false; }

    default void setTwoFactorEmailMasked(String twoFactorEmailMasked) {}

    default String getTwoFactorEmailMasked() { return null; }

    default void setTwoFactorToken(String twoFactorToken) {}

    default String getTwoFactorToken() { return null; }

    Double getUsrId();

    String getRole();

    Double getSesId();

    String getSesKey();

    String getIp();

    String getBrowser();

    String getUserPasswordChangeToken();

    Double getApiId();

    void setUsername(String subject);

    void setUsrId(Double parseDouble);

    void setRole(String string);

    void setSesId(Double parseDouble);

    void setSesKey(String string);

    void setIp(String ip);

    void setDaoSession(Object session);

    void setBrowser(String browser);

    void setUserPasswordChangeToken(String usrPasswordChangeToken);

    void setApiId(Double apiId);

}
