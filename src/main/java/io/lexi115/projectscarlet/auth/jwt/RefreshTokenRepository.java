package io.lexi115.projectscarlet.auth.jwt;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken, @NonNull Long> {
    boolean existsByTokenHash(String tokenHash);

    void deleteByTokenHash(String tokenHash);
}
