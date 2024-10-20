import java.util.*;

public class BankService {
    private Map<String, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private FileService fileService = new FileService();


    public BankService(){
        accounts = fileService.loadAccounts();
        transactions = fileService.loadTransactions();
    }


    public void createAccount(String username, Enums.CurrencyType currency, String IBAN, String pin){
        Account newAccount = new Account(username, currency, IBAN, pin);
        accounts.put(IBAN, newAccount);
        fileService.updateAccount(newAccount);

    }


    public void makeTransaction(String transactionID, Account accountFrom, Account accountTo,
                                double amount, Enums.TransactionType type)
    {
        Transaction newTransaction = new Transaction(accountFrom, accountTo, amount, type);
        transactions.add(newTransaction); // adăugat tranzacția în listă

        fileService.updateTransactions(transactions);
    }
}
