package io.lexi115.projectscarlet.cache;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link CacheService} that provides interaction with a Redis server for caching values.
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class RedisCacheService implements CacheService {

    /**
     * The template used to interact with the Redis server instance.
     */
    private RedisTemplate<String, String> template;

    /**
     * Returns a value given a key.
     *
     * @param key The key.
     * @return The value.
     * @since 1.0
     */
    @Override
    public String get(@NonNull final String key) {
        return template.opsForValue().get(key);
    }

    /**
     * Returns a value given a key.
     *
     * @param key          The key.
     * @param defaultValue The default value returned if the key could not be found.
     * @return The value.
     * @since 1.0
     */
    @Override
    public String get(@NonNull final String key, final String defaultValue) {
        var value = template.opsForValue().get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Sets a value.
     *
     * @param key   The key.
     * @param value The value.
     */
    @Override
    public void set(@NonNull final String key, final String value) {
        template.opsForValue().set(key, value);
    }

}
