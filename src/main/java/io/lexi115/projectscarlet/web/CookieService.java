package io.lexi115.projectscarlet.web;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * A service class for managing HTTP cookies.
 *
 * @author Lexi115
 * @since 1.0
 */
@AllArgsConstructor
@Service
public class CookieService {
    /**
     * Creates a secure HTTPS-only cookie with the specified configurations.
     *
     * @param name   The name of the cookie.
     * @param value  The value to be stored in the cookie.
     * @param path   The path within which this cookie is accessible.
     * @param maxAge The maximum age of the cookie in seconds. A null value indicates a session cookie.
     * @return The created {@link Cookie} object configured with the provided parameters
     * and additional security settings.
     * @since 1.0
     */
    public Cookie createSecureCookie(
            final String name,
            final String value,
            final String path,
            final Integer maxAge
    ) {
        var cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
