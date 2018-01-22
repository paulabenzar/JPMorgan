package org.jpmorgan.message.logging.strategies;

import org.jpmorgan.message.logging.strategies.chain.AdjustmentReporterChain;
import org.jpmorgan.message.logging.strategies.chain.ReportingStrategiesChain;
import org.jpmorgan.message.logging.strategies.chain.SalesReporterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class ReportingStrategyImpl implements ReportingStrategy {

    private List<ReportingStrategiesChain> chains;

    public ReportingStrategyImpl() {
        chains = new ArrayList<>();
        addChain(new SalesReporterChain());
        addChain(new AdjustmentReporterChain());
    }

    public void applyReportingStrategy() {
        chains.get(0).generateReport();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ReportingStrategyImpl) {
            return chains.equals(((ReportingStrategyImpl) obj).chains);
        }
        return false;
    }

    protected void clearChains() {
        chains.clear();
    }

    protected void addChain(ReportingStrategiesChain chain) {
        if (!chains.isEmpty()) {
            chains.get(chains.size() - 1).setNextChain(chain);
        }
        chains.add(chain);
    }
}
