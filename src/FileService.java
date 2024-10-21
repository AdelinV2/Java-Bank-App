import java.util.*;
import java.io.*;

public class FileService {
    final String accPath = "data/accounts.txt";
    final String trsPath = "data/transactions.txt";


    public Map<String, Account> loadAccounts(){
        Map<String, Account> accountMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(accPath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String username = parts[0];
                Enums.CurrencyType currency = Enums.CurrencyType.valueOf(parts[1]);
                String IBAN = parts[2];
                String pin = parts[3];

                Account account = new Account(username, currency, IBAN, pin);
                accountMap.put(IBAN, account);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return accountMap;
    }


    public void saveAccounts(Map<String, Account> accounts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(accPath))) {
            for (Account account : accounts.values()) {
                bw.write(account.getUsername() + "," + account.getCurrency() + "," +
                        account.getIBAN() + "," + account.getPin());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateAccount(Account account) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(accPath, true))) {
            bw.write(account.getUsername() + "," + account.getCurrency() + "," +
                    account.getIBAN() + "," + account.getPin());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(trsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String transactionID = parts[0];
                Account accountFrom = new Account(null, Enums.CurrencyType.valueOf(parts[2]), parts[1], null);
                Account accountTo;
                if(!parts[3].equals("null")) {
                    accountTo = new Account(null, Enums.CurrencyType.valueOf(parts[4]), parts[3], null);
                }
                else{
                    accountTo = new Account(null,null,null,null);
                }
                double amount = Double.parseDouble(parts[5]);
                Enums.TransactionType type = Enums.TransactionType.valueOf(parts[6]);
                boolean completed = Boolean.parseBoolean(parts[7]);
                Transaction transaction = new Transaction(accountFrom, accountTo, amount, type);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public void saveTransactions(List<Transaction> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(trsPath))) {
            for (Transaction transaction : transactions) {
                if(transaction.getAccountTo() != null) {
                    bw.write(transaction.getTransactionID() + "," +
                            transaction.getAccountFrom().getIBAN() + "," +
                            transaction.getAccountFrom().getCurrency() + "," +
                            transaction.getAccountTo().getIBAN() + "," +
                            transaction.getAccountTo().getCurrency() + "," +
                            transaction.getAmount() + "," +
                            transaction.getType() + "," +
                            transaction.isCompletedTransaction() + "," +
                            transaction.getDate());
                }
                else{
                    bw.write(transaction.getTransactionID() + "," +
                            transaction.getAccountFrom().getIBAN() + "," +
                            transaction.getAccountFrom().getCurrency() + "," +
                            "null,null," +
                            transaction.getAmount() + "," +
                            transaction.getType() + "," +
                            transaction.isCompletedTransaction()+ "," +
                            transaction.getDate());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateTransactions(List<Transaction> newTransactions) {
        saveTransactions(newTransactions);
    }
}
