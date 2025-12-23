package io.lexi115.projectscarlet.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

/**
 * Configuration class for setting up application security.
 * This class customizes Spring Security behavior, defines authentication mechanisms,
 * and establishes rules for securing application endpoints.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    /**
     * The individual security rules classes.
     */
    private final Collection<SecurityRules> securityRulesCollection;

    /**
     * The service class for retrieving user details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Creates and configures a {@link PasswordEncoder} bean for application-wide use.
     *
     * @return a {@code PasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Creates and configures an {@link AuthenticationProvider} bean for application-wide use.
     *
     * @return an {@code AuthenticationProvider} instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates and configures an {@link AuthenticationManager} bean for application-wide use.
     *
     * @param config the {@link AuthenticationConfiguration} used to provide the authentication setup.
     * @return an {@code AuthenticationManager} instance to manage authentication processes.
     */
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    /**
     * Configures and returns a {@link SecurityFilterChain} bean for securing HTTP requests.
     *
     * @param http the {@link HttpSecurity} instance used to define security configurations.
     * @return a {@link SecurityFilterChain} instance representing the configured security chain.
     * @throws Exception if any configuration error occurs during the creation of the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) {
        http
                // Setup security rules for different endpoints.
                .authorizeHttpRequests(c -> {
                    securityRulesCollection.forEach(r -> r.configure(c));
                    // Any other request requires users to be authenticated.
                    c.anyRequest().authenticated();
                });

        return http.build();
    }
}
