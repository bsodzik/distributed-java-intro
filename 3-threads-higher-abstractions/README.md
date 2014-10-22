## Labs 3 - Threads - higher abstractions ##


### Goal ###
- Learn `Condition` class as alternative to `wait`/`notify`
- Understand blocking collections based on `BlockingQueue`
- Learn `Callable` and `Future` interfaces  
- Get familiar with Fork/Join framework

### Realization ###

**Exercise 1**

Using `java.util.concurrent.locks.Condition` (see [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/locks/Condition.html))

`Condition` class was introduced as a replacement for Object monitor methods `wait` and `notify`. Its purpose is to provide high level mechanism for suspending one thread execution until notified by different thread. `Condition` class is strongly connected with `Lock` interface. To obtain `Condition` instance method `newCondition` from `Lock` interface should be invoked. It is possible to create multiple conditions connected with one `Lock`.  

It can be said that `Lock` interface is a replacement for `synchronized` keyword and methods `await`, `signal`, `signalAll` from `Condition` are replacements for `wait`, `notify`, `notifyAll` from `Object`.

The problem to solve in this example is the same as in last exercise from previous lab.

1. Execute `main` method to observe that `IllegalStateException` indeed is thrown for some painters
2. Create `Condition` instances in both `Paints` and `Brushes` classes
  - Create and initialize field *lock* of type `ReentrantLock`
  - Create field *condition* of type `Condition` and initialize it using `newCondition` method from `Lock` interface
2. Secure `takePaint` and `takeBrush` methods
  - Instead of `synchronized` keyword use *lock* objects for securing methods
  - Add guarded block at the beginning of `try/finally` block. Suspend the thread if there are no paints/brushes available. Use `await` method from `Condition`
  - Remember to release lock at the end (in `finally` block)
3. Extend `returnPaint` and `returnBrush` methods
  - Secure both methods with *lock* objects
  - Once paint/brush is not needed any more, painter should inform other painters about it. Use `signal` method from `Condition`
  - Make sure other painters are signalled once the painter really returned his item


**Exercise 2**

Using `java.util.concurrent.BlockingQueue` (see [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/BlockingQueue.html))

`BlockingQueue` is based on a regular `Queue` interface with addition of two significant behaviours

  - when retrieving an element, `BlockingQueue` can suspend thread for as long as there is some element in the queue
  - when inserting an element, `BlockingQueue` can suspend thread for as long as there is some free space in the queue

Java provides several implementations of `BlockingQueue`. The most important one are

  - `ArrayBlockingQueue` has fixed capacity and is based on array
  - `LinkedBlockingQueue` can be optionally limited to fixed capacity and is based on linked nodes
  - `PriorityBlockingQueue` has unlimited capacity and can support different ordering than FIFO (first-in-first-out) using `Comparable` or `Comparator`

Vast number of problems from *producer-consumer* family can be solved with use of queue data structure. If queue is blocking then implementation of such problems can be greatly simplified. 


The problem to solve in this example is further extension of painters problem. Lets assume there is limited number of paints of different colour and limited number of brushes of different shape. Both paints and brushes are nicely arranged and each painter is kind enough to take first available paint/brush from arrangement and to return it to the end of arrangement. Once painter gets paint and brush he/she paints the drawing and then returns tools for other painters. 

1. Provide queues of paints/brushes in `Paints` and `Brushes`
  - In each class create a field of type `BlockingQueue` and fixed capacity. Which implementation is most suitable?
  - Fill paints queue with several different paint colours, e.g. *red*, *green* and *blue*
  - Fill brushes queue with several different brush shapes, e.g. *regular*, *triangular* and *spectacular*
  - Consider using `offer` method for inserting elements
2. Implement `takePaint`/`takeBrush` methods
  - Return first element from the queue
  - Consider using `take` method. Are there any other methods that could be used here?
3. Implement `returnPaint`/`returnBrush` methods
  - Insert passed element to the queue
  - Consider using `offer` method. Are there any other methods that could be used here?
4. Execute `main` method and observe output. Notice that different painters are using different combinations of paints and brushes


**Exercise 3**

Using `java.util.concurrent.Callable` (see [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/Callable.html)) and `java.util.concurrent.Future` (see [Javadoc](http://docs.oracle.com/javase/7/docs/api/index.html?java/util/concurrent/Future.html))

A `Callable<V>` interface represents a task that returns some value. Except from returning value it is similar to `Runnable`. `Callable<V>` provides single method `call` which returns value of type `V`.

A `Future<V>` represents the result of an asynchronous operation. `Future<V>` provides methods to verify whether operation has already finished or is still in progress. To get actual value of asynchronous operation (which is of type `V`) blocking method `get` must be invoked. `Future<V>` also provides mechanism for asynchronous operation cancellation.

`Callable` and `Future` are well integrated with `ExecutorService` interface. Instead of `execute` method known from previous labs, `submit` method can be used to invoke `Callable` instance and return `Future` object.

The goal of this task is to implement very simple WebCrawler that counts occurrences of given word. It should scan given web page and all web pages which are directly linked to it. To simplify this task several helper classes were prepared.

1. Get familiar with `common` package
  - `HtmlDocument` is an abstract class that represents HTML documents. It uses [jsoup](http://jsoup.org/) library for loading, parsing and traversing through web page. In order to implement this exercise you don't have to know internals of *jsoup* (but you are more than welcome to get familiar with that library at home).
  - `GazetaHtmlDocument` and `PudelekHtmlDocument` are exemplary concrete implementations of `HtmlDocument`. Because each web page has specific structure, each one requires a slightly different implementation. The main reason for that is to prevent from crawling external links and to search for words only in specific section of web page.
  - `StringUtils` class provides simple method for counting occurrences of given pattern within given text content
2. Prepare `WordCounter` class
 - Implement `Callable<Integer>` interface and provide `call` method
3. Code `call` method logic
 - Create new `HtmlDocument` based on `documentUrl`. Use `GazetaHtmlDocument` class
 - Get content from created document using `getContent` method. For better accuracy of your search results convert content to lower case
 - Return number of occurrences of `wordToCount` with help of `countOccurrences` method from `StringUtils` class
4. Implement `main` method. The general idea is to invoke separate instance of `WordCounter` for each link from root document, store `Future` object connected with each task in a list and in the end to count total number of word occurrences (follow `TODO` sections)
  - Create `ExecutorService` instance
  - Create a list that will store results of  of type `List<Future<Integer>>`
  - For each link create new `WordCounter`
  - Submit each `WordCounter` using method `submit` from `ExecutorService`. Add resulted `Future<Integer>` object to previously created list
  - Shut down executor
  - Iterate through list of `Future<Integer>` objects and invoke method `get` on each one. Method `get` returns number of words found on particular web page
  - Accumulate number of words from all web pages
5. Experiment with different implementations of `ExecutorService`. You can easily observe benefits of multi threading when you compare results with `newSingleThreadExecutor` (which is single threaded as name suggests)


**Exercise 4**

Fork/Join framework in action (see [docs](http://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html), [article](http://www.oracle.com/technetwork/articles/java/fork-join-422606.html))

The Fork/Join framework is a quaint implementation of `ExecutorService`. It was designed for tasks that can be broken into smaller pieces recursively. One family of such tasks are algorithms known as "divide and conquer" or more recently as "map and reduce".

Divide and conquer algorithm can be used for searching files with given content in a file system. We can define two types of tasks. One called `FileSearchTask` is searching through the file. The other one, `DirectoryScanTask`, is recursively listing all files and directories from current directory. For each file it starts new `FileSearchTask` and for each directory it starts new `DirectoryScanTask`. The search continues for as long as there are some files/directories in file system. Results of search are then propagated from bottom to top as in most recursive calls.

In Fork/Join framework tasks are defined as instances of `RecursiveAction` or `RecursiveTask<V>`. First are similar to `Runnable` and second are similar to `Callable<V>`. The very important thing about Fork/Join tasks is that each task can easily create different tasks (which will be executed asynchronously). Two most important methods of Fork/Join tasks are

  - `fork` - arranges given task to be executed asynchronously
  - `join` - returns the result of the computation once it is completed

One of the core examples of Fork/Join framework in use is `parallelSort` introduced in Java 8.

The goal of this task is to improve WebCrawler from previous exercise to work recursively. It should not only count words for given document but also detect all links available on the document and crawl them.

There are two types of Fork/Join tasks in this example. First, `WordCountingTask`, is responsible only for counting words in given document. Second, `WebCrawlingTask`, is responsible for creating `WordCountingTask` for the document as well as for detecting links on the document and creating `WebCrawlingTask` for each link. `WebCrawlingTask` should be implemented in such way that it breaks the processing once particular number of web pages have been crawled.

1. Get familiar with `WordCountingTask`
  - Notice how it extends `RecursiveTask<Integer>`
  - Observe overridden `compute` method
2. Implement `compute` method in `WebCrawlingTask` (follow `TODO` sections)
  - Create list of forks of type `List<RecursiveTask<Integer>>`
  - Create new `WordCountingTask` for given document and add it to list of forks
  - Invoke method `fork` on created `WordCountingTask`
  - Create new `WebCrawlingTask` for each not visited link and add it to list of forks
  - Invoke method `fork` on each created `WebCrawlingTask`
3. Once all Fork/Join tasks have been created, start collecting their results using `join` method
  - Iterate over list of forks and for each invoke `join` method
  - Add value returned from `join` method to total result
4. Implement `main` method (follow `TODO` sections)
  - Create new `ForkJoinPool` object
  - Create new `WebCrawlingTask` for root URL and given word to count
  - Invoke `invoke` method on `ForkJoinPool` object passing `WebCrawlingTask`
  - Result of `invoke` method is the number of found words