package paintChatServer.packets;

import paintChatServer.enums.DrawType;
import paintChatServer.exceptions.UnknownPacketException;

/**
 * Represents a draw packet.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class DrawPacket extends PacketBase {
    /**
     * Content of the packet.
     */
    private String content;

    /**
     * Represents the type of draw.
     */
    private DrawType drawType;

    /**
     * Indicates if we must fill the draw of a colour.
     */
    private boolean fill;

    /**
     * Represents the colour to use.
     */
    private int colour;

    /**
     * Represents the x1 coordonate.
     */
    private int x1;

    /**
     * Represents the x2 coordonate.
     */
    private int x2;

    /**
     * Represents the y1 coordonate.
     */
    private int y1;

    /**
     * Represents the y2 coordonate.
     */
    private int y2;

    /**
     * Size of the text when drawType is a Text.
     */
    private int size;

    /**
     * Creates a new packet depending on the received content.
     * The syntax of the DrawPacket is:
     *   1 [drawType (0-5)] [fill (0/1)] [colour (hexadecimal)] [x1] [x2] [y1] [y2]
     *   1 [drawType 6] [colour (hexadecimal)] [x1] [y1] [size]
     * @param content Content of the entire packet.
     */
    public DrawPacket(String content) throws UnknownPacketException {
        super(content);

        String[] values = content.split(" ");

        if (values.length == 7) {
            drawType = Enum.valueOf(DrawType.class, values[0]);
            fill = !values[1].equals("0");
            colour = Integer.parseInt(values[2], 16);
            x1 = Integer.parseInt(values[3], 10);
            x2 = Integer.parseInt(values[4], 10);
            y1 = Integer.parseInt(values[5], 10);
            y2 = Integer.parseInt(values[6], 10);
        } else if (values.length == 5) {
            drawType = Enum.valueOf(DrawType.class, values[0]);
            colour = Integer.parseInt(values[1], 16);
            x1 = Integer.parseInt(values[2], 10);
            y1 = Integer.parseInt(values[3], 10);
            size = Integer.parseInt(values[4], 10);
        } else {
            throw new UnknownPacketException("Unable to parse DrawPacket from: " + content);
        }
    }

    public String toString() {
        return "1 " + super.content;
    }
}
