package pl.edu.amu.dji.jms.lab4.prototype;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class PointOfSale {

    private Map<String, Double> products = new HashMap<String, Double>();

    private Reporting reporting;

    public PointOfSale(Reporting reporting) {
        this.reporting = reporting;
    }

    public void initProducts(Map<String, Double> products) {
        this.products = products;
    }

    public void updatePrice(String name, Double price) {
        Preconditions.checkState(!products.isEmpty());

        products.put(name.toUpperCase(), price);
    }

    public void sale(String name) {
        Preconditions.checkState(!products.isEmpty());

        Double price = products.get(name.toUpperCase());
        reporting.updateReport(name, price);
    }
}
