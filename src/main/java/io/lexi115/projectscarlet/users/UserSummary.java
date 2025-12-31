package io.lexi115.projectscarlet.users;

import lombok.Data;

/**
 * DTO for a user. Shows basic info like the username and the amount of game wins.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class UserSummary {
    /**
     * The username.
     */
    private String username;

    /**
     * The amount of game wins.
     */
    private Integer wins;
}
