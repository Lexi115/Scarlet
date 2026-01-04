package io.lexi115.projectscarlet.core;

import io.lexi115.projectscarlet.security.SecurityRules;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

/**
 * Class implementing security rules for general endpoints.
 *
 * @author Lexi115
 * @since 1.0
 */
@Component
public class CoreSecurityRules implements SecurityRules {

    /**
     * Configures security rules.
     *
     * @param registry the registry used to define authorization rules for HTTP request matching patterns.
     * @since 1.0
     */
    @Override
    public void configure(
            final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
                // Root
                .requestMatchers("/").permitAll()
                .requestMatchers("/index.html").permitAll()

                // Error page
                .requestMatchers("/error").permitAll()

                // API documentation
                .requestMatchers("/api-docs/**").permitAll()

                // Swagger UI
                .requestMatchers("/swagger-ui/**").permitAll();
    }

}
