package org.jpmorgan.message.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class CurrencyConverterTest {

    private CurrencyConverter sut;

    @Before
    public void beforeTest() {
        sut = new CurrencyConverter();
    }

    @Test
    public void testCurrencyConverter() {
        BigDecimal value = new BigDecimal("1.2");
        assertEquals(value, sut.convertToPounds(value));
    }
}
