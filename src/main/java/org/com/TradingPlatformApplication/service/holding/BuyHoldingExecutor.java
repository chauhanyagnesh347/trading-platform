package org.com.TradingPlatformApplication.service.holding;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.enums.TransactionType;
import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.Transaction;
import org.com.TradingPlatformApplication.model.User;
import org.com.TradingPlatformApplication.service.TransactionService;

import static org.com.TradingPlatformApplication.enums.TransactionType.BUY;
import static org.com.TradingPlatformApplication.service.TradingPlatformService.getTimeStamp;

@Slf4j
public class BuyHoldingExecutor implements HoldingExecutor {


    private static final Logger log = LogManager.getLogger(BuyHoldingExecutor.class);
//    private TransactionService transactionService;

    public BuyHoldingExecutor() {
    }

    @Override
    public void execute(User user, Stock stock, Long quantity) {
        Double txnAmount = stock.getStockPrice()*quantity;
        if(txnAmount <= user.getWalletBalance()) {
            user.setWalletBalance(user.getWalletBalance() - txnAmount);
            user.getPortfolio().computeIfAbsent(stock.getStockName(), v -> new User.Holdings(stock,0L, stock.getStockPrice())).addQuantity(quantity);
//            transactionService.recordTransaction(new Transaction(BUY, user.getUserName(), stock.getStockName(), quantity, stock.getStockPrice(), getTimeStamp()));
        } else {
            log.info("Not possible");
            throw new RuntimeException("Insufficient wallet balance!");
        }
    }

}
