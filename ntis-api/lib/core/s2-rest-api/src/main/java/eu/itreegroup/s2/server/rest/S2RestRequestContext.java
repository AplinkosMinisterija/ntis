package eu.itreegroup.s2.server.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

public class S2RestRequestContext<S extends BackendUserSession> {

    private static final Logger log = LoggerFactory.getLogger(S2RestRequestContext.class);

    // private static final int MAX_LANGUAGE_LENGTH = 2;
    //
    // private static final int MAX_CONFIRM_WARNINGS_LENGTH = 500;

    private static String localHostName;

    protected String clientIp;

    protected String jwtToken;

    protected S userSession;

    @Autowired(required = true)
    protected HttpServletRequest request;

    @Autowired(required = true)
    protected ServletContext servletContext;

    public void onDestroy() {

    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public static String getLocalHostName() {
        if (localHostName == null) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                localHostName = addr.getHostName();
            } catch (UnknownHostException uhe) {
                localHostName = "UNKNOWN_HOST";
            }
        }
        return localHostName;
    }

    public String getJwtToken() {
        if (jwtToken == null) {
            if (request == null) {
                log.debug("request is null");
                return null;
            }
            jwtToken = request.getHeader("Authorization");
        }
        return jwtToken;
    }

    public String getClientIp() {
        if (clientIp == null) {
            // if request is not initialized yet - return at once
            if (request == null) {
                return null;
            }
            clientIp = request.getHeader("X-Real-IP");
            if (clientIp == null) {
                clientIp = request.getRemoteAddr();
            }
        }
        return clientIp;
    }

    public String getLanguage() {
        return "LT";
    }

    public S getUserSession() {
        return userSession;
    }

    public void setUserSession(S userSession) {
        this.userSession = userSession;
    }

}
