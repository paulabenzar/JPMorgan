package org.jpmorgan.message.records;

import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class SalesManagerTest {

    private SalesManager sut = SalesManager.INSTANCE;
    private SaleMessage saleMessage3;
    private BigDecimal price = new BigDecimal("1");

    @Test
    public void testUpdateSale() {
        SaleMessage updateMessage1 = new SaleMessage("UPT", 1, Price.buildGBPPrice(price));
        SaleMessage updateMessage2 = new SaleMessage("UPT", 1, Price.buildGBPPrice(price));
        sut.updateSale(updateMessage1);
        sut.updateSale(updateMessage2);
        assertEquals(updateMessage2, sut.getSales("UPT"));
    }

    @Test
    public void testNewRecodSale() {
        SaleMessage saleMessage = new SaleMessage("SL", 1, Price.buildGBPPrice(price));
        sut.recordSale(saleMessage);
        assertEquals(saleMessage, sut.getSales("SL"));
    }

    @Test
    public void testRecordSaleExistingProductCode() {
        addSame2ProductCodeSales("DP");
    }


    private void addSame2ProductCodeSales(String productCode) {
        SaleMessage saleMessage1 = new SaleMessage(productCode, 1, Price.buildGBPPrice(price));
        SaleMessage saleMessage2 = new SaleMessage(productCode, 1, Price.buildGBPPrice(price));
        sut.recordSale(saleMessage1);
        sut.recordSale(saleMessage2);
        assertEquals(2, sut.getSales(productCode).getNrOSales());
        assertEquals(new BigDecimal("2"), sut.getSales(productCode).getTotal().getValue());
    }

    @Test
    public void testSeparateProductCode() {
        SaleMessage saleMessage = new SaleMessage("DGH", 1, Price.buildGBPPrice(price));
        sut.recordSale(saleMessage);
        addSame2ProductCodeSales("DR");
        assertEquals(saleMessage, sut.getSales("DGH"));

    }
}
