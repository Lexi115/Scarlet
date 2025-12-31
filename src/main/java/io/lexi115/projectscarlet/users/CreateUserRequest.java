package io.lexi115.projectscarlet.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for a user creation request.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class CreateUserRequest {
    /**
     * The username. Cannot be blank.
     */
    @NotBlank
    @Length(min = 1, max = 255)
    private String username;

    /**
     * The user's password. Cannot be blank.
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_!$&@#]{1,255}")
    private String password;
}
