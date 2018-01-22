package org.jpmorgan.message.logging.strategies.chain;

import org.jpmorgan.message.records.MessageCounter;
import org.jpmorgan.message.records.SalesManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;


import static org.mockito.Mockito.*;

/**
 * Created by paulabenzar on 21.01.2018.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({SalesManager.class, MessageCounter.class})
public class SalesReporterChainTest {

    @InjectMocks
    private SalesReporterChain sut;
    @Mock
    private SalesManager salesManagerInstance;
    @Mock
    private MessageCounter messageCounterInstance;
    @Mock
    private AdjustmentReporterChain adjustmentReporterChain;

    @Before
    public void beforeTest() {
        Whitebox.setInternalState(SalesManager.class, "INSTANCE", salesManagerInstance);
        Whitebox.setInternalState(MessageCounter.class, "INSTANCE", messageCounterInstance);
    }


    @Test
    public void testReportingWhenNumberOfMessagesIs10() {
        runtTestPrintReport(10l);
    }

    private void runtTestPrintReport(Long nrOfMessages) {
        doNothing().when(salesManagerInstance).reportSales();
        doReturn(nrOfMessages).when(messageCounterInstance).getNrOfMessages();
        sut.generateReport();
        verify(salesManagerInstance, times(1)).reportSales();
    }

    @Test
    public void testReportingWhenNumberOfMessagesIs20() {
        runtTestPrintReport(20l);
    }

    @Test
    public void testReportingWhenNumberOfMessagesIsSmallerThan10() {
        runTesNoReportShouldPePrinted(9l);
    }

    private void runTesNoReportShouldPePrinted(Long nrOfMessages) {
        doNothing().when(salesManagerInstance).reportSales();
        doReturn(nrOfMessages).when(messageCounterInstance).getNrOfMessages();
        sut.generateReport();
        verify(salesManagerInstance, never()).reportSales();
    }

    @Test
    public void testReportingWhenNumberOfMessagesIsBiggerThan10() {
        runTesNoReportShouldPePrinted(11l);
    }

    @Test
    public void testNextChainPresent() {
        sut.setNextChain(adjustmentReporterChain);
        runTesNoReportShouldPePrinted(1l);
        verify(adjustmentReporterChain, times(1)).generateReport();
    }
}
