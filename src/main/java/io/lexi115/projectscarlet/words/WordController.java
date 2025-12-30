package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.auth.AuthService;
import io.lexi115.projectscarlet.users.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/words")
@CrossOrigin
@AllArgsConstructor
public class WordController {

    private final UserService userService;
    private final AuthService authService;
    private WordService wordService;

    @PostMapping("/guess")
    public GuessResponse guessWord(@Valid @RequestBody final GuessRequest request) {
        var response = wordService.guessWord(request);
        var authenticatedUser = authService.getAuthenticatedUser();
        if (authenticatedUser != null) {
            userService.incrementUserWins(response, authenticatedUser.getUsername());
        }
        return response;
    }

    @PostMapping("/randomWord")
    public void chooseRandomWord() {
        wordService.chooseRandomWord();
    }

    @GetMapping("/solution")
    public SolutionResponse getSolution() {
        return wordService.getSolution();
    }
}
