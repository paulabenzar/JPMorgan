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
public class AdjustmentReporterChainTest {


    @InjectMocks
    private AdjustmentReporterChain sut;
    @Mock
    private MessageCounter messageCounterInstance;
    @Mock
    private AdjustmentReporterChain adjustmentReporterChain;
    @Mock
    private SalesManager salesManagerInstance;

    @Before
    public void beforeTest() {
        Whitebox.setInternalState(MessageCounter.class, "INSTANCE", messageCounterInstance);
        Whitebox.setInternalState(SalesManager.class, "INSTANCE", salesManagerInstance);
    }

    @Test
    public void testReportingWhenNumberOfMessagesIs50() {
        testPrintReport(50l);
    }

    @Test
    public void testReportingWhenNumberOfMessagesIs100() {
        testPrintReport(100l);
    }

    public void testPrintReport(Long nr) {
        doNothing().when(salesManagerInstance).reportAdjustments();
        doReturn(nr).when(messageCounterInstance).getNrOfMessages();
        sut.generateReport();
        verify(salesManagerInstance, times(1)).reportAdjustments();
    }

    @Test
    public void testReportingWhenNumberOfMessagesIsSmallerThan50() {
        runTesNoReportShouldPePrinted(49l);
    }

    private void runTesNoReportShouldPePrinted(Long nrOfMessages) {
        doNothing().when(salesManagerInstance).reportAdjustments();
        doReturn(nrOfMessages).when(messageCounterInstance).getNrOfMessages();
        sut.generateReport();
        verify(salesManagerInstance, never()).reportAdjustments();
    }

    @Test
    public void testReportingWhenNumberOfMessagesIsBiggerThan50() {
        runTesNoReportShouldPePrinted(51l);
    }

    @Test
    public void testNextChainPresent() {
        sut.setNextChain(adjustmentReporterChain);
        runTesNoReportShouldPePrinted(1l);
        verify(adjustmentReporterChain, times(1)).generateReport();
    }
}
