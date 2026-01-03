package io.lexi115.projectscarlet.auth.jwt;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

/**
 * POJO class representing a JSON Web Token (JWT).
 *
 * @author Lexi115
 * @since 1.0
 */
@AllArgsConstructor
@Getter
@Setter
public class Jwt {
    /**
     * The fields inside the JWT payload.
     */
    @NonNull
    private Map<String, Object> claims;

    /**
     * Returns the subject (generally the ID of the user).
     *
     * @return The subject.
     * @since 1.0
     */
    public String getSubject() {
        return getClaim(Claims.SUBJECT, String.class);
    }

    /**
     * Sets the subject (generally the ID of the user).
     *
     * @param subject The subject.
     * @since 1.0
     */
    public void setSubject(final String subject) {
        claims.put(Claims.SUBJECT, subject);
    }

    /**
     * Returns the issue date as a UNIX timestamp.
     *
     * @return The issue date.
     * @since 1.0
     */
    public Long getIssuedAt() {
        return getClaim(Claims.ISSUED_AT, Long.class);
    }

    /**
     * Sets the issue date as a UNIX timestamp.
     *
     * @param issuedAt The issue date.
     * @since 1.0
     */
    public void setIssuedAt(final Long issuedAt) {
        claims.put(Claims.ISSUED_AT, issuedAt);
    }

    /**
     * Returns the expiration date as a UNIX timestamp.
     *
     * @return The expiration date.
     * @since 1.0
     */
    public Long getExpiration() {
        return getClaim(Claims.EXPIRATION, Long.class);
    }

    /**
     * Sets the expiration date as a UNIX timestamp.
     *
     * @param expiration The expiration date.
     * @since 1.0
     */
    public void setExpiration(final Long expiration) {
        claims.put(Claims.EXPIRATION, expiration);
    }

    /**
     * Returns a payload claim given a certain key.
     *
     * @param key  The claim key.
     * @param type The claim class type (used for casting).
     * @param <T>  The claim class.
     * @return The claim.
     * @since 1.0
     */
    public <T> T getClaim(final String key, @NonNull final Class<T> type) {
        return type.cast(claims.getOrDefault(key, null));
    }

    /**
     * Sets a payload claim.
     *
     * @param key   The claim key.
     * @param value The claim object value.
     * @since 1.0
     */
    public void setClaim(final String key, final Object value) {
        claims.put(key, value);
    }

    /**
     * Checks whether the JWT is expired.
     *
     * @return <code>true</code> if the JWT is expired, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isExpired() {
        return Instant.ofEpochSecond(getExpiration()).isBefore(Instant.now());
    }
}
