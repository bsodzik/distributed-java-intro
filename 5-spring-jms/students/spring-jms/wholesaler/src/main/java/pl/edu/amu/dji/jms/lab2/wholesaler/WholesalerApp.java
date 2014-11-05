package pl.edu.amu.dji.jms.lab2.wholesaler;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WholesalerApp {
    public static final String EXIT = "exit";

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        OfferService offerService = (OfferService) context.getBean("offerService");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (!in.equalsIgnoreCase(EXIT)) {
            System.out.print("New price:");
            in = bufferedReader.readLine();

            if (NumberUtils.isNumber(in)) {
                offerService.sendOffer(Double.valueOf(in));
            }
        }
    }
}
