package spiis.server.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends SpiisException {

    public NotFoundException() {
        this("The requested resource was not found");
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
