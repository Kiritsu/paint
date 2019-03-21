package paint.services;

import paint.Logger;
import paint.Server;
import paint.enums.LogLevel;
import paint.exceptions.UnknownPacketException;
import paint.packets.ChatPacket;
import paint.packets.DrawPacket;
import paint.packets.PacketBase;

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
     * Number that define the amount of client that connected the server.
     */
    private static int autoIdIncrement;

    /**
     * Auto incremented id of the client.
     */
    private int id;

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
     * @param server paint.Server on which we'll wait for users.
     */
    public ServerClientInputListenerService(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;
        this.id = ++autoIdIncrement;

        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    /**
     * Constantly checking for new user's packet(s).
     */
    @Override
    public void run() {
        try {
            server.broadcastPacket(new ChatPacket("0 Client " + id + " s'est connecté."), client);
            server.sendUserCount();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!server.isQuitRequested()) {
            try {
                String content = reader.readLine();
                PacketBase packet = null;
                switch (content.charAt(0)) {
                    case '0':
                        packet = new ChatPacket("0 [Client " + id + "]: " + content.substring(2));
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

        try {
            server.deleteClient(client);
            server.sendUserCount();
            server.broadcastPacket(new ChatPacket("0 Client " + id + " s'est déconnecté."), client);
            Logger.println(LogLevel.Info, "ServerClientInputListener Service", "A client has disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
