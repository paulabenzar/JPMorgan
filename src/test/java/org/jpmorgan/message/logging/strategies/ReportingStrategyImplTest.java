package org.jpmorgan.message.logging.strategies;

import org.jpmorgan.message.logging.strategies.chain.AdjustmentReporterChain;
import org.jpmorgan.message.logging.strategies.chain.SalesReporterChain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class ReportingStrategyImplTest {

    private ReportingStrategyImpl sut;
    private SalesReporterChain salesReporterChain;
    private AdjustmentReporterChain adjustmentReporterChain;

    @Before
    public void beforeTest() {
        sut = new ReportingStrategyImpl();
        salesReporterChain = new SalesReporterChain();
        adjustmentReporterChain = new AdjustmentReporterChain();
        salesReporterChain.setNextChain(adjustmentReporterChain);
    }

    @Test
    public void testChainsAreSet() {
        ReportingStrategyImpl strategy = new ReportingStrategyImpl();
        strategy.clearChains();
        strategy.addChain(salesReporterChain);
        strategy.addChain(adjustmentReporterChain);
        assertEquals(strategy, sut);
    }

    @Test
    public void testChainsAreAdded() {
        ReportingStrategyImpl strategy = new ReportingStrategyImpl();
        strategy.addChain(adjustmentReporterChain);
        strategy.addChain(salesReporterChain);
        assertNotEquals(strategy, sut);
    }
}
