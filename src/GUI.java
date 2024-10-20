import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();


    public GUI(){
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridBagLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Java Bank App");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);
    }


    public void startInterface(){
        JButton createAccButton = new JButton("Create Account");
        JButton logInButton = new JButton("Log In");

        createAccButton.addActionListener(e -> {
            clearPanel();
            createAccountInterface();
        });
        logInButton.addActionListener(e -> {
            clearPanel();
            // TODO create log in function

        });

        addButton(createAccButton, 0, 1);
        addButton(logInButton, 0, 2);

        frame.pack();
    }


    public void createAccountInterface(){
        JButton returnButton = new JButton("Return");
        JButton submitButton = new JButton("Submit");
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel currencyLabel = new JLabel("Currency: ");

        addButton(returnButton, 0, 3);
        addButton(submitButton, 1, 3);

        frame.pack();
    }


    public void addButton(JButton button, int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        button.setFocusPainted(false);

        panel.add(button, gbc);
    }


    public void clearPanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

}