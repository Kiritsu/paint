package paintChatServer.services;

import paintChatServer.Logger;
import paintChatServer.Server;
import paintChatServer.enums.LogLevel;

import java.net.Socket;

/**
 * This class will constantly accept new users.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ClientAcceptorService extends Thread {
    /**
     * Server on which we'll wait for users.
     */
    private Server server;

    /**
     * Creates a new instance of this service.
     * @param server Server on which we'll wait for users.
     */
    public ClientAcceptorService(Server server) {
        this.server = server;
    }

    /**
     * Constantly checking for new users connecting to our server.
     */
    @Override
    public void run() {
        while (!server.isQuitRequested()) {
            try {
                Socket socket = server.getServerSocket().accept();
                server.addClient(socket);

                ServerClientInputListenerService clientListener = new ServerClientInputListenerService(server, socket);
                clientListener.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        Logger.println(LogLevel.Info, "ClientAcceptor Service", "Terminating ClientAcceptorService.");
    }
}
