package paintChatServer;

import paintChatServer.enums.LogLevel;
import paintChatServer.packets.ChatPacket;
import paintChatServer.packets.DrawPacket;
import paintChatServer.services.ChatService;
import paintChatServer.services.ClientMessageInputListenerService;
import paintChatServer.services.DrawingService;

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
    private ClientMessageInputListenerService clientMessageService;

    /**
     * Prints packets to the remote server.
     */
    private PrintWriter writer;

    /**
     * Service that will hold every drawings.
     */
    private DrawingService drawing;

    /**
     * Service that will hold every message.
     */
    private ChatService chatting;

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
     */
    public void connect() throws IOException, InterruptedException {
        this.socket = new Socket(this.address, this.port);

        Logger.println(LogLevel.Info, "Client",
                "Connected to the remote server " + this.address + ":" + this.port);

        this.clientMessageService = new ClientMessageInputListenerService(socket, this);
        this.clientMessageService.start();

        this.writer = new PrintWriter(this.socket.getOutputStream());
    }

    /**
     * Sends a ChatPacket to the server.
     * @param message Message to send.
     */
    public void sendMessage(String message) {
        ChatPacket packet = new ChatPacket(message);
        chatting.add(packet);
        writer.println(packet.toString());
        writer.flush();
    }

    /**
     * Method called when a packet has been received and handled in the chat.
     * @param packet Packet to handle.
     */
    public void updateChatUi(ChatPacket packet) {
        chatting.add(packet);

        //todo: update the GUI.
        Logger.println(LogLevel.Info, "Packet Received", "Message Received: " + packet.getMessage());
    }

    /**
     * Method called when a packet has been received and handled in the draw window.
     * @param packet Packet to handle.
     */
    public void updateDrawUi(DrawPacket packet) {
        switch (packet.getDrawType()) {
            case Circle:
            case Square:
            case Arrow:
            case Text:
                drawing.add(packet);
                break;
            case Eraser:
                drawing.remove(drawing.get(packet.getX1(), packet.getY1()));
                break;
            case Undo:
                drawing.undo();
                break;
        }

        //todo: update the GUI.
        Logger.println(LogLevel.Info, "Packet Received", "Draw received: " + packet.toString());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client("localhost", (short) 8000).connect();
    }
}
