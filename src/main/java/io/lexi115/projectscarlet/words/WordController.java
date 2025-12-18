package io.lexi115.projectscarlet.words;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
public class WordController {

    private WordService wordService;

    @PostMapping("/guess")
    public GuessResponse guessWord(@Valid @RequestBody final GuessRequest request) {
        return wordService.guessWord(request);
    }

    @PostMapping("/randomWord")
    public void chooseRandomWord() {
        wordService.chooseRandomWord();
    }

    @GetMapping("/solution")
    public String getSolution() {
        return wordService.getSolution();
    }
}
