package paintChatServer.enums;

/**
 * Represents the kind of log.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public enum LogLevel {
    /**
     * A simple message.
     */
    Info,
    /**
     * Something probably wrong happened.
     */
    Warning,
    /**
     * Unhandled error happened.
     */
    Error,
    /**
     * Verbose and debug intended.
     */
    Debug
}
