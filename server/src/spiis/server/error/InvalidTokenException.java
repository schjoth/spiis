package spiis.server.error;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when no token is provided in a request,
 * or the token provided is invalid
 * or doesn't belong to any users.
 */
public class InvalidTokenException extends SpiisException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid/unrecognized token");
    }
}
