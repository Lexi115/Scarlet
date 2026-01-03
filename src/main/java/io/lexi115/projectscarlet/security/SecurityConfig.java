package io.lexi115.projectscarlet.security;

import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;

/**
 * Configuration class for setting up application security. It defines authentication mechanisms and establishes
 * rules for securing application endpoints.
 *
 * @author Lexi115
 * @since 1.0
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
     * The JWT authorization filter.
     */
    private final Filter jwtAuthorizationFilter;

    /**
     * Creates and configures a {@link PasswordEncoder} bean for application-wide use.
     *
     * @return A {@code PasswordEncoder} instance.
     * @since 1.0
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and configures an {@link AuthenticationManager} bean for application-wide use.
     *
     * @param config The {@link AuthenticationConfiguration} used to provide the authentication setup.
     * @return An {@code AuthenticationManager} instance to manage authentication processes.
     * @since 1.0
     */
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    /**
     * Configures and returns a {@link SecurityFilterChain} bean for securing HTTP requests.
     *
     * @param http The {@link HttpSecurity} instance used to define security configurations.
     * @return A {@link SecurityFilterChain} instance representing the configured security chain.
     * @since 1.0
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) {
        http
                .sessionManagement(configurer
                        -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    securityRulesCollection.forEach(rules -> rules.configure(registry));
                    registry
                            .requestMatchers("/error").permitAll()
                            .anyRequest().authenticated();
                })
                .exceptionHandling(configurer -> {
                    configurer.authenticationEntryPoint(
                            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    configurer.accessDeniedHandler((req, resp, e)
                            -> resp.setStatus(HttpStatus.FORBIDDEN.value()));
                })
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
