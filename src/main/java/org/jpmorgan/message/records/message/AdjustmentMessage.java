package org.jpmorgan.message.records.message;

import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class AdjustmentMessage {

    private Price originalValue;
    private Price finalValue;
    private Price adjustmentValue;
    private OperationType operationType;

    public AdjustmentMessage(Price adjustmentValue, Price originalValue,
                             Price finalValue, OperationType operationType) {
        this.originalValue = originalValue;
        this.finalValue = finalValue;
        this.adjustmentValue = adjustmentValue;
        this.operationType = operationType;
    }


    public Price getOriginalValue() {
        return originalValue;
    }

    public Price getFinalValue() {
        return finalValue;
    }

    public Price getAdjustmentValue() {
        return adjustmentValue;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String toString() {
        return operationType + " adjustment value: " + adjustmentValue +
                ", original value: " + originalValue + " final value " + finalValue;
    }
}
