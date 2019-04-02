package paint;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Configuration {
    public String server;
    public short port;

    public Configuration() {
        this("localhost", (short) 8000);
    }

    public Configuration(String server, short port) {
        this.server = server;
        this.port = port;
    }

    public static Configuration parse(String path) {
        try
        {
            String server = "localhost";
            short port = 8000;

            File f = new File(path);
            if (f.isFile()) {
                List<String> lines = Files.readAllLines(Paths.get(path));
                for (String line : lines) {
                    if (line.startsWith("server=")) {
                        server = line.substring(7);
                    } else if (line.startsWith("port=")) {
                        port = Short.parseShort(line.substring(5));
                    }
                }
            }

            return new Configuration(server, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Configuration();
    }
}
