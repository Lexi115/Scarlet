package io.lexi115.projectscarlet.users;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper {

    public @NonNull User toUser(@NonNull final CreateUserRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        return user;
    }

    public @NonNull UserSummary toSummary(@NonNull final User user) {
        var summary = new UserSummary();
        summary.setUsername(user.getUsername());
        summary.setWins(user.getWins());
        return summary;
    }

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
