package paint.services;

import paint.packets.DrawPacket;

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
}
