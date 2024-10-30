import java.util.*;

public class BankService {
    private Map<String, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private FileService fileService = new FileService();


    public BankService(){
        accounts = fileService.loadAccounts();
        transactions = fileService.loadTransactions();
    }


    public Transaction findTransaction(String id){
        for (Transaction transaction : transactions) {
            String transId = transaction.getTransactionID();
            if (transId != null && transId.equals(id)) {
                return transaction;
            }
        }
        return null;
    }
    

    public void saveAccountsAfterTransactions(){
        fileService.saveAccounts(accounts);
    }


    public List<Transaction> findTransactionsByIBAN(String iban){
        List<Transaction> transactionList = new ArrayList<>();

        for(Transaction transaction : transactions){
            String transIban = transaction.getAccountFrom().getIBAN();
            if(transIban != null && transIban.equals(iban))
                transactionList.add(transaction);
        }

        return transactionList;
    }


    public Account findAccount(String iban){
        return accounts.get(iban);
    }


    public void createAccount(String username, Enums.CurrencyType currency, String IBAN, String pin){
        Account newAccount = new Account(username, currency, IBAN, pin, 0.0);
        accounts.put(IBAN, newAccount);
        fileService.updateAccount(newAccount);
    }


    public String generateIBAN(){
        String countryCode = "RO";
        String bankCode = "1234";
        String accountNumber = String.format("%016d", new Random().nextLong((long) Math.pow(10, 16)));
        return countryCode + bankCode + accountNumber;
    }


    public String generateTransactionID(Enums.TransactionType type){
        return type.name() + String.format("%012d", new Random().nextLong((long) Math.pow(10, 12)));
    }


    public void makeTransaction(String transactionID, Account accountFrom, Account accountTo,
                                double amount, Enums.TransactionType type)
    {
        Transaction newTransaction = new Transaction(transactionID, accountFrom, accountTo, amount, type);
        transactions.add(newTransaction);

        fileService.updateTransactions(newTransaction);
    }
}
