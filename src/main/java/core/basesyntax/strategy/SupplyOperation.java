package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public int apply(int currentQuantity, int transactionQuantity) {
        return currentQuantity + transactionQuantity;
    }
}
