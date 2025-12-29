package io.lexi115.projectscarlet.users;

import io.lexi115.projectscarlet.errors.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{username}")
    public UserSummary getUserByUsername(@PathVariable @NotBlank final String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping
    public UserSummary createUser(@Valid @RequestBody final CreateUserRequest request) {
        return userService.createUser(request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onUserNotFound(@NonNull final UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse onUserAlreadyExists(@NonNull final UserAlreadyExistsException e) {
        return new ErrorResponse(e.getMessage());
    }
}
