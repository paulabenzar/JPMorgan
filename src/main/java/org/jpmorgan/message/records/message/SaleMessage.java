package org.jpmorgan.message.records.message;

import org.jpmorgan.message.types.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public class SaleMessage{

    protected String productCode;
    protected Price total;
    protected int nrOSales;
    protected List<AdjustmentMessage> adjustments;

    public SaleMessage(String productCode, int nrOSales, Price total) {
        this.productCode = productCode;
        this.nrOSales = nrOSales;
        this.total = total;
        adjustments = new ArrayList<>();
    }

    public String getProductCode() {
        return productCode;
    }

    public Price getTotal() {
        return total;
    }

    public int getNrOSales() {
        return nrOSales;
    }

    public void setTotal(Price total) {
        this.total = total;
    }

    public void setNrOSales(int nrOSales) {
        this.nrOSales = nrOSales;
    }

    public void addAdjustment(AdjustmentMessage adjustmentMessage){
        adjustments.add(adjustmentMessage);
    }

    public List<AdjustmentMessage> getAdjustments(){
        return adjustments;
    }

    public String getProductDetails(){
        return "Product "+productCode + ": sold " + nrOSales + " times, total price " + total;
    }

    public String getAdjustmentDetails(){
        return  adjustments.stream().map(adj->productCode + ":" + adj.toString())
                .collect(Collectors.joining("'\n"));
    }

    public String toString() {
       return getProductDetails()+ "\nAdjustments:\n"+getAdjustmentDetails();
    }
}
