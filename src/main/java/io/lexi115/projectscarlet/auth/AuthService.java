package io.lexi115.projectscarlet.auth;

import io.jsonwebtoken.JwtException;
import io.lexi115.projectscarlet.auth.jwt.JwtService;
import io.lexi115.projectscarlet.auth.jwt.RefreshTokenService;
import io.lexi115.projectscarlet.users.UserDetailsSummary;
import io.lexi115.projectscarlet.users.UserMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final RefreshTokenService refreshTokenService;

    /**
     * The user details service.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Requests a user login.
     *
     * @param request The login request.
     * @return The login response, containing the access and refresh tokens.
     * @since 1.0
     */
    public LoginResponse login(final @NonNull LoginRequest request) {
        var credentials = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var authentication = authenticationManager.authenticate(credentials);
        var userDetails = (UserDetails) Objects.requireNonNull(authentication.getPrincipal());
        var tokens = generateTokens(userDetails);
        return new LoginResponse(tokens[0], tokens[1]);
    }

    @Transactional
    public RefreshResponse refreshAccessToken(final @NonNull String oldRefreshToken) {
        if (!refreshTokenService.refreshTokenExists(oldRefreshToken)) {
            throw new JwtException("Refresh token not found in DB!");
        }
        var refreshJwt = jwtService.decodeJwt(oldRefreshToken);
        var subject = refreshJwt.getSubject();
        if (refreshJwt.isExpired() || subject == null) {
            throw new JwtException("Invalid refresh token");
        }
        var userDetails = userDetailsService.loadUserByUsername(subject);
        refreshTokenService.removeRefreshToken(oldRefreshToken);
        var tokens = generateTokens(userDetails);
        return new RefreshResponse(tokens[0], tokens[1]);
    }

    private String[] generateTokens(@NonNull final UserDetails userDetails) {
        var accessToken = jwtService.createAccessToken(userDetails);
        var refreshToken = jwtService.createRefreshToken(userDetails);
        refreshTokenService.addRefreshToken(refreshToken, userDetails.getUsername());
        return new String[]{accessToken, refreshToken};
    }

    public void logout(final @NonNull String refreshToken) {
        refreshTokenService.removeRefreshToken(refreshToken);
    }

    /**
     * Returns the authenticated user if present in the security context.
     *
     * @return A summary of the authenticated user's details, or <code>null</code> if not present.
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
