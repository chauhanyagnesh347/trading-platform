package org.com.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.model.Stock;

import java.util.List;

import static org.com.database.StockDatabase.*;

public class TradingPlatformService {

    private static final Logger log = LogManager.getLogger(TradingPlatformService.class);
    private static Integer timeTick = 0;
    private static final Integer workingHour = 10;
    private StockPricing pricingStretagy;

    public TradingPlatformService() {
        timeTick=0;
        pricingStretagy = new RandomStockPricing();
    }

    public static void resetTime() {
        timeTick=0;
    }

    public void addStock(String name, Double price) {
        Stock stock = new Stock(name, price);
        insertStock(stock);
    }

    public void moveTime() {
        if(timeTick<workingHour) {
            timeTick++;
            pricingStretagy.updateStockPrice();
            log.info("Current timestamp: t{}", timeTick);
        }
        else throw new RuntimeException("Market already closed");
    }

    public void listAllPrices() {
        List<Stock> allStocks = getAllStocks();
        allStocks.forEach(stk -> {
            log.info("{}: {}", stk.getStockName(), stk.getStockPrice());
        });
    }

    public void getPriceFor(String stockName) {
        Stock stk = findStock(stockName);
        log.info("{}", stk.getStockPrice());
    }

    public static Boolean isMarketOpen() {
        return timeTick<workingHour;
    }

    public static Integer getTimeTick() {
        return timeTick;
    }
}
