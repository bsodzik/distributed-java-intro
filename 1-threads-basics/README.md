## Labs 1 - Threads - basics ##


### Goal ###
- Understand general concepts behind threads
- Learn different ways of creating threads in Java
- Detecting when thread is finished

### Realization ###

#### General concepts behind threads ####
Thread in computing science is a basic unit of execution managed by a scheduler. Scheduler is responsible for assigning processor to every thread using such concepts as time slicing and context switching. Scheduler is part of operating system.

Threads can be seen as lightweight processes. Each process consists of at least single thread. Threads can share resources (memory), processes can't.

**Question 1** Can introduction of multi-threading into existing software **slow down** it's execution?

**Question 2** Can introduction of multi-threading into existing software **speed up** it's execution on single core processor?


#### Java Thread class ####

Get familiar with [java.lang.Thread](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html) documentation.

Hereinafter `Thread` refers to java.lang.Thread class whereas lower case thread refers to the instance of `Thread`.  

**Exercice 1**

Display id and name of main thread in Java application.

*Hint*: Get familiar with static method `Thread.currentThread`.

**Exercise 2**

How to use `Thread` class.

1. Define a class `MyThread`
  - Make it extend `Thread`.
  - Add single argument constructor that takes thread name and sets it on super object.
  - Override `run` method. Implement a for loop that iterates 10 times and on each iteration prints thread name followed by iteration number to `System.out`.
2. Implement `main` method
  - Create an array of type `Thread` and length 4.
  - Fill array with instances of `MyThread` class. Names of created threads should follow pattern `Thread-n` where `n` refers to the index in threads array.
  - Iterate over threads array and for each thread execute `run` method. Observe output. Do you see something fishy?
  - Replace invocation of `run` with `start`. Observe output.
3. Modify `run` method in `MyThread` class by adding `Thread.sleep(10)` invocation after each print to `System.out`. You have to surround `sleep` invocation in `try/catch` block. Observe output.

**Exercise 3**

How to use `Runnable` interface?

1. Define a class `MyRunnable`
  - Make it implement `Runnable` interface
  - Implement `run` method in similar way as in previous example (omit `sleep` invocation). How can you get thread name?
2. Implement `main` method
  - Create an array of type `Thread` and length 4.
  - Fill array with instances of `Thread` class. Use two arguments constructor taking `Runnable` instance and thread name (for name use the same pattern as in previous exercise).
  - Iterate over threads array and for each thread execute `run` method. Observe output. Do you see something fishy?
  - Replace invocation of `run` with `start`. Observe output.
3. Are you able to answer which way of creating threads is recommended (extending `Thread` or implementing `Runnable`)?

**Exercise 3 - extension**

How to determine whether all threads finished their job?

1. Naive approach
  - Print to `System.out` something like `"FINISHED"` directly after loop which starts threads. Observe output.
  - Precede print with `Thread.sleep(5000)` (consider using `TimeUnit.SECONDS.sleep(5)`). Observe output.
2. Continuous polling
  - Implement infinitive loop that waits several milliseconds (`Thread.sleep`) and then checks whether any thread is alive (`thread.isAlive`). If none threads are alive print `"FINISHED"` and break the loop, otherwise repeat step.
  - Replace the order of waiting and checking logic (now first check if none threads are alive and then wait). Observe output.
3. Scheduled invocation
  - Follow each invocation of `start` method with invocation of `join` method on the same thread. Directly after the loop print `"FINISHED"`. Observe output. Do you see something fishy?
  - Directly after the loop that starts threads implement new loop that will invoke `join` method on each thread. Directly after this loop print `"FINISHED"`. Observe output. Can you explain the difference?

**Exercise 4**

How to use `java.util.concurrent.ExecutorService`?

1. Reuse class `MyRunnable` from previous exercise.
2. Implement `main` method
  - Create an instance of `ExecutorService` with fixed number of threads (equals to 4). Use appropriate factory method from `Executors` class.
  - Inform executor service to execute command defined by `MyRunnable` exactly 4 times. Use loop for that purpose. Observe output.
3. Use `shutdown` method to inform executor service that no more commands will be added.  Observe output.

**Exercise 4 - extension**

How to determine whether executor service finished all commands?

1. Print `"FINISHED"` directly after `shutdown` method. Observe output.
2. Add `awaitTermination` method with reasonable time-out directly after `shutdown` method. Print `"FINISHED"` afterwards. Observe output. To which finishing strategy from exercise 3 would you compare this strategy?

### Resources ###

- [http://docs.oracle.com/javase/tutorial/essential/concurrency/index.html](http://docs.oracle.com/javase/tutorial/essential/concurrency/index.html) - tutorial from Oracle
- [Thinking in Java](http://www.amazon.co.uk/Thinking-Java-Bruce-Eckel/dp/0131872486/ref=sr_1_1?ie=UTF8&qid=1412793786&sr=8-1&keywords=thinking+in+java) - chapter about Java concurrency is pretty good
- [Java Concurrency in Practice](http://www.amazon.co.uk/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601/ref=sr_1_1?s=books&ie=UTF8&qid=1412793849&sr=1-1&keywords=java+concurrency+in+practice) - the most precise compendium about Java concurrency 