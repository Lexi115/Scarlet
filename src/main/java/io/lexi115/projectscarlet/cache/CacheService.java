package io.lexi115.projectscarlet.cache;

import lombok.NonNull;

public interface CacheService {

    String get(@NonNull String key);

    String get(@NonNull String key, String defaultValue);

    void set(@NonNull String key, String value);
}
