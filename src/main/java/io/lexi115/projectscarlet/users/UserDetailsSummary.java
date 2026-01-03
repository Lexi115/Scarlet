package io.lexi115.projectscarlet.users;

import lombok.Data;

import java.util.Collection;

/**
 * DTO for user details, generally used to show info about the currently authenticated user.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class UserDetailsSummary {

    /**
     * The username.
     */
    private String username;

    /**
     * The user's roles.
     */
    private Collection<UserRoleSummary> roles;

}
