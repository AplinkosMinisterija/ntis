package eu.itreegroup.s2.server.jwt;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// MDC context cleaning is also done in eu.itreegroup.s2.server.listener.ClearRequestContextListener after requestDestroyed
public class S2JwtAuthenticationFilter extends JwtAuthenticationFilter {

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Override
    protected void onAuthSuccess(Authentication authResult) {
        BackendUserSession userSession = (BackendUserSession) authResult.getPrincipal();
        requestContext.setUserSession(userSession);
        setSlf4jContextValues(userSession);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        removeSlf4jContextValues();
    }

    protected void setSlf4jContextValues(BackendUserSession userSession) {
        if (userSession == null) {
            removeSlf4jContextValues();
            return;
        }
        if (userSession.getUsername() != null) {
            MDC.put("user", userSession.getUsername());
        }

        if (userSession.getRole() != null) {
            MDC.put("role", userSession.getRole());
        }

        if (userSession.getSesKey() != null) {
            MDC.put("session", userSession.getSesKey());
        }
        MDC.put("clientIp", requestContext.getClientIp());
        MDC.put("hostname", S2RestRequestContext.getLocalHostName());
    }

    public static void removeSlf4jContextValues() {
        MDC.remove("user");
        MDC.remove("role");
        MDC.remove("session");
        MDC.remove("clientIp");
        MDC.remove("hostname");
    }

}
