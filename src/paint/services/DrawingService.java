package paint.services;

import paint.Logger;
import paint.enums.DrawType;
import paint.enums.LogLevel;
import paint.packets.DrawPacket;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class that will hold every draws and help to retrieve specific datas.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class DrawingService {
    /**
     * Contains the different draws.
     */
    private ArrayList<DrawPacket> draws;

    /**
     * Current color used for this client.
     */
    private Color currentColor;

    /**
     * Current draw type for this client.
     */
    private DrawType currentDrawType;

    /**
     * Defines if we colorize the different drawings.
     */
    private boolean fill;

    /**
     * Creates a new Drawing Service.
     */
    public DrawingService() {
        this.currentColor = Color.black;
        this.currentDrawType = DrawType.Pen;
        this.draws = new ArrayList<>();
    }

    /**
     * Undo the last draw.
     */
    public void undo() {
        this.draws.remove(draws.size() - 1);
    }

    /**
     * Removes a drawing.
     * @param packet Drawing to remove.
     */
    public void remove(DrawPacket packet) {
        this.draws.remove(packet);
    }

    /**
     * Adds a new draw.
     * @param packet Packet to add.
     */
    public void add(DrawPacket packet) {
        this.draws.add(packet);
    }

    /**
     * Gets a draw by the position of the mouse at the moment the user clicked the draw.
     * @param x Position x of the mouse.
     * @param y Position y of the mouse.
     */
    public DrawPacket get(int x, int y) {
        for (int i = this.draws.size() - 1; i > 0; i--) {
            DrawPacket packet = this.draws.get(i);
            if (packet.getX1() <= x && packet.getX2() >= x) {
                if (packet.getY1() <= y && packet.getY2() >= y) {
                    return packet;
                }
            }
        }

        return null;
    }

    /**
     * Changes the current user's color.
     * @param color Color.
     */
    public void setCurrentColor(Color color) {
        Logger.println(LogLevel.Debug, "Drawing Service", "Changed the current color to: " + color);
        this.currentColor = color;
    }

    /**
     * Changes the current user's draw type.
     * @param type Type of drawing.
     */
    public void setCurrentDrawType(DrawType type) {
        Logger.println(LogLevel.Debug, "Drawing Service", "Changed the current draw type to: " + type.name());
        this.currentDrawType = type;
    }

    /**
     * Toggles true or false the fill attribute.
     */
    public void toggleFill() {
        Logger.println(LogLevel.Debug, "Drawing Service", "Toggled fill to: " + !fill);
        fill = !fill;
    }

    public void handleGraphics(Graphics graphics, int x1, int x2, int y1, int y2) {
        switch (currentDrawType) {
            case Circle:
                break;
            case Square:
                break;
            case Pen:
                graphics.drawLine(x1, x2, y1, y2);
                break;
            case Eraser:
                break;
            case Text:
                break;
        }
    }
}
