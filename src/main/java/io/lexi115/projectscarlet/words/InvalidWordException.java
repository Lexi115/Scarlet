package io.lexi115.projectscarlet.words;

/**
 * Exception thrown when an invalid word is provided.
 *
 * @author Lexi115
 * @since 1.0
 */
public class InvalidWordException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message The error message.
     * @since 1.0
     */
    public InvalidWordException(final String message) {
        super(message);
    }

}
