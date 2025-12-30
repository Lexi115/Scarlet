package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.Claims;
import lombok.*;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class Jwt {

    @NonNull
    private Map<String, Object> claims;

    public String getSubject() {
        return getClaim(Claims.SUBJECT, String.class);
    }

    public void setSubject(final String subject) {
        claims.put(Claims.SUBJECT, subject);
    }

    public Long getIssuedAt() {
        return getClaim(Claims.ISSUED_AT, Long.class);
    }

    public void setIssuedAt(final Long issuedAt) {
        claims.put(Claims.ISSUED_AT, issuedAt);
    }

    public Long getExpiration() {
        return getClaim(Claims.EXPIRATION, Long.class);
    }

    public void setExpiration(final Long expiration) {
        claims.put(Claims.EXPIRATION, expiration);
    }

    public <T> T getClaim(final String key, final @NonNull Class<T> type) {
        return type.cast(claims.getOrDefault(key, null));
    }

    public void setClaim(final String key, final Object object) {
        claims.put(key, object);
    }

    public boolean isExpired() {
        return new Date(getExpiration()).before(new Date());
    }
}
