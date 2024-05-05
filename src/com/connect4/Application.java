package com.connect4;

import com.connect4.peer.ConnectFourSocket;
import com.connect4.ui.Connect4Gui;
import com.connect4.ui.WaitingDialog;

import javax.swing.*;
import java.io.IOException;

public class Application {
    private static final int port = 8001;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                var socket = new ConnectFourSocket(port);
                var gui = new Connect4Gui(socket);
                gui.setVisible(true);
                if (socket.isServer()) {
                    var waitingDlg = new WaitingDialog(gui);
                    SwingUtilities.invokeLater(waitingDlg::show);
                    Thread connectionThread = new Thread(() -> {
                        try {
                            socket.accept();
                            gui.setStatus("Player 2 connected");
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(gui, "Failed to established connection with player2, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            waitingDlg.hide();
                        }
                    });
                    connectionThread.start();
                } else {
                    gui.setStatus("Connected to player 1");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to start as  player1, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
