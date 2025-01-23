package org.com.TradingPlatformApplication.service.holding;

import org.com.TradingPlatformApplication.database.TransactionDao;
import org.com.TradingPlatformApplication.enums.TransactionType;
import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.User;
import org.com.TradingPlatformApplication.service.TransactionService;

public interface HoldingExecutor {


    void execute(User user, Stock stock, Long quantity);

}
