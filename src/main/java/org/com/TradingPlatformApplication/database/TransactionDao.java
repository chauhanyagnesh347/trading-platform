package org.com.TradingPlatformApplication.database;

import org.com.TradingPlatformApplication.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDao {

    private final List<Transaction> transactionData = new ArrayList<>();
    private final Map<String, List<Transaction>> transactionIndex = new HashMap<>();

    public void initTransactionData() {
        transactionData.clear();
        transactionIndex.clear();
    }

    public void insertTransaction(Transaction txn) {
        transactionData.add(txn);
        transactionIndex.computeIfAbsent(txn.getUserName(),v -> new ArrayList<>()).add(txn);
    }

    public List<Transaction> findTransactions(String userName) {
        return transactionIndex.get(userName);
    }
}
