import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Connect4GUI extends JFrame {

    private static final int ROWS = 6;
    private static final int COLUMNS= 7;
    private static final int CONNECT_COUNT = 4;

    private JLabel  statusLabel;
    private JButton[][] boardButtons;

    private boolean isPlayer1Turn = true;
    private boolean gameOver = false;

    public Connect4GUI() {
        setTitle();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainJPanel = new JPanel();
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




    }
}
