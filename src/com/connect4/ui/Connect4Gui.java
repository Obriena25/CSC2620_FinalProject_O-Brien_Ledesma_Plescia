package com.connect4.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.connect4.Constants;
import com.connect4.models.Board;
import com.connect4.peer.ConnectFourSocket;

public class Connect4Gui extends JFrame implements Constants {
    private static final int ANIMATION_SPEED = 50; // Speed of the animation (lower is faster)

    private final ConnectFourSocket socket;
    private final StatusPanel statusPanel;
    private JPanel boardPanel;
    private Board board = new Board();
    private JButton[] columnButtons;


    public Connect4Gui(ConnectFourSocket socket) throws IOException {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(COLUMNS * CELL_SIZE, (ROWS * CELL_SIZE) + 90);
        this.socket = socket;

        var status = socket.isServer() ? "Waiting for player 2" : "connecting to player 1";
        this.statusPanel = new StatusPanel(status);
        add(this.statusPanel, BorderLayout.SOUTH);
    
    // Create and add column buttons
    JPanel buttonPanel = new JPanel(new GridLayout(1, COLUMNS));
    columnButtons = new JButton[COLUMNS];
    for (int i = 0; i < COLUMNS; i++) {
        columnButtons[i] = new JButton(String.valueOf(i + 1));
        int column = i;
        columnButtons[i].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropPiece(column);
            }
        });
        buttonPanel.add(columnButtons[i]);
    
    add(buttonPanel, BorderLayout.NORTH);
    drawBoard(); // Add this line to initialize the board panel
}
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
                    if (currentRow > 0) {  // Animation
                        // System.out.println("1st condition");
                        // Clearing previous row
                        board.setValue(currentRow - 1, selectedColumn, EMPTY);
                        repaint();
                    }
                    var player = socket.isServer() ? PLAYER1 : PLAYER2;
                    if (currentRow < ROWS - 1 && board.getValue(currentRow + 1, selectedColumn) == 0) { // Animation
                        // System.out.println("2nd condition");
                        board.setValue(currentRow, selectedColumn, player);
                        currentRow++;
                        repaint();
                    } else {
                        ((Timer) e.getSource()).stop();
                        board.setValue(currentRow, selectedColumn, player);
                        repaint();
                        var winner = board.checkForWinner();
                        if (winner != EMPTY) {
                            // send last messages
                            showWinner(currentRow, selectedColumn, player);
                        } else {
                            waitingForPlayer(currentRow, selectedColumn, player);
                        }
                    }
                }
            });
            timer.start();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move, please select a different column", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void waitingForPlayer(int row, int col, int player) {
        try {
            var message = String.format("Waiting for the player %d to move", player == PLAYER1 ? PLAYER2 : PLAYER1);
            if (row != -1) {
                socket.sendMessage(row, col, player);
            }
            var waitingDlg = new WaitingDialog(this, message);
            SwingUtilities.invokeLater(waitingDlg::show);
            Thread connectionThread = new Thread(() -> {
                try {
                    socket.receiveMessage(this.board);
                    repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to established connection with player 2, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    waitingDlg.hide();
                    var winner = board.checkForWinner();
                    if (winner != EMPTY) {
                        showLoser();
                    }
                }
            });
            connectionThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            var message = String.format("Failed to send message to player %d", player);
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showWinner(int row, int col, int player) {
        try {
            var message = String.format("<html>You have won, player %d<br>Waiting for rematch</html>", player);
            var currentPlayer = socket.isServer() ? PLAYER1 : PLAYER2;
            if (currentPlayer == player) { // Send winning move
                socket.sendMessage(row, col, player);
            }
            var waitingDlg = new WaitingDialog(this, message);
            SwingUtilities.invokeLater(waitingDlg::show);
            Thread connectionThread = new Thread(() -> {
                try {
                    if (socket.receiveRematchConfirmation() == 'Y') {
                        board.resetBoard();
                        repaint();
                    } else {
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to established connection with player 2, please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    waitingDlg.hide();
                }
            });
            connectionThread.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            var message = String.format("Failed to send message to player %d", player);
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showLoser() {
        var currentPlayer = socket.isServer() ? PLAYER1 : PLAYER2;
        try {
            var message = String.format("<html>You have lost, player %d<br>Want a rematch?<html>", currentPlayer);
            int option = JOptionPane.showConfirmDialog(this, message, "Rematch Confirmation", JOptionPane.YES_NO_OPTION);
            socket.sendRematchConfirmation(option == JOptionPane.YES_OPTION ? 'Y' : 'N');
            if (option == JOptionPane.NO_OPTION) {
                System.exit(0); // Close the program
            } else {
                board.resetBoard();
                repaint();
                waitingForPlayer(-1, -1, currentPlayer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            var message = String.format("Failed to send message to player %d", currentPlayer);
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
