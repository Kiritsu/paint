package paint.packets;

/**
 * Reprensents a base packet.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public abstract class PacketBase {
    /**
     * Full content of the packet.
     */
    protected String content;

    /**
     * Creates a new Packet.
     * @param content Full content of the packet.
     */
    public PacketBase(String content) {
        this.content = content;
    }
}
