package org.jpmorgan.message.records;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.jpmorgan.message.records.message.SaleMessage;
import org.jpmorgan.message.types.Price;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public enum SalesManager {

    INSTANCE;

    private Map<String, SaleMessage> messages = new TreeMap<>();

    public void updateSale(SaleMessage saleMessage) {
        messages.put(saleMessage.getProductCode(), saleMessage);
    }

    public void recordSale(SaleMessage saleMessage) {
        if (messages.containsKey(saleMessage.getProductCode())) {
            SaleMessage sale = messages.get(saleMessage.getProductCode());
            Price price = sale.getTotal();
            sale.setTotal(Price.buildGBPPrice(price.getValue().add(saleMessage.getTotal().getValue())));
            sale.setNrOSales(sale.getNrOSales() + saleMessage.getNrOSales());
            saleMessage = sale;
        }
        messages.put(saleMessage.getProductCode(), saleMessage);
    }

    public SaleMessage getSales(String productCode) {
        return messages.get(productCode);
    }

    public void reportSales() {
        System.out.println("Sales report:");
        System.out.println(messages.values().stream().map(SaleMessage::getProductDetails)
                .collect(Collectors.joining("\n")));
    }

    public void reportAdjustments(){
        System.out.println("Adjustments report: ");
        System.out.println(messages.values().stream().map(SaleMessage::getAdjustmentDetails)
                .collect(Collectors.joining("\n")));
    }

    public String toString(){
        String report = "Total Sales report:\n";
        report += messages.values().stream().map(SaleMessage::toString).collect(Collectors.joining("\n"));
        return report;
    }

    public void clearMessages(){
        this.messages.clear();
    }
}
