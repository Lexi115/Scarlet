package io.lexi115.projectscarlet.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * Represents a contract for defining security rules to configure HTTP request authorization.
 */
public interface SecurityRules {
    /**
     * Configures security rules.
     *
     * @param registry the registry used to define authorization rules for HTTP request matching patterns.
     */
    void configure(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry
    );
}
