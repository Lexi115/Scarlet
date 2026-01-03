package io.lexi115.projectscarlet.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String accessToken;

    /**
     * The refresh token.
     */
    @JsonIgnore
    private String refreshToken;

}
