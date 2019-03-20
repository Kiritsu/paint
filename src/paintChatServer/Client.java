package paintChatServer;

import paintChatServer.enums.LogLevel;
import paintChatServer.packets.ChatPacket;
import paintChatServer.packets.DrawPacket;
import paintChatServer.services.ClientMessageService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class represents a Client that will connect to a server and send different packets depending on the client's action.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Client {
    /**
     * Address on which the client is connected.
     */
    private String address;

    /**
     * Port on which the client is connected.
     */
    private short port;

    /**
     * Server on which the client will try to connect.
     */
    private Socket socket;

    /**
     * Service that will handle every packet received.
     */
    private ClientMessageService clientMessageService;

    /**
     * Prints packets to the remote server.
     */
    private PrintWriter writer;

    /**
     * Creates a new client.
     * @param address Address of the remote server.
     * @param port Port of the remote server.
     */
    public Client(String address, short port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Initializes a connection to the remote server.
     * @throws IOException
     */
    public void connect() throws IOException, InterruptedException {
        this.socket = new Socket(this.address, this.port);

        Logger.println(LogLevel.Info, "Client",
                "Connected to the remote server " + this.address + ":" + this.port);

        this.clientMessageService = new ClientMessageService(socket, this);
        this.clientMessageService.start();

        this.writer = new PrintWriter(this.socket.getOutputStream());

        sendMessage("Salut les gars !!!");
    }

    /**
     * Sends a ChatPacket to the server.
     * @param message
     */
    public void sendMessage(String message) {
        ChatPacket packet = new ChatPacket(message);
        writer.println(packet.toString());
        writer.flush();
    }

    /**
     * Method called when a packet has been received and handled in the chat.
     * @param packet Packet to handle.
     */
    public void updateChatUi(ChatPacket packet) {
        Logger.println(LogLevel.Info, "Packet Received", "Message Received: " + packet.getMessage());
    }

    /**
     * Method called when a packet has been received and handled in the draw window.
     * @param packet Packet to handle.
     */
    public void updateDrawUi(DrawPacket packet) {
        Logger.println(LogLevel.Info, "Packet Received", "Draw received: " + packet.toString());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client("localhost", (short) 8000).connect();
    }
}
