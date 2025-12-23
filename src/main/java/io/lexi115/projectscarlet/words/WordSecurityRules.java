package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.security.SecurityRules;
import io.lexi115.projectscarlet.users.UserRole;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * Class implementing security rules specifically for endpoints under the <code>/words</code> path.
 */
@Component
public class WordSecurityRules implements SecurityRules {

    /**
     * The base path for all auth endpoints.
     */
    private static final String BASE_PATH = "/words";

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
                // Guess word
                .requestMatchers(HttpMethod.POST, BASE_PATH + "/guess").permitAll()

                // Choose random word
                .requestMatchers(HttpMethod.POST, BASE_PATH + "/randomWord").hasRole(UserRole.ADMIN.name())

                // Get solution
                .requestMatchers(HttpMethod.GET, BASE_PATH + "/solution").permitAll();
    }
}
