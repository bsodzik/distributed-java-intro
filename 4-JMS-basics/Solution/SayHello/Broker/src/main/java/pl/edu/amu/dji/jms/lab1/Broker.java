package pl.edu.amu.dji.jms.lab1;

import org.apache.activemq.broker.BrokerService;

public class Broker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
