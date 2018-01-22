package org.jpmorgan.message.operation;

import org.jpmorgan.message.records.message.AdjustmentMessage;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;

import java.math.BigDecimal;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class MultiplyOperation extends AdjustmentOperation {


    @Override
    protected void applyAdjustment(SaleMessage recordedMessage, SaleMessage adjustmentMessage) {
        Price originalPrice = recordedMessage.getTotal();
        BigDecimal totalAdjustmentValue = adjustmentMessage.getTotal().getValue();
        Price finalPrice = Price.buildGBPPrice(originalPrice.getValue().multiply(totalAdjustmentValue));
        recordedMessage.addAdjustment(new AdjustmentMessage(adjustmentMessage.getTotal(),
                originalPrice, finalPrice, OperationType.MULTIPLY));
    }
}
