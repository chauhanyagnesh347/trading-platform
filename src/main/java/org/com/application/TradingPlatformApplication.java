package org.com.application;

import jdk.nashorn.internal.runtime.regexp.joni.constants.internal.Arguments;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.service.TradingPlatformService;
import org.com.service.UserService;

import java.util.Scanner;

import static org.com.database.StockDatabase.initStockData;
import static org.com.database.UserDatabase.initUserData;

@Slf4j
public class TradingPlatformApplication {

    private static final Logger log = LogManager.getLogger(TradingPlatformApplication.class);


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TradingPlatformService tradingService = new TradingPlatformService();
        UserService userService = new UserService();
        initUserData();
        initStockData();

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
                        tradingService.addStock(arguments[0].trim(),Double.valueOf(arguments[1].trim()));
                        break;
                    case "CREATE_USER":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.createUser(arguments[0].trim(), Double.valueOf(arguments[1].trim()));
                        break;
                    case "MOVE_TIME":
                        tradingService.moveTime();
                        break;
                    case "LIST_ALL_MARKET_STOCK_PRICES":
                        tradingService.listAllPrices();
                        break;
                    case "GET_STOCK_PRICE":
                        arguments = secondaryCommand[0].trim().split(",");
                        tradingService.getPriceFor(arguments[0].trim());
                        break;
                    case "BUY_STOCK":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.buyStock(arguments[0].trim(), arguments[1].trim(), Long.valueOf(arguments[2].trim()));
                        break;
                    case "SELL_STOCK":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.sellStock(arguments[0].trim(), arguments[1].trim(), Long.valueOf(arguments[2].trim()));
                        break;
                    case "VIEW_PORTFOLIO":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.viewPortfolio(arguments[0].trim());
                        break;
                    case "VIEW_TRANSACTION_HISTORY":
                        arguments = secondaryCommand[0].trim().split(",");
                        userService.viewTransactionHistory(arguments[0].trim());
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
