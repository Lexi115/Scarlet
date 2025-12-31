package io.lexi115.projectscarlet.words;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO representing a word guess request.
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
public class GuessRequest {
    /**
     * The guess. Cannot be blank.
     */
    @NotBlank
    private String guess;
}
