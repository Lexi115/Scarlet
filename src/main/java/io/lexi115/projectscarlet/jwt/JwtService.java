package io.lexi115.projectscarlet.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtService {

    private JwtConfig jwtConfig;

    public Jwt createJwt(final @NonNull UserDetails userDetails) {
        return createJwt(userDetails, Map.of());
    }

    public Jwt createJwt(final @NonNull UserDetails userDetails, final Map<String, Object> extraClaims) {
        var nowMillis = System.currentTimeMillis();
        return new Jwt(
                userDetails.getUsername(),
                new Date(nowMillis),
                new Date(nowMillis + jwtConfig.getAccessTokenExpiration()),
                extraClaims,
                generateSecretKey(jwtConfig.getSecret())
        );
    }

    public boolean validateJwt(final @NonNull Jwt jwt, final @NonNull UserDetails userDetails) {
        return jwt.getSubject().equals(userDetails.getUsername())
                && !jwt.isExpired();
    }

    private @NonNull Key generateSecretKey(final @NonNull String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
