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
            logInInterface();
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
        JLabel pinLabel = new JLabel("PIN: ");
        JTextField usernameField = new JTextField(15);
        JTextField pinField = new JTextField(4);
        JComboBox<String> cuurencyBox = new JComboBox<>(getCurrencyOptionsAsString());

        String username;
        Enums.CurrencyType currency;
        String pin;


        returnButton.addActionListener(e -> {
            clearPanel();
            startInterface();
        });
        submitButton.addActionListener(e -> {
            // TODO verify and add account
        });

        addButton(returnButton, 0, 3);
        addButton(submitButton, 1, 3);

        addComponent(usernameLabel, 0, 0);
        addComponent(usernameField, 1, 0);
        addComponent(currencyLabel, 0, 1);
        addComponent(cuurencyBox, 1, 1);
        addComponent(pinLabel, 0, 2);
        addComponent(pinField, 1, 2);

        frame.pack();
    }


    public void logInInterface(){
        JButton returnButton = new JButton("Return");
        JButton submitButton = new JButton("Submit");
        JLabel ibanLabel = new JLabel("IBAN: ");
        JLabel pinLabel = new JLabel("PIN: ");
        JTextField ibanField = new JTextField(24);
        JTextField pinField = new JTextField(4);

        returnButton.addActionListener(e -> {
            clearPanel();
            startInterface();
        });
        submitButton.addActionListener(e -> {
            // TODO verify details and log in, else error
        });

        addButton(returnButton, 0, 2);
        addButton(submitButton, 1, 2);

        addComponent(ibanLabel, 0, 0);
        addComponent(ibanField, 1, 0);
        addComponent(pinLabel, 0, 1);
        addComponent(pinField, 1, 1);

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


    public void addComponent(JComponent component, int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0 ,10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(component, gbc);
    }


    public void clearPanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }


    private String[] getCurrencyOptionsAsString() {
        Enums.CurrencyType[] currencies = Enums.CurrencyType.values();
        String[] options = new String[currencies.length];

        for (int i = 0; i < currencies.length; i++) {
            options[i] = currencies[i].name();
        }

        return options;
    }

}