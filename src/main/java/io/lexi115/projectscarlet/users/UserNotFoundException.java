package io.lexi115.projectscarlet.users;

/**
 * Exception thrown when a user is not found.
 *
 * @author Lexi115
 * @since 1.0
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param message The error message.
     * @since 1.0
     */
    public UserNotFoundException(final String message) {
        super("User not found: " + message);
    }
}
