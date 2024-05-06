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
                    var waitingDlg = new WaitingDialog(gui, "Waiting for player 2...");
                    SwingUtilities.invokeLater(waitingDlg::show);
                    Thread connectionThread = new Thread(() -> {
                        try {
                            socket.accept();
                            gui.setStatus("Player 1 ready");
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(gui, "Failed to established connection with player2, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            waitingDlg.hide();
                        }
                    });
                    connectionThread.start();
                } else {
                    gui.setStatus("Player 2 is ready");
                    gui.waitingForPlayer(-1, -1, Constants.PLAYER2);
                }
                gui.drawBoard();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to start as player1, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
