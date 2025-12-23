package io.lexi115.projectscarlet.users;

import lombok.NonNull;
import org.springframework.stereotype.Component;

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
}
