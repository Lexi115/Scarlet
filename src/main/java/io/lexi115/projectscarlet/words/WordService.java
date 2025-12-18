package io.lexi115.projectscarlet.words;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordService {

    public boolean guessWord(@NonNull final String word, @NonNull final String guess) {
        var formattedWord = word.toUpperCase().trim();
        var formattedGuess = guess.toUpperCase().trim();
        return formattedGuess.equals(formattedWord);
    }

    public Map<Character, Integer> countMisplacedCharacters(
            @NonNull final String word,
            @NonNull final String guess
    ) {
        if (word.length() != guess.length()) {
            throw new IllegalArgumentException("Lengths must match!");
        }
        var displacements = new HashMap<Character, Integer>();
        var occurrences = countCharacterOccurrences(word);
        for (int i = 0; i < word.length(); i++) {
            var wordCharacter = word.charAt(i);
            var guessCharacter = guess.charAt(i);
            if (wordCharacter != guessCharacter) {
                var found = displacements.getOrDefault(guessCharacter, 0);
                var available = occurrences.getOrDefault(guessCharacter, 0);
                if (found < available) {
                    displacements.put(guessCharacter, found + 1);
                }
            }
        }
        return displacements;
    }

    public Map<Character, Integer> countCharacterOccurrences(@NonNull final String string) {
        var stringLength = string.length();
        var map = new HashMap<Character, Integer>();
        char c;
        for (int i = 0; i < stringLength; i++) {
            c = string.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }
}
