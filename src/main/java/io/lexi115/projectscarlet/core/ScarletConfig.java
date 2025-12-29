package io.lexi115.projectscarlet.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "scarlet")
@Data
public class ScarletConfig {

    private String defaultWord;
}

