package spiis.server.error;

import org.springframework.http.HttpStatus;
import spiis.server.api.SpiisErrorResponse;

public class SpiisException extends RuntimeException {

    private final HttpStatus statusCode;

    public SpiisException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public SpiisErrorResponse buildErrorObject() {
        return new SpiisErrorResponse(getMessage());
    }
}
