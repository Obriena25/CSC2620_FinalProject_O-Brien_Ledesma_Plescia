package com.connect4;
import javax.swing.JFrame;   

public class Buttons extends JFrame {

    public static void main(String[] args) {
        GUIButtons guiButtons = new GUIButtons();
        guiButtons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiButtons.setSize(500,300);
        guiButtons.setVisible(true);
    }
}