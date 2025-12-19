package io.lexi115.projectscarlet.configs;

import io.lexi115.projectscarlet.words.WordService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    private final WordService wordService;

    @Scheduled(cron = "${scheduler.jobs.daily-word.cron}")
    public void chooseDailyWord() {
        var word = wordService.chooseRandomWord();
        logger.info("Chosen daily word: {}", word);
    }
}
