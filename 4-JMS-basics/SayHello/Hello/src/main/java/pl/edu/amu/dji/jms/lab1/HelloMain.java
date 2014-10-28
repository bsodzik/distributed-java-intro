package pl.edu.amu.dji.jms.lab1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class HelloMain {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        /*
        Create Connection instance from ConnectionFactory

        Create Session instance from connection object.
        - Session shouldn't by transactional and should by in auto acknowledge mode (Session.AUTO_ACKNOWLEDGE).

        Create Destination queue from session (check Session class and createQueue method)
        - queue name should be "SayHelloQueue"


        Create Destination topic instance from session (check Session class and createTopic method)
        - topic name should be "SayHelloTopic"
         */

        Connection connection = null;
        Session session = null;
        Destination queue = null;
        MessageConsumer consumer = null;

        /*
        Create MessageConsumer instance from session (check Session class and createConsumer method)

        Implement onMessage in MessageListener interface
        - Check if message is in proper type (see message type in Say class) by instanceof
        - Get text from message (remember to cat message to proper type)
        - Print message text to sysout
        - Don't forgot to handle JMSException
         */
        MessageListener helloListener = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                throw new UnsupportedOperationException();
            }
        };

        //Set MessageListener implementation as a message listener in MessageConsumer

        connection.start();
    }
}
