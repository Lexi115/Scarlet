package io.lexi115.projectscarlet.cache;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {

    private RedisTemplate<String, String> template;

    public String get(final String key) {
        return template.opsForValue().get(key);
    }

    public String get(final String key, final String defaultValue) {
        var value = template.opsForValue().get(key);
        return value != null ? value : defaultValue;
    }

    public void set(final String key, final String value) {
        template.opsForValue().set(key, value);
    }
}
