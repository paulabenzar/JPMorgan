package org.jpmorgan.message.operation;

import org.jpmorgan.message.records.message.SaleMessage;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@FunctionalInterface
public interface Operation {

    void performOperation(SaleMessage saleMessage);
}
