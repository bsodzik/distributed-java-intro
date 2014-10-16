## Labs 2 - Threads - synchronization ##


### Goal ###
- Understand main risks of concurrent access
- Learn different ways of synchronization in Java
- Understand `volatile` keyword
- Learn how to detect and avoid *deadlocks*
- Get familiar with *guarded blocks*

### Realization ###

#### Main problems in concurrent access ####

The biggest problem in concurrent applications is caused by the fact that multiple threads can access the same *resource* at the same time. One example of such resource is computer memory, more precisely a variable stored in the memory.

Let's imagine that two objects want to increment the same variable. First, each of them have to read the variable's value from the memory, then increase that value by one and finally write new value to the memory. In sequential application it is straightforward because objects increment the value one after the other.

However, in concurrent application it is possible that two separate threads would read the variable's value from the memory before any of them actually writes incremented value to the memory. 

Let's visualize this situation on a diagram. Assume that objects are called `A` and `B`.

Sequential code
```
A [read: 5] --> [inc: +1] --> [write: 6]
B -------------------------------------> [read: 6] --> [inc: +1] --> [write: 7]
```

Concurrent code:
```
A [read: 5] ----------------------> [inc: +1] ---------> [write: 6]
B --------> [read: 5] --> [inc: +1] --------> [write: 6]
```
Presented situation is called `race condition`. The most important aspect of concurrent programming is the ability to detect sections of code with potential race conditions and secure them. Sections where race conditions happen are called `critical sections`.


#### Securing critical sections in Java ####

Java provides several mechanisms for protecting critical sections. The most common one is `synchronized` keyword. It can be used on method level as well as code block level.

When method marked as `synchronized` is invoked, JVM changes the state of internal *monitor* of such object to locked. When the monitor is locked, then no other thread is able to execute `synchronized` methods on such object. In such situation other threads are blocked and queued by task scheduler. When thread which acquired lock on the monitor finally ends its execution, then lock on the monitor is released and task scheduler selects single thread from the queue and allows it to execute `synchronized` operation. Remaining threads wait in queue for their turn.

Besides `synchronized` keyword which is part of Java language there are also high level locking mechanisms. One of them is `ReentrantLock` class. It provides all the functionalities of `synchronized` keyword and extends them. `ReentrantLock` allows clients to check whether given object is locked and also to wait in queue only for specific amount of time. `ReentrantLock` is much more flexible than `synchronized`.

Another mechanism for securing critical section is based on the concept of *atomic* operations. Atomic operation can not be divided into smaller operations therefore race condition between two atomic operations does not exist. Java provides atomic implementation for several classes such as `Integer`, `Long` or `Boolean`.



**Exercise 1**

Using language level synchronization

1. Provide implementation for class `SynchronizedCounter`
  - create private field of type `long` and assign it to 0
  - method `increment` should increase the value of created field by 1
  - method `getValue` should return current value of created field
2. Secure method `increment` using `synchronized` keyword on method level
3. Compare output of `main` method with and without `synchronized` keyword


**Exercise 2**

Using `java.util.concurrent.locks.ReentrantLock` (see [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/locks/ReentrantLock.html))

1. Provide implementation for class `LockingCounter`
  - create private field of type `long` and assign it to 0
  - method `increment` should increase the value of created field by 1
  - method `getValue` should return current value of created field
2. Secure method `increment` using `ReentrantLock`
  - create private final field of type `ReentrantLock` and initialize it with new instance of `ReentrantLock`
  - surround code in `increment` method with `lock` and `unlock` methods from `ReentrantLock`. Use `try/finally` block as shown in [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/locks/ReentrantLock.html)
3. Compare output of `main` method with and without locking object


**Exercise 3**

Using `java.util.concurrent.atomic.AtomicLong` (see [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/atomic/AtomicLong.html))

1. Provide implementation for class `AtomicCounter`
  - create private field of type `AtomicLong` and assign new instance of `AtomicLong` to it
  - method `increment` should increase the value of created field using `incrementAndGet` method
  - method `getValue` should return current value of created field using `longValue` method
2. You may want to compare execution times of all implemented strategies. For that purpose use `System.currentTimeMillis` method and implement time measurement in `common.CountingRunner` class. Remember to stop the timer no sooner than all threads had finished their jobs. 


**Exercise 4**

Securing invocation of several methods using `synchronized` keyword

1. Get familiar with `EvenCheckingTask` class. The general idea behind this class is to increment counter twice and check if counter value is even.
2. Execute `main` method with each implementation of counter (`SynchronizedCounter`, `LockingCounter` and `AtomicCounter`). Observe output.
3. Identify critical section in `EvenCheckingTask` class. Secure it using `synchronized` keyword. Remember that critical section should be as small as possible. For synchronization use object with as small range as necessary.
4. What other mechanism could be used to synchronize block of code? Try to use `ReentrantLock`. Make sure you don't create separate `ReentrantLock` instance for each thread.


**Exercise 5**

Getting familiar with `volatile` keyword

The main purpose of `volatile` keyword is to indicate that a variable will be modified by different threads. The implication of this is that all reads and writes to such variable always go directly to the memory. The CPU cache is never used for volatile variables.

In other words, if one thread changes volatile variable, all other threads are aware of such change. Without `volatile` keyword that condition would not be satisfied.

Have in mind that only atomic operations such as variable read and write can be executed safely on `volatile` variable. Operations such as increment (`++`) or decrement (`--`) are not atomic! Therefore `volatile` is not sufficient to secure counter implemented in previous exercises.

1. Execute `main` method and observe output. Why the program is not ending?
2. Change `VolatileTask` to work correctly in multithreading environment.
  - Mark `isRunning` variable as `volatile`
  - Execute `main` method and observe output. 


**Exercise 6**

Deadlock detection

This exercise illustrates a simple example of deadlock problem. Let's imagine a situation where two painters want to paint at the same time. Unfortunately, there is only one paint and one brush. What's more, first painter takes the paint for himself and the second one takes the brush. Because they are not willing to give their equipment back, neither one can actually paint.

Take a look at implemented code and find the place causing deadlock. Consider what are the options to avoid such situation. Try to fix the behaviour of painters so that both of them can actually paint their drawings.


**Exercise 7 - optional**

Get familiar with `wait` and `notify` methods

Methods `wait` and `notify` (as well as `notifyAll`) are implemented in `Object` class, what means they are available for each object. The idea behind them is to provide easy and efficient way to implement idiom called *guarded block*. Guarded blocks are used to coordinate actions executed by different threads. The simplest form of guarded block is a `while` loop checking some condition in each iteration. Once the condition is satisfied loop breaks and code beyond guarded block can be executed. Of course such idle polling wastes processor cycles. To make it efficient, thread should be suspended and awake once some other thread changed condition. For more details check Oracle [docs](http://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html).

The problem to solve in this exercise is extended version of painters problem from previous exercise. This time there are several paints and brushes and even more painters. Painters always take paint first and then they take brush. If all paints or brushes are taken then other painters are not able to take those items (and application throws `IllegalStateException`). Your task is to make sure that `IllegalStateException` will never be thrown.

1. Execute `main` method to observe that `IllegalStateException` indeed is thrown for some painters
2. Secure `takePaint` and `takeBrush` methods
  - Add guarded block at the beginning of each method. Suspend the thread if there are no paints/brushes available
  - Do not dare to remove code that throws exception and pretend that you solved the problem!
3. Extend `returnPaint` and `returnBrush` methods
  - Once paint/brush is not needed any more, painter should inform other painters about it
  - Make sure other painters are notified once the painter really returned his item
