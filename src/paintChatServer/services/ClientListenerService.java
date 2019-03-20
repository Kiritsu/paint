package paintChatServer.services;

import paintChatServer.Logger;
import paintChatServer.Server;
import paintChatServer.enums.LogLevel;
import paintChatServer.exceptions.UnknownPacketException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Service that listen every client to broadcast their packets.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ClientListenerService extends Thread {
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
    public ClientListenerService(Server server, Socket client) throws IOException {
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
                PacketBase packet = new PacketBase(reader.readLine());
                switch (packet.getType()) {
                    case Chat:
                        packet = packet.toChatPacket();
                        break;
                    case Paint:
                        packet = packet.toDrawPacket();
                        break;
                }

                server.broadcastPacket(packet, client);
            } catch (IOException e) {
                break;
            } catch (UnknownPacketException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Logger.println(LogLevel.Info, "ClientListener Service", "Terminating ClientListenerService.");
    }
}
