package io.lexi115.projectscarlet.util.json;

/**
 * Exception thrown when a JSON conversion fails.
 *
 * @author Lexi115
 * @since 1.0
 */
public class JsonException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message The error message.
     * @since 1.0
     */
    public JsonException(final String message) {
        super(message);
    }

}
