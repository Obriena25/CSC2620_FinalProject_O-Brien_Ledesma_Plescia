import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class BoardGUI extends JFrame {

    public BoardGUI() {
        JButton[][] buttons;
        int ROWS = 6;
        int COLS = 7;

            setTitle("Connect Four");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            buttons = new JButton[ROWS][COLS];
        
            for (int i = 0; i < ROWS; i++) {
                for(int j = 0; j < COLS; j++) {
                    buttons[i][j] = new JButton();
                    buttons[i][j].setBackground(Color.WHITE);
                    buttons[i][j].setPreferredSize(new Dimension(50, 50));
                }
            }
            setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new BoardGUI());
        }

    }

