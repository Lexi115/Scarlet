package io.lexi115.projectscarlet.words;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word")
@AllArgsConstructor
public class WordController {

    private WordService wordService;

    @PostMapping("/guess")
    public GuessResponse guessWord(@Valid @RequestBody final GuessRequest request) {
        return wordService.guessWord(request);
    }
}
