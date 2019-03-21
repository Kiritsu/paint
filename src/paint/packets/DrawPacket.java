package paint.packets;

import paint.enums.DrawType;
import paint.exceptions.UnknownPacketException;

/**
 * Represents a draw packet.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class DrawPacket extends PacketBase {
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
     *   1 [drawType (0-2)] [fill (0/1)] [colour (hexadecimal)] [x1] [x2] [y1] [y2]
     *   1 [drawType 3] [x1] [y1]
     *   1 [drawType 4]
     *   1 [drawType 5] [colour (hexadecimal)] [x1] [y1] [size]
     * @param content Content of the entire packet.
     */
    public DrawPacket(String content) throws UnknownPacketException {
        super(content);

        String[] values = content.split(" ");

        drawType = DrawType.values()[Integer.valueOf(values[0])];

        if (values.length == 8) {
            fill = !values[2].equals("0");
            colour = Integer.parseInt(values[3], 16);
            x1 = Integer.parseInt(values[4], 10);
            x2 = Integer.parseInt(values[5], 10);
            y1 = Integer.parseInt(values[6], 10);
            y2 = Integer.parseInt(values[7], 10);
        } else if (values.length == 6) {
            colour = Integer.parseInt(values[1], 16);
            x1 = Integer.parseInt(values[2], 10);
            y1 = Integer.parseInt(values[3], 10);
            size = Integer.parseInt(values[4], 10);
        } else if (values.length == 4) {
            x1 = Integer.parseInt(values[2]);
            y1 = Integer.parseInt(values[3]);
        } else if (values.length == 2) {

        } else {
            throw new UnknownPacketException("Unable to parse DrawPacket from: " + content);
        }
    }

    public DrawType getDrawType() {
        return this.drawType;
    }

    public int getX1() {
        return this.x1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getY1() {
        return this.y1;
    }

    public int getY2() {
        return this.y2;
    }

    @Override
    public String toString() {
        return super.content;
    }
}
