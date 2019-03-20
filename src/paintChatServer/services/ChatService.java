package paintChatServer.services;

import paintChatServer.packets.ChatPacket;

import java.util.ArrayList;

/**
 * Class that will hold every send messages in the chat box.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ChatService {
    /**
     * Contains the different messages.
     */
    private ArrayList<ChatPacket> logs;

    /**
     * Creates a new chatting service.
     */
    public ChatService() {
        this.logs = new ArrayList<>();
    }

    /**
     * Adds a new message.
     * @param packet Packet to add.
     */
    public void add(ChatPacket packet) {
        this.logs.add(packet);
    }
}
