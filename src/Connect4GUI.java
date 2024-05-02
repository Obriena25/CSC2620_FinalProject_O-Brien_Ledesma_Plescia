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
    private static final int COLUMNS= 7;
    private static final int CONNECT_COUNT = 4;

    private JLabel  statusLabel;
    private JButton[][] boardButtons;

    private boolean isPlayer1Turn = true;
    private boolean gameOver = false;

    public Connect4GUI() {
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLUMNS));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boardButtons = new JButton[ROWS][COLUMNS];

        for (int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLUMNS; col++) {
                boardButtons[row][col] = new JButton();
                boardButtons[row][col].setBackground(Color.WHITE);
                boardButtons[row][col].setOpaque(true);
                boardButtons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardButtons[row][col].setPreferredSize(new Dimension(60, 60));
                boardButtons[row][col].addActionListener(new BoardButtonListener(row, col));
                boardPanel.add(boardButtons[row][col]);
            }
        }
        statusLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 10));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private class BoardButtonListener implements ActionListener {
        private int col;

        public BoardButtonListener(int row, int col) {
            this.col = col;
        }
        public void actionPerformed(ActionEvent e) {
            if(gameOver) return;

            int row = getLowestEmptyRow(col);
            if(row != -1) {
                if(isPlayer1Turn) {
                    boardButtons[row][col].setBackground(Color.RED);
                    statusLabel.setText("Player 2's Turn");
                } else {
                    boardButtons[row][col].setBackground(Color.YELLOW);
                    statusLabel.setText("Player 1's Turn");
                }
                if (checkWinCondidtion(row, col)) {
                    String winner = isPlayer1Turn ? "Player 1" : "Player 2";
                    JOptionPane.showMessageDialog(Connect4GUI.this, "Congratulations! " + winner + "wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    gameOver = true;
                }
                isPlayer1Turn = !isPlayer1Turn;
            }
        }
        private int getLowestEmptyRow(int col) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (boardButtons[row][col].getBackground() == Color.WHITE) {
                    return row;
                }
            }
            return -1;
        }
        private boolean checkWinCondidtion(int row, int col) {
            Color currentColor = boardButtons[row][col].getBackground();
            if (countConnected(row, col, 0, 1, currentColor) + countConnected(row, col, 0, -1, currentColor) + 1  >= CONNECT_COUNT) return true;

            if (countConnected(row, col, 1, 0, currentColor) + countConnected(row, col, 0, -1, currentColor) + 1 >= CONNECT_COUNT) return true;

            if (countConnected(row, col, -1, 1, currentColor) + countConnected(row, col, 1, -1, currentColor) + 1 >= CONNECT_COUNT) return true;

            return countConnected(row, col, 1, 1, currentColor) + countConnected(row, col, -1, -1, currentColor) + 1 >= CONNECT_COUNT;
        }
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
            SwingUtilities.invokeLater(() -> new Connect4GUI());
        }

}
