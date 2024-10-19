public class Account {
    private String username;
    private Enums.CurrencyType currency;
    private double balance;
    private String IBAN;
    private String pin;


    public Account(String username, Enums.CurrencyType currency, String IBAN, String pin){
        this.username = username;
        this.currency = currency;
        this.balance = 0.0;
        this.IBAN = IBAN;
        this.pin = pin;
    }


    public void updateBalance(double amount){
        this.balance += amount;
    }


    public void setPin(String pin) {
        this.pin = pin;
    }


    public String getUsername() {
        return username;
    }


    public Enums.CurrencyType getCurrency() {
        return currency;
    }


    public double getBalance() {
        return balance;
    }


    public String getIBAN() {
        return IBAN;
    }


    public String getPin() {
        return pin;
    }
}
