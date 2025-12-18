package io.lexi115.projectscarlet.words;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
public class WordController {

    private WordService wordService;

    private RedisTemplate<String, String> template;

    @PostMapping("/guess")
    public GuessResponse guessWord(@Valid @RequestBody final GuessRequest request) {
        return wordService.guessWord(request);
    }

    @GetMapping("/test")
    public void test() {
        template.opsForValue().set("my_test", "sbw");
    }

    @GetMapping("/test1")
    public void test1() {
        var out = template.opsForValue().get("my_test");
        System.out.println("output: " + out);
    }
}
