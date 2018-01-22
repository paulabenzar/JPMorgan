package org.jpmorgan.message.operation;

import org.jpmorgan.message.records.SalesManager;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.Price;

import java.math.BigDecimal;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class StoreOperation implements Operation {


    @Override
    public void performOperation(SaleMessage saleMessage) {
        if (saleMessage.getNrOSales() > 1) {
            computeTotalPriceValue(saleMessage);
        }
        SalesManager.INSTANCE.recordSale(saleMessage);
    }

    private void computeTotalPriceValue(SaleMessage saleMessage){
        BigDecimal pricePerSale = saleMessage.getTotal().getValue();
        BigDecimal nrOfSales = new BigDecimal(String.valueOf(saleMessage.getNrOSales()));
        BigDecimal salesTotal = pricePerSale.multiply(nrOfSales);
        saleMessage.setTotal(Price.buildGBPPrice(salesTotal));
    }
}
