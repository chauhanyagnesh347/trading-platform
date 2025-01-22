package org.com.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.model.Stock;
import org.com.model.User;

import java.util.Map;

import static org.com.database.StockDatabase.findStock;
import static org.com.database.UserDatabase.findUser;
import static org.com.database.UserDatabase.insertUser;
import static org.com.service.TradingPlatformService.isMarketOpen;

public class UserService {


    private static final Logger log = LogManager.getLogger(UserService.class);

    public UserService() {
    }

    public void createUser(String userName, Double initWalletAmount) {
        User user = new User(userName, initWalletAmount);
        insertUser(user);
    }

    public void buyStock(String userName, String stockName, Long quantity) {
        if (isMarketOpen()) {
            Stock stock = findStock(stockName);
            User user = findUser(userName);
            Double txnAmount = stock.getStockPrice() * quantity;
            if (txnAmount <= user.getWalletBalance()) {
                user.updateHoldings(stockName, quantity, "BUY");
            } else {
                log.info("Not possible");
                throw new RuntimeException("Insufficient wallet balance!");
            }
        } else throw new RuntimeException("Buying now allowed. Market closed.");
    }


    public void sellStock(String userName, String stockName, Long quantity) {
        if(isMarketOpen()) {
            Stock stock = findStock(stockName);
            User user = findUser(userName);
            Long holdingQty = user.getQuantity(stockName);
            if (quantity <= holdingQty) {
                user.updateHoldings(stockName, quantity, "SELL");
            } else {
                log.info("Not possible");
                throw new RuntimeException("Insufficient holding quantity!");
            }
        } else throw new RuntimeException("Selling not allowed after. Market closed.");
    }

    public void viewPortfolio(String userName) {
        User user = findUser(userName);
        displayPortfolioForUser(user);
    }

    private void displayPortfolioForUser(User user) {
        log.info("Wallet Balance: {}", user.getWalletBalance().toString());
        Map<String, User.Holdings> portfolio = user.getPortfolio();
        log.info("Stocks:");
        portfolio.forEach((k, v) -> {
            log.info("{}, {}", k, v.getQty());
        });

        log.info("Return rate = {}%", currentReturnRate(portfolio));

    }

    private Double currentReturnRate(Map<String, User.Holdings> portfolio) {
        Double buyNav = 0D;
        Double currNav = 0D;
        for (Map.Entry e : portfolio.entrySet()) {
            String key = (String) e.getKey();
            User.Holdings value = (User.Holdings) e.getValue();
            Stock stock = findStock(key);
            buyNav += (value.getQty() * value.getPrice());
            currNav += (value.getQty() * stock.getStockPrice());
        }
        if(buyNav.equals(0D))
            return 0D;
        return (Double) ((currNav - buyNav) * 100) / buyNav;
    }


}
