package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.cache.CacheService;
import io.lexi115.projectscarlet.configs.ScarletConfig;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class WordService {

    private final String DAILY_WORD_KEY = "dailyWord";

    private CacheService cacheService;

    private WordRepository wordRepository;

    private ScarletConfig scarletConfig;

    public void chooseRandomWord() {
        var numOfWords = (int) wordRepository.count();
        var randomId = new Random().nextInt(numOfWords) + 1;
        var word = wordRepository.findById(randomId).orElseThrow();
        cacheService.set(DAILY_WORD_KEY, word.getValue());
    }

    public SolutionResponse getSolution() {
        return new SolutionResponse(cacheService.get(DAILY_WORD_KEY, scarletConfig.getDefaultWord()));
    }

    public GuessResponse guessWord(@NonNull final GuessRequest request) {
        var word = cacheService.get(DAILY_WORD_KEY, scarletConfig.getDefaultWord()).toUpperCase().trim();
        var guess = request.getGuess().toUpperCase().trim();
        var correctGuess = guess.equals(word);
        var correctPositions = getCorrectPositions(word, guess);
        var misplacedCharacters = getMisplacedCharacters(word, guess);
        var absentCharacters = getAbsentCharacters(word, guess);
        return new GuessResponse(correctGuess, correctPositions, misplacedCharacters, absentCharacters);
    }

    public Set<Integer> getCorrectPositions(@NonNull final String word, @NonNull final String guess) {
        var wordLength = word.length();
        if (wordLength != guess.length()) {
            throw new IllegalArgumentException("Lengths must match!");
        }
        var positions = new HashSet<Integer>();
        for (int i = 0; i < wordLength; i++) {
            if (word.charAt(i) == guess.charAt(i)) {
                positions.add(i);
            }
        }
        return positions;
    }

    public Set<Character> getAbsentCharacters(@NonNull final String word, @NonNull final String guess) {
        var wordLength = word.length();
        if (wordLength != guess.length()) {
            throw new IllegalArgumentException("Lengths must match!");
        }
        if (word.equals(guess)) {
            return Set.of();
        }
        var absences = new HashSet<Character>();
        var occurrences = getCharacterOccurrences(word);
        char c;
        for (int i = 0; i < wordLength; i++) {
            c = guess.charAt(i);
            if (occurrences.get(c) == null) {
                absences.add(c);
            }
        }
        return absences;
    }

    public Map<Character, Integer> getMisplacedCharacters(@NonNull final String word, @NonNull final String guess) {
        var wordLength = word.length();
        if (wordLength != guess.length()) {
            throw new IllegalArgumentException("Lengths must match!");
        }
        if (word.equals(guess)) {
            return Map.of();
        }
        var displacements = new HashMap<Character, Integer>();
        var occurrences = getCharacterOccurrences(word);
        for (int i = 0; i < wordLength; i++) {
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

    public Map<Character, Integer> getCharacterOccurrences(@NonNull final String string) {
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
