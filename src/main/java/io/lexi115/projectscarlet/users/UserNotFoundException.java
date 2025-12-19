package io.lexi115.projectscarlet.users;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String message) {
        super("User not found: " + message);
    }
}
