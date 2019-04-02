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
     * Text to print when drawType is a Text.
     */
    private String text;

    /**
     * Indicates if positions x1 x2 were inverted during packet deserialization.
     */
    private boolean xInverted;

    /**
     * Indicates wether positions y1 y2 were inverted during packet deserialization.
     */
    private boolean yInverted;

    /**
     * Creates a new packet depending on the received content.
     * The syntax of the DrawPacket is:
     *   1 [drawType (0-2)] [fill (0/1)] [colour (hexadecimal)] [x1] [x2] [y1] [y2]
     *   1 [drawType 3] [x1] [y1]
     *   1 [drawType 4]
     *   1 [drawType 5] [colour (hexadecimal)] [x1] [y1] :[text]
     * @param content Content of the entire packet.
     */
    public DrawPacket(String content) throws UnknownPacketException {
        super(content);

        String[] values = content.split(" ");

        drawType = DrawType.values()[Integer.valueOf(values[1])];

        if (values.length == 8 && drawType != DrawType.Text) {
            fill = !values[2].equals("0");
            colour = Integer.parseInt(values[3], 10);
            x1 = Integer.parseInt(values[4], 10);
            x2 = Integer.parseInt(values[5], 10);
            y1 = Integer.parseInt(values[6], 10);
            y2 = Integer.parseInt(values[7], 10);

            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                xInverted = true;
            }

            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
                yInverted = true;
            }
        } else if (values.length == 4) {
            x1 = Integer.parseInt(values[2]);
            y1 = Integer.parseInt(values[3]);
        } else if (values.length == 2) {

        } else if (drawType == DrawType.Text) {
            colour = Integer.parseInt(values[2], 10);
            x1 = Integer.parseInt(values[3], 10);
            y1 = Integer.parseInt(values[4], 10);
            text = content.substring(content.indexOf(':') + 1);
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

    public int getColour() {
        return this.colour;
    }

    public boolean fill() {
        return this.fill;
    }

    public String getText() {
        return this.text;
    }

    public boolean isXInverted() {
        return this.xInverted;
    }

    public boolean isYInverted() {
        return this.yInverted;
    }

    @Override
    public String toString() {
        return super.content;
    }
}
