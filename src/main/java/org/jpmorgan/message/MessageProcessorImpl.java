package org.jpmorgan.message;

import org.jpmorgan.message.logging.strategies.ReportingStrategy;
import org.jpmorgan.message.logging.strategies.ReportingStrategyImpl;
import org.jpmorgan.message.operation.Operation;
import org.jpmorgan.message.operation.OperationFactory;
import org.jpmorgan.message.records.MessageCounter;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;

import java.util.Optional;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class MessageProcessorImpl implements MessageProcessor {

    //@Inject
    private ReportingStrategy reportingStrategy;


    MessageProcessorImpl() {
        reportingStrategy = new ReportingStrategyImpl();
    }

    @Override
    public void processMessage(String productCode, Price price, Optional<Integer> nrOfSales) {
        consumeMessage(new SaleMessage(productCode, nrOfSales.orElse(1), price),
                OperationFactory.RECORD.getOperation());
    }

    @Override
    public void applyAdjustment(String productCode, Price price, OperationType operation) {

        consumeMessage(new SaleMessage(productCode, 1, price),
                OperationFactory.valueOf(operation.name()).getOperation());

    }

    private void consumeMessage(SaleMessage message, Operation operation) {
        operation.performOperation(message);
        MessageCounter.INSTANCE.increment();
        reportingStrategy.applyReportingStrategy();
    }
}
