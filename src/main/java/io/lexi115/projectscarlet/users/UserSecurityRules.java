package io.lexi115.projectscarlet.users;

import io.lexi115.projectscarlet.security.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * Class implementing security rules specifically for endpoints under the <code>/users</code> path.
 */
@Component
public class UserSecurityRules implements SecurityRules {

    /**
     * The base path for all auth endpoints.
     */
    private static final String BASE_PATH = "/users";

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
                // Get user by username
                .requestMatchers(HttpMethod.GET, BASE_PATH + "/{username}").permitAll()

                // Create user
                .requestMatchers(HttpMethod.POST, BASE_PATH).permitAll();
    }
}
