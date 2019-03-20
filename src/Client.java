import java.net.Socket;

public class Client {
    private Server server;
    private Socket clientSocket;

    public Client(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }
}
