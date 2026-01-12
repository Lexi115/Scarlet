package io.lexi115.projectscarlet.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for JSON converters.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
public class JsonConfig {

    /**
     * Returns an {@link ObjectMapper} bean.
     *
     * @return An object mapper.
     * @since 1.0
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
