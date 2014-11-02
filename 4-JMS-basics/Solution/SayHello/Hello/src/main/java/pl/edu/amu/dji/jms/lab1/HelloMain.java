package pl.edu.amu.dji.jms.lab1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class HelloMain {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue("SayHelloQueue");
        Destination topic = session.createTopic("SayHelloTopic");
        MessageConsumer consumer = session.createConsumer(queue, "dots = true");


        MessageListener listener = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        boolean property = textMessage.getBooleanProperty("dots");
                        System.out.println("[" + Thread.currentThread().getName() + "]" + "Hello " + text);
                        System.out.println("[" + Thread.currentThread().getName() + "]" + "String property " + property);
                    } catch (JMSException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        consumer.setMessageListener(listener);
        connection.start();
    }
}
