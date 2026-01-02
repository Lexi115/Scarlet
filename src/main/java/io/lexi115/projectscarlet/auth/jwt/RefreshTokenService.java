package io.lexi115.projectscarlet.auth.jwt;

import io.lexi115.projectscarlet.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final JwtConfig jwtConfig;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenSummary getRefreshTokenByString(final String tokenString) {
        var token = refreshTokenRepository.findByTokenHash(tokenString).orElse(null);
        return token != null ? refreshTokenMapper.toSummary(token) : null;
    }

    @Transactional
    public void addRefreshToken(final String tokenString, final String username) {
        if (getRefreshTokenByString(tokenString) != null) {
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeAllRefreshTokens(final UUID userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }

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
