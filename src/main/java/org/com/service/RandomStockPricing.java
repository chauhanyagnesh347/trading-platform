package org.com.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.model.Stock;

import java.util.List;
import java.util.Random;

import static org.com.database.StockDatabase.getAllStocks;

@Slf4j
public class RandomStockPricing implements StockPricing {

    private static final Logger log = LogManager.getLogger(RandomStockPricing.class);
    private static Random random = new Random();

    @Override
    public void updateStockPrice() {
        List<Stock> currentStocks = getAllStocks();
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
