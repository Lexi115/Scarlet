package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.security.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * Class implementing security rules specifically for endpoints under the <code>/auth</code> path.
 *
 * @author Lexi115
 * @since 1.0
 */
@Component
public class AuthSecurityRules implements SecurityRules {

    /**
     * The base path for the endpoints.
     */
    private static final String BASE_PATH = "/auth";

    /**
     * Configures security rules.
     *
     * @param registry The registry used to define authorization rules for HTTP request matching patterns.
     * @since 1.0
     */
    @Override
    public void configure(
            final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry
    ) {
        registry
                // Login
                .requestMatchers(HttpMethod.POST, BASE_PATH + "/login").permitAll()

                // Logout
                .requestMatchers(HttpMethod.POST, BASE_PATH + "/logout").permitAll()

                // Refresh access token
                .requestMatchers(HttpMethod.POST, BASE_PATH + "/refresh").permitAll();
    }

}
