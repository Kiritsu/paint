package paintChatServer;

import paintChatServer.enums.LogLevel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class that help to log things easily.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Logger {
    public synchronized static void println(LogLevel level, String source, String message) {
        String dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("[" + dateTime + "] [" + level.name() + "] [" + source + "] - " + message);
    }
}