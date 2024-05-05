package com.connect4.ui;

import javax.swing.*;
import java.awt.*;

public class WaitingDialog {
    private final JDialog dialog;

    /**
     * Constructs a WaitingDialog with the specified parent frame.
     *
     * @param parent The parent frame to which the dialog is attached.
     */
    public WaitingDialog(JFrame parent) {
        this.dialog = new JDialog(parent, "Waiting", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Disable close button
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(parent);
        dialog.setUndecorated(true);

        JLabel messageLabel = new JLabel("Waiting for player 2...");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the message

        dialog.add(messageLabel, BorderLayout.CENTER);
    }

    /**
     * Shows the rendering dialog.
     */
    public void show() {
        this.dialog.setVisible(true);
    }

    /**
     * Hides the rendering dialog.
     */
    public void hide() {
        this.dialog.dispose();
    }
}
