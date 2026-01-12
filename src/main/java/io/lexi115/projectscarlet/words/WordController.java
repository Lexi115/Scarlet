package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.auth.AuthService;
import io.lexi115.projectscarlet.errors.ErrorResponse;
import io.lexi115.projectscarlet.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for word-related operations (basically the game itself).
 *
 * @author Lexi115
 * @since 1.0
 */
@RestController
@RequestMapping("/words")
@CrossOrigin
@AllArgsConstructor
@Tag(name = "Words", description = "Operations related to the game itself.")
public class WordController {

    /**
     * The user service.
     */
    private final UserService userService;

    /**
     * The authentication service.
     */
    private final AuthService authService;

    /**
     * The word service.
     */
    private WordService wordService;

    /**
     * Tries guessing the solution to the game. If a user is authenticated, it will increment his win counter in case
     * he guesses right.
     *
     * @param request The guess request.
     * @return The guess response, containing info such as whether the guess was correct.
     * @since 1.0
     */
    @PostMapping("/guess")
    @Operation(summary = "Guess the word", description = "Lets the user (or guest) try guessing the solution.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valid guess (not necessarily correct)"),
            @ApiResponse(responseCode = "400", description = "Invalid guess"),
    })
    public GuessResponse guessWord(@Valid @RequestBody final GuessRequest request) {
        var response = wordService.guessWord(request);
        var authenticatedUser = authService.getAuthenticatedUser();
        if (authenticatedUser != null) {
            userService.incrementUserWins(response, authenticatedUser.getUsername());
        }
        return response;
    }

    /**
     * Randomly chooses a word from the word pool and saves it in the cache.
     *
     * @return The chosen word wrapped in a {@link SolutionResponse}.
     * @since 1.0
     */
    @Operation(summary = "Random word", description = "The system chooses a random word.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random word chosen"),
    })
    @PostMapping("/randomWord")
    public SolutionResponse chooseRandomWord() {
        return new SolutionResponse(wordService.chooseRandomWord());
    }

    /**
     * Returns the solution to the game.
     *
     * @return The solution word wrapped in a {@link SolutionResponse}.
     * @since 1.0
     */
    @Operation(summary = "Get solution", description = "Reveals the solution to the game.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solution provided")
    })
    @GetMapping("/solution")
    public SolutionResponse getSolution() {
        return wordService.getSolution();
    }

    /**
     * Method that handles exceptions when an invalid word is provided.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(InvalidWordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onInvalidWord(@NonNull final InvalidWordException e) {
        return new ErrorResponse(e.getMessage());
    }

}
