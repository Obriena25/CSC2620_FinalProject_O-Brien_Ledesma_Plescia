package com.connect4.ui;

import com.connect4.peer.ConnectFourSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Connect4Gui extends JFrame {
    private final ConnectFourSocket socket;
    private final StatusPanel statusPanel;

    public Connect4Gui(ConnectFourSocket socket) throws IOException {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 750);
        this.socket = socket;

        var status = socket.isServer() ? "Waiting for player 2" : "connecting to player 1";
        this.statusPanel = new StatusPanel(status);
        add(this.statusPanel, BorderLayout.SOUTH);
    }

    public void setStatus(String status) {
        this.statusPanel.setStatus(status);
    }

}
