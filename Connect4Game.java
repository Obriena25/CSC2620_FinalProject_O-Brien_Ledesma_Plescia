public class Connect4Game {
    private final int ROWS = 6;
    private final int COLS = 7;
    private char[][] board;
    private char currentPlayerPiece;

    public Connect4Game() {
        board = new char[ROWS][COLS];
        currentPlayerPiece = 'X'; // Player 'X' starts the game
        reset();
    }

    public void reset() {
        // Initialize the board with empty spaces
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayerPiece = 'X';
    }

    public boolean placePiece(int col, char piece) {
        // Check if the column is valid and not full
        if (col < 0 || col >= COLS || board[0][col] != ' ') {
            return false;
        }
        // Find the lowest empty row in the column
        int row = ROWS - 1;
        while (row >= 0 && board[row][col] != ' ') {
            row--;
        }
        // Place the piece in the lowest empty row
        if (row >= 0) {
            board[row][col] = piece;
            return true;
        } else {
            return false; // Column is full
        }
    }

    public boolean checkWin(char piece) {
        // Check horizontal win
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col <= COLS - 4; col++) {
                if (board[row][col] == piece && board[row][col + 1] == piece &&
                    board[row][col + 2] == piece && board[row][col + 3] == piece) {
                    return true; // Horizontal win
                }
            }
        }
    
        // Check vertical win
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == piece && board[row + 1][col] == piece &&
                    board[row + 2][col] == piece && board[row + 3][col] == piece) {
                    return true; // Vertical win
                }
            }
        }
    
        // Check diagonal (down-right) win
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int col = 0; col <= COLS - 4; col++) {
                if (board[row][col] == piece && board[row + 1][col + 1] == piece &&
                    board[row + 2][col + 2] == piece && board[row + 3][col + 3] == piece) {
                    return true; // Diagonal (down-right) win
                }
            }
        }
    
        // Check diagonal (up-right) win
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col <= COLS - 4; col++) {
                if (board[row][col] == piece && board[row - 1][col + 1] == piece &&
                    board[row - 2][col + 2] == piece && board[row - 3][col + 3] == piece) {
                    return true; // Diagonal (up-right) win
                }
            }
        }
    
        return false; // No win found
    }
    
    public boolean isBoardFull() {
        // Check if the board is full (no empty spaces)
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public void switchPlayer() {
        // Switch to the next player's turn
        currentPlayerPiece = (currentPlayerPiece == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayerPiece() {
        return currentPlayerPiece;
    }

    public char getPieceAt(int row, int col) {
        return board[row][col];
    }

    public int getLastMoveRow() {
        throw new UnsupportedOperationException("Unimplemented method 'getLastMoveRow'");
    }

    public char[][] getBoard() {

        throw new UnsupportedOperationException("Unimplemented method 'getBoard'");
    }

    // Other methods for handling turns, checking for draws, etc.
}
