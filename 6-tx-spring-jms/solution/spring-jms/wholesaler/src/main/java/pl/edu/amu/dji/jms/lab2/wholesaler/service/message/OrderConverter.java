package pl.edu.amu.dji.jms.lab2.wholesaler.service.message;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

@Component("orderConverter")
public class OrderConverter implements MessageConverter {
    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order fromMessage(Message message) throws JMSException, MessageConversionException {
        Preconditions.checkArgument(message instanceof MapMessage);

        MapMessage mapMessage = (MapMessage) message;
        int quantity = mapMessage.getInt("quantity");
        String retailerID = mapMessage.getString("retailerID");

        Preconditions.checkState(quantity > 0);
        Preconditions.checkState(StringUtils.isNotEmpty(retailerID));

        return new Order(quantity, retailerID);
    }
}
