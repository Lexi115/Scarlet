package io.lexi115.projectscarlet.auth.jwt;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken, @NonNull Long> {
    boolean existsByTokenHash(String tokenHash);

    void deleteByTokenHash(String tokenHash);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteAllByUserId(UUID userId);

    void deleteAllByExpirationDateLessThanEqual(LocalDateTime expirationDate);
}
