public class Account {
    public enum currencyType {EUR, USD, GBP}

    private String username;
    private currencyType currency;
    private double balance;
    private String IBAN;
    private String pin;


    public Account(String username, currencyType currency, String IBAN, String pin){
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


    public currencyType getCurrency() {
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
