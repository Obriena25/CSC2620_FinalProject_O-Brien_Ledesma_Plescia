public class Connect4Game {
    private final int ROWS = 6;
    private final int COLS = 7; 
    private char[][] board;
    private cahr currentPlayerPlace;

    public Connect4Game() {
        board = new char[ROWS][COLS];
        currentPlayerPlace = 'X';
        reset();
    }
}
