import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private Account accountFrom;
    private Account accountTo;
    private double amount;
    private LocalDate date;
    private Enums.TransactionType type;
    private boolean completedTransaction;


    public Transaction(Account accountFrom, Account accountTo,
                       double amount, Enums.TransactionType type)
    {
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

            case TRANSFER:
                transfer();
                break;
        }

        // TODO save transaction

    }


    private boolean haveEnoughMoney(){
        return this.amount <= accountFrom.getBalance();
    }


    private void withdraw(){
        // TODO complete withdraw text for GUI
        if (haveEnoughMoney()) {
            accountFrom.updateBalance(-amount);
            completedTransaction = true;
        }

        else{
            completedTransaction = false;
        }
    }


    private void deposit(){
        // TODO complete deposit text for GUI
        accountFrom.updateBalance(amount);
        completedTransaction = true;

    }


    private void transfer(){
        // TODO complete transfer text for GUI
        if (haveEnoughMoney()){
            accountFrom.updateBalance(-amount);

            if(accountTo.getCurrency() == accountFrom.getCurrency())
                accountTo.updateBalance(amount);

            else{
                double amountInEur = amount * accountFrom.getCurrency().getConversionRate();
                double convertedAmount = amountInEur / accountTo.getCurrency().getConversionRate();
                accountTo.updateBalance(convertedAmount);

            }

            completedTransaction = true;
        }

        else{
            completedTransaction = false;
            // TODO complete else if there are not enough money to transfer
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
