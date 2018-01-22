package org.jpmorgan.message.records.message;

import org.jpmorgan.message.types.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class SaleMessageTest {

    @Mock
    private Price totalPrice;
    @Mock
    private Price secondTotalPrice;

    @Test
    public void testFieldsAreSetInConstructor() {
        SaleMessage sut = new SaleMessage("CD1", 1, totalPrice);
        assertEquals("CD1", sut.getProductCode());
        assertEquals(totalPrice, sut.getTotal());
        assertEquals(1, sut.getNrOSales());
    }

    @Test
    public void testFieldsAreSet() {
        SaleMessage sut = new SaleMessage("CD1", 1, totalPrice);
        sut.setNrOSales(3);
        sut.setTotal(secondTotalPrice);
        assertEquals("CD1", sut.getProductCode());
        assertEquals(secondTotalPrice, sut.getTotal());
        assertEquals(3, sut.getNrOSales());
    }
}
