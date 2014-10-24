package common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountingRunner {

    public static final int numberOfThreads = 8;
    public static final int numberOfIterations = 1000000;

    public void execute(Counter counter) throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < numberOfThreads; ++i) {
            executors.execute(new CountingTask(counter, numberOfIterations));
        }
        executors.shutdown();
        executors.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("Actual: " + counter.getValue() + ", Expected: " + (numberOfThreads * numberOfIterations));
    }
}
