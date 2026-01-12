package io.lexi115.projectscarlet.words;

import io.lexi115.projectscarlet.cache.CacheService;
import io.lexi115.projectscarlet.core.ScarletConfig;
import io.lexi115.projectscarlet.util.json.JsonHelper;
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
     * The key for the chosen word key-value pair stored in the cache.
     */
    private static final String CHOSEN_WORD_KEY = "chosenWord";

    /**
     * The key for the word list stored in the cache.
     */
    private static final String WORD_LIST_KEY = "wordList";

    /**
     * The cache service.
     */
    private final CacheService<String> cacheService;

    /**
     * The word repository.
     */
    private final WordRepository wordRepository;

    /**
     * The app configuration.
     */
    private final ScarletConfig scarletConfig;

    /**
     * The JSON converter class.
     */
    private final JsonHelper jsonHelper;

    /**
     * Constructor.
     *
     * @param cacheService   The cache service.
     * @param wordRepository The word repository.
     * @param scarletConfig  The app configuration.
     * @param jsonHelper     The JSON converter class.
     * @since 1.0
     */
    public WordService(
            final CacheService<String> cacheService,
            final WordRepository wordRepository,
            final ScarletConfig scarletConfig,
            final JsonHelper jsonHelper
    ) {
        this.cacheService = cacheService;
        this.wordRepository = wordRepository;
        this.scarletConfig = scarletConfig;
        this.jsonHelper = jsonHelper;
        loadWordsInCache();
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
        var word = cacheService.get(CHOSEN_WORD_KEY, scarletConfig.getDefaultWord()).toLowerCase().trim();
        var guess = request.getGuess().toLowerCase().trim();
        var correctGuess = guess.equals(word);
        if (!getWordList().contains(guess)) {
            throw new InvalidWordException("Invalid word!");
        }
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
        var wordList = getWordList();
        var randomId = new Random().nextInt(wordList.size()) + 1;
        var wordString = wordList.get(randomId);
        cacheService.set(CHOSEN_WORD_KEY, wordString);
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
        return new SolutionResponse(cacheService.get(CHOSEN_WORD_KEY, scarletConfig.getDefaultWord()));
    }

    /**
     * Returns the correct character positions of the guess.
     *
     * @param word  The word to guess.
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
     * @param word  The word to guess.
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
     * @param word  The word to guess.
     * @param guess The guess.
     * @return A map containing the misplaced characters.
     * @since 1.0
     */
    public Map<Character, Integer> getMisplacedCharacters(@NonNull final String word, @NonNull final String guess) {
        if (word.equals(guess)) {
            return Map.of();
        }
        var wordLength = word.length();
        if (wordLength != guess.length()) {
            throw new IllegalArgumentException("Lengths must match!");
        }
        char w;
        char g;
        int count;
        var availableChars = new HashMap<Character, Integer>();
        for (int i = 0; i < wordLength; i++) {
            w = word.charAt(i);
            g = guess.charAt(i);
            if (w != g) {
                availableChars.put(w, availableChars.getOrDefault(w, 0) + 1);
            }
        }
        var displacements = new HashMap<Character, Integer>();
        for (int i = 0; i < wordLength; i++) {
            w = word.charAt(i);
            g = guess.charAt(i);
            if (w != g) {
                count = availableChars.getOrDefault(g, 0);
                if (count > 0) {
                    displacements.put(g, displacements.getOrDefault(g, 0) + 1);
                    availableChars.put(g, count - 1);
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

    private void loadWordsInCache() {
        var words = wordRepository.findAll();
        var stringList = words.stream().map(word -> word.getValue().trim().toLowerCase()).toList();
        cacheService.set(WORD_LIST_KEY, jsonHelper.stringify(stringList));
    }

    private List<String> getWordList() {
        var jsonString = cacheService.get(WORD_LIST_KEY, "{}");
        return jsonHelper.parseList(jsonString, String.class);
    }
}
