package common;

public class CountingTask implements Runnable {

    private final Counter counter;
    private final int numberOfIterations;

    public CountingTask(Counter counter, int numberOfIterations) {
        this.counter = counter;
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; ++i) {
            counter.increment();
        }
    }
}
