package org.jpmorgan.message.operation;

import org.jpmorgan.message.records.MessageCounter;
import org.jpmorgan.message.records.SalesManager;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.Price;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by paulabenzar on 21.01.2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SalesManager.class})
public class StoreOperationTest {

    private BigDecimal value = new BigDecimal("1");
    @InjectMocks
    private StoreOperation sut;
    @Mock
    private SalesManager salesManagerInstance;
    ArgumentCaptor<SaleMessage> captor = ArgumentCaptor.forClass(SaleMessage.class);


    @Before
    public void beforeTest() {
        Whitebox.setInternalState(SalesManager.class, "INSTANCE", salesManagerInstance);
    }

    @Test
    public void testStore1Sale() {
        SaleMessage appleSale = new SaleMessage("APPL", 1, Price.buildGBPPrice(value));
        sut.performOperation(appleSale);
        verify(salesManagerInstance, times(1)).recordSale(appleSale);
    }

    @Test
    public void testStoreMultipleSales() {
        SaleMessage orrangeSale = new SaleMessage("ORNG", 3, Price.buildGBPPrice(value));
        sut.performOperation(orrangeSale);
        verify(salesManagerInstance, times(1)).recordSale(captor.capture());
        assertEquals(value.multiply(new BigDecimal("3")), captor.getValue().getTotal().getValue());

    }
}
