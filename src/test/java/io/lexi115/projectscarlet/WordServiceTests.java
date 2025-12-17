package io.lexi115.projectscarlet;

import static org.junit.jupiter.api.Assertions.*;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

@AllArgsConstructor
public class WordServiceTests {

    private final WordService wordService = new WordService();

    @Test
    void test() {
        var word = "pippo";
        var guess = "ppipo";
        var guessed = wordService.guessWord(word, guess);
        var mismatches = wordService.findDisplacements(word, guess);
        mismatches.forEach((ch, occ) -> System.out.println("letter: " + ch + " | occ: " + occ));
        assertTrue(guessed);
    }
}
