package io.lexi115.projectscarlet.jobs;

import io.lexi115.projectscarlet.auth.jwt.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Job that removes old refresh tokens from the database to clear up space.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class RemoveOldRefreshTokensJob {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveOldRefreshTokensJob.class);

    /**
     * The refresh token service.
     */
    private final RefreshTokenService refreshTokenService;

    /**
     * Refresh token's lifecycle (in seconds).
     */
    @Value("${scheduler.jobs.remove-old-refresh-tokens.refresh-token-lifecycle}")
    private Long refreshTokenLifecycle;

    /**
     * Constructor.
     *
     * @param refreshTokenService The refresh token service.
     * @since 1.0
     */
    public RemoveOldRefreshTokensJob(final RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Job execution logic.
     *
     * @since 1.0
     */
    @Scheduled(cron = "${scheduler.jobs.remove-old-refresh-tokens.cron}")
    public void removeOldRefreshTokens() {
        var dateLimit = LocalDateTime.ofEpochSecond(
                Instant.now().minusSeconds(refreshTokenLifecycle).getEpochSecond(), 0, ZoneOffset.UTC);
        refreshTokenService.removeAllRefreshTokensBefore(dateLimit);
        LOGGER.info("Removed old refresh tokens!");
    }

}
