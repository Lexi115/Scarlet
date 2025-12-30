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

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody final LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserDetailsSummary getAuthenticatedUser() {
        return Optional.ofNullable(authService.getAuthenticatedUser()).orElseThrow(
                () -> new UserNotFoundException("Authenticated user not found!")
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onBadCredentials() {
        return new ErrorResponse("Incorrect credentials!");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onUserNotFound(@NonNull final UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}
