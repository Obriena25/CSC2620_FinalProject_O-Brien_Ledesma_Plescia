package com.connect4.ui;

import com.connect4.Constants;
import com.connect4.models.Board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardListener extends MouseAdapter implements Constants {

    private final Connect4Gui parent;
    public BoardListener(Connect4Gui parent) {
        this.parent = parent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        var selectedColumn = e.getX() / CELL_SIZE;
        parent.dropPiece(selectedColumn);
    }
}
