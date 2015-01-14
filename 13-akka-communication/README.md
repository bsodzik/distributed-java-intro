## Labs 13 - Akka - communication ##


### Goal ###

- Learn different ways of creating actors in Akka
- Learn how to exchange messages between actors
- Get familiar with common *master/workers* pattern

### Realization ###

#### Creating actors in Akka ####

There are two different ways of creating actors in Akka. 

- Actors are created by `ActorSystem`. If one actor requires reference to another actor, the reference is passed as a constructor argument.
- Actors are created by another actor. Created actors are children of the actor that created them.

The first approach, where `ActorSystem` is used to create actors, is presented below

	ActorSystem system = ActorSystem.create("systemName");
	ActorRef someActor = system.actorOf(Props.create(SomeActor.class), "actorName");

Instantiation of `SomeActor` class is performed by `Props.create` method. Example presented above assumes that `SomeActor` class has no-arguments constructor. However, `Props.create` method can also instantiate actors which require arguments passed to constructor.

Let's assume there is another actor defined by `AnotherActor` class. It has one constructor with single argument of type `String`. Following code presents instantiation of `AnotherActor`

	ActorRef anotherActor = system.actorOf(Props.create(AnotherActor.class, "ConstructorArgument"), "anotherActorName");

It is possible to pass any object as a constructor argument, e.g. a reference to actor

	ActorRef someActor = system.actorOf(Props.create(SomeActor.class), "actorName");
	ActorRef anotherActor = system.actorOf(Props.create(AnotherActor.class, someActor), "anotherActorName");

`AnotherActor` class can store reference to `SomeActor` in a field and then send messages to this actor.

The biggest disadvantage of presented approach is that all actors are created in a single place using `ActorSystem`. In many complex scenarios such approach is not convenient.

Second approach allows actors to create child actors directly. Creation of child actors should be implemented in constructor of parent actor

	public SomeActor() {
		getContext().actorOf(Props.create(AnotherActor.class), "anotherActorName");
	}

In example presented above `SomeActor` became a parent of `AnotherActor`. In order to obtain reference to children following code fragment should be used

	Iterable<ActorRef> children = getContext().children();

Because `SomeActor` has exactly one child, the reference to it can be obtained in the following way

	 ActorRef anotherActor = children.iterator().next();

#### Communication between actors ####

In order to send a message to particular actor method `tell` should be executed on the `ActorRef` object representing that actor. Method `tell` takes two parameters. The first one is the message to be sent and can be of any type. Second parameter is of type `ActorRef`. Its purpose is to provide information of who is the sender of the message.

When sending messages directly from actor system no actor can be provided as a sender. Instead, construction `ActorRef.noSender()` should be used

	someActor.tell("Message", ActorRef.noSender());

However, when sending a message from one actor to another, the information about sender can be included. There are two important methods defined in `ActorRef` class

- `getSelf` - returns a reference to current actor. Method `getSelf` has to be used instead of `this` because `this` variable points to actor object (of type `UntypedActor`) rather than to actor reference (of type `ActorRef`).
- `getSender` - returns a reference to the actor who sent received message

When sending a message to *someActor* from another actor following code can be used

	someActor.tell("How are you?", getSelf());

Now *someActor* can determine sender of that message and reply directly

	getSender().tell("Excellent!", getSelf());


**Exercise 1**

The goal of this exercise is to exchange question and answer between two actors.

There are two actors - *sender* and *receiver*. The communication is initiated directly from *main* method be sending `Start` message to sender. When sender receives `Start` message it sends a `Question` message to receiver. Receiver receives `Question` from sender and replies to it with an `Answer` message. When sender receives `Answer` message, it prints its content to console and shuts down whole system.

Classes representing messages are already implemented. The remaining steps are

1. `Receiver` class
  - Receiver processes only messages of type `Question`.
  - Received question is printed to console using `info` method from provided `LoggingAdapter` *log* object.
  - Answer to the question is created using `Answer` class.
  - `getSender` method should be used to obtain reference to the sender.
  - `tell` method on the sender reference should be used to send answer.
2. `Sender` class
  - Sender contains one-argument constructor which takes `ActorRef` object. Value passed to the constructor should be saved in `receiver` field.
  - Sender processes messages of type `Start` and `Answer`.
  - When sender receives message of type `Start` it sends message of type `Question` to receiver. Instance of `Question` message should be created, then `tell` method on `receiver` field should be used to send that message. A reference to itself obtained from `getSelf` method should be passed as a second argument of `tell` method.
  - When sender receives message of type `Answer` it should print the text of received answer and then shut down the system. To shut down the system following code can be used `getContext().system().shutdown()`.
3. `Main` class
  - `Receiver` actor should be instantiated and named `"receiver"`.
  - `Sender` actor should be instantiated and named `"sender"`. Receiver object should be passed to the sender using method `Props.create` with multiple arguments.
  - Message `Start` should be sent to sender using `tell` method. As a second parameter provide `ActorRef.noSender()`.


**Exercise 2**

This exercise is equivalent to the previous one. The only difference is the way of creating *receiver* actor. In this exercise *receiver* should be created directly from *sender* actor.

1. `Receiver` class
  - Should be copied from previous exercise.
2. `Sender` class
  - Sender contains no-arguments constructor in which child actor of type `Receiver` is instantiated. Method `actorOf` defined in `ActorContext` object (context can be obtained using `getContext` method) should be used to instantiate child actor *receiver*. 
  - Sender processes messages of type `Start` and `Answer`.
  - When sender receives message of type `Start` it sends message of type `Question` to receiver. Instance of `Question` message should be created. To get reference to receiver actor methods chain `getContext().children().iterator().next()` should be invoked. Having reference to receiver, question should be sent using `tell` method. A reference to itself obtained from `getSelf` method should be passed as a second argument of `tell` method.
  - When sender receives message of type `Answer` it should print the text of received answer and then shut down the system in the same way as in previous exercise.
3. `Main` class
  - `Sender` actor should be instantiated and named `"sender"`.
  - Message `Start` should be sent to sender using `tell` method. As a second parameter provide `ActorRef.noSender()`.


**Exercise 3**

The purpose of this exercise is to demonstrate how Akka can be used to distribute large set of data among multiple actors to perform computation simultaneously.

The problem to solve is finding prime numbers in large interval of numbers. Architecture assumes one actor called *master* and multiple actors called *workers*. Master is responsible for creating workers, distributing data among them and finally collecting partial results from each worker and merging them. Each worker receives a smaller interval and is responsible for searching all prime numbers in that interval. Once the interval is analysed, worker sends results to master. There are only two types of messages - *input* and *output*.    

To simplify the exercise several components are already implemented

- `PrimeChecker` class contains logic for checking whether a number is prime number.
- `Interval` class represents set of numbers bounded inclusively from both sides. `Interval` contains method `divide` which can be very useful when dividing large set of data into smaller chunks for each worker.
- `Input` message contains interval of numbers to process.
- `Output` message contains set of prime numbers found in interval.

To finish this exercise implement following steps

1. `Worker` class
  - Worker receives only messages of type `Input`.
  - Worker iterates through all numbers from given interval and if the number is a prime number worker stores it in a variable of type `Set<Long>`.
  - Once interval is analysed, worker sends `Output` message with found prime numbers back to the actor who sent `Input` message (back to master).
2. `Master` class
  - Master first instantiates workers. The amount of workers is given in constructor's `numberOfWorkers` argument. To create actors directly from `Master` use technique known from previous exercise.
  - Master receives two types of messages - `Input` (from actor system) and `Output` (from workers).
  - For `Input` message master divides large interval into smaller intervals depending on the number of workers. Every worker should receive exactly one interval wrapped into `Input` message.
  - For `Output` message master collects received prime numbers and stores them in a field variable of type `Set<Long>`. When messages from all workers reach the master, the number of found prime numbers should be printed to console and system should be shut down. To determine whether all workers have sent output messages a common integer counter should be used.
3. `PrimesFinder` class
  - Primes finder creates `ActorSystem` and one actor of type `Master`. Variable `numberOfWorkers` should be passed to master actor.
  - Primes finder sends `Input` message with interval to master.
4. Measure processing time depending on number of workers. Compare application performance for 1, 2, 4, 10, 100, 1000, 10000, 100000 of workers. Draw your own conclusions.
 