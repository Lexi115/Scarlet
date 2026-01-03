package io.lexi115.projectscarlet.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for app-specific properties.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "scarlet")
@Data
public class ScarletConfig {

    /**
     * The default game word when no other word is chosen (present in cache).
     */
    private String defaultWord;

    /**
     * The duration of the cookie used to store the refresh token.
     */
    private Integer refreshTokenCookieDuration;

}

