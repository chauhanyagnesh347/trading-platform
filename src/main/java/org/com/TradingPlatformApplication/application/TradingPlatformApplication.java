package org.com.TradingPlatformApplication.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.TradingPlatformApplication.database.StockDao;
import org.com.TradingPlatformApplication.database.TransactionDao;
import org.com.TradingPlatformApplication.database.UserDao;
import org.com.TradingPlatformApplication.service.StockService;
import org.com.TradingPlatformApplication.service.TradingPlatformService;
import org.com.TradingPlatformApplication.service.TransactionService;
import org.com.TradingPlatformApplication.service.UserService;

import java.util.Scanner;


@Slf4j
public class TradingPlatformApplication {

    private static final Logger log = LogManager.getLogger(TradingPlatformApplication.class);


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StockDao stockDao = new StockDao();
        stockDao.initStockData();
        UserDao userDao = new UserDao();
        userDao.initUserData();
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.initTransactionData();
        TradingPlatformService tradingService = new TradingPlatformService(stockDao);
        UserService userService = new UserService(userDao);
        TransactionService transactionService = new TransactionService(transactionDao);
        StockService stockService = new StockService(stockDao);

        while(true) {
            String commandLine = scanner.nextLine();
            String[] parsedCommand = commandLine.trim().split("\\(");
            String command = parsedCommand[0].trim();
            try{
                if(command.equals(""))
                    throw new IllegalArgumentException("Emtpy command.");
                String[] secondaryCommand = parsedCommand[1].trim().split("\\)");
                String[] arguments;
                switch(command) {
                    case "ADD_STOCKS":
                        arguments = secondaryCommand[0].split(",");
                        stockService.addStock(arguments[0].trim(),Double.valueOf(arguments[1].trim()));
                        break;
                    case "CREATE_USER":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.createUser(arguments[0].trim(), Double.valueOf(arguments[1].trim()));
                        break;
                    case "MOVE_TIME":
                        tradingService.moveTime();
                        break;
                    case "LIST_ALL_MARKET_STOCK_PRICES":
                        stockService.listAllPrices();
                        break;
                    case "GET_STOCK_PRICE":
                        arguments = secondaryCommand[0].trim().split(",");
                        stockService.getPriceFor(arguments[0].trim());
                        break;
                    case "BUY_STOCK":
                        arguments = secondaryCommand[0].trim().split(",");
                        transactionService.buyStock(userDao.findUser(arguments[0].trim()), stockDao.findStock(arguments[1].trim()), Long.valueOf(arguments[2].trim()));
                        break;
                    case "SELL_STOCK":
                        arguments = secondaryCommand[0].trim().split(",");
                        transactionService.sellStock(userDao.findUser(arguments[0].trim()), stockDao.findStock(arguments[1].trim()), Long.valueOf(arguments[2].trim()));
                        break;
                    case "VIEW_PORTFOLIO":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.viewPortfolio(arguments[0].trim());
                        break;
                    case "VIEW_TRANSACTION_HISTORY":
                        arguments = secondaryCommand[0].trim().split(",");
                        transactionService.viewTransactionHistory(userDao.findUser(arguments[0].trim()));
                        break;
                    default:
                        throw new IllegalArgumentException("No such command: " + command);
                }
            } catch (Exception e) {
                log.error("Exception occured. {} : {}", e.getClass(), e.getMessage());
            }
        }
    }
}
