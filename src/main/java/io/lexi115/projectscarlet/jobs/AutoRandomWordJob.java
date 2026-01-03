package io.lexi115.projectscarlet.jobs;

import io.lexi115.projectscarlet.words.WordService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Job that chooses a random word periodically.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
@EnableScheduling
@AllArgsConstructor
public class AutoRandomWordJob {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoRandomWordJob.class);

    /**
     * The word service.
     */
    private final WordService wordService;

    /**
     * Job execution logic.
     *
     * @since 1.0
     */
    @Scheduled(cron = "${scheduler.jobs.auto-random-word.cron}")
    public void chooseRandomWord() {
        var word = wordService.chooseRandomWord();
        LOGGER.info("Chosen random word: {}", word);
    }

}
