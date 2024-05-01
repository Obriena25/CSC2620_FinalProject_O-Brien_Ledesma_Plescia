import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Connect4GUI extends JFrame {

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int CONNECT_COUNT = 4;

    private JLabel statusLabel;
    private JButton[][] boardButtons;

    private boolean isPlayer1Turn = true; // Flag to track current player's turn
    private boolean gameOver = false;

    public Connect4GUI() {
        // Set up the JFrame
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Set up the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the game board panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLUMNS));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize the boardButtons array
        boardButtons = new JButton[ROWS][COLUMNS];

        // Add buttons to represent the game board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                boardButtons[row][col] = new JButton();
                boardButtons[row][col].setBackground(Color.WHITE);
                boardButtons[row][col].setOpaque(true);
                boardButtons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add frame
                boardButtons[row][col].setPreferredSize(new Dimension(60, 60)); // Set dimensions for squares
                boardButtons[row][col].addActionListener(new BoardButtonListener(row, col));
                boardPanel.add(boardButtons[row][col]);
            }
        }

        // Create a status label to display game status
        statusLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add components to the main panel
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        // Add the main panel to the JFrame
        getContentPane().add(mainPanel);

        // Pack and display the JFrame
        pack();
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    // ActionListener for board buttons
    private class BoardButtonListener implements ActionListener {
        private int col;

        public BoardButtonListener(int row, int col) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return; // Game over, do not allow further moves

            // Find the lowest empty row in the clicked column
            int row = getLowestEmptyRow(col);
            if (row != -1) {
                // Change the color of the lowest empty button in the column
                if (isPlayer1Turn) {
                    boardButtons[row][col].setBackground(Color.RED);
                    statusLabel.setText("Player 2's Turn");
                } else {
                    boardButtons[row][col].setBackground(Color.YELLOW);
                    statusLabel.setText("Player 1's Turn");
                }
                // Check for win condition
                if (checkWinCondition(row, col)) {
                    String winner = isPlayer1Turn ? "Player 1" : "Player 2";
                    JOptionPane.showMessageDialog(Connect4GUI.this, "Congratulations! " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    gameOver = true;
                }
                // Toggle player turn
                isPlayer1Turn = !isPlayer1Turn;
            }
        }

        // Method to find the lowest empty row in a column
        private int getLowestEmptyRow(int col) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (boardButtons[row][col].getBackground() == Color.WHITE) {
                    return row;
                }
            }
            return -1; // Column is full
        }

        // Method to check for win condition
        private boolean checkWinCondition(int row, int col) {
            Color currentColor = boardButtons[row][col].getBackground();
            // Check horizontally
            if (countConnected(row, col, 0, 1, currentColor) + countConnected(row, col, 0, -1, currentColor) + 1 >= CONNECT_COUNT) return true;
            // Check vertically
            if (countConnected(row, col, 1, 0, currentColor) + countConnected(row, col, -1, 0, currentColor) + 1 >= CONNECT_COUNT) return true;
            // Check diagonally (ascending)
            if (countConnected(row, col, -1, 1, currentColor) + countConnected(row, col, 1, -1, currentColor) + 1 >= CONNECT_COUNT) return true;
            // Check diagonally (descending)
            return countConnected(row, col, 1, 1, currentColor) + countConnected(row, col, -1, -1, currentColor) + 1 >= CONNECT_COUNT;
        }

        // Method to count consecutive discs in a specific direction
        private int countConnected(int row, int col, int rowDelta, int colDelta, Color color) {
            int count = 0;
            int r = row + rowDelta;
            int c = col + colDelta;
            while (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && boardButtons[r][c].getBackground() == color) {
                count++;
                r += rowDelta;
                c += colDelta;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new Connect4GUI());
    }
}
