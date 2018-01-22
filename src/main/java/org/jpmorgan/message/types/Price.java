package org.jpmorgan.message.types;

import org.jpmorgan.message.currency.CurrencyConverter;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class Price {


    private BigDecimal value;
    private Currency currency;

    private Price(BigDecimal value) {
        this.value = value;
        this.currency = Currency.getInstance("GBP");
    }

    public static Price buildGBPPrice(BigDecimal value) {
        return new Price(value);
    }

    public static Price buildPrice(BigDecimal value, Currency currency) {
        return new Price(CurrencyConverter.convertToPounds(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String toString() {
        return value.toPlainString() + " " + currency.getSymbol();
    }

    public boolean equals(Object obj) {
        return obj instanceof Price &&
                value.equals(((Price) obj).value) &&
                currency.equals(((Price) obj).currency);
    }
}
