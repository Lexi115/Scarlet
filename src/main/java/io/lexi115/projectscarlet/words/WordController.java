package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.auth.AuthService;
import io.lexi115.projectscarlet.users.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
     * @since 1.0
     */
    @PostMapping("/randomWord")
    public void chooseRandomWord() {
        wordService.chooseRandomWord();
    }

    /**
     * Returns the solution to the game.
     *
     * @return The solution word.
     * @since 1.0
     */
    @GetMapping("/solution")
    public SolutionResponse getSolution() {
        return wordService.getSolution();
    }

}
