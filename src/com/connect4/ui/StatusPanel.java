package com.connect4.ui;

import javax.swing.*;
import java.awt.*;

/**
 * The StatusPanel class represents a panel for displaying status information.
 *
 * @author Maddie
 */
public class StatusPanel extends JPanel {

    private final JLabel statusLabel;

    /**
     * Constructs a StatusPanel with default settings.
     */
    public StatusPanel(String status) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(getWidth(), 30));
        setBackground(new Color(240, 240, 240)); // Light gray background

        statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Bold font
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 5)); // Add padding
        add(statusLabel, BorderLayout.CENTER);
    }

    /**
     * Sets the status text to be displayed.
     *
     * @param status The status text to be displayed.
     */
    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}
