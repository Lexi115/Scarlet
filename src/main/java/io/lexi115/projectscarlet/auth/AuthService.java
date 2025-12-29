package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.users.AuthenticatedUserSummary;
import io.lexi115.projectscarlet.users.UserMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private AuthenticationManager authenticationManager;

    public AuthenticatedUserSummary login(final @NonNull LoginRequest request) {
        var credentials = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var authentication = authenticationManager.authenticate(credentials);
        var userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
        return userMapper.toAuthenticatedSummary(userDetails);
    }
}
