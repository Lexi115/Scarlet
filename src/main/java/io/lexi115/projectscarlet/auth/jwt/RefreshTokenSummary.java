package io.lexi115.projectscarlet.auth.jwt;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RefreshTokenSummary {
    private String tokenHash;
    private UUID userId;
    private LocalDateTime expirationDate;
    private boolean revoked;
}
