package pl.edu.amu.dji.jms.lab4.prototype;

import java.util.HashMap;
import java.util.Map;

public class Reporting {
    public static final Map<String, Double> PRODUCTS = new HashMap<String, Double>();

    public void updateReport(String name, Double price) {
        Double current = PRODUCTS.get(name);

        current = current != null ? current + price : price;

        PRODUCTS.put(name, current);
    }
}
