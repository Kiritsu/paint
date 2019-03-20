package paintChatServer.services;

import paintChatServer.Logger;
import paintChatServer.Server;
import paintChatServer.enums.LogLevel;

import java.net.Socket;
import java.util.ArrayList;

/**
 * This class will constantly check if users have timed out.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ClientTimeoutCheckService extends Thread {
    /**
     * Server on which we'll check if every user is still connected.
     */
    private Server server;

    /**
     * Creates a new instance of this service.
     * @param server Server on which we'll wait for users.
     */
    public ClientTimeoutCheckService(Server server) {
        this.server = server;
    }

    /**
     * Constantly check for disconnected clients. We create two copy of Server#getClients() to
     * avoid potential mutation errors during iteration of the ArrayList.
     */
    @Override
    public void run() {
        while (!server.isQuitRequested()) {
            ArrayList<Socket> clients = new ArrayList<>(server.getClients());
            ArrayList<Socket> iteration = new ArrayList<>(clients);
            for (Socket client : iteration) {
                if (!client.isConnected() || client.isClosed()) {
                    clients.remove(client);
                    Logger.println(LogLevel.Debug, "TimeoutService", "A client has disconnected.");
                }
            }

            server.updateClients(clients);

            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Logger.println(LogLevel.Info, "ClientTimeoutCheck Service", "Terminating ClientTimeoutCheckService.");
    }
}
