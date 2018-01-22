package org.jpmorgan.message.operation;

import org.jpmorgan.message.records.message.AdjustmentMessage;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class MultiplyOperationTest {

    @InjectMocks
    private MultiplyOperation sut;

    @Test
    public void testMultiplyOperation() {
        SaleMessage sale = new SaleMessage("CD", 2, Price.buildGBPPrice(new BigDecimal("2")));
        SaleMessage adjustment = new SaleMessage("CD", 1, Price.buildGBPPrice(new BigDecimal("2")));
        sut.applyAdjustment(sale, adjustment);
        assertEquals(1,  sale.getAdjustments().size());
        assertEquals(new BigDecimal("4"),  sale.getAdjustments().get(0).getFinalValue().getValue());
        assertEquals(OperationType.MULTIPLY,  sale.getAdjustments().get(0).getOperationType());
    }

}
