package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing JSON Web Tokens (JWT), used for authorization.
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class JwtService {
    /**
     * The JWT configuration.
     */
    private final JwtConfig jwtConfig;

    /**
     * Creates a new JWT based on the provided {@link UserDetails}.
     *
     * @param userDetails The user details.
     * @return The newly created JWT.
     * @since 1.0
     */
    public Jwt createJwt(final @NonNull UserDetails userDetails) {
        return createJwt(userDetails, new HashMap<>());
    }

    /**
     * Creates a new JWT based on the provided {@link UserDetails}.
     *
     * @param userDetails The user details.
     * @param extraClaims The extra fields to add into the JWT payload.
     * @return The newly created JWT.
     * @since 1.0
     */
    public Jwt createJwt(final @NonNull UserDetails userDetails, final @NonNull Map<String, Object> extraClaims) {
        var nowMillis = System.currentTimeMillis();
        var claims = new HashMap<String, Object>();
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        claims.put(Claims.ISSUED_AT, nowMillis);
        claims.put(Claims.EXPIRATION, nowMillis + jwtConfig.getAccessTokenExpiration());
        claims.putAll(extraClaims);
        return new Jwt(claims);
    }

    /**
     * Encoded a {@link Jwt} object into an actual (signed) JWT token string.
     *
     * @param jwt The object.
     * @return The corresponding token string.
     * @since 1.0
     */
    public String encodeJwt(final @NonNull Jwt jwt) {
        return Jwts.builder()
                .claims(jwt.getClaims())
                .signWith(generateSecretKey())
                .compact();
    }

    /**
     * Decodes a (signed) JWT token string into a {@link Jwt} object.
     *
     * @param token The token string.
     * @return The corresponding object.
     * @since 1.0
     */
    public Jwt decodeJwt(final @NonNull String token) {
        return new Jwt(getSignedClaims(token));
    }

    /**
     * Validates a JWT by checking the username and expiration date.
     *
     * @param jwt         The JWT object.
     * @param userDetails The user details.
     * @return <code>true</code> if the JWT is valid, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean validateJwt(final @NonNull Jwt jwt, final @NonNull UserDetails userDetails) {
        return jwt.getSubject().equals(userDetails.getUsername()) && !jwt.isExpired();
    }

    private @NonNull SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    private @NonNull Map<String, Object> getSignedClaims(final @NonNull String token) throws JwtException {
        var claims = Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return new HashMap<>(claims);
    }
}
