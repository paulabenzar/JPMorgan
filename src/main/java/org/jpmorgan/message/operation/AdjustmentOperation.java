package org.jpmorgan.message.operation;

import org.jpmorgan.message.records.SalesManager;
import org.jpmorgan.message.records.message.AdjustmentMessage;
import org.jpmorgan.message.records.message.SaleMessage;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public abstract class AdjustmentOperation implements Operation {

    @Override
    public void performOperation(SaleMessage adjustmentMessage) {

        SaleMessage recordedSales = SalesManager.INSTANCE.getSales(adjustmentMessage.getProductCode());
        if (null == recordedSales)
            return;
        applyAdjustment(recordedSales, adjustmentMessage);
        SalesManager.INSTANCE.updateSale(recordedSales);
    }

    protected abstract void applyAdjustment(SaleMessage recorderMessage, SaleMessage adjustmentMessage);


}
