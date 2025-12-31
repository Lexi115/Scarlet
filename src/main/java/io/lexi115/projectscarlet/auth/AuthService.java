package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.auth.jwt.JwtService;
import io.lexi115.projectscarlet.users.UserDetailsSummary;
import io.lexi115.projectscarlet.users.UserMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service class for authentication-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class AuthService {
    /**
     * The authentication manager (the one that actually does the heavy lifting).
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The user mapper.
     */
    private final UserMapper userMapper;

    /**
     * The JWT service.
     */
    private final JwtService jwtService;

    /**
     * Requests a user login.
     *
     * @param request The login request.
     * @return The login response, containing the access token.
     * @since 1.0
     */
    public LoginResponse login(final @NonNull LoginRequest request) {
        var credentials = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var authentication = authenticationManager.authenticate(credentials);
        var userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
        var jwt = jwtService.createJwt(userDetails);
        return new LoginResponse(jwtService.encodeJwt(jwt));
    }

    /**
     * Returns the authenticated user if present in the security context.
     *
     * @return A summary of the authenticated user's details.
     * @since 1.0
     */
    public UserDetailsSummary getAuthenticatedUser() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }
        var userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
        return userMapper.toSummary(userDetails);
    }
}
