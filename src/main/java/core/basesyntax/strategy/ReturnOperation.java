package core.basesyntax.strategy;

import core.basesyntax.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public int apply(int currentQuantity, int transactionQuantity) {
        return currentQuantity + transactionQuantity;
    }
}
