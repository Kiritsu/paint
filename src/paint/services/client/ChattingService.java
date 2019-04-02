package paint.services.client;

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
        JLabel textArea = this.frame.getLabelText();

        StringBuilder builder = new StringBuilder();
        builder.append("<html>");

        int i;
        if (logs.size() < 13) {
            i = 0;
        } else {
            i = logs.size() - 13;
        }

        for (; i < logs.size(); i++) {
            builder.append(logs.get(i).getMessage());
            builder.append("<br/>");
        }
        builder.append("</html>");

        textArea.setText(builder.toString());
    }
}
