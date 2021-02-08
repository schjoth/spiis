package spiis.server.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SpiisExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions thrown in controllers
     * @param ex the SpiisException or a subclass
     * @param request the Request that cause the Exception
     * @return ResponseEntity that will be sent back to the client
     */
    @ExceptionHandler(value = SpiisException.class)
    protected ResponseEntity<Object> handleConflict(
            SpiisException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.buildErrorObject(),
                new HttpHeaders(), ex.getStatusCode(), request);
    }
}