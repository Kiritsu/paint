package paint.services.client;

import paint.packets.DrawPacket;

import javax.swing.*;
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
     * Creates a new Drawing Service.
     */
    public DrawingService() {
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
     * Handles a new incoming draw packet.
     * @param packet Packet to handle.
     * @param graphics Graphics on which we apply the packets.
     */
    public void handle(DrawPacket packet, JPanel pnl, Graphics graphics) {
        switch (packet.getDrawType()) {
            case Circle:
                add(packet);
                break;
            case Square:
                add(packet);
                break;
            case Arrow:
                add(packet);
                break;
            case Eraser:
                remove(get(packet.getX1(), packet.getY1()));
                break;
            case Undo:
                undo();
                break;
            case Text:
                add(packet);
                break;
        }

        pnl.repaint();

        try {
            Thread.sleep(150);
        } catch(Exception e){}

        for (DrawPacket draw : draws) {
            graphics.setColor(new Color(draw.getColour()));

            switch (draw.getDrawType()) {
                case Circle:
                    if (draw.fill()) {
                        graphics.fillOval(draw.getX1(), draw.getY1(),draw.getX2() - draw.getX1(), draw.getY2() - draw.getY1());
                    } else {
                        graphics.drawOval(draw.getX1(), draw.getY1(),draw.getX2() - draw.getX1(), draw.getY2() - draw.getY1());
                    }
                    break;
                case Square:
                    if (draw.fill()) {
                        graphics.fillRect(draw.getX1(), draw.getY1(),draw.getX2() - draw.getX1(), draw.getY2() - draw.getY1());
                    } else {
                        graphics.drawRect(draw.getX1(), draw.getY1(),draw.getX2() - draw.getX1(), draw.getY2() - draw.getY1());
                    }
                    break;
                case Text:
                    graphics.drawString(draw.getText(), draw.getX1(), draw.getY1());
                    break;
                case Arrow:
                    if (draw.isXInverted()) {
                        if (draw.isYInverted()) {
                            graphics.fillOval(draw.getX1(), draw.getY1(), 10, 10);
                            graphics.drawLine(draw.getX2(), draw.getY2(), draw.getX1(), draw.getY1());
                        } else {
                            graphics.fillOval(draw.getX1(), draw.getY2(), 10, 10);
                            graphics.drawLine(draw.getX1(), draw.getY2(), draw.getX2(), draw.getY1());
                        }
                    } else {
                        if (draw.isYInverted()) {
                            graphics.fillOval(draw.getX2(), draw.getY1(), 10, 10);
                            graphics.drawLine(draw.getX2(), draw.getY1(), draw.getX1(), draw.getY2());
                        } else {
                            graphics.fillOval(draw.getX2(), draw.getY2(), 10, 10);
                            graphics.drawLine(draw.getX2(), draw.getY2(), draw.getX1(), draw.getY1());
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Adds a new draw and redraw the graphics.
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
}
