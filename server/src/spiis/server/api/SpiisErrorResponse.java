package spiis.server.api;

public class SpiisErrorResponse {

    public String message;

    /**
     * A general object used to send error information 4XX and 5XX responses
     * @param message the reason for the error or other info
     */
    public SpiisErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
