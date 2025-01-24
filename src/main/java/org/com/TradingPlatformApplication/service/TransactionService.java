package org.com.TradingPlatformApplication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.database.TransactionDao;
import org.com.TradingPlatformApplication.factory.holding.BuyHoldingExecutor;
import org.com.TradingPlatformApplication.factory.holding.HoldingExecutorFactory;
import org.com.TradingPlatformApplication.factory.holding.SellHoldingExecutor;
import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.Transaction;
import org.com.TradingPlatformApplication.model.User;

import java.util.List;

import static org.com.TradingPlatformApplication.enums.TransactionType.BUY;
import static org.com.TradingPlatformApplication.enums.TransactionType.SELL;
import static org.com.TradingPlatformApplication.service.TradingPlatformService.getTimeStamp;
import static org.com.TradingPlatformApplication.service.TradingPlatformService.isMarketOpen;

public class TransactionService {

    private static final Logger log = LogManager.getLogger(TransactionService.class);
    private final TransactionDao transactionDao;

    private final HoldingExecutorFactory executorFactory = new HoldingExecutorFactory();

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
        executorFactory.registerHoldingExecutor(BUY, new BuyHoldingExecutor());
        executorFactory.registerHoldingExecutor(SELL, new SellHoldingExecutor());
    }

    public void recordTransaction(Transaction transaction) {
        transactionDao.insertTransaction(transaction);
    }

    public void buyStock(User user, Stock stock, Long quantity) {
        if (isMarketOpen()) {
            executorFactory.getHoldingExecutorFor(BUY).execute(user, stock, quantity);
            recordTransaction((new Transaction(BUY, user.getUserName(), stock.getStockName(), quantity, stock.getStockPrice(), getTimeStamp())));
        } else throw new RuntimeException("Buying now allowed. Market closed.");
    }

    public void sellStock(User user, Stock stock, Long quantity) {
        if (isMarketOpen()) {
            executorFactory.getHoldingExecutorFor(SELL).execute(user, stock, quantity);
            recordTransaction((new Transaction(SELL, user.getUserName(), stock.getStockName(), quantity, stock.getStockPrice(), getTimeStamp())));
        } else throw new RuntimeException("Selling not allowed after. Market closed.");
    }

    public void viewTransactionHistory(User user) {
        log.info("Transaction history: {}", user.getUserName());
        List<Transaction> txnHistory = transactionDao.findTransactions(user.getUserName());
        for (Transaction txn : txnHistory) {
            log.info("{}", txn.toString());
        }
    }
}
