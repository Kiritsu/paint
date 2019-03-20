package paintChatServer.packets;

/**
 * Reprensents a base packet.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public abstract class PacketBase {
    /**
     * Content of the packet.
     */
    protected String content;

    /**
     * Creates a new Packet.
     * @param content
     */
    public PacketBase(String content) {
        this.content = content;
    }
}
