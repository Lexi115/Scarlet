package io.lexi115.projectscarlet.security;

import io.lexi115.projectscarlet.auth.jwt.JwtAuthorizationFilter;
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

    private final Filter jwtAuthorizationFilter;

    /**
     * Creates and configures a {@link PasswordEncoder} bean for application-wide use.
     *
     * @return a {@code PasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
