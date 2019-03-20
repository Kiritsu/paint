package paintChatServer.packets;

import paintChatServer.enums.PacketType;
import paintChatServer.exceptions.UnknownPacketException;

/**
 * This class represents a packet with data.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class PacketBase {
    /**
     * Type of the packet.
     */
    protected PacketType source;

    /**
     * Content of the packet.
     */
    protected String rawContent;

    /**
     * Content without type of the packet.
     */
    protected String content;

    /**
     * Returns different values from the entire packet.
     */
    protected String[] values;

    /**
     * Creates a new packet depending on the received content.
     * @param content Content of the entire packet.
     */
    public PacketBase(String content) {
        values = content.split(" ");

        if (values.length <= 0) {
            return;
        }

        this.rawContent = content;
        this.source = Enum.valueOf(PacketType.class, values[0]);
        this.content = content.substring(0, values[0].length() + 1);
    }

    /**
     * Returns the entire content of the packet
     */
    @Override
    public String toString() {
        return rawContent;
    }

    /**
     * Gets the current packet as a chat packet.
     */
    public ChatPacket toChatPacket() {
        return new ChatPacket(rawContent);
    }

    /**
     * Gets the current packet as a draw packet.
     */
    public DrawPacket toDrawPacket() throws UnknownPacketException {
        return new DrawPacket(rawContent);
    }
}
