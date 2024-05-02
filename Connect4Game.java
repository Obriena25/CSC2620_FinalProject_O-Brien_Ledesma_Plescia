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

    public void rest() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[1][j] = ' ';
            }
        }
        currentPlayerPlace = 'X';
    }

    public boolean placePiece(int col, char piece) {
        if(col < 0 || col >= COLS || board[0][col] != ' ') {
            return false;
        }
        int row = ROWS - 1;
        while (row >= 0 && board[row][col] != ' ') {
            row--;
        }
        if (row >= 0) {
            board[row][col] = piece;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkWIn(char piece) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col <= COLS - 4; col++) {
                if (board[row][col] == piece && board[row][col + 1] == piece &&
                    board[row][col + 2] == piece && board[row][col + 3] == piece) {
                    return true;
                }
            }
        }
    
        for (int row = 0; row < ROWS; row++) {
            for(int col = 0; col <= COLS - 4; col++) {
                if (board[row][col] == piece && board[row][col + 1] == piece &&
                    board[row + 2][col] == piece && board[row][col + 3] == piece) {
                    return true;
                }
            }
        }
        
        for (int row = 3; row < ROWS; row++) {
            for(int col = 0; col <= COLS - 4; col++) {
                if (board[row][col] == piece && board[row + 1][col] == piece &&
                    board[row + 2][col + 2] == piece && board[row + 3][col] == piece) {
                    return true;
                }
            }
        }
        
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col <= COLS - 4; col++) {
                if(board[row][col] == piece && board[row + 1 ][col + 1] == piece &&
                board[row - 2][col + 2] == piece && board[row - 3][col + 3] == piece) {
                return true;
                }
            }
        }
        return false;
    }
        public boolean isBoardFull() {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if(board[i][j] = ' ') {
                        return false;
                    }
                }
            }
            return true;
        }

    public void switchPlayer() {
        currentPlayerPlace = (currentPlayerPlace == 'X') ? 'O' : 'X';
    }
    public char getCurrentPlayerPiece() {
        return currentPlayerPlace;
    }
    public char getPieceAt(int row, int col) {
        return board[row][col];
    }
    public int getLastMoveRow() {
    }
    public char[][] getBoard() {

    }
    }
    
    



