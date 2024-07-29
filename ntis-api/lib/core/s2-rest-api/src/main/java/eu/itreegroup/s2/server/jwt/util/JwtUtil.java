package eu.itreegroup.s2.server.jwt.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.jwt.exception.JwtTokenMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public abstract class JwtUtil {

    private static Logger log = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    public BackendUserSession parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            BackendUserSession u = createUserSession();
            u.setUsername(body.getSubject());
            // u.setUsrId(body.get("userId") != null ? Double.parseDouble((String) body.get("userId")) : null);
            u.setRole((String) body.get("role"));
            u.setSesId(body.get("sesId") != null ? (Double) body.get("sesId") : null);
            u.setSesKey((String) body.get("sesKey"));
            u.setApiId(body.get("apiId") != null ? (Double) body.get("apiId") : null);

            return u;
        } catch (JwtException | ClassCastException e) {
            log.error("Unable to parse jwt token", e);
            return null;
        }
    }

    public String generateToken(BackendUserSession u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        // claims.put("userId", u.getUsrId() + "");
        claims.put("role", u.getRole());
        claims.put("sesId", u.getSesId());
        claims.put("sesKey", u.getSesKey());
        return generateJwtToken(claims);
    }

    public static String getTokenFromAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtTokenMissingException("No JWT token found in request headers");
        }
        return header.substring(7);
    }

    /**
     * This method generates a JWT (JSON Web Token) by incorporating the provided parameters map
     * and signing it using the HS512 (HMAC with SHA-512) signature algorithm for security.
     *
     * @param claimsMap the parameters to be included in the JWT, typically containing user information and permissions.
     * @param claimsSubject the parameters to be included in the JWT as subject.
     * @return A JWT token string that can be used for authentication and authorization.
     */
    public String generateJwtTokenByClaimsMap(String claimsSubject, Map<String, Object> claimsMap) {
        return generateJwtToken(Jwts.claims(claimsMap).setSubject(claimsSubject));
    }

    /**
     * This method generates a JWT (JSON Web Token) by incorporating the provided claims
     * and signing it using the HS512 (HMAC with SHA-512) signature algorithm for security.
     *
     * @param claims The claims to be included in the JWT, typically containing user information and permissions.
     * @return A JWT token string that can be used for authentication and authorization.
     */
    private String generateJwtToken(Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    protected abstract BackendUserSession createUserSession();
}
