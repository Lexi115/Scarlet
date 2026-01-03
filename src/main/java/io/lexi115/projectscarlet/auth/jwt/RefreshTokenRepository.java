package io.lexi115.projectscarlet.auth.jwt;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository class for operations related to refresh tokens.
 *
 * @author Lexi115
 * @since 1.0
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken, @NonNull Long> {
    /**
     * Finds a refresh token by its hash.
     *
     * @param tokenHash The hash.
     * @return An {@link Optional} containing the refresh token if found, or an empty one otherwise.
     * @since 1.0
     */
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    /**
     * Deletes all refresh tokens linked to a certain user.
     *
     * @param userId The user ID.
     * @since 1.0
     */
    void deleteAllByUserId(UUID userId);

    /**
     * Deletes all refresh tokens that had been expired for a certain period of time.
     *
     * @param dateLimit The date limit.
     * @since 1.0
     */
    void deleteAllByExpirationDateLessThanEqual(LocalDateTime dateLimit);
}
