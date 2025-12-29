package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.errors.ErrorResponse;
import io.lexi115.projectscarlet.users.UserDetailsSummary;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

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
        return authService.getAuthenticatedUser();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onBadCredentials() {
        return new ErrorResponse("Incorrect credentials!");
    }
}
