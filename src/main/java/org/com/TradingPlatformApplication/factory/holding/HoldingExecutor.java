package org.com.TradingPlatformApplication.factory.holding;

import org.com.TradingPlatformApplication.model.Stock;
import org.com.TradingPlatformApplication.model.User;

public interface HoldingExecutor {


    void execute(User user, Stock stock, Long quantity);

}
