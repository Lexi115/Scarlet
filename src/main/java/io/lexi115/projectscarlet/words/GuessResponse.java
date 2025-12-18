package io.lexi115.projectscarlet.words;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class GuessResponse {

    @JsonProperty("correct")
    private boolean correct;

    @JsonProperty("correctPositions")
    private Set<Integer> correctPositions;

    @JsonProperty("misplaced")
    private Map<Character, Integer> misplacedCharacters;

    @JsonProperty("absent")
    private Set<Character> absentCharacters;
}
