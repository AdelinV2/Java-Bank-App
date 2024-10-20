
public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();
        GUI appGUI = new GUI(bank);

        appGUI.startInterface();
    }
}