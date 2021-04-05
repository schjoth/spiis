package spiis.server.error;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when a user is logged in,
 * but tries doing something the user isn't allowed to do.
 */
public class ForbiddenException extends SpiisException {
    public ForbiddenException() {
        this("Forbidden action for this user");
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
