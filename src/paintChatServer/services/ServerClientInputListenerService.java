package paintChatServer.services;

import paintChatServer.Logger;
import paintChatServer.Server;
import paintChatServer.enums.LogLevel;
import paintChatServer.exceptions.UnknownPacketException;
import paintChatServer.packets.ChatPacket;
import paintChatServer.packets.DrawPacket;
import paintChatServer.packets.PacketBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Service that listen every client to broadcast their packets.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ServerClientInputListenerService extends Thread {
    /**
     * Server on which we'll broadcast users' packets.
     */
    private Server server;

    /**
     * Represents a client.
     */
    private Socket client;

    /**
     * Output stream that will handle every message we received from the client.
     */
    private BufferedReader reader;

    /**
     * Creates a new instance of this service.
     * @param server paintChatServer.Server on which we'll wait for users.
     */
    public ServerClientInputListenerService(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;

        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    /**
     * Constantly checking for new user's packet(s).
     */
    @Override
    public void run() {
        while (!server.isQuitRequested()) {
            try {
                String content = reader.readLine();
                PacketBase packet = null;
                switch (content.charAt(0)) {
                    case '0':
                        packet = new ChatPacket(content);
                        break;
                    case '1':
                        packet = new DrawPacket(content);
                        break;
                }

                server.broadcastPacket(packet, client);
            } catch (IOException e) {
                break;
            } catch (UnknownPacketException e) {
                e.printStackTrace();
            }
        }

        Logger.println(LogLevel.Info, "ServerClientInputListener Service", "Terminating ServerClientInputListenerService.");
    }
}
