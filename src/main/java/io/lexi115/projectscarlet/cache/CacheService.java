package io.lexi115.projectscarlet.cache;

import lombok.NonNull;

/**
 * Interface for service classes that provide caching features.
 *
 * @author Lexi115
 * @since 1.0
 */
public interface CacheService {
    /**
     * Returns a value given a key.
     *
     * @param key The key.
     * @return The value.
     * @since 1.0
     */
    String get(@NonNull String key);

    /**
     * Returns a value given a key.
     *
     * @param key          The key.
     * @param defaultValue The default value returned if the key could not be found.
     * @return The value.
     * @since 1.0
     */
    String get(@NonNull String key, String defaultValue);

    /**
     * Sets a value.
     *
     * @param key   The key.
     * @param value The value.
     */
    void set(@NonNull String key, String value);
}
