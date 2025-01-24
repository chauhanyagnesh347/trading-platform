package org.com.TradingPlatformApplication.factory.holding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.User;

public class SellHoldingExecutor implements HoldingExecutor {

    private static final Logger log = LogManager.getLogger(SellHoldingExecutor.class);

    public SellHoldingExecutor() {
    }

    @Override
    public void execute(User user, Stock stock, Long quantity) {
        Double txnAmount = stock.getStockPrice() * quantity;
        Long holdingQty = user.getQuantity(stock);
        if (quantity <= holdingQty) {
            user.setWalletBalance(user.getWalletBalance() + txnAmount);
            user.getPortfolio().get(stock.getStockName()).addQuantity(-1L * quantity);
        } else {
            log.info("Not possible");
            throw new RuntimeException("Insufficient holding quantity!");
        }
    }

}
