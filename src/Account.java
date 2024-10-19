public class Account {
    public enum currencyType {EUR, USD, GBP}

    private String username;
    private currencyType currency;
    private double balance;
    private String accountNumber;


    public Account(String username, currencyType currency, String accountNumber){
        this.username = username;
        this.currency = currency;
        this.balance = 0.0;
        this.accountNumber = accountNumber;
    }


    public void updateBalance(double amount){
        this.balance += amount;
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


    public String getAccountNumber() {
        return accountNumber;
    }
}
