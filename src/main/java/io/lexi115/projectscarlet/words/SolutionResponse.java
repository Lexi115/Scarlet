package io.lexi115.projectscarlet.words;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing the solution response.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class SolutionResponse {

    /**
     * The solution word.
     */
    @JsonProperty("solution")
    private String solution;

}
