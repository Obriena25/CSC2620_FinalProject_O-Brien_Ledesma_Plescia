package com.connect4.models;

/**
 * Represents the Connect Four Board
 * @author Maddie
 */
public class Board {
    private int[][] board;

    /**
     * Default construct, initialize a new game.
     */
    public Board() {
        this.board = new int[6][7 ];
    }
}
