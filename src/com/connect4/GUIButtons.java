package com.connect4;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GUIButtons extends JFrame {
    
    private final JTextArea textArea;
    private final JTextArea textArea1;
    private final JButton copyJButton;
    private final JButton switchEditable;
    private final JButton exitButton;
    private final JButton saveButton;
    private final JButton addNewTextAreaButton;
    private ArrayList<JTextArea> areas = new ArrayList();
    private final JPanel buttonPanel = new JPanel(new GridLayout(1,1,1,1));
    private JPanel extraTextAreasPanel = new JPanel(new FlowLayout());

    public GUIButtons() {
        super("JTextArea Buttom");
        Box box = Box.createHorizontalBox();
        String button = "This is the button string that \n"
                + "will be copied from one text area to \n"
                + "the other. \n";

        textArea = new JTextArea(button, 5, 5);
        box.add(new JScrollPane(textArea));

        copyJButton = new JButton("Copy ");
        buttonPanel.add(copyJButton);

        copyJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(textArea.getSelectedText());
            }
        });


        switchEditable = new JButton(" Editable ");
        buttonPanel.add(switchEditable);
        switchEditable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setEditable(!textArea.isEditable());
                textArea1.setEditable(!textArea1.isEditable());
            }
        });

        saveButton = new JButton("Save");
        buttonPanel.add(saveButton);
        saveButton.addActionListener(new SaveHandler());

        addNewTextAreaButton = new JButton("Add new Text Area");
        buttonPanel.add(saveButton);
        addNewTextAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extraTextAreasPanel.add(new JTextArea(5,5));
                validate();
            }
        });
        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        box.add(buttonPanel);

        textArea1 = new JTextArea("", 5, 5);
        textArea1.setEditable(false);
        box.add(new JScrollPane(textArea1));

        box.add(extraTextAreasPanel);
        this.add(box);
    }

    @SuppressWarnings("unused")
    private class SaveHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editablTextArea = getEditableTextArea();
            File file = null;
            try {
                JFileChooser fc = new JFileChooser();
                int returnValue = fc.showOpenDialog(GUIButtons.this);
                file = fc.getSelectedFile();

                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.write(editablTextArea.getText());
                writer.close();

                editablTextArea.setText("");
            }
            catch(NullPointerException ex) {
                System.err.println("No File selected");
            }
            catch (FileNotFoundException | UnsupportedEncodingException ex) {
                System.err.println("\n File not written: " + file.getName());
            }
        }
    }

    private JTextArea getEditableTextArea() {
        if(textArea.isEditable())
            return textArea;
        return textArea1;
    }
}