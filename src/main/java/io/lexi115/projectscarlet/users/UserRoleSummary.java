package io.lexi115.projectscarlet.users;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for user roles.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class UserRoleSummary {
    /**
     * The role name.
     */
    private String role;
}
