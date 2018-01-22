package org.jpmorgan.message.operation;


import org.jpmorgan.message.records.message.AdjustmentMessage;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;

import java.math.BigDecimal;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class SubstractOperation extends AdjustmentOperation {


    @Override
    protected void applyAdjustment(SaleMessage recordedMessage, SaleMessage adjustmentMessage) {
        Price originalValue = recordedMessage.getTotal();
        BigDecimal nrOfSales = new BigDecimal(String.valueOf(recordedMessage.getNrOSales()));
        BigDecimal totalAdjustmentValue = adjustmentMessage.getTotal().getValue().multiply(nrOfSales);
        Price finalValue = Price.buildGBPPrice(originalValue.getValue().subtract(totalAdjustmentValue));
        recordedMessage.addAdjustment(new AdjustmentMessage(adjustmentMessage.getTotal(),
                originalValue, finalValue, OperationType.SUBSTRACT));
    }
}
