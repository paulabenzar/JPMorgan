package org.jpmorgan.message;

import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;

import java.util.Optional;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public interface MessageProcessor {

    void processMessage(String productCode, Price value, Optional<Integer> nrOfSales);

    void applyAdjustment(String productCode, Price value, OperationType operation);
}
