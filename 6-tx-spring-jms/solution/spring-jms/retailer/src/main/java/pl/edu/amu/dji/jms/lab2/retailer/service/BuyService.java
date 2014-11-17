package pl.edu.amu.dji.jms.lab2.retailer.service;

import com.google.common.base.Preconditions;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.lang.IllegalStateException;

public class BuyService implements MessageListener {

    private JmsTemplate jmsTemplate;

    private Double maxPrice;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public void onMessage(Message message) {
        Preconditions.checkArgument(message instanceof MapMessage);

        MapMessage mapMessage = (MapMessage) message;

        try{
            Double price = mapMessage.getDouble("price");
            if(maxPrice.compareTo(price)==1){
                Destination replayTo = mapMessage.getJMSReplyTo();

                jmsTemplate.send(replayTo, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        MapMessage buy = session.createMapMessage();
                        buy.setString("retailerID", getClass().getName());
                        buy.setInt("quantity", 10);

                        return buy;
                    }
                });
            }
        } catch (JMSException ex){
            throw new IllegalStateException(ex);
        }
    }
}
