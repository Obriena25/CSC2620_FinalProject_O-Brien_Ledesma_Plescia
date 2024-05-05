package com.connect4.ui;

import com.connect4.Constants;
import com.connect4.models.Board;
import com.connect4.peer.ConnectFourSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Connect4Gui extends JFrame implements Constants {
    private static final int ANIMATION_SPEED = 50; // Speed of the animation (lower is faster)

    private final ConnectFourSocket socket;
    private final StatusPanel statusPanel;
    private JPanel boardPanel;
    private Board board = new Board();


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
        boardPanel.addMouseListener(new BoardListener(this));
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
                int y = (row) * CELL_SIZE; // Start drawing from the bottom
                g.setColor(board.getColor(row, col));
                g.fillOval(x, y, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawOval(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    protected void dropPiece(final int selectedColumn) {
        if (board.isValidMove(selectedColumn)) {
            Timer timer = new Timer(ANIMATION_SPEED, new ActionListener() {
                int currentRow = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentRow > 0) {
                        // Clearing previous row
                        board.setValue(currentRow-1, selectedColumn, 0);
                        repaint();
                    }
                    if (currentRow < ROWS - 1 && board.getValue(currentRow + 1, selectedColumn) == 0) {
                        board.setValue(currentRow, selectedColumn, socket.isServer() ? 1 : 2);
                        currentRow++;
                        repaint();
                    } else {
                        ((Timer) e.getSource()).stop();
                        board.setValue(currentRow, selectedColumn, socket.isServer() ? 1 : 2);
                        repaint();
                    }
                }
            });
            timer.start();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move, please select a different column", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
