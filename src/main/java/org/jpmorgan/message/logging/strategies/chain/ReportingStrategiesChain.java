package org.jpmorgan.message.logging.strategies.chain;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public interface ReportingStrategiesChain {

    void generateReport();
    void setNextChain(ReportingStrategiesChain strategy);
}
