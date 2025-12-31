package io.lexi115.projectscarlet.auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for operations related to JSON Web Tokens (JWT).
 *
 * @author Lexi115
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    /**
     * The secret string used by the server to sign JWTs (giving them authenticity).
     */
    private String secret;

    /**
     * The access token expiration date (expressed in milliseconds).
     */
    private Integer accessTokenExpiration;

    /**
     * The refresh token expiration date (expressed in milliseconds).
     */
    private Integer refreshTokenExpiration;
}
