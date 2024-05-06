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
                    if (currentRow > 0) {  // Animation
                        // System.out.println("1st condition");
                        // Clearing previous row
                        board.setValue(currentRow - 1, selectedColumn, 0);
                        repaint();
                    }
                    if (currentRow < ROWS - 1 && board.getValue(currentRow + 1, selectedColumn) == 0) { // Animation
                        // System.out.println("2nd condition");
                        board.setValue(currentRow, selectedColumn, socket.isServer() ? 1 : 2);
                        currentRow++;
                        repaint();
                    } else { // Actually Place Piece.
                        // System.out.println("3rd condition");
                        ((Timer) e.getSource()).stop();
                        var player = socket.isServer() ? 1 : 2;
                        board.setValue(currentRow, selectedColumn, player);
                        repaint();
                        if(testForWin(currentRow, selectedColumn, player)){
                            System.out.println("Player #" + player + "WINS!!");
                            return;
                        }
                        waitingForPlayer(currentRow, selectedColumn, player);
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
            var message = String.format("Waiting for the player %d to move", player == 1 ? 2 : 1);
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
                }
            });
            connectionThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            var message = String.format("Failed to send message to player %d", player);
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    public boolean testForWin(int row, int column, int player){
        System.out.println("*****************TEST FOR WIN: row == " + row);
        Color playerColor = PLAYER1_COLOR;
        if(player == 2){ 
            playerColor = PLAYER2_COLOR;
        }
        if(checkLeft(row, column, playerColor) ||
                 checkUpRight(row, column, playerColor) ||
                 checkUp(row, column, playerColor) || 
                 checkUpLeft(row, column, playerColor)){
            return true;
        }  
        return false;
    }


    public boolean checkLeft(int row, int column, Color playerColor){
        // int orig_row = row;
        int orig_col = column;
        int counter = 1;
        boolean keepGoing = true;
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(column == 0){ // check for out of bounds
                break;
            }
            column--; // check to left
            if(board.getColor(row, column).equals(playerColor)) // check the players color
            {
                counter++; // one more in row
                continue;
            }
            break; // not playerColor, go in other direction.
        }
        // row = orig_row;
        column = orig_col; // reset to most recently dropped piece.
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(column == 6){ // check for out of bounds
                break;
            }
            column++; // check to right
            if(board.getColor(row, column).equals(playerColor))
            {
                counter++;
                continue;
            }
            break; // not player color, no win
        }
        return false; // no win

    }


    public boolean checkUp(int row, int column, Color playerColor){
        int orig_row = row;
        // int orig_col = column;
        int counter = 1;
        boolean keepGoing = true;
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(row == 0){ // check for out of bounds
                break;
            }
            row--; // check to left
            if(board.getColor(row, column).equals(playerColor)) // check the players color
            {
                counter++; // one more in row
                continue;
            }
            break; // not playerColor, go in other direction.
        }
        row = orig_row;
        // column = orig_col; // reset to most recently dropped piece.
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(row == 5){ // check for out of bounds
                break;
            }
            row++; // check to right
            if(board.getColor(row, column).equals(playerColor))
            {
                counter++;
                continue;
            }
            break; // not player color, no win
        }
        return false; // no win

    }


    public boolean checkUpRight(int row, int column, Color playerColor){
        int orig_row = row;
        int orig_col = column;
        int counter = 1;
        boolean keepGoing = true;
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(column == 0 || row == 5){ // check for out of bounds
                break;
            }
            column--; // check to left
            row++; // check to down
            if(board.getColor(row, column).equals(playerColor)) // check the players color
            {
                counter++; // one more in row
                continue;
            }
            break; // not playerColor, go in other direction.
        }
        row = orig_row; // reset to most recently dropped piece.
        column = orig_col; // reset to most recently dropped piece.
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(column == 6 || row == 0){ // check for out of bounds
                break;
            }
            column++; // check to right
            row--; // check to up
            if(board.getColor(row, column).equals(playerColor))
            {
                counter++;
                continue;
            }
            break; // not player color, no win
        }
        return false; // no win

    }

    public boolean checkUpLeft(int row, int column, Color playerColor){
        int orig_row = row;
        int orig_col = column;
        int counter = 1;
        boolean keepGoing = true;
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(column == 0 || row == 0){ // check for out of bounds
                break;
            }
            column--; // check to left
            row--; // check to up
            if(board.getColor(row, column).equals(playerColor)) // check the players color
            {
                counter++; // one more in row
                continue;
            }
            break; // not playerColor, go in other direction.
        }
        row = orig_row; // reset to most recently dropped piece.
        column = orig_col; // reset to most recently dropped piece.
        while(keepGoing){
            if(counter == 4){ // check for win
                return true;
            }
            if(column == 6 || row == 5){ // check for out of bounds
                break;
            }
            column++; // check to right
            row++; // check to down
            if(board.getColor(row, column).equals(playerColor))
            {
                counter++;
                continue;
            }
            break; // not player color, no win
        }
        return false; // no win

    }







}
