package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String CSV_DELIMITER = ",";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> rawData) {
        List<FruitTransaction> transactions = new ArrayList<>();
        if (rawData == null || rawData.isEmpty()) {
            return transactions;
        }

        for (String line : rawData) {
            if (line == null || line.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid line detected: line is null or empty");
            }
            if (line.startsWith("type,")) {
                continue; 
            }
            String[] parts = line.split(CSV_DELIMITER);
            if (parts.length != 3) {
                throw new RuntimeException("Invalid data format in line: '" + line
                        + "'. Expected 'type,fruit,quantity'.");
            }

            try {
                String operationCode = parts[0];
                String fruitName = parts[1];
                int quantity = Integer.parseInt(parts[2]);

                if (operationCode.startsWith(" ") || operationCode.endsWith(" ")) {
                    throw new IllegalArgumentException("operationCode contains leading or trailing spaces");
                }

                FruitTransaction.Operation operation = FruitTransaction.Operation
                        .getByCode(operationCode);
                transactions.add(new FruitTransaction(operation, fruitName, quantity));

            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid quantity format in line: '" + line + "'", e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid operation code in line: '" + line + "'", e);
            }
        }
        return transactions;
    }
}
