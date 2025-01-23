package org.com.TradingPlatformApplication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.database.UserDao;
import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.Transaction;
import org.com.TradingPlatformApplication.model.User;

import java.util.List;
import java.util.Map;

public class UserService {


    private static final Logger log = LogManager.getLogger(UserService.class);
    private final UserDao userDao;

    public UserService(UserDao userDao) {
    this.userDao=userDao;
    }

    public void createUser(String userName, Double initWalletAmount) {
        User user = new User(userName, initWalletAmount);
        userDao.insertUser(user);
    }

    public void viewPortfolio(String userName) {
        User user = userDao.findUser(userName);
        displayPortfolioForUser(user);
    }

    private void displayPortfolioForUser(User user) {
        log.info("Wallet Balance: {}", user.getWalletBalance().toString());
        Map<String, User.Holdings> portfolio = user.getPortfolio();
        log.info("Stocks:- ");
        portfolio.forEach((k, v) -> {
            log.info("{}, {}", k, v.getHoldingQty());
        });

        log.info("Return rate = {}%", currentReturnRate(portfolio));

    }

    private Double currentReturnRate(Map<String, User.Holdings> portfolio) {
        Double buyNav = 0D;
        Double currNav = 0D;
        for (Map.Entry e : portfolio.entrySet()) {
            String key = (String) e.getKey();
            User.Holdings value = (User.Holdings) e.getValue();
            Stock stock = value.getStock();
            buyNav += (value.getHoldingQty() * value.getBuyingPrice());
            currNav += (value.getHoldingQty() * stock.getStockPrice());
        }
        if(buyNav.equals(0D))
            return 0D;
        return (Double) ((currNav - buyNav) * 100) / buyNav;
    }


}
