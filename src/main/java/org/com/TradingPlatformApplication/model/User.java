package org.com.TradingPlatformApplication.model;

import org.com.TradingPlatformApplication.enums.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.com.TradingPlatformApplication.enums.TransactionType.BUY;
import static org.com.TradingPlatformApplication.enums.TransactionType.SELL;
import static org.com.TradingPlatformApplication.service.TradingPlatformService.getTimeStamp;

public class User {

    private String userName;
    private Double walletBalance;
    //    Map<String, Map<String, Holdings>> portfolio;
    private Map<String, Holdings> portfolio;


    public User(String userName, Double walletBalance) {
        this.userName = userName;
        this.walletBalance = walletBalance;
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
        return this.portfolio;
    }


    public Long getQuantity(Stock stock) {
        if (portfolio.containsKey(stock.getStockName())) {
            return portfolio.get(stock.getStockName()).getHoldingQty();
        } else
            throw new IllegalArgumentException("User does not have holdings for Stock: " + stock.getStockName());
    }


    public static class Holdings {

        public Holdings(Stock stock, Long holdingQty, Double buyingPrice) {
            this.stock = stock;
            this.holdingQty = holdingQty;
            this.buyingPrice = buyingPrice;
        }

        Stock stock;
        Long holdingQty;
        Double buyingPrice;

        public void addQuantity(Long quantity) {
            this.holdingQty += quantity;
        }

        public Stock getStock() {
            return stock;
        }

        public Long getHoldingQty() {
            return holdingQty;
        }

        public Double getBuyingPrice() {
            return buyingPrice;
        }
    }
}
