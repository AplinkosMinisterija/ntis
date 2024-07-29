package eu.itreegroup.s2.server.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import eu.itreegroup.s2.server.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String HEADER_AUTHORIZATION = "Authorization";

    protected JwtAuthenticationFilter() {
        super("/**");
    }

    protected abstract void onAuthSuccess(Authentication authResult);

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        String authToken = JwtUtil.getTokenFromAuthorizationHeader(header);
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        log.debug("successfulAuthentication:{}", authResult.getPrincipal());
        onAuthSuccess(authResult);
        chain.doFilter(request, response);
    }
}
