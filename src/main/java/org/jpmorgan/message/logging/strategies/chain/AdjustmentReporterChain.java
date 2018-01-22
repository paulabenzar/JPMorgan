package org.jpmorgan.message.logging.strategies.chain;

import org.jpmorgan.message.records.MessageCounter;
import org.jpmorgan.message.records.SalesManager;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class AdjustmentReporterChain extends ReportingStrategiesChainImpl {

    @Override
    public void generateReport() {
        if (MessageCounter.INSTANCE.getNrOfMessages() % 50 == 0) {
            System.out.println("System is pausing");
            SalesManager.INSTANCE.reportAdjustments();
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        goToNextChain();
    }

    public boolean equals(Object obj) {
        return obj instanceof AdjustmentReporterChain &&
                getNextStrategy().equals(((AdjustmentReporterChain) obj).getNextStrategy());
    }
}
