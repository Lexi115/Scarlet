package io.lexi115.projectscarlet.users;

import io.lexi115.projectscarlet.errors.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for user-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    /**
     * The user service.
     */
    private UserService userService;

    /**
     * Gets a user by the username.
     *
     * @param username The username. Cannot be blank.
     * @return A summary of the retrieved user.
     * @since 1.0
     */
    @GetMapping("/{username}")
    public UserSummary getUserByUsername(@PathVariable @NotBlank final String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Creates a new user.
     *
     * @param request The user creation request.
     * @return A summary of the created user.
     * @since 1.0
     */
    @PostMapping
    public UserSummary createUser(@Valid @RequestBody final CreateUserRequest request) {
        return userService.createUser(request);
    }

    /**
     * Method that handles exceptions when a user already exists.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse onUserAlreadyExists(@NonNull final UserAlreadyExistsException e) {
        return new ErrorResponse(e.getMessage());
    }

}
