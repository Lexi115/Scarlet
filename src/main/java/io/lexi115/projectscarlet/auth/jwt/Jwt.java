package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.Claims;
import lombok.*;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class Jwt {

    @NonNull
    private Map<String, Object> claims;

    public Jwt(
            final String subject,
            final Date issuedAt,
            final Date expiration,
            @NonNull final Map<String, Object> claims
    ) {
        this.claims = claims;
        this.claims.put(Claims.SUBJECT, subject);
        this.claims.put(Claims.ISSUED_AT, issuedAt);
        this.claims.put(Claims.EXPIRATION, expiration);
    }

    public String getSubject() {
        return getClaim(Claims.SUBJECT, String.class);
    }

    public void setSubject(final String subject) {
        claims.put(Claims.SUBJECT, subject);
    }

    public Date getIssuedAt() {
        return getClaim(Claims.ISSUED_AT, Date.class);
    }

    public void setIssuedAt(final Date issuedAt) {
        claims.put(Claims.ISSUED_AT, issuedAt);
    }

    public Date getExpiration() {
        return getClaim(Claims.EXPIRATION, Date.class);
    }

    public void setExpiration(final Date expiration) {
        claims.put(Claims.EXPIRATION, expiration);
    }

    public <T> T getClaim(final String key, final @NonNull Class<T> type) {
        return type.cast(claims.getOrDefault(key, null));
    }

    public void setClaim(final String key, final Object object) {
        claims.put(key, object);
    }

    public boolean isExpired() {
        return getExpiration().before(new Date());
    }
}
