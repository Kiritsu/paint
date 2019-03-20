package paintChatServer.exceptions;

/**
 * Exception that is being thrown when a parse of packet was unsuccessfull.
 */
public class UnknownPacketException extends Exception {
    /**
     * Content of the exception.
     */
    private String message;

    /**
     * Creates a new exception without a reason.
     */
    public UnknownPacketException() {
        super();
    }

    /**
     * Creates a new exception with a specified reason.
     * @param message Reason of the Exception.
     */
    public UnknownPacketException(String message) {
        super(message);
        this.message = message;
    }
}
