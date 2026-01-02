package io.lexi115.projectscarlet.auth.jwt;

import io.lexi115.projectscarlet.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final JwtConfig jwtConfig;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public boolean refreshTokenExists(final String tokenString) {
        return refreshTokenRepository.existsByTokenHash(tokenString);
    }

    @Transactional
    public void addRefreshToken(final String tokenString, final String username) {
        if (refreshTokenExists(tokenString)) {
            return;
        }
        var user = userService.getUserByUsername(username);
        var refreshToken = new RefreshToken();
        refreshToken.setTokenHash(tokenString);
        refreshToken.setUserId(user.getId());
        var expirationDate = LocalDateTime.ofEpochSecond(
                jwtConfig.getRefreshTokenDuration(), 0, ZoneOffset.UTC);
        refreshToken.setExpirationDate(expirationDate);
        refreshTokenRepository.save(refreshToken);
    }

    public void removeRefreshToken(final String tokenString) {
        refreshTokenRepository.deleteByTokenHash(tokenString);
    }
}
