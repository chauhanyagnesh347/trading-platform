package org.com.TradingPlatformApplication.factory.holding;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.User;

@Slf4j
public class BuyHoldingExecutor implements HoldingExecutor {


    private static final Logger log = LogManager.getLogger(BuyHoldingExecutor.class);
//    private TransactionService transactionService;

    public BuyHoldingExecutor() {
    }

    @Override
    public void execute(User user, Stock stock, Long quantity) {
        Double txnAmount = stock.getStockPrice() * quantity;
        if (txnAmount <= user.getWalletBalance()) {
            user.setWalletBalance(user.getWalletBalance() - txnAmount);
            user.getPortfolio().computeIfAbsent(stock.getStockName(), v -> new User.Holdings(stock, 0L, stock.getStockPrice())).addQuantity(quantity);
        } else {
            log.info("Not possible");
            throw new RuntimeException("Insufficient wallet balance!");
        }
    }

}
