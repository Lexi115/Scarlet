package io.lexi115.projectscarlet.auth.jwt;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for a refresh token.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class RefreshTokenSummary {

    /**
     * The token string.
     */
    private String tokenHash;

    /**
     * The user ID.
     */
    private UUID userId;

    /**
     * The token expiration date.
     */
    private LocalDateTime expirationDate;

    /**
     * Whether the token has been revoked.
     */
    private boolean revoked;

}
