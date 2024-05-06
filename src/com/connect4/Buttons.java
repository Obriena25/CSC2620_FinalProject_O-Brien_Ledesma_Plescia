package com.connect4;
import javax.swing.JFrame;   

public class Buttons {

    public static void main(String[] args) {
        GUIButtons guiButtons = new GUIButtons();
        guiButtons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiButtons.setSize(10,10);
        guiButtons.setVisible(true);
    }
}