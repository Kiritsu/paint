package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class represents the graphic user interface.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class ClientFrame extends JFrame {
    /**
     * Panel that contains our tchatLabel.
     */
    private JPanel textPanel;

    /**
     * Panel that contains our textField.
     */
    private JPanel textBoxPanel;

    /**
     * TextField on which we have our text input.
     */
    private JTextField textField;

    /**
     * Label on which the text sent/received is displayed
     */
    private JLabel tchatLabel;

    /**
     * Client associated with that GUI.
     */
    private Client client;

    /**
     * Creates a new instance of that GUI.
     */
    public ClientFrame(Client client) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Paint Chat Server");
        this.setSize(700, 700);
        this.setLayout(new GridLayout(2, 2));

        this.textPanel = new JPanel();
        this.tchatLabel = new JLabel();
        this.textPanel.add(this.tchatLabel);

        this.textBoxPanel = new JPanel();
        this.textField = new JTextField();
        this.textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                JTextField tf = (JTextField)e.getSource();

                if (e.getKeyCode() == 10) { //pressing enter key.
                    client.sendMessage(tf.getText());
                    tf.setText("");
                }
            }
        });
        this.textBoxPanel.setLayout(new BorderLayout());
        this.textBoxPanel.add(this.textField, BorderLayout.SOUTH);

        this.add(this.textPanel);
        this.add(this.textBoxPanel);

        this.setVisible(true);
    }

    public JLabel getTchatLabel() {
        return this.tchatLabel;
    }
}
