package paintChatServer.exceptions;

/**
 * Exception that is being thrown when a parse of packet was unsuccessfull.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class UnknownPacketException extends Exception {
    /**
     * Creates a new exception with a specified reason.
     * @param message Reason of the Exception.
     */
    public UnknownPacketException(String message) {
        super(message);
    }
}
