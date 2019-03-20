import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    public synchronized static void println(LogLevel level, String source, String message) {
        String dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("[" + dateTime + "] [" + level.name() + "] [" + source + "] - " + message);
    }
}