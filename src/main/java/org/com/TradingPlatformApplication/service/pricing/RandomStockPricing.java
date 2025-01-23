package org.com.TradingPlatformApplication.service.pricing;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.database.StockDao;
import org.com.TradingPlatformApplication.model.Stock;

import java.util.List;
import java.util.Random;

//import static org.com.TradingPlatformApplication.database.StockDao.getAllStocks;

@Slf4j
public class RandomStockPricing implements StockPricing {

    private static final Logger log = LogManager.getLogger(RandomStockPricing.class);
    private static final Random random = new Random();
    private StockDao stockDao;

    public RandomStockPricing(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public void updateStockPrice() {
        List<Stock> currentStocks = stockDao.getAllStocks();
        currentStocks.forEach(s -> {
            s.setStockPrice(newStockPrice(s.getStockPrice()));
        });
    }


    private Double newStockPrice(Double oldPrice) {
        Double factor = random.nextDouble();
        factor = factor - 0.5;
        log.info("factor: {}", factor);
        return oldPrice + (oldPrice * factor);
    }


}
