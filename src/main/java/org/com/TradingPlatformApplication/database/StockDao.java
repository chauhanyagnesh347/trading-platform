package org.com.TradingPlatformApplication.database;

import org.com.TradingPlatformApplication.model.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockDao {

    private final Map<String, Stock> stocks = new HashMap<>();

    public void initStockData() {
        stocks.clear();
    }

    //TODO: put existance checks
    public void insertStock(Stock stock) {
        stocks.put(stock.getStockName(), stock);
    }

    public  void deleteStock(String stockName) {
        stocks.remove(stockName);
    }

    public  Stock findStock(String stockName) {
        if (stocks.containsKey(stockName))
            return stocks.get(stockName);
        throw new IllegalArgumentException("Stock " + stockName + " not found.");
    }

    public  List<Stock> getAllStocks() {
        return new ArrayList<>(stocks.values());
    }

    public  void updateStock(Stock stock) {
        insertStock(stock);
    }

}
