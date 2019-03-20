package paint.services;

import paint.Client;
import paint.Logger;
import paint.enums.LogLevel;
import paint.exceptions.UnknownPacketException;
import paint.packets.ChatPacket;
import paint.packets.DrawPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Service that check when a client receive a packet to update the user interface.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ClientMessageInputListenerService extends Thread {
    /**
     * Server that will send packets.
     */
    private Socket server;

    /**
     * Client that will receive packets.
     */
    private Client client;

    /**
     * Reader of the client that will receive every message.
     */
    private BufferedReader output;

    /**
     * Creates a new instance of this service.
     * @param server Server that will send packets.
     * @param client Client that will receive packets.
     */
    public ClientMessageInputListenerService(Socket server, Client client) throws IOException {
        this.server = server;
        this.client = client;

        this.output = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    /**
     * Constantly checking for new packets.
     */
    @Override
    public void run() {
        while (!server.isConnected() || !server.isClosed()) {
            try {
                String content = output.readLine();
                switch (content.charAt(0)) {
                    case '0':
                        client.updateChatUi(new ChatPacket(content));
                        break;
                    case '1':
                        client.updateDrawUi(new DrawPacket(content));
                        break;
                }

            } catch (IOException e) {
                break;
            } catch (UnknownPacketException e) {
                e.printStackTrace();
            }
        }

        Logger.println(LogLevel.Info, "ClientMessage Service", "Terminating ClientMessageInputListenerService.");
    }
}
