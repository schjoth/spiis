package spiis.server.error;

import org.springframework.http.HttpStatus;

public class ModelError extends SpiisException {
    public ModelError(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
