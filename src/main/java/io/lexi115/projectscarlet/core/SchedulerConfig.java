package io.lexi115.projectscarlet.core;

import io.lexi115.projectscarlet.auth.jwt.RefreshTokenService;
import io.lexi115.projectscarlet.words.WordService;
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
 * Configuration class for scheduling asynchronous jobs.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

    /**
     * The word service.
     */
    private final WordService wordService;

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
     * @param wordService         The word service.
     * @param refreshTokenService The refresh token service.
     * @since 1.0
     */
    public SchedulerConfig(final WordService wordService, final RefreshTokenService refreshTokenService) {
        this.wordService = wordService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Job that chooses a random word periodically.
     *
     * @since 1.0
     */
    @Scheduled(cron = "${scheduler.jobs.daily-word.cron}")
    public void chooseDailyWord() {
        var word = wordService.chooseRandomWord();
        LOGGER.info("Chosen daily word: {}", word);
    }

    /**
     * Job that removes old refresh tokens from the database to clear up space.
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
