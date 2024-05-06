package com.connect4.models;

import com.connect4.Constants;

import java.awt.*;

/**
 * Represents the Connect Four Board
 *
 * @author Maddie
 */
public class Board implements Constants {
    private static final int CONNECT = 4;

    private int[][] board = new int[ROWS][COLUMNS]; // 0: empty, 1: player 1, 2: player 2

    /**
     * Default construct, initialize a new game.
     */
    public Board() {
    }

    public boolean isValidMove(int column) {
        return board[0][column] == 0;
    }


    public int getValue(int row, int col) {
        return board[row][col];
    }

    public void setValue(int row, int col, int value) {
        board[row][col] = value;
    }

    public Color getColor(int row, int col) {
        if (board[row][col] == EMPTY) {
            return EMPTY_COLOR;
        } else if (board[row][col] == PLAYER1) {
            return PLAYER1_COLOR;
        }
        return PLAYER2_COLOR;
    }


    public int checkForWinner() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col <= COLUMNS - CONNECT; col++) {
                int player = board[row][col];
                if (player != 0 && player == board[row][col + 1] && player == board[row][col + 2] && player == board[row][col + 3]) {
                    return player; // Found winner
                }
            }
        }

        // Vertical check
        for (int row = 0; row <= ROWS - CONNECT; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                int player = board[row][col];
                if (player != 0 && player == board[row + 1][col] && player == board[row + 2][col] && player == board[row + 3][col]) {
                    return player; // Found winner
                }
            }
        }

        // Diagonal (bottom-left to top-right) check
        for (int row = 0; row <= ROWS - CONNECT; row++) {
            for (int col = 0; col <= COLUMNS - CONNECT; col++) {
                int player = board[row][col];
                if (player != 0 && player == board[row + 1][col + 1] && player == board[row + 2][col + 2] && player == board[row + 3][col + 3]) {
                    return player; // Found winner
                }
            }
        }

        // Diagonal (top-left to bottom-right) check
        for (int row = CONNECT - 1; row < ROWS; row++) {
            for (int col = 0; col <= COLUMNS - CONNECT; col++) {
                int player = board[row][col];
                if (player != 0 && player == board[row - 1][col + 1] && player == board[row - 2][col + 2] && player == board[row - 3][col + 3]) {
                    return player; // Found winner
                }
            }
        }

        // No winner
        return 0;
    }

    public void resetBoard() {
        this.board = new int[ROWS][COLUMNS];
    }
}
