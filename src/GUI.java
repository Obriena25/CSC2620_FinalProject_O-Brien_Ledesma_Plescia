import javax.swing.JFrame;

public class GUI {

    public static void main(String[] args) {
        GUIButtons guiConnectFour = new GUIButtons();
        guiConnectFour.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiConnectFour.setSize(800,800);
        guiConnectFour.setVisible(false);
    }
    
}
