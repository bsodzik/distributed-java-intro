## Labs 5 - Spring JMS ##

### Requirements ###
- Run Linux on UAM workstation
- Use IntelliJ IDEA

### Goal ###
- Get familiar with basic concept of spring application context
- Get familiar with complex JMS Java code

#### Spring Framework introduction ####
- Comprehensive programming and configuration model 
- Dependency Injection
- Aspect-Oriented Programming (transaction management...)
- Support for JDBC, JPA, JMS
- [Hello world example](http://www.mkyong.com/spring3/spring-3-hello-world-example/)

#### Spring JMS ####
ActivaMQ specific:
- [ActiveMQConnectionFactory](http://activemq.apache.org/maven/apidocs/org/apache/activemq/ActiveMQConnectionFactory.html)
- [ActiveMQQueue](http://activemq.apache.org/maven/apidocs/org/apache/activemq/command/ActiveMQQueue.html)
- [ActiveMQTopic](http://activemq.apache.org/maven/apidocs/org/apache/activemq/command/ActiveMQTopic.html)
Spring specific:
- [JmsTemplate](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/core/JmsTemplate.html)
    - [connectionFactory](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/support/JmsAccessor.html#setConnectionFactory-javax.jms.ConnectionFactory-)
    - [send](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/core/JmsTemplate.html#send-javax.jms.Destination-org.springframework.jms.core.MessageCreator-)
- [SimpleMessageListenerContainer](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/listener/SimpleMessageListenerContainer.html)
    - [messageListener](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/listener/AbstractMessageListenerContainer.html#setMessageListener-java.lang.Object-)
    - [connectionFactory](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/support/JmsAccessor.html#setConnectionFactory-javax.jms.ConnectionFactory-)
    - [destination](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/listener/AbstractMessageListenerContainer.html#setDestination-javax.jms.Destination-)
- [MessageCreator](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jms/core/MessageCreator.html)

#### [User Stories](http://en.wikipedia.org/wiki/User_story) ####
- As a OfferService I want to publish offer on "offerTopic" topic 
    - message should contain "buyQueue" for retailers to response (in javax.jms.Message#setJMSReplyTo) and price (Double)
- As a OrderService I want to receive all offers from "offerTopic" topic
- As a OrderService when price in offer is below my max price then I want to send buy message  (javax.jms.MapMessage) with quantity and retailerID to queue (from javax.jms.Message#getJMSReplyTo)
- As a BuyService I want to receive all buy messages from "buyQueue"

#### Exercise 1: implement wholesaler OfferService ####
- Implement pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService#sendOffer method
- Send map message by using org.springframework.jms.core.JmsTemplate#send(final Destination destination, final MessageCreator messageCreator) with:
    - Implement org.springframework.jms.core.MessageCreator#createMessage
    - Map message should contain
        - Price as a javax.jms.MapMessage#setDouble (use "price" string as a key)
        - OrderQueue as a javax.jms.Message#setJMSReplyTo
- Go to /context.xml
    - Create pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService bean (set all properties)

#### Exercise 2: implement retailer BuyService ####
- Implement pl.edu.amu.dji.jms.lab2.retailer.service.BuyService#onMessage method
    - Cast message to javax.jms.MapMessage
    - Get price from map message (javax.jms.MapMessage#getDouble, use "price" string as a key)
    - If price is ok (maxPrice.compareTo(price)==1) send order 
        - Send order by using org.springframework.jms.core.JmsTemplate#send(final Destination destination, final MessageCreator messageCreator)
        - Use javax.jms.Message#getJMSReplyTo as a destination
        - Implement org.springframework.jms.core.MessageCreator#createMessage
        - Map message should contain
            - retailerID string with getClass().getName() as a value 
            - quantity with some double value
- Go to /context.xml
    - Create pl.edu.amu.dji.jms.lab2.retailer.service.BuyService (with buyService as a id)
        - Set all properties
    - Create org.springframework.jms.listener.SimpleMessageListenerContainer
        - Set messageListener property to buyService
        - Set connectionFactory property to connectionFactory
        - Set destination to offerTopic


#### Exercise 3: implement wholesaler OrderService ####
- Implement pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService#onMessage
    - Just print quantity and retailerID
- Go to /context.xml
    - Create pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService bean (with orderService as a id)
    - Create org.springframework.jms.listener.SimpleMessageListenerContainer
        - Set messageListener property to buyService
        - Set connectionFactory property to connectionFactory
        - Set destination to orderQueue


##[Feedback](http://goo.gl/forms/C1t1wHU8AL)##
