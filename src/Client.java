import java.net.Socket;

/**
 * This class represents a Client that will connect to a server and send different packets depending on the client's action.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Client {
    private Server server;
    private Socket clientSocket;

    public Client(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }
}
