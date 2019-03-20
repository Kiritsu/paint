package paint;

import paint.enums.LogLevel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class that help to log things easily.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Logger {
    /**
     * Logs a message to the console.
     * @param level Level of the message.
     * @param source Source of the message.
     * @param message Message.
     */
    public synchronized static void println(LogLevel level, String source, String message) {
        String dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("[" + dateTime + "] [" + level.name() + "] [" + source + "] - " + message);
    }
}