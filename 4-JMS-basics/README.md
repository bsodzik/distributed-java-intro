## Labs 4 - Java Messaging System (JMS) ##

### Goal ###
- Understand Message Oriented Middleware (MOM) concept
- Learn about JMS topic and queue concept
- Learn about basic message types
- Get familiar with JMS java code

#### JMS Overview####
1. [What Is Messaging?](http://docs.oracle.com/javaee/6/tutorial/doc/bncdr.html)
    - method of communication via messaging agent
    - sender and the receiver do not have to be available at the same time
    - sender does not need to know anything about the receiver / receiver does not need to know anything about the sender
    - sender and reciver neet to know message format and message destination
    - **Question: point main difference between Remote Method Invocation and Messaging.**

2. [Messaging models](http://activemq.apache.org/how-does-a-queue-compare-to-a-topic.html)
    - pub-sub: one producer can send a message to many consumers trought a virtual channel called topic
    - point-to-point: point to point model allows JMS clients to send and recive messages via virtual channels known as queues. Given queue may have multiple recivers, but only one reciever may consume each message.

3. [What Is the JMS API?](http://docs.oracle.com/javaee/6/tutorial/doc/bncdr.html)
    - JMS is an API
    - allows to create, send, receive, and read messages
    - its for programs written in the Java 
    - JMS is vendor independent
    - in JMS communication is asynchronous and reliable

4. [What is jms good for?](http://stackoverflow.com/questions/222017/what-is-jms-good-for)
    - data synchronisation
    - realtime
    - notifying (we need to propagate information about new employee to all company systems)
    - async processing (we want to process multiple "commands" on remote services in asynchronous fashion)
    - as an integration layer (check [spring integration](http://projects.spring.io/spring-integration/))
    - **Question: how to implement [codility](https://codility.com/demo/take-sample-test/) like application (remember about heavy load during "RUN" operation)**

5. Example of JMS messaging agents 
    - [ActiveMQ](http://activemq.apache.org/)
    - [HorntetQ](http://hornetq.jboss.org/)

6. Alternatives 
    - [RabbitMQ](http://www.rabbitmq.com/)
    - [Akka](http://akka.io/)
    - [ZeroMQ](http://zeromq.org/)
    - [Kafka](http://kafka.apache.org/)
    - **Question: can we use [Advanced Message Queuing Protocol](http://www.amqp.org/) protocol with [ActiveMQ](http://activemq.apache.org/amqp.html)**

#### Exercise 1: Overview of the JMS API ####
1. Check JMS [JavaDoc](http://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html): 
    - [javax.jms.ConnectionFactory](http://docs.oracle.com/javaee/7/api/javax/jms/ConnectionFactory.html)
    - [javax.jms.Connection](http://docs.oracle.com/javaee/7/api/javax/jms/Connection.html)
    - [javax.jms.Session](http://docs.oracle.com/javaee/7/api/javax/jms/Session.html)
    - [javax.jms.Destination](http://docs.oracle.com/javaee/7/api/javax/jms/Destination.html)
    - [javax.jms.MessageConsumer](http://docs.oracle.com/javaee/7/api/javax/jms/MessageConsumer.html)
    - [javax.jms.MessageProducer](http://docs.oracle.com/javaee/7/api/javax/jms/MessageProducer.html)
    - [javax.jms.MessageListener](http://docs.oracle.com/javaee/7/api/javax/jms/MessageListener.html)
    - [javax.jms.TextMessage](http://docs.oracle.com/javaee/7/api/javax/jms/TextMessage.html)
2. implement pl.edu.amu.dji.jms.exercise1.SayMain
    - follow woth comments in code
    - ask question
    - this class should produce messages for "SayHelloQueue" queue

3. implement pl.edu.amu.dji.jms.exercise1.HelloMain
    - follow woth comments in code
    - ask question
    - this class should consume messages for "SayHelloQueue" queue

4. Run 
    - pl.edu.amu.dji.jms.lab1.Broker (start one instance)
    - pl.edu.amu.dji.jms.lab1.SayMain (start one instance)
    - interact with SayMain console
    - pl.edu.amu.dji.jms.lab1.HelloMain (start one instance)
    - **explain HelloMain behaviour**

4. Run 
    - pl.edu.amu.dji.jms.lab1.Broker (start one instance)
    - pl.edu.amu.dji.jms.lab1.HelloMain (start 3 instances)
    - pl.edu.amu.dji.jms.lab1.SayMain (start one instance)
    - interact with SayMain console
    - **explain HelloMain behaviour**

#### Exercise 2: Overview of the JMS API ####
1. Change queue to topic in HelloMain and SayMain

2. Run 
    - pl.edu.amu.dji.jms.lab1.Broker (start one instance)
    - pl.edu.amu.dji.jms.lab1.SayMain (start one instance)
    - interact with SayMain console
    - pl.edu.amu.dji.jms.lab1.HelloMain (start 3 instances)
    - **explain HelloMain behaviour**

3. Run 
    - pl.edu.amu.dji.jms.lab1.Broker (start one instance)
    - pl.edu.amu.dji.jms.lab1.HelloMain (start 3 instances)
    - pl.edu.amu.dji.jms.lab1.SayMain (start one instance)
    - interact with SayMain console
    - **explain HelloMain behaviour**

