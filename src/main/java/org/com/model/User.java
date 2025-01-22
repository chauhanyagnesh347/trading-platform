package org.com.model;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;
import static org.com.database.StockDatabase.findStock;
import static org.com.model.User.TransactionType.BUY;
import static org.com.model.User.TransactionType.SELL;
import static org.com.service.TradingPlatformService.getTimeStamp;
import static org.com.service.TradingPlatformService.getTimeTick;

public class User {

    public String userName;
    private Double walletBalance;
    private List<Transaction> transactionHistory;
//    Map<String, Map<String, Holdings>> portfolio;
    private Map<String, Holdings> portfolio;


    public User(String userName, Double walletBalance) {
        this.userName = userName;
        this.walletBalance = walletBalance;
        transactionHistory = new ArrayList<>();
        portfolio = new HashMap<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Map<String, Holdings> getPortfolio() {
        return new HashMap<>(this.portfolio);
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public void updateHoldings(String stockName, Long quantity, String activity) {
        TransactionType txnType = TransactionType.valueOf(activity.toUpperCase());
        Stock stock = findStock(stockName);
        Double txnAmount = stock.getStockPrice()*quantity;
        switch (txnType) {
            case BUY:
                this.walletBalance-=txnAmount;
                portfolio.computeIfAbsent(stockName, v -> new Holdings(0L, stock.getStockPrice())).addQuantity(quantity);
                transactionHistory.add(new Transaction(BUY, stockName, quantity, stock.getStockPrice(), getTimeStamp()));
                /*if(portfolio.containsKey(stockName)) {
                    Map<String, Holdings> currHolding = portfolio.get(stockName);
                    String timeStamp = "t"+getTimeTick().toString();
                    currHolding.computeIfAbsent(timeStamp, h -> new Holdings(0L, stock.getStockPrice())).addQuantity(quantity);
                }*/
                break;
            case SELL:
                this.walletBalance+=txnAmount;
                portfolio.get(stockName).addQuantity(-1L*quantity);
                transactionHistory.add(new Transaction(SELL, stockName, quantity, stock.getStockPrice(), getTimeStamp()));
                break;
            default:
                break;
        }
    }

    /*public Long getQuantityFor(String stockName) {
        Map<String, Holdings> currHolding = portfolio.get(stockName);
        AtomicLong totalQty = new AtomicLong(0L);
        currHolding.values().forEach(v -> {
            totalQty.set(totalQty.addAndGet(v.getQty()));
        });
        return totalQty.get();
    }*/

    public Long getQuantity(String stockName) {
        if(portfolio.containsKey(stockName)) {
            return portfolio.get(stockName).getQty();
        } else
            throw new IllegalArgumentException("User does not have holdings for Stock: " + stockName);
    }


    public class Transaction {

        public Transaction(TransactionType txnType, String stockName, Long qty, Double price, String time) {
            this.txnType = txnType;
            this.stockName = stockName;
            this.qty = qty;
            this.price = price;
            this.time = time;
        }

        TransactionType txnType;
        String stockName;
        Long qty;
        Double price;
        String time;

        @Override
        public String toString() {
            return format("[ %s, %s, %s, %s, %s ]", txnType, stockName, qty.toString(), price.toString(), time);
        }
    }

    public enum TransactionType {
        BUY, SELL;
    }

    public static class Holdings {

        public Holdings(Long qty, Double price) {
            this.qty = qty;
            this.price = price;
        }

        Long qty;
        Double price;

        public void addQuantity(Long quantity) {
            this.qty+=quantity;
        }

        public Long getQty() {
            return qty;
        }

        public Double getPrice() {
            return price;
        }
    }
}
