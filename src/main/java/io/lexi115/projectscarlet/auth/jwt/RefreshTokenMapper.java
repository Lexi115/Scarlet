package io.lexi115.projectscarlet.auth.jwt;

import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Mapper class used to convert {@link RefreshToken} objects into their DTO counterparts.
 *
 * @author Lexi115
 * @since 1.0
 */
@Component
public class RefreshTokenMapper {
    /**
     * Converts a {@link RefreshToken} into a {@link RefreshTokenSummary} DTO.
     *
     * @param refreshToken The refresh token.
     * @return The resulting user entity.
     * @since 1.0
     */
    public RefreshTokenSummary toSummary(@NonNull final RefreshToken refreshToken) {
        var summary = new RefreshTokenSummary();
        summary.setTokenHash(refreshToken.getTokenHash());
        summary.setUserId(refreshToken.getUserId());
        summary.setExpirationDate(refreshToken.getExpirationDate());
        summary.setRevoked(refreshToken.isRevoked());
        return summary;
    }
}
