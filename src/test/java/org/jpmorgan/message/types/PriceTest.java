package org.jpmorgan.message.types;

import org.jpmorgan.message.currency.CurrencyConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class PriceTest {

    @Test
    public void buildGBPPrice() {
        BigDecimal value = new BigDecimal("1.2");
        Price sut = Price.buildGBPPrice(value);
        assertEquals(value, sut.getValue());
        assertEquals(Currency.getInstance("GBP"), sut.getCurrency());
    }

    @Test
    public void buildDollarPrice() {
        BigDecimal value = new BigDecimal("1.2");
        Price sut = Price.buildPrice(value, Currency.getInstance("USD"));
        assertEquals(CurrencyConverter.convertToPounds(value), sut.getValue());
        assertEquals(Currency.getInstance("GBP"), sut.getCurrency());
    }
}
