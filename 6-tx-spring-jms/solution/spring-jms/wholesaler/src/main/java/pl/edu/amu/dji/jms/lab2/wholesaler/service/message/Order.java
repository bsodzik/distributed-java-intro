package pl.edu.amu.dji.jms.lab2.wholesaler.service.message;

/**
 * User: mateuszjancy Date: 11/11/14 10:43 AM
 */
public class Order {
    private int quantity;
    private String retailerID;

    public Order() {
    }

    public Order(int quantity, String retailerID) {
        this.quantity = quantity;
        this.retailerID = retailerID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRetailerID() {
        return retailerID;
    }

    public void setRetailerID(String retailerID) {
        this.retailerID = retailerID;
    }
}
