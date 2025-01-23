package org.com.TradingPlatformApplication.model;

import org.com.TradingPlatformApplication.enums.TransactionType;

import static java.lang.String.format;

public class Transaction {
    public Transaction(TransactionType txnType, String userName, String stockName, Long qty, Double price, String time) {
        this.txnType = txnType;
        this.userName = userName;
        this.stockName = stockName;
        this.qty = qty;
        this.price = price;
        this.time = time;
    }

    private TransactionType txnType;
    private String userName;
    private String stockName;
    private Long qty;
    private Double price;
    private String time;

    @Override
    public String toString() {
        return format("[ %s, %s, %s, %s, %s, %s ]", txnType, userName, stockName, qty.toString(), price.toString(), time);
    }

    public TransactionType getTxnType() {
        return txnType;
    }

    public String getUserName() {
        return userName;
    }

    public String getStockName() {
        return stockName;
    }

    public Long getQty() {
        return qty;
    }

    public Double getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }
}
