package io.lexi115.projectscarlet.users;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("User already exists: " + message);
    }
}
