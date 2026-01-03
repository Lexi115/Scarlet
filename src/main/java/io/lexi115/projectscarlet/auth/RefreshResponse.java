package io.lexi115.projectscarlet.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing an access token refresh response.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class RefreshResponse {

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
