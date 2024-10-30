import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private Account accountFrom;
    private Account accountTo;
    private double amount;
    private LocalDate date;
    private Enums.TransactionType type;
    private boolean completedTransaction;


    public Transaction(String transactionID, Account accountFrom, Account accountTo,
                       double amount, Enums.TransactionType type)
    {
        this.transactionID = transactionID;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.type = type;
        this.date = LocalDate.now();

        switch (type){
            case WITHDRAW:
                withdraw();
                break;

            case DEPOSIT:
                deposit();
                break;

            case TRANSFER_TO:
                transfer();
                break;
        }
    }


    private boolean haveEnoughMoney(){
        return this.amount <= accountFrom.getBalance();
    }


    private void withdraw(){
        if (haveEnoughMoney()) {
            accountFrom.updateBalance(-amount);
            completedTransaction = true;
        }

        else{
            completedTransaction = false;
        }
    }


    private void deposit(){
        accountFrom.updateBalance(amount);
        completedTransaction = true;
    }


    private void transfer(){
        if (haveEnoughMoney()){
            accountFrom.updateBalance(-amount);

            if(accountTo.getCurrency() == accountFrom.getCurrency())
                accountTo.updateBalance(amount);

            else{
                double amountInEur = amount * accountFrom.getCurrency().getConversionRate();
                double convertedAmount = amountInEur / accountTo.getCurrency().getConversionRate();
                convertedAmount = Math.round(convertedAmount * 100.0) / 100.0;
                accountTo.updateBalance(convertedAmount);
            }

            completedTransaction = true;
        }

        else{
            completedTransaction = false;
        }
    }


    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionID() {
        return transactionID;
    }


    public Account getAccountFrom() {
        return accountFrom;
    }


    public Account getAccountTo() {
        return accountTo;
    }


    public double getAmount() {
        return amount;
    }


    public boolean isCompletedTransaction() {
        return completedTransaction;
    }


    public LocalDate getDate() {
        return date;
    }


    public Enums.TransactionType getType() {
        return type;
    }
}
