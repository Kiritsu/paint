package paint.services;

import paint.ClientFrame;
import paint.packets.ChatPacket;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Class that will hold every send messages in the chat box.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ChattingService {
    /**
     * Contains the different messages.
     */
    private ArrayList<ChatPacket> logs;

    /**
     * Our frame GUI instance.
     */
    private ClientFrame frame;

    /**
     * Creates a new chatting service.
     */
    public ChattingService(ClientFrame frame) {
        this.frame = frame;
        this.logs = new ArrayList<>();
    }

    /**
     * Adds a new message.
     * @param packet Packet to add.
     */
    public void add(ChatPacket packet) {
        this.logs.add(packet);
        this.updateLabel();
    }

    /**
     * Updates our Label.
     */
    private void updateLabel() {
        JLabel label = this.frame.getTchatLabel();

        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        for (ChatPacket pck : logs) {
            builder.append(pck.getMessage());
            builder.append("<br/>");
        }
        builder.append("</html>");

        label.setText(builder.toString());
    }
}
