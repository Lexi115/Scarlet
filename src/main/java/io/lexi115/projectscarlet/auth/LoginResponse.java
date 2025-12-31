package io.lexi115.projectscarlet.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing a login response.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    /**
     * The access token.
     */
    private String token;
}
