package org.jpmorgan.message.records;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class MessageCounterTest {

    @InjectMocks
    private MessageCounter sut = MessageCounter.INSTANCE;
    @Test
    public void testIncrement() {
        Long initialValue = sut.getNrOfMessages();
        sut.increment();
        initialValue++;
        assertTrue(initialValue + "vs. " + sut.getNrOfMessages(),
                initialValue == sut.getNrOfMessages());
    }
}
