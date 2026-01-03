package io.lexi115.projectscarlet.users;

/**
 * Exception thrown when a user already exists.
 *
 * @author Lexi115
 * @since 1.0
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message The error message.
     * @since 1.0
     */
    public UserAlreadyExistsException(final String message) {
        super("User already exists: " + message);
    }

}
