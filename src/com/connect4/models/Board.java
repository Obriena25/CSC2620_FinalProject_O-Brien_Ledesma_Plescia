package com.connect4.models;

import com.connect4.Constants;

import java.awt.*;

/**
 * Represents the Connect Four Board
 *
 * @author Maddie
 */
public class Board implements Constants {
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
        if (board[row][col] == 0) {
            return EMPTY_COLOR;
        } else if (board[row][col] == 1) {
            return PLAYER1_COLOR;
        }
        return PLAYER2_COLOR;
    }
}
