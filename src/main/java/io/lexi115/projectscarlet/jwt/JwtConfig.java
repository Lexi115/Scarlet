package io.lexi115.projectscarlet.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

    private String secret;

    private Integer accessTokenExpiration;

    private Integer refreshTokenExpiration;
}
