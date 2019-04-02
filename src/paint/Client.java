package paint;

import paint.enums.LogLevel;
import paint.packets.ChatPacket;
import paint.packets.DrawPacket;
import paint.packets.PacketBase;
import paint.services.client.ChattingService;
import paint.services.client.ClientMessageInputListenerService;
import paint.services.client.DrawingService;

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
    private ChattingService chatting;

    /**
     * Represents the GUI instance.
     */
    private ClientFrame frame;

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
    public void connect() throws IOException {
        this.frame = new ClientFrame(this);
        this.frame.setVisible(true);

        this.socket = new Socket(this.address, this.port);

        Logger.println(LogLevel.Info, "Client",
                "Connected to the remote server " + this.address + ":" + this.port);

        this.chatting = new ChattingService(this.frame);
        this.drawing = new DrawingService();

        this.clientMessageService = new ClientMessageInputListenerService(socket, this);
        this.clientMessageService.start();

        this.writer = new PrintWriter(this.socket.getOutputStream());
    }

    /**
     * Sends a packet to the server.
     * @param packet Packet to send.
     */
    public void sendPacket(PacketBase packet) {
        writer.println(packet.toString());
        writer.flush();
    }

    /**
     * Method called when a packet has been received and handled in the chat.
     * @param packet Packet to handle.
     */
    public void updateChatUi(ChatPacket packet) {
        Logger.println(LogLevel.Debug, "Packet Received", "Message Received: " + packet.getMessage());
        chatting.add(packet);
    }

    /**
     * Method called when a packet has been received and handled in the draw window.
     * @param packet Packet to handle.
     */
    public void updateDrawUi(DrawPacket packet) {
        Logger.println(LogLevel.Debug, "Packet Received", "Draw received: " + packet.toString());
        drawing.handle(packet, frame.getPanelGraphics());
    }

    /**
     * Sets the frame's title with the amount of users connected. This packet is only created and sent by the server.
     * @param count Amount of users connected.
     */
    public void setUserCount(int count) {
        this.frame.setTitle("Paint Chat Server | " + count + " utilisateurs connectés.");
    }

    public static void main(String[] args) throws IOException {
        new Client("163.172.176.132", (short) 8000).connect();
    }
}
