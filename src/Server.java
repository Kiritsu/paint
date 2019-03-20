import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * This class represents a server that will accept clients and broadcast packets all over the different clients.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Server {
    /**
     * Represents the list of every client.
     */
    private ArrayList<Client> clients;

    /**
     * ServerSocket that will handle client's connections and sending/receiving packets.
     */
    private ServerSocket server;

    /**
     * Indicates whether the server must be stopped.
     */
    private boolean quitRequested;

    /**
     * IPAddress of the server. (doesn't really matter because we're running under localhost)
     */
    private String address;

    /**
     * Port of the server.
     */
    private short port;

    /**
     * Creates a new server.
     * @param address Address of the server.
     * @param port Port of the server.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public Server(String address, short port) throws IOException {
        this.clients = new ArrayList<>();
        this.quitRequested = false;

        this.address = address;
        this.port = port;

        this.server = new ServerSocket(this.port);
    }

    /**
     * Starts the server and starts listening for new clients.
     * @throws IOException If an I/O error occurs when listening.
     */
    public void startServer() throws IOException {

    }

    /**
     * Gets a String representation of the current server.
     */
    @Override
    public String toString() {
        return "Server bound to " + address + ":" + port + " | " + clients.size() + " clients connected.";
    }
}
