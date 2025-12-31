package io.lexi115.projectscarlet.core;

import io.lexi115.projectscarlet.words.WordService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configuration class for scheduling asynchronous jobs.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
@EnableScheduling
@AllArgsConstructor
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
     * Job that chooses a random word periodically.
     *
     * @since 1.0
     */
    @Scheduled(cron = "${scheduler.jobs.daily-word.cron}")
    public void chooseDailyWord() {
        var word = wordService.chooseRandomWord();
        LOGGER.info("Chosen daily word: {}", word);
    }
}
