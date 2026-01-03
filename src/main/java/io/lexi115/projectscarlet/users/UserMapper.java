package io.lexi115.projectscarlet.users;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Mapper class used to convert user entities to their DTO counterparts and vice versa.
 *
 * @author Lexi115
 * @since 1.0
 */
@Component
public class UserMapper {

    /**
     * Converts a {@link CreateUserRequest} into a {@link User} entity. Note that the password in this phase is still
     * in plain text.
     *
     * @param request The user creation request.
     * @return The resulting user entity.
     * @since 1.0
     */
    public @NonNull User toUser(@NonNull final CreateUserRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return user;
    }

    /**
     * Converts a {@link User} entity into a {@link UserSummary} DTO.
     *
     * @param user The user entity.
     * @return The resulting user summary.
     * @since 1.0
     */
    public @NonNull UserSummary toSummary(@NonNull final User user) {
        var summary = new UserSummary();
        summary.setId(user.getId());
        summary.setUsername(user.getUsername());
        summary.setWins(user.getWins());
        return summary;
    }

    /**
     * Converts {@link UserDetails} into a {@link UserDetailsSummary} DTO.
     *
     * @param userDetails The user details.
     * @return The resulting user details summary.
     * @since 1.0
     */
    public @NonNull UserDetailsSummary toSummary(@NonNull final UserDetails userDetails) {
        var summary = new UserDetailsSummary();
        summary.setUsername(userDetails.getUsername());
        var roles = new HashSet<UserRoleSummary>();
        userDetails.getAuthorities().forEach(authority -> {
            var authorityName = authority.getAuthority();
            // Consider only granted authorities that start with "ROLE_"
            if (authorityName != null && authorityName.startsWith("ROLE_")) {
                roles.add(new UserRoleSummary(authorityName.substring(5)));
            }
        });
        summary.setRoles(roles);
        return summary;
    }

}
