package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.*;
import core.basesyntax.strategy.impl.OperationStrategyImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private final String inputFilePath = "src/main/resources/reportToRead.csv";
    private final String outputFilePath = "src/main/resources/finalReport.csv";

    public static void main(String[] arg) {

        FileReader fileReader = new FileReaderImpl();
        final List<String> inputReport = fileReader.read(inputFilePath);

        DataConverter dataConverter = new DataConverterImpl();
        final List<FruitTransaction> transactions = dataConverter
                .convertToTransaction(inputReport);

        final Map<FruitTransaction.Operation, OperationHandler> operationHandlers =
                new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        final OperationStrategy operationStrategy =
                new OperationStrategyImpl(operationHandlers);

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        final String resultingReport = reportGenerator.getReport();

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(resultingReport, outputFilePath);

        System.out.println("Fruit shop report generated successfully!");
        System.out.println("Check '" + OUTPUT_FILE_PATH + "' for the output.");
    }
}
