package ro.fasttrackit.course20.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course20.homework.model.Transaction;
import ro.fasttrackit.course20.homework.model.TransactionType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static long ID_COUNTER = 1L;
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> filterProducts(String product, String type) {
        if (product == null && type == null) {
            return transactions;
        } else if (product != null && type == null) {
            return filterByProduct(product);
        } else if (product == null && type != null) {
            return filterByType(type);
        } else {
            return filterByProductAndType(product, type);
        }
    }

    private List<Transaction> filterByProduct(String product) {
        return transactions.stream()
                .filter(transaction -> transaction.product().equalsIgnoreCase(product))
                .toList();
    }

    private List<Transaction> filterByType(String type) {
        return transactions.stream()
                .filter(transaction -> transaction.type() == TransactionType.valueOf(type.toUpperCase()))
                .toList();
    }

    private List<Transaction> filterByProductAndType(String product, String type) {
        return Stream.of(filterByProduct(product), filterByType(type))
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    public Transaction getTransactionById(long id) {
        return transactions.stream()
                .filter(filter -> filter.id() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No transaction with id %s found".formatted(id)));
    }

    public Transaction addTransaction(Transaction trans) {
        Transaction newTransaction = trans.withId(ID_COUNTER++);
        transactions.add(newTransaction);
        return newTransaction;
    }

    public Transaction replaceTransaction(int id, Transaction toAdd) {
        Transaction newTransaction = toAdd.withId(id);
        transactions.replaceAll(transaction -> transaction.id() == newTransaction.id() ? newTransaction : transaction);
        return newTransaction;
    }

    public Transaction deleteTransaction(int id) {
        Transaction transactionToDelete = getTransactionById(id);
        transactions.remove(transactionToDelete);
        return transactionToDelete;
    }
}
