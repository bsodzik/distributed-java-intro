## Labs 6 - Transactions and Spring JMS support ##

### Requirements ###
- Run Linux on UAM workstation
- Use IntelliJ IDEA

### Goals: Get familiar with ###
- JMS guaranteed messaging
- Spring JMS transactions support
- Spring annotations and advanced JMS support

#### Guaranteed messaging ####
- Message autonomy
	- Self-contained autonomous entities
	- May be sent and re-sent multiple times across multiple processes
- Store and forward
	- Messages which are marked as PERSISTENT are persisted on JMS Server
	- Forwarding mechanism is responsible for retrieving messages from storage and then delivering them
- Message acknowledgment for AUTO_ACKNOWLEDGE
	- Producer perspective
	- Server perspective 
	- Consumer perspective
- Transactions
	- On session in transaction mode 
	- Handled by session.commit and session.rollback 

#### Exercise 1: add JMS Transactions ####
Goal: add Transactional on OfferService.sendOffer and OrderService.onMessage methods. Add tx:annotation-driven in context.xml
- Add org.springframework.transaction.annotation.Transactional on pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService#sendOffer method
- Add org.springframework.transaction.annotation.Transactional on pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService#onMessage method
- Go to context.xml and 
	- Add xmlns:tx="http://www.springframework.org/schema/tx" namespace with http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd schema location
	- Add tx:annotation-driven inside beans element
	- Create org.springframework.jms.connection.JmsTransactionManager bean (use transactionManager as id), set connectionFactory
	- Change org.springframework.jms.listener.SimpleMessageListenerContainer to org.springframework.jms.listener.DefaultMessageListenerContainer  in messageListenerContainer bean
	- Set sessionTransacted to true in messageListenerContainer bean
	- Set sessionTransacted to true in jmsTemplate bean

#### Exercise 2: add message driven POJO and use component scan ####
Goal: Instead MapMessage use message driven POJOs. Create converters by implementing MessageConverter spring interface. In converter convert Message to POJO and POJO to Message (remember that we are using MapMessage).

- Create pl.edu.amu.dji.jms.lab2.wholesaler.service.message.Offer POJO with price (Double) and replyTo (Destination) fields
- Create pl.edu.amu.dji.jms.lab2.wholesaler.service.message.Order POJO with quantity (int) and retailerID (String) fields
- Create pl.edu.amu.dji.jms.lab2.wholesaler.service.message.OfferConverter
	- Implement org.springframework.jms.support.converter.MessageConverter interface 
	- Throws java.lang.UnsupportedOperationException in pl.edu.amu.dji.jms.lab2.wholesaler.service.message.OfferConverter#fromMessage method
	- In pl.edu.amu.dji.jms.lab2.wholesaler.service.message.OfferConverter#toMessage convert Offer to MapMessage
	- Add org.springframework.stereotype.Component annotation on class with offerConverter value
- Create pl.edu.amu.dji.jms.lab2.wholesaler.service.message.OrderConverter
	- Implement org.springframework.jms.support.converter.MessageConverter interface 
	- Throws java.lang.UnsupportedOperationException in pl.edu.amu.dji.jms.lab2.wholesaler.service.message.OrderConverter#toMessage method
	- In pl.edu.amu.dji.jms.lab2.wholesaler.service.message.OrderConverter#fromMessage convert MapMessage to Order
	- Add org.springframework.stereotype.Component annotation on class with orderConverter value


Use Offer POJO in OfferService
- Go to pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService
	- Add org.springframework.stereotype.Service annotation on class with offerService value
	- Add org.springframework.beans.factory.annotation.Autowired annotation and org.springframework.beans.factory.annotation.Qualifier annotation with offerJmsTemplate value on pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService#jmsTemplate field
	- Add org.springframework.beans.factory.annotation.Autowired annotation and org.springframework.beans.factory.annotation.Qualifier annotation with orderQueue value on pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService#orderQueue field
	- Remove all other fields
	- Remove all getters and setters
	- In pl.edu.amu.dji.jms.lab2.wholesaler.service.OfferService#sendOffer 
		- Replace old MapMessage implementation by Offer message driven POJO
		- Send POJO by org.springframework.jms.core.JmsTemplate#convertAndSend

Use Order POJO in OrderService
- Go to pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService 
	- Change pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService#onMessage to pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService#order, remove Override annotation
	- Use pl.edu.amu.dji.jms.lab2.wholesaler.service.message.Order as a attribute for pl.edu.amu.dji.jms.lab2.wholesaler.service.OrderService#order method
	- Remove old MapMessage implementation 
	- Print quantity and retailer id from order parameter
	- Remove MessageListener
	- Add org.springframework.stereotype.Service annotation on class with orderService as a value

Goal: Enable component scan in context.xml, remove offerService and orderService then add MessageListenerAdapter for orderService. Change jmsTemplate id to offerJmsTemplate and set defaultDestination and messageConverter for offerTopic
- Go to context.xml and 
	- Add xmlns:context="http://www.springframework.org/schema/context" namespace with http://www.springframework.org/schema/context and http://www.springframework.org/schema/context/spring-context.xsd schema location
	- Add component-scan (use context namespace), set base-package attribute to pl.edu.amu.dji.jms.lab2.wholesaler.*
	- Remove orderService bean
	- Remove offerService bean
	- Add org.springframework.jms.listener.adapter.MessageListenerAdapter (with orderServiceAdapter id)
		- Set delegate property to orderService and defaultListenerMethod property to order
		- Set messageConverter to  orderConverter
	- Change jmsTemplate id to offerJmsTemplate and set defaultDestination to offerTopic and messageConverter to offerConverter
	- In messageListenerContainer set messageListener to orderServiceAdapter bean

#### Exercise 3: add pooled connection ####
- Go to context.xml and 
	- Change connectionFactory bean to toUseConnectionFactory (id)
	- Add new connectionFactory bean with org.apache.activemq.pool.PooledConnectionFactory class and stop destroy-method attribute
		- Set connectionFactory property to toUseConnectionFactory

##[Feedback](http://goo.gl/forms/2dIvZGJMCM)##
