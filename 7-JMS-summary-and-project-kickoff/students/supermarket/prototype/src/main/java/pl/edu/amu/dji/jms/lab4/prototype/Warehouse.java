package pl.edu.amu.dji.jms.lab4.prototype;

import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    public static final Map<String, Double> PRODUCTS;

    static {
        PRODUCTS = new HashMap<String, Double>();
        PRODUCTS.put("A", 10.99);
        PRODUCTS.put("B", 9.99);
    }

    private List<PointOfSale> pointOfSales;

    private final Cloner cloner;

    public Warehouse() {
        pointOfSales = new ArrayList<PointOfSale>();
        cloner = new Cloner();
    }

    public void registerPointOfSale(PointOfSale pointOfSale) {
        pointOfSales.add(pointOfSale);
    }

    public void unregister(PointOfSale pointOfSale) {
        pointOfSales.remove(pointOfSale);
    }

    public void sendProducts() {
        for (PointOfSale pointOfSale : pointOfSales) {
            pointOfSale.initProducts(cloner.deepClone(PRODUCTS));
        }
    }

    public void changePrice(String name, Double price) {
        name = name.toUpperCase();

        PRODUCTS.put(name, price);

        for (PointOfSale pointOfSale : pointOfSales) {
            pointOfSale.updatePrice(name, price);
        }
    }
}
