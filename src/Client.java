import java.io.IOException;
import java.net.Socket;

/**
 * This class represents a Client that will connect to a server and send different packets depending on the client's action.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Client {
    /**
     * Address on which the client is connected.
     */
    private String address;

    /**
     * Port on which the client is connected.
     */
    private short port;

    /**
     * Server on which the client will try to connect.
     */
    private Socket socket;

    /**
     * Creates a new client.
     * @param address Address of the remote server.
     * @param port Port of the remote server.
     */
    public Client(String address, short port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Initializes a connection to the remote server.
     * @throws IOException
     */
    public void connect() throws IOException {
        this.socket = new Socket(this.address, this.port);
        Logger.println(LogLevel.Info, "Client",
                "Connected to the remote server " + this.address + ":" + this.port);

        this.socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client("localhost", (short) 8000).connect();
    }
}
