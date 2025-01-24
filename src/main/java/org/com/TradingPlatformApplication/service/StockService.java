package org.com.TradingPlatformApplication.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.database.StockDao;
import org.com.TradingPlatformApplication.model.Stock;

import java.util.List;

@Slf4j
public class StockService {

    private static final Logger log = LogManager.getLogger(StockService.class);
    private final StockDao stockDao;

    public StockService(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    public void addStock(String name, Double price) {
        Stock stock = new Stock(name, price);
        stockDao.insertStock(stock);
    }

    public void listAllPrices() {
        List<Stock> allStocks = stockDao.getAllStocks();
        allStocks.forEach(stk -> {
            log.info("{}: {}", stk.getStockName(), stk.getStockPrice());
        });
    }

    public void getPriceFor(String stockName) {
        Stock stock = stockDao.findStock(stockName);
        log.info("{}", stock.getStockPrice());
    }

}
