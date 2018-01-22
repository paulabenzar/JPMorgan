package org.jpmorgan.message.logging.strategies.chain;

import java.util.Optional;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public abstract class ReportingStrategiesChainImpl implements ReportingStrategiesChain {

    private Optional<ReportingStrategiesChain> nextStrategy = Optional.empty();

    @Override
    public void setNextChain(ReportingStrategiesChain strategy) {
        nextStrategy = Optional.of(strategy);
    }

    protected void goToNextChain() {
        nextStrategy.ifPresent(s -> s.generateReport());
    }

    protected Optional<ReportingStrategiesChain> getNextStrategy() {
        return nextStrategy;
    }
}
