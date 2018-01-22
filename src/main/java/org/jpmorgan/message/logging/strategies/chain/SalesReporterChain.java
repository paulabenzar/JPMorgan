package org.jpmorgan.message.logging.strategies.chain;

import org.jpmorgan.message.records.MessageCounter;
import org.jpmorgan.message.records.SalesManager;


/**
 * Created by paulabenzar on 21.01.2018.
 */
public class SalesReporterChain extends ReportingStrategiesChainImpl {

    @Override
    public void generateReport() {
        if (MessageCounter.INSTANCE.getNrOfMessages() % 10 == 0) {
            SalesManager.INSTANCE.reportSales();
        }
        goToNextChain();
    }

    public boolean equals(Object obj) {
        return obj instanceof SalesReporterChain &&
                getNextStrategy().equals(((SalesReporterChain) obj).getNextStrategy());
    }
}
