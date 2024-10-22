import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();
    private BankService bank;


    public GUI(BankService bank){
        this.bank = bank;

        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridBagLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Java Bank App");
        frame.setPreferredSize(new Dimension(500, 350));
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
        JComboBox<String> currencyBox = new JComboBox<>(getCurrencyOptionsAsString());

        returnButton.addActionListener(e -> {
            clearPanel();
            startInterface();
        });

        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            Enums.CurrencyType currency = Enums.CurrencyType.valueOf((String) currencyBox.getSelectedItem());
            String pin = pinField.getText();

            if (isValidPin(pin)){
                String iban = this.bank.generateIBAN();
                this.bank.createAccount(username, currency, iban, pin);
                JOptionPane.showMessageDialog(frame, "Your IBAN is: " + iban);

                clearPanel();
                startInterface();
            }

            else{
                pinField.setText("");
            }
        });

        addButton(returnButton, 0, 3);
        addButton(submitButton, 1, 3);

        addComponent(usernameLabel, 0, 0);
        addComponent(usernameField, 1, 0);
        addComponent(currencyLabel, 0, 1);
        addComponent(currencyBox, 1, 1);
        addComponent(pinLabel, 0, 2);
        addComponent(pinField, 1, 2);

        frame.pack();
    }


    public void logInInterface(){
        JButton returnButton = new JButton("Return");
        JButton submitButton = new JButton("Submit");
        JLabel ibanLabel = new JLabel("IBAN: ");
        JLabel pinLabel = new JLabel("PIN: ");
        JTextField ibanField = new JTextField(25);
        JTextField pinField = new JTextField(4);

        returnButton.addActionListener(e -> {
            clearPanel();
            startInterface();
        });

        submitButton.addActionListener(e -> {
            String iban = ibanField.getText();
            String pin = pinField.getText();
            Account account = this.bank.findAccount(iban);

            if(account != null){
                if(account.getPin().equals(pin)){
                    clearPanel();
                    selectionInterface(account);
                }
                else{
                    pinField.setText("");
                    JOptionPane.showMessageDialog(frame, "Incorrect PIN!");
                }
            }

            else
                JOptionPane.showMessageDialog(frame, "Account not found!");

        });

        addButton(returnButton, 0, 2);
        addButton(submitButton, 1, 2);

        addComponent(ibanLabel, 0, 0);
        addComponent(ibanField, 1, 0);
        addComponent(pinLabel, 0, 1);
        addComponent(pinField, 1, 1);

        frame.pack();
    }


    public void selectionInterface(Account account){
        JButton accountInfoButton = new JButton("Account Info");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton returnButton = new JButton("Return");

        addButton(accountInfoButton, 0, 0);
        addButton(transferButton, 0, 1);
        addButton(withdrawButton, 2, 0);
        addButton(depositButton, 2, 1);
        addButton(returnButton, 1,2);


        returnButton.addActionListener(e -> {
            clearPanel();
            startInterface();
        });

        accountInfoButton.addActionListener(e -> {
            clearPanel();
            accountInfoInterface(account);
        });

        withdrawButton.addActionListener(e -> {
            clearPanel();
            withdrawInterface(account);
        });

        depositButton.addActionListener(e -> {
            clearPanel();
            depositInterface(account);
        });

        frame.pack();
    }


    void bankStatementInterface(Account account){
        JButton returnButton = new JButton("Return");
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton(returnButton, 0,1);

        returnButton.addActionListener(e -> {
            clearPanel();
            selectionInterface(account);
        });

        for(Transaction transaction : this.bank.findTransactionsByIBAN(account.getIBAN())){
            if(transaction.getType() == Enums.TransactionType.TRANSFER_TO ||
                    transaction.getType() == Enums.TransactionType.TRANSFER_FROM){
                textArea.append(String.valueOf(transaction.getType()) + " | " + transaction.getAccountFrom().getIBAN() +
                        " | " + transaction.getAmount() + transaction.getAccountTo().getCurrency() + " | " +
                        transaction.getDate() + "\n");
            }
            else {
                textArea.append(String.valueOf(transaction.getType()) + " | " + transaction.getAmount() +
                        transaction.getAccountTo().getCurrency() + " | " + transaction.getDate() + "\n");
            }
        }


        frame.pack();
    }


    public void accountInfoInterface(Account account){
        JButton returnButton = new JButton("Return");
        JButton generateBankStatement = new JButton("Bank Statement");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel usernameField = new JLabel(account.getUsername());
        JLabel balanceLabel = new JLabel("Balance:");
        JLabel balanceField = new JLabel(String.valueOf(account.getBalance()) + " " + account.getCurrency());

        addButton(generateBankStatement,1,2);
        addButton(returnButton, 0,2);
        addComponent(usernameLabel, 0,0);
        addComponent(usernameField, 1, 0);
        addComponent(balanceLabel, 0, 1);
        addComponent(balanceField,1,1);

        returnButton.addActionListener(e -> {
            clearPanel();
            selectionInterface(account);
        });

        generateBankStatement.addActionListener(e -> {
            // TODO generateBankStatement function
        });

        frame.pack();
    }


    public void transferInterface(Account account){
        JButton transferButton = new JButton("Transfer");
        JButton returnButton = new JButton("Return");
        JLabel amountToTransfer = new JLabel("Amount to transfer");
        JTextField amountField = new JTextField(15);

        addButton(returnButton, 0,1);
        addButton(transferButton, 1, 1);
        addComponent(amountToTransfer, 0, 0);
        addComponent(amountField, 1,0);

        returnButton.addActionListener(e -> {
            selectionInterface(account);
        });

        transferButton.addActionListener(e -> {
            Account accountTo = this.bank.findAccount(amountField.getText());
            if (accountTo != null) {
                Integer amount = getInt(amountField.getText());
                if (amount > 0) {
                    String transID = this.bank.generateTransactionID(Enums.TransactionType.TRANSFER_TO);
                    this.bank.makeTransaction(transID, account, accountTo, amount, Enums.TransactionType.TRANSFER_TO);
                    if (this.bank.findTransaction(transID).isCompletedTransaction()){
                        this.bank.makeTransaction(this.bank.generateTransactionID(Enums.TransactionType.TRANSFER_FROM),
                                accountTo, account, amount, Enums.TransactionType.TRANSFER_FROM);
                        JOptionPane.showMessageDialog(frame, "Transaction successful!");
                        clearPanel();
                        selectionInterface(account);
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Transaction was canceled!");
                    }
                }
            }

            else{
                JOptionPane.showMessageDialog(frame, "Account not found!");
            }
        });

        frame.pack();
    }


    public void depositInterface(Account account){
        JButton returnButton = new JButton("Return");
        JButton depositButton = new JButton("Deposit");
        JLabel amountToDeposit = new JLabel("Amount to deposit");
        JTextField amountField = new JTextField(15);

        addButton(returnButton, 0, 3);
        addButton(depositButton,1,3);
        addComponent(amountToDeposit, 0, 0);
        addComponent(amountField, 1, 0);

        returnButton.addActionListener(e -> {
            clearPanel();
            selectionInterface(account);
        });

        depositButton.addActionListener(e -> {
            Integer amount = getInt(amountField.getText());
            if(amount > 0) {
                String transID = this.bank.generateTransactionID(Enums.TransactionType.DEPOSIT);
                this.bank.makeTransaction(transID, account, null, amount, Enums.TransactionType.DEPOSIT);
                if(this.bank.findTransaction(transID).isCompletedTransaction()){
                    JOptionPane.showMessageDialog(frame, "Deposit was successful");
                    clearPanel();
                    selectionInterface(account);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Deposit was canceled!");
                }
            }
        });

        frame.pack();
    }


    public void withdrawInterface(Account account){
        JButton returnButton = new JButton("Return");
        JButton withdrawButton = new JButton("Withdraw");
        JLabel amountToWithdraw = new JLabel("Amount to withdraw");
        JTextField amountField = new JTextField(15);

        addButton(returnButton, 0, 3);
        addButton(withdrawButton, 1, 3);
        addComponent(amountToWithdraw, 0,0);
        addComponent(amountField, 1, 0);

        returnButton.addActionListener(e -> {
            clearPanel();
            selectionInterface(account);
        });

        withdrawButton.addActionListener(e -> {
            Integer amount = getInt(amountField.getText());
            if (amount > 0) {
                String transID = this.bank.generateTransactionID(Enums.TransactionType.WITHDRAW);
                this.bank.makeTransaction(transID, account, null, amount, Enums.TransactionType.WITHDRAW);
                if (this.bank.findTransaction(transID).isCompletedTransaction()){
                    JOptionPane.showMessageDialog(frame, "Transaction successful!");
                    clearPanel();
                    selectionInterface(account);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Transaction was canceled!");
                }
            }
        });

        frame.pack();
    }


    public void addButton(JButton button, int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension size = new Dimension(160, 26);

        button.setFocusPainted(false);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);

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


    public boolean isValidPin(String pin){
        if (pin.length() != 4){
            JOptionPane.showMessageDialog(frame, "PIN must be of length 4!");
            return false;
        }

        for(char c : pin.toCharArray()){
            if(!Character.isDigit(c)) {
                JOptionPane.showMessageDialog(frame, "PIN must contain only digits!");
                return false;
            }
        }
        return true;
    }


    public Integer getInt(String text){
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(frame, "You can only type numbers!");
            return null;
        }
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