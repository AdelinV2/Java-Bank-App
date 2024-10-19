import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private Account accountFrom;
    private Account accountTo;
    private double amount;
    private LocalDate date;
    private Enums.TransactionType type;


    public Transaction(String transactionID, Account accountFrom, Account accountTo,
                       double amount, Enums.TransactionType type)
    {
        this.transactionID = transactionID;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;

        switch (type){
            case WITHDRAW:
                withdraw();
                break;

            case DEPOSIT:
                deposit();
                break;

            case TRANSFER:
                break;

        }
    }


    private boolean haveEnoughMoney(){
        return this.amount <= accountFrom.getBalance();
    }


    private void withdraw(){
        // TODO complete withdraw text for GUI
        if (haveEnoughMoney())
            accountFrom.updateBalance(-amount);

        else{

        }

        // TODO save transaction for withdraw
    }


    private void deposit(){
        // TODO complete deposit text for GUI
        accountFrom.updateBalance(amount);

        // TODO save transaction for deposit
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
        }

        else{
            // TODO complete else if there are not enough money to transfer
        }

        //TODO save transaction for transfer
    }
}
