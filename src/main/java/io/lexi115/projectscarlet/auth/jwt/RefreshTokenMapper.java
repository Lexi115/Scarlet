package io.lexi115.projectscarlet.auth.jwt;

import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {
    public RefreshTokenSummary toSummary(final RefreshToken refreshToken) {
        var summary = new RefreshTokenSummary();
        summary.setTokenHash(refreshToken.getTokenHash());
        summary.setUserId(refreshToken.getUserId());
        summary.setExpirationDate(refreshToken.getExpirationDate());
        return summary;
    }
}
