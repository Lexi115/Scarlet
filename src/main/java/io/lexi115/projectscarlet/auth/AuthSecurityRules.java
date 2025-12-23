package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.security.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * Class implementing security rules specifically for endpoints under the <code>/auth</code> path.
 */
@Component
public class AuthSecurityRules implements SecurityRules {

    /**
     * The base path for all auth endpoints.
     */
    private static final String BASE_PATH = "/auth";

    /**
     * Configures security rules.
     *
     * @param registry the registry used to define authorization rules for HTTP request matching patterns.
     */
    @Override
    public void configure(
            final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry
    ) {
        registry
                // Login
                .requestMatchers(HttpMethod.POST, BASE_PATH + "/login").permitAll();
    }
}
