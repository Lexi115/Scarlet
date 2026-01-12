package io.lexi115.projectscarlet.users;

import io.lexi115.projectscarlet.errors.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for user-related operations.
 *
 * @author Lexi115
 * @since 1.0
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Operations related to users and accounts.")
public class UserController {

    /**
     * The user service.
     */
    private UserService userService;

    /**
     * Returns a user by providing the username.
     *
     * @param username The username. Cannot be blank.
     * @return A summary of the retrieved user.
     * @since 1.0
     */
    @GetMapping("/{username}")
    @Operation(summary = "Get user by username", description = "Returns a user by providing the username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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
    @Operation(summary = "Create a user", description = "Creates a user account by providing a username and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "409", description = "Username already in use")
    })
    public UserSummary createUser(@Valid @RequestBody final CreateUserRequest request) {
        return userService.createUser(request);
    }

    /**
     * Returns a page of the users' leaderboard (sorted by most wins).
     *
     * @param page The page number (must be at least 1).
     * @return The leaderboard page.
     * @since 1.0
     */
    @GetMapping("/leaderboard")
    @Operation(summary = "Get user leaderboard", description = "Returns a page of the users' leaderboard (sorted by most wins).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leaderboard retrieved"),
    })
    public List<LeaderboardEntry> getLeaderboard(
            @RequestParam(name = "page", defaultValue = "1") @Min(1) final int page
    ) {
        return userService.getLeaderboard(page);
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
