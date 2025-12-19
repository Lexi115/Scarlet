package io.lexi115.projectscarlet.words;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolutionResponse {

    @JsonProperty("solution")
    private String solution;
}
