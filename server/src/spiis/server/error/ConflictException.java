package spiis.server.error;

import org.springframework.http.HttpStatus;

public class ConflictException extends SpiisException {
    public ConflictException() {
        this("Request is conflicting with existing entities");
    }

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
