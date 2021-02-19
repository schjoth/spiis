package spiis.server.error;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when a user is logged in,
 * but tries doing something the user isn't allowed to do.
 */
public class ForbiddenException extends SpiisException {
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "Forbidden action for this user");
    }
}
