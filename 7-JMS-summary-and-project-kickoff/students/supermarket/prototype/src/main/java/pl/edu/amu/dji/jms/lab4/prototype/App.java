package pl.edu.amu.dji.jms.lab4.prototype;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        Reporting reporting = new Reporting();
        Warehouse warehouse = new Warehouse();

        PointOfSale pos1 = new PointOfSale(reporting);
        PointOfSale pos2 = new PointOfSale(reporting);
        PointOfSale pos3 = new PointOfSale(reporting);
        PointOfSale pos4 = new PointOfSale(reporting);

        warehouse.registerPointOfSale(pos1);
        warehouse.registerPointOfSale(pos2);
        warehouse.registerPointOfSale(pos3);
        warehouse.registerPointOfSale(pos4);

        //Send full product list to POS
        warehouse.sendProducts();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Available products:");
        for (Map.Entry entry : warehouse.PRODUCTS.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        //Sale part
        String productToSale;

        System.out.println("Select product to sale on pos 1:");
        productToSale = bufferedReader.readLine();
        pos1.sale(productToSale);

        System.out.println("Select product to sale on pos 2:");
        productToSale = bufferedReader.readLine();
        pos2.sale(productToSale);

        //Change price
        System.out.println("Select product name:");
        String name = bufferedReader.readLine();

        System.out.println("New product price:");
        String price = bufferedReader.readLine();


        if (NumberUtils.isNumber(price)) {
            try {
                warehouse.changePrice(name, Double.valueOf(price));
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            }
        }

        //Sale part
        System.out.println("Select product to sale on pos 3:");
        productToSale = bufferedReader.readLine();
        pos3.sale(productToSale);

        System.out.println("Select product to sale on pos 4:");
        productToSale = bufferedReader.readLine();
        pos4.sale(productToSale);

        //Sales report
        System.out.println("Sales report:");
        for (Map.Entry entry : reporting.PRODUCTS.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }
}
