package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.users.UserDetailsSummary;
import io.lexi115.projectscarlet.users.UserMapper;
import io.lexi115.projectscarlet.users.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private AuthenticationManager authenticationManager;

    public UserDetailsSummary login(final @NonNull LoginRequest request) {
        var credentials = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var authentication = authenticationManager.authenticate(credentials);
        var userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
        return userMapper.toSummary(userDetails);
    }

    public UserDetailsSummary getAuthenticatedUser() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        if (authentication == null) {
            throw new UserNotFoundException("Could not find user in context");
        }
        var userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
        return userMapper.toSummary(userDetails);
    }
}
