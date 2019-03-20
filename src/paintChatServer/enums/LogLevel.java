package paintChatServer.enums;

/**
 * Represents the kind of log.
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
