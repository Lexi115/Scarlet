package io.lexi115.projectscarlet.auth.jwt;

import io.lexi115.projectscarlet.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenMapper refreshTokenMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public boolean refreshTokenExists(final String tokenString) {
        return refreshTokenRepository.existsByTokenHash(tokenString);
    }

    public RefreshTokenSummary addRefreshToken(
            final String tokenString,
            final String username,
            final long expiration
    ) {
        var user = userService.getUserByUsername(username);
        var refreshToken = new RefreshToken();
        refreshToken.setTokenHash(tokenString);
        refreshToken.setUserId(user.getId());
        refreshToken.setExpirationDate(LocalDateTime.ofEpochSecond(expiration, 0, ZoneOffset.UTC));
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshTokenMapper.toSummary(refreshToken);
    }

    public void removeRefreshToken(final String tokenString) {
        refreshTokenRepository.deleteByTokenHash(tokenString);
    }
}
