import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUIButtons extends JButton {
private JTextField textField;
private JButton button;

public GUIButtons() {
    super("JButton Demo");
    setLayout(new FlowLayout());

    textField = new JTextField();
    this.add(textField);

    TextFieldHandler handler = new TextFieldHandler();
    textField.addActionListener(handler); 
    textField.addFocusListener(handler);

    button = new JButton("Click here");
    this.add(button);
    button.addActionListener(handler);

}
private class TextFieldHandler implements ActionListener, FocusListener {
    @Override
    public void focusGained(FocusEvent event) {
        ((JTextField) event.getSource()).setText("");
    }

    @Override
    public void focusLost(FocusEvent event) {
        if(((JTextField)event.getSource()).getText().equals(""))
            ((JTextField) event.getSource()).setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String string = String.format("textField: %s", 
                textField.getText());
        JOptionPane.showMessageDialog(button, GUIButtons.this, string, 0, null);
    }
}
public void setDefaultCloseOperation(int exitOnClose) {
}

}