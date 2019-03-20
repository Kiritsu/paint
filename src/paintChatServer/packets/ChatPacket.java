package paintChatServer.packets;

/**
 * Reprensents a chat packet.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ChatPacket extends PacketBase {
    /**
     * Creates a new packet depending on the received content.
     * @param content Content of the entire packet.
     */
    public ChatPacket(String content) {
        super(content);
    }

    /**
     * Returns the message sent in the tchat.
     */
    public String getMessage() {
        return content;
    }
}
