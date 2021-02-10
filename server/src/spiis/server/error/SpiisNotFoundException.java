package spiis.server.error;

import org.springframework.http.HttpStatus;

public class SpiisNotFoundException extends SpiisException {

    public SpiisNotFoundException() {
        this("The requested resource was not found");
    }

    public SpiisNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
