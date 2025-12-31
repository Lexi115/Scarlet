package io.lexi115.projectscarlet.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing an error response (often used in error handler classes).
 *
 * @author Lexi115
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    /**
     * The error message.
     */
    private String message;
}
