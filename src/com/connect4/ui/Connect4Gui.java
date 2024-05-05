package com.connect4.ui;

import com.connect4.peer.ConnectFourSocket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Connect4Gui extends JFrame {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int CELL_SIZE = 100;
    private static final Color BOARD_COLOR = Color.BLUE;
    private static final Color EMPTY_COLOR = Color.WHITE;
    private static final Color PLAYER1_COLOR = Color.RED;
    private static final Color PLAYER2_COLOR = Color.YELLOW;

    private final ConnectFourSocket socket;
    private final StatusPanel statusPanel;
    private JPanel boardPanel;
    private int[][] board = new int[ROWS][COLUMNS]; // 0: empty, 1: player 1, 2: player 2

    public Connect4Gui(ConnectFourSocket socket) throws IOException {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(COLUMNS * CELL_SIZE, (ROWS * CELL_SIZE) + 70);
        this.socket = socket;

        var status = socket.isServer() ? "Waiting for player 2" : "connecting to player 1";
        this.statusPanel = new StatusPanel(status);
        add(this.statusPanel, BorderLayout.SOUTH);
    }

    public void drawBoard() {
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(COLUMNS * CELL_SIZE, ROWS * CELL_SIZE));
        add(boardPanel, BorderLayout.CENTER);
    }

    public void setStatus(String status) {
        this.statusPanel.setStatus(status);
    }

    private void drawBoard(Graphics g) {
        // Draw the board background
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, COLUMNS * CELL_SIZE, ROWS * CELL_SIZE);

        // Draw the cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                int x = col * CELL_SIZE;
                int y = (ROWS - 1 - row) * CELL_SIZE; // Start drawing from the bottom
                if (board[row][col] == 0) {
                    g.setColor(EMPTY_COLOR);
                } else if (board[row][col] == 1) {
                    g.setColor(PLAYER1_COLOR);
                } else {
                    g.setColor(PLAYER2_COLOR);
                }
                g.fillOval(x, y, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawOval(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

}
