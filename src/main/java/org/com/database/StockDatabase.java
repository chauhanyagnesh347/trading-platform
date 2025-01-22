package org.com.database;

import org.com.model.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StockDatabase {

    private static Map<String, Stock> stocks = new HashMap<>();

    public static void initStockData() {
        stocks.clear();
    }

    //TODO: put existance checks
    public static void insertStock(Stock stock) {
        stocks.put(stock.getStockName(), stock);
    }

    public static void deleteStock(String stockName) {
        stocks.remove(stockName);
    }

    public static Stock findStock(String stockName) {
        if (stocks.containsKey(stockName))
            return stocks.get(stockName);
        throw new IllegalArgumentException("Stock " + stockName + " not found.");
    }

    public static List<Stock> getAllStocks() {
        return new ArrayList<>(stocks.values());
    }

    public static void updateStock(Stock stock) {
        insertStock(stock);
    }

}
