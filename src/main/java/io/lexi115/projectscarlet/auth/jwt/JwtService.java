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

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public Jwt createJwt(final @NonNull UserDetails userDetails) {
        return createJwt(userDetails, new HashMap<>());
    }

    public Jwt createJwt(final @NonNull UserDetails userDetails, final @NonNull Map<String, Object> extraClaims) {
        var nowMillis = System.currentTimeMillis();
        var claims = new HashMap<String, Object>();
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        claims.put(Claims.ISSUED_AT, nowMillis);
        claims.put(Claims.EXPIRATION, nowMillis + jwtConfig.getAccessTokenExpiration());
        claims.putAll(extraClaims);
        return new Jwt(claims);
    }

    public String encodeJwt(final @NonNull Jwt jwt) {
        return Jwts.builder()
                .claims(jwt.getClaims())
                .signWith(generateSecretKey())
                .compact();
    }

    public Jwt decodeJwt(final @NonNull String token) {
        return new Jwt(getSignedClaims(token));
    }

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
