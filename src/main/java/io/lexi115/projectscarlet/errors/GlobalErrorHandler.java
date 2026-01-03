package io.lexi115.projectscarlet.errors;

import io.lexi115.projectscarlet.users.UserNotFoundException;
import lombok.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global error handler that captures generic exceptions.
 *
 * @author Lexi115
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalErrorHandler {

    /**
     * Method that handles exceptions when illegal arguments are provided.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onIllegalArgument(@NonNull final IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Method that handles exceptions when bad request arguments are provided.
     *
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onBadRequestArguments() {
        return new ErrorResponse("One or more arguments provided are invalid.");
    }

    /**
     * Method that handles exceptions when a data integrity violation occurs (for example a duplicate primary key).
     *
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse onDataIntegrityViolation() {
        return new ErrorResponse("Data integrity violation.");
    }

    /**
     * Method that handles exceptions when a required cookie is not found in the request.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onMissingRequestCookie(@NonNull final MissingRequestCookieException e) {
        return new ErrorResponse("Missing request cookie: " + e.getCookieName());
    }

    /**
     * Method that handles exceptions when a user is not found.
     *
     * @param e The exception.
     * @return The error response.
     * @since 1.0
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onUserNotFound(@NonNull final UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

}
