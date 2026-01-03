package io.lexi115.projectscarlet.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration class for a Redis service.
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
public class RedisConfig {

    /**
     * Returns a {@link RedisTemplate} bean.
     *
     * @param connectionFactory The connection factory.
     * @return The bean.
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate(final RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
