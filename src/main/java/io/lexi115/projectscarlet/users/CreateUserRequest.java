package io.lexi115.projectscarlet.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateUserRequest {

    @NotBlank
    @Length(min = 1, max = 255)
    private String username;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_!$&@#]{1,255}")
    private String password;
}
