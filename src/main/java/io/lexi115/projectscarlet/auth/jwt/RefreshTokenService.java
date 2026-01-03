package io.lexi115.projectscarlet.auth.jwt;

import io.lexi115.projectscarlet.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Service class that manages operations related to refresh tokens.
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class RefreshTokenService {
    /**
     * The JWT configuration.
     */
    private final JwtConfig jwtConfig;

    /**
     * The refresh token repository.
     */
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * The user service.
     */
    private final UserService userService;

    /**
     * The refresh token mapper.
     */
    private final RefreshTokenMapper refreshTokenMapper;

    /**
     * Returns a refresh token by providing its hash string.
     *
     * @param tokenString The token string.
     * @return A summary of the retrieved token if found, or <code>null</code> otherwise.
     * @since 1.0
     */
    public RefreshTokenSummary getRefreshTokenByString(final String tokenString) {
        var token = refreshTokenRepository.findByTokenHash(tokenString).orElse(null);
        return token != null ? refreshTokenMapper.toSummary(token) : null;
    }

    /**
     * Adds a refresh token if not already present in the database.
     *
     * @param tokenString The token string.
     * @param username    The username.
     * @since 1.0
     */
    @Transactional
    public void addRefreshToken(final String tokenString, final String username) {
        if (getRefreshTokenByString(tokenString) != null) {
            return;
        }
        var user = userService.getUserByUsername(username);
        var refreshToken = new RefreshToken();
        refreshToken.setTokenHash(tokenString);
        refreshToken.setUserId(user.getId());
        var expirationTimestamp = Instant.now().plusSeconds(jwtConfig.getRefreshTokenDuration()).getEpochSecond();
        var expirationDate = LocalDateTime.ofEpochSecond(expirationTimestamp, 0, ZoneOffset.UTC);
        refreshToken.setExpirationDate(expirationDate);
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * Deletes all refresh tokens linked to a certain user.
     *
     * @param userId The user ID.
     * @since 1.0
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeAllRefreshTokens(final UUID userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }

    /**
     * Deletes all refresh tokens that had been expired for a certain period of time.
     *
     * @param dateLimit The date limit.
     * @since 1.0
     */
    @Transactional
    public void removeAllRefreshTokensBefore(final LocalDateTime dateLimit) {
        refreshTokenRepository.deleteAllByExpirationDateLessThanEqual(dateLimit);
    }

    /**
     * Marks a certain refresh token as revoked (if found).
     *
     * @param tokenString The token string.
     * @since 1.0
     */
    @Transactional
    public void markAsRevoked(final String tokenString) {
        var token = refreshTokenRepository.findByTokenHash(tokenString).orElse(null);
        if (token == null) {
            return;
        }
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
