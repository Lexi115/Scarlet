package io.lexi115.projectscarlet;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordService {

    public boolean guessWord(final String word, final String guess) {
        var formattedWord = word.toUpperCase().trim();
        var formattedGuess = guess.toUpperCase().trim();
        return formattedGuess.equals(formattedWord);
    }

    public Map<Character, Integer> findDisplacements(final String first, final String second) {
        var displacements = new HashMap<Character, Integer>();
        var wordLength = first.length();
        var guessLength = second.length();
        if (wordLength != guessLength) {
            throw new IllegalArgumentException("Lengths must match!");
        }
        var charMap = getAllCharOccurrences(first);
        for (int i = 0; i < wordLength; i++) {
            var letter = first.charAt(i);
            var guessLetter = second.charAt(i);
            if (guessLetter != letter) {
                var occurrences = Optional.ofNullable(displacements.get(guessLetter)).orElse(0);
                var charGet = charMap.get(guessLetter);
                if (charGet != null && occurrences < charGet) {
                    occurrences++;
                    displacements.put(guessLetter, occurrences);
                }
            }
        }
        return displacements;
    }

    public int countCharOccurrences(final char c, final String string) {
        var stringLength = string.length();
        var occurrences = 0;
        for (int i = 0; i < stringLength; i++) {
            if (string.charAt(i) == c) {
                occurrences++;
            }
        }
        return occurrences;
    }

    public Map<Character, Integer> getAllCharOccurrences(final String string) {
        var stringLength = string.length();
        var map = new HashMap<Character, Integer>();
        for (int i = 0; i < stringLength; i++) {
            var c = string.charAt(i);
            var occ = countCharOccurrences(c, string);
            if (occ > 0) {
                map.put(c, occ);
            }
        }
        return map;
    }
}
