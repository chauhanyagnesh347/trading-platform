package org.com.TradingPlatformApplication.service.holding;

import org.com.TradingPlatformApplication.enums.TransactionType;

import java.util.HashMap;
import java.util.Map;

public class HoldingExecutorFactory {

    private final Map<TransactionType, HoldingExecutor> transactionTypeToHoldingExecutorMap = new HashMap<>();

    public void registerHoldingExecutor(TransactionType txnType, HoldingExecutor holdingExecutor) {
        transactionTypeToHoldingExecutorMap.put(txnType, holdingExecutor);
    }

    public HoldingExecutor getHoldingExecutorFor(TransactionType txnType) {
        return transactionTypeToHoldingExecutorMap.get(txnType);
    }

}
