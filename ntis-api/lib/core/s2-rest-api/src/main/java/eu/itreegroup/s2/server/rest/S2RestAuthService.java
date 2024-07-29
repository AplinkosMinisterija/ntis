package eu.itreegroup.s2.server.rest;

import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.jwt.util.JwtUtil;
import eu.itreegroup.s2.server.rest.model.LoginResult;
import eu.itreegroup.s2.server.rest.model.WebSessionInfo;
import jakarta.servlet.http.HttpServletRequest;

public abstract class S2RestAuthService<T extends WebSessionInfo, S extends BackendUserSession> extends S2RestApiService<S> {

    @Autowired
    protected JwtUtil jwtUtil;

    protected abstract S createNewSession();

    protected S beforeSessionCreated(S2RestRequestContext<S> requestContext) {
        S userSession = createNewSession();
        HttpServletRequest request = requestContext.getRequest();
        if (request != null) {
            String clientIp = requestContext.getClientIp();
            if (clientIp != null && clientIp.length() > 40) {
                clientIp = clientIp.substring(0, 40);
            }
            userSession.setIp(clientIp);
            String userAgent = request.getHeader("User-Agent");
            if (userAgent != null && userAgent.length() > 200) {
                userAgent = userAgent.substring(0, 200);
            }
            userSession.setBrowser(userAgent);
        }
        return userSession;
    }

    protected LoginResult<T> createLoginResult(T webSession, BackendUserSession userSession) {
        LoginResult<T> result = new LoginResult<T>();
        result.setSession(webSession);
        if (userSession.getSesKey() != null) {
            result.setToken(jwtUtil.generateToken(userSession));
        }
        return result;
    }

}
