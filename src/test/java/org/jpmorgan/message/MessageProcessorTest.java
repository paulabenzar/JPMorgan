package org.jpmorgan.message;

/**
 * Created by paulabenzar on 21.01.2018.
 */

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.jpmorgan.message.records.MessageCounter;
import org.jpmorgan.message.records.SalesManager;
import org.jpmorgan.message.types.OperationType;
import org.jpmorgan.message.types.Price;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class MessageProcessorTest {

    private MessageProcessor sut;

    private final String APPLES = "APPL";
    private final String ORANGES = "ORGN";
    private final String TV = "TVDE3";
    private final Price APPLE_PRICE = Price.buildGBPPrice(new BigDecimal("1"));
    private final Price ORANGES_PRICE = Price.buildGBPPrice(new BigDecimal("2"));
    private final Price TV_PRICE = Price.buildGBPPrice(new BigDecimal("100"));
    private Random rand = new Random();
    private List<Product> productCodes;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void init(){
        sut = new MessageProcessorImpl();
        productCodes = Arrays.asList(new Product(APPLES, APPLE_PRICE),
                new Product(ORANGES, ORANGES_PRICE), new Product(TV, TV_PRICE));
        System.setOut(new PrintStream(outContent));
        SalesManager.INSTANCE.clearMessages();
        MessageCounter.INSTANCE.reset();
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    @Ignore
    public void testAdjustMessages(){

        int nrOfAppleSales = 0;
        for(int i=0;i<9;i++) {
            int nr = rand.nextInt(4)+1;
            nrOfAppleSales+=nr;
            sut.processMessage(APPLES, APPLE_PRICE, Optional.of(nr));
        }
        BigDecimal originalAppleValue1 = APPLE_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOfAppleSales)));
        sut.applyAdjustment(APPLES, Price.buildGBPPrice(new BigDecimal("2")),  OperationType.ADD);
        BigDecimal finalAppleValue1 = originalAppleValue1.add(
                new BigDecimal("2").multiply(new BigDecimal(String.valueOf(nrOfAppleSales))));

        for(int i=0;i<9;i++) {
            int nr = rand.nextInt(4)+1;
            nrOfAppleSales+=nr;
            sut.processMessage(APPLES, APPLE_PRICE, Optional.of(nr));
        }
        BigDecimal originalAppleValue2 = APPLE_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOfAppleSales)));
        sut.applyAdjustment(APPLES, Price.buildGBPPrice(new BigDecimal("2")),  OperationType.ADD);
        BigDecimal finalAppleValue2 = originalAppleValue2.add(
                new BigDecimal("2").multiply(new BigDecimal(String.valueOf(nrOfAppleSales))));

        int nrOfOrangesSales = 0;
        for(int i=0;i<19;i++) {
            int nr = rand.nextInt(4)+1;
            nrOfOrangesSales+=nr;
            sut.processMessage(ORANGES, ORANGES_PRICE, Optional.of(nr));
        }
        BigDecimal originalOrangesValue = ORANGES_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOfOrangesSales)));
        sut.applyAdjustment(ORANGES, Price.buildGBPPrice(new BigDecimal("2")),  OperationType.MULTIPLY);
        BigDecimal finalOrangesValue = originalOrangesValue.multiply(new BigDecimal("2"));

        int nrOfTVSales = 0;
        for(int i=0;i<9;i++) {
            int nr = rand.nextInt(4)+1;
            nrOfTVSales+=nr;
            sut.processMessage(TV, TV_PRICE, Optional.of(nr));
        }
        BigDecimal originalTVsValue = TV_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOfTVSales)));
        sut.applyAdjustment(TV, Price.buildGBPPrice(new BigDecimal("10")),  OperationType.SUBSTRACT);
        BigDecimal finalTVsValue = originalTVsValue.subtract(
                new BigDecimal("10").multiply(new BigDecimal(String.valueOf(nrOfTVSales))));

        String report = outContent.toString();
        assertTrue(report.contains("APPL:ADD adjustment value: 2 £, original value: "
                +originalAppleValue1+" £ final value "+finalAppleValue1+" £"));
        assertTrue(report.contains("APPL:ADD adjustment value: 2 £, original value: "
                +originalAppleValue2+" £ final value "+finalAppleValue2+" £"));
        assertTrue(report.contains("ORGN:MULTIPLY adjustment value: 2 £, original value: "
                +originalOrangesValue+" £ final value "+finalOrangesValue+" £"));
        assertTrue(report.contains("TVDE3:SUBSTRACT adjustment value: 10 £, original value: "
                +originalTVsValue+" £ final value "+finalTVsValue+" £"));
    }

    @Test
    public void testRecord10Messages(){
       Map<String, Integer> nrOFSales = new HashMap<>();
       nrOFSales.put(APPLES, 0);
       nrOFSales.put(ORANGES, 0);
       nrOFSales.put(TV, 0);
       for(int i=0;i<10;i++) {
            Product product = productCodes.get(rand.nextInt(3));
            int nr = rand.nextInt(4)+1;
            nrOFSales.put(product.productCode, nr+nrOFSales.get(product.productCode));
            sut.processMessage(product.productCode, product.price, Optional.of(nr));
       }

        BigDecimal applesPrice = APPLE_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOFSales.get(APPLES))));
        BigDecimal orangesPrice = ORANGES_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOFSales.get(ORANGES))));
        BigDecimal tvsPrice = TV_PRICE.getValue().multiply(new BigDecimal(String.valueOf(nrOFSales.get(TV))));
        String report = outContent.toString();
        assertTrue(report.contains("Product APPL: sold "+nrOFSales.get(APPLES)+" times, total price "+applesPrice+" £"));
        assertTrue(report.contains("Product ORGN: sold "+nrOFSales.get(ORANGES)+" times, total price "+orangesPrice+" £"));
        assertTrue(report.contains("Product TVDE3: sold "+nrOFSales.get(TV)+" times, total price "+tvsPrice+" £"));
    }

    @Test
    public void testRecord20Messages(){
        for(int i=0;i<20;i++) {
            Product product = productCodes.get(rand.nextInt(3));
            int nr = rand.nextInt(4)+1;
            sut.processMessage(product.productCode, product.price, Optional.of(nr));
        }

        String report = outContent.toString();
        assertTrue(3==report.split("Sales report:").length);
    }

    @Test
    public void testRecord11Messages(){
        for(int i=0;i<11;i++) {
            Product product = productCodes.get(rand.nextInt(3));
            int nr = rand.nextInt(4)+1;
            sut.processMessage(product.productCode, product.price, Optional.of(nr));
        }

        String report = outContent.toString();
        assertTrue(2==report.split("Sales report:").length);
    }

    @Test
    public void testRecordMessagesSmallerThan10(){
        for(int i=0;i<9;i++) {
            Product product = productCodes.get(rand.nextInt(3));
            int nr = rand.nextInt(4)+1;
            sut.processMessage(product.productCode, product.price, Optional.of(nr));
        }

        String report = outContent.toString();
        assertFalse(report.contains("Sales report:"));
    }

    private class Product{
        private String productCode;
        private Price price;

        Product(String productCode, Price price){
            this.price = price;
            this.productCode = productCode;
        }

    }
}
