package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.cache.CacheService;
import io.lexi115.projectscarlet.core.ScarletConfig;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for word-related operations (basically the game itself).
 *
 * @author Lexi115
 * @since 1.0
 */
@Service
public class WordService {
    /**
     * The key for the daily word key-value pair stored in the cache.
     */
    private static final String DAILY_WORD_KEY = "dailyWord";

    /**
     * The cache service.
     */
    private final CacheService cacheService;

    /**
     * The word repository.
     */
    private final WordRepository wordRepository;

    /**
     * The app configuration.
     */
    private final ScarletConfig scarletConfig;

    /**
     * Constructor.
     *
     * @param cacheService   The cache service.
     * @param wordRepository The word repository.
     * @param scarletConfig  The app configuration.
     * @since 1.0
     */
    public WordService(
            final CacheService cacheService,
            final WordRepository wordRepository,
            final ScarletConfig scarletConfig
    ) {
        this.cacheService = cacheService;
        this.wordRepository = wordRepository;
        this.scarletConfig = scarletConfig;
        chooseRandomWord();
    }

    /**
     * Tries guessing the solution to the game. If no random word has been chosen previously, defaults to the default
     * word configured in {@link ScarletConfig}.
     *
     * @param request The guess request.
     * @return The guess response, containing info such as whether the guess was correct.
     * @since 1.0
     */
    public GuessResponse guessWord(@NonNull final GuessRequest request) {
        var word = cacheService.get(DAILY_WORD_KEY, scarletConfig.getDefaultWord()).toUpperCase().trim();
        var guess = request.getGuess().toUpperCase().trim();
        var correctGuess = guess.equals(word);
        var correctPositions = getCorrectPositions(word, guess);
        var misplacedCharacters = getMisplacedCharacters(word, guess);
        var absentCharacters = getAbsentCharacters(word, guess);
        return new GuessResponse(correctGuess, correctPositions, misplacedCharacters, absentCharacters);
    }

    /**
     * Randomly chooses a word from the word pool and saves it in the cache.
     *
     * @return The chosen word.
     * @since 1.0
     */
    public String chooseRandomWord() {
        var numOfWords = (int) wordRepository.count();
        var randomId = new Random().nextInt(numOfWords) + 1;
        var wordString = wordRepository.findById(randomId).orElseThrow().getValue();
        cacheService.set(DAILY_WORD_KEY, wordString);
        cacheService.set("guessId", UUID.randomUUID().toString());
        return wordString;
    }

    /**
     * Returns the solution to the game. If no random word has been chosen previously, defaults to the default
     * word configured in {@link ScarletConfig}.
     *
     * @return The solution word.
     * @since 1.0
     */
    public SolutionResponse getSolution() {
        return new SolutionResponse(cacheService.get(DAILY_WORD_KEY, scarletConfig.getDefaultWord()));
    }

    /**
     * Returns the correct character positions of the guess.
     *
     * @param word The word to guess.
     * @param guess The guess.
     * @return A set containing the correct position indexes.
     * @since 1.0
     */
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

    /**
     * Returns the characters in the guess which are absent in the actual solution.
     *
     * @param word The word to guess.
     * @param guess The guess.
     * @return A set containing the correct position indexes.
     * @since 1.0
     */
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

    /**
     * Returns the characters in the guess which are present in the actual solution but misplaced. The value part of
     * the map is the number of misplacement occurrences for that character.
     *
     * @param word The word to guess.
     * @param guess The guess.
     * @return A map containing the misplaced characters.
     * @since 1.0
     */
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

    private @NonNull Map<Character, Integer> getCharacterOccurrences(@NonNull final String string) {
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
