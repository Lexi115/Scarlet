package io.lexi115.projectscarlet.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO representing a login request.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class LoginRequest {
    /**
     * The username. Cannot be blank.
     */
    @NotBlank
    private String username;

    /**
     * The user password. Cannot the blank.
     */
    @NotBlank
    private String password;
}
