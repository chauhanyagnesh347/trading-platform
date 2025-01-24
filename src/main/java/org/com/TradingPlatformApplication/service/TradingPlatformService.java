package org.com.TradingPlatformApplication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.database.StockDao;
import org.com.TradingPlatformApplication.service.pricing.RandomStockPricing;
import org.com.TradingPlatformApplication.service.pricing.StockPricing;

public class TradingPlatformService {

    private static final Logger log = LogManager.getLogger(TradingPlatformService.class);
    private static Integer timeTick = 0;
    private static final Integer workingHour = 10;
    private final StockPricing pricingStretagy;

    public TradingPlatformService(StockDao stockDao) {
        timeTick = 0;
        pricingStretagy = new RandomStockPricing(stockDao);
    }

    public static void resetTime() {
        timeTick = 0;
    }

    public void moveTime() {
        if (timeTick < workingHour) {
            timeTick++;
            pricingStretagy.updateStockPrice();
            log.info("Current timestamp: t{}", timeTick);
        } else throw new RuntimeException("Market already closed");
    }

    public static Boolean isMarketOpen() {
        return timeTick < workingHour;
    }

    public static Integer getTimeTick() {
        return timeTick;
    }

    public static String getTimeStamp() {
        return "t" + timeTick.toString();
    }
}
