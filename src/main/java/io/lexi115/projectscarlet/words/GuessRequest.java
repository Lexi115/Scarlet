package io.lexi115.projectscarlet.words;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuessRequest {

    @NotBlank
    private String guess;
}