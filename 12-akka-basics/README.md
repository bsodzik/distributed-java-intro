## Labs 12 - REST Summary and Introduce to Akka  ##

### Goals ###
- REST Summary
- Introduce to Akka
- Implement *Akka Hello World*
- Review JMS projects

### Realization ###

**REST Summary**

- Labs 8 - [rest - basics](8-rest-basics/README.md)
- Labs 9 - [rest - consumption](9-rest-consumption/README.md)
- Labs 10 - [rest - web service](https://github.com/bsodzik/distributed-java-intro/blob/master/10-rest-web-service/README.md)
- Labs 11 - [rest - web service advanced](https://github.com/bsodzik/distributed-java-intro/blob/master/11-rest-web-service-advanced/README.md)

**Introduce to Akka**

*Akka is a toolkit and runtime for building highly concurrent, distributed, and resilient message-driven applications on the JVM.* ([http://akka.io/](http://akka.io/))

Akka is:
- Non blocking
- For Java and Scala
- Build on actor and messaging concept

Actor
- Response to a message it receives
- Can concurrently 
	  - Send messages to other actors, 
		- Create new actors
		- Have state

Concurrency through messaging
```
Source --Message-->
                  | 
           [Mailbox Queue]
           [   Message   ]
           [   Message   ]			
           [   Message   ]
           [   Message   ] -- Dequeue -->[Dispatcher] --Process--> [Actor]
                  |                       |       |                  |
                  <----- Dequeue Next -----       <------ Done -------	
```

- Message come into a mailbox (non blocking operation)
	- Message need to be immutable
- The dispatcher sends the message to actor
- Actor processes it (in thread)
	- It can't see other messages
	- Actor can process one message in one time (no synchronization)
- Actor finish processing
	- Dispatcher is informed about this fact
	- Dispatcher will send next message to actor

Actor system key components
- Dead Letter Office Actor
- System Guardian Actor
- User Guardian Actor
- Scheduler
- Event Stream
- Settings

**Exercise: implement Akka Hello World**

- Create Greeting class, implementation should:
	- Implement Serializable
	- Contain String who property (passed by constructors available by getter)

- Create GreetingActor class, implementation should:
	- Extends UntypedActor
	- Have log property:

			LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	- Override onReceive method with implementation:

			if (message instanceof Greeting) {
			  log.info("Hello " + ((Greeting) message).getWho());
			}

	- Have CREATOR static property with implementation:
	
			public static final Creator<Actor> CREATOR = new Creator<Actor>() {
			    @Override
			    public Actor create() throws Exception {
			      return new GreetingActor();
			    }
			};

- Create GreetingApp class and main method with:

	- Actor System instance:

			ActorSystem system = ActorSystem.create("GreetingSystem");
	
	- Greeter actor instance:

			ActorRef greeter = system.actorOf(Props.create(GreetingActor.CREATOR));

	- Tell something:
		
			greeter.tell(new Greeting("UAM"), ActorRef.noSender());


**Resources**
- [Documentation](http://doc.akka.io/docs/akka/2.3.8/java.html?_ga=1.64629551.1445290926.1419011872)
- [Tutorial](http://www.typesafe.com/activator/template/hello-akka?_ga=1.64013743.1445290926.1419011872)
