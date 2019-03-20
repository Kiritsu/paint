package paintChatServer.services;

import paintChatServer.Client;
import paintChatServer.Logger;
import paintChatServer.enums.LogLevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Service that check when a client receive a packet to update the user interface.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ClientMessageService extends Thread {
    /**
     * Server that will send packets.
     */
    private Socket server;

    /**
     * Client that will receive packets.
     */
    private Client client;

    /**
     * Creates a new instance of this service.
     * @param server Server that will send packets.
     * @param client Client that will receive packets.
     */
    public ClientMessageService(Socket server, Client client) {
        this.server = server;
        this.client = client;

        Logger.println(LogLevel.Debug, "ClientMessage Service", "Checking for new packets...");
    }

    /**
     * Constantly checking for new packets.
     */
    @Override
    public void run() {
        while (!server.isConnected() || !server.isClosed()) {
            try {
                BufferedReader output = new BufferedReader(new InputStreamReader(server.getInputStream()));
                String content = output.readLine();
                PacketBase packet = new PacketBase(content);
                switch (packet.getType()) {
                    case Chat:
                        client.updateChatUi(packet.toChatPacket());
                        break;
                    case Paint:
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Logger.println(LogLevel.Info, "ClientMessage Service", "Terminating ClientMessageService.");
    }
}
