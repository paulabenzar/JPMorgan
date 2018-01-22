package org.jpmorgan.message.records.message;

import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
public class AdjustmentMessageTest {

    @Mock
    private Price originalValue;
    @Mock
    private Price finalValue;
    @Mock
    private Price adjustmentValue;

    @Test
    public void testAdjustmentMessageFieldsAreSet() {
        AdjustmentMessage sut = new AdjustmentMessage(adjustmentValue, originalValue, finalValue,
                OperationType.ADD);
        assertEquals(originalValue, sut.getOriginalValue());
        assertEquals(finalValue, sut.getFinalValue());
        assertEquals(adjustmentValue, sut.getAdjustmentValue());
        assertEquals(OperationType.ADD, sut.getOperationType());

    }
}
