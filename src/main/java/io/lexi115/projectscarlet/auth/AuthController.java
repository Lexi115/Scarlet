package io.lexi115.projectscarlet.auth;

import io.jsonwebtoken.JwtException;
import io.lexi115.projectscarlet.core.ScarletConfig;
import io.lexi115.projectscarlet.errors.ErrorResponse;
import io.lexi115.projectscarlet.users.UserDetailsSummary;
import io.lexi115.projectscarlet.users.UserNotFoundException;
import io.lexi115.projectscarlet.web.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class for authentication-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    /**
     * The authentication service.
     */
    private final AuthService authService;

    /**
     * The HTTP cookie utility service.
     */
    private final CookieService cookieService;

    /**
     * The app configuration.
     */
    private final ScarletConfig scarletConfig;

    /**
     * Returns the authenticated user if present.
     *
     * @return A summary of the authenticated user's details.
     * @since 1.0
     */
    @GetMapping("/me")
    public UserDetailsSummary getAuthenticatedUser() {
        return Optional.ofNullable(authService.getAuthenticatedUser()).orElseThrow(
                () -> new UserNotFoundException("Authenticated user not found!")
        );
    }

    /**
     * Requests a user login.
     *
     * @param request  The login request.
     * @param response The HTTP response object.
     * @return The login response, containing the access and refresh tokens.
     * @since 1.0
     */
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody final LoginRequest request,
            final HttpServletResponse response
    ) {
        var loginResponse = authService.login(request);
        var refreshTokenCookie = createRefreshTokenCookie(loginResponse.getRefreshToken());
        response.addCookie(refreshTokenCookie);
        return loginResponse;
    }

    /**
     * Requests a user logout.
     *
     * @param refreshToken The refresh token string.
     * @since 1.0
     */
    @PostMapping("/logout")
    public void logout(@CookieValue(name = "refreshToken") @NonNull final String refreshToken) {
        authService.logout(refreshToken);
    }

    /**
     * Refreshes the currently held access and refresh tokens.
     *
     * @param oldRefreshToken The refresh token string.
     * @param response        The HTTP response object.
     * @return The refresh response, containing the new tokens.
     * @since 1.0
     */
    @PostMapping("/refresh")
    public RefreshResponse refreshTokens(
            @CookieValue(name = "refreshToken") @NonNull final String oldRefreshToken,
            final HttpServletResponse response
    ) {
        var refreshResponse = authService.refreshTokens(oldRefreshToken);
        var refreshTokenCookie = createRefreshTokenCookie(refreshResponse.getRefreshToken());
        response.addCookie(refreshTokenCookie);
        return refreshResponse;
    }

    private Cookie createRefreshTokenCookie(final String refreshToken) {
        return cookieService.createSecureCookie(
                "refreshToken",
                refreshToken,
                "/auth",
                scarletConfig.getRefreshTokenCookieDuration()
        );
    }

    /**
     * Method that handles exceptions when incorrect user credentials are provided.
     *
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onBadCredentials() {
        return new ErrorResponse("Incorrect credentials!");
    }

    /**
     * Method that handles exceptions when a generic authentication error occurs.
     *
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onAuthenticationError() {
        return new ErrorResponse("Authentication error!");
    }

    /**
     * Method that handles exceptions when a user is not found in the security context.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onUserNotFound(@NonNull final UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Method that handles JSON Web Token (JWT) exceptions.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onJwtException(@NonNull final JwtException e) {
        return new ErrorResponse("Invalid JWT: " + e.getMessage());
    }
}
