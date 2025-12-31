package io.lexi115.projectscarlet.words;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * DTO representing a word guess response.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class GuessResponse {
    /**
     * Whether the guess was correct or not.
     */
    @JsonProperty("correct")
    private boolean correct;

    /**
     * The correct position indexes in the guess.
     */
    @JsonProperty("correctPositions")
    private Set<Integer> correctPositions;

    /**
     * The misplaced characters in the guess.
     */
    @JsonProperty("misplaced")
    private Map<Character, Integer> misplacedCharacters;

    /**
     * The absent characters in the solution.
     */
    @JsonProperty("absent")
    private Set<Character> absentCharacters;
}
