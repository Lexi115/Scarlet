package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.errors.ErrorResponse;
import io.lexi115.projectscarlet.users.UserDetailsSummary;
import io.lexi115.projectscarlet.users.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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
     * Requests a user login.
     *
     * @param request The login request.
     * @return The login response, containing the access token.
     * @since 1.0
     */
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody final LoginRequest request) {
        return authService.login(request);
    }

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
     * Method that handles exceptions when incorrect user credentials are provided.
     *
     * @return The error response.
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onBadCredentials() {
        return new ErrorResponse("Incorrect credentials!");
    }

    /**
     * Method that handles exceptions when a user is not found in the security context.
     *
     * @param e The exception.
     * @return The error response.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onUserNotFound(@NonNull final UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
