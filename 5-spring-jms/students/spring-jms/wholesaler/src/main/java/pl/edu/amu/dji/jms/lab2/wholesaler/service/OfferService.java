package pl.edu.amu.dji.jms.lab2.wholesaler.service;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

/**
 * User: mateuszjancy Date: 11/4/14 5:08 PM
 */
public class OfferService {

    private JmsTemplate jmsTemplate;

    private Destination offerTopic;

    private Destination orderQueue;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setOfferTopic(Destination offerTopic) {
        this.offerTopic = offerTopic;
    }

    public void setOrderQueue(Destination orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void sendOffer(final Double price) {
        throw new UnsupportedOperationException();
    }
}
