package pl.edu.amu.dji.jms.lab2.wholesaler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.amu.dji.jms.lab2.wholesaler.service.message.Offer;

import javax.jms.Destination;

@Service("offerService")
public class OfferService {

    @Autowired
    @Qualifier("offerJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("orderQueue")
    private Destination orderQueue;

    @Transactional
    public void sendOffer(final Double price){
        Offer offer = new Offer(price, orderQueue);
        jmsTemplate.convertAndSend(offer);

        if(price < 5){
            throw new IllegalStateException();
        }
    }
}
