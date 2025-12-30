package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public Jwt createJwt(final @NonNull UserDetails userDetails) {
        return createJwt(userDetails, new HashMap<>());
    }

    public Jwt createJwt(final @NonNull UserDetails userDetails, final @NonNull Map<String, Object> extraClaims) {
        var nowMillis = System.currentTimeMillis();
        return new Jwt(
                userDetails.getUsername(),
                new Date(nowMillis),
                new Date(nowMillis + jwtConfig.getAccessTokenExpiration()),
                extraClaims
        );
    }

    public String encodeJwt(final @NonNull Jwt jwt) {
        return Jwts.builder()
                .claims(jwt.getClaims())
                .signWith(generateSecretKey())
                .compact();
    }

    public Jwt decodeJwt(final @NonNull String token) {
        var claims = getClaims(token);
        return new Jwt(
                claims.getSubject(),
                claims.getIssuedAt(),
                claims.getExpiration(),
                claims
        );
    }

    public boolean validateJwt(final @NonNull Jwt jwt, final @NonNull UserDetails userDetails) {
        return jwt.getSubject().equals(userDetails.getUsername())
                && !jwt.isExpired();
    }

    private @NonNull SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    /**
     * Extracts and parses the claims from the provided JSON Web Token (JWT).
     *
     * @param token the JSON Web Token (JWT) string to be parsed.
     * @return the parsed {@link Claims} object containing the claims from the token.
     */
    private Claims getClaims(final String token) {
        return Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
