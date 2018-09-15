import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Register RegisterPanel = new Register();
        frame.setContentPane(RegisterPanel.getMainPanel());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,650);
        frame.setVisible(true);
//
    }
}
