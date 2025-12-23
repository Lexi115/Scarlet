package io.lexi115.projectscarlet.auth;

import io.lexi115.projectscarlet.errors.ErrorResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Authentication login(@Valid @RequestBody final LoginRequest request) {
        return authService.login(request);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse onAuthenticationError(final AuthenticationException e) {
        return new ErrorResponse("Auth error!");
    }
}
