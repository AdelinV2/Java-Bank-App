import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    public GUI(){


        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 70, 100));
        panel.setLayout(new GridLayout(4, 2));
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Java Bank App");
        frame.pack();
        frame.setVisible(true);
    }

    public void start(){
        JButton createAccButton = new JButton("Create Account");
        JButton logInButton = new JButton("Log In");
        panel.add(createAccButton);
        panel.add(logInButton);
        frame.pack();
    }

}