package exercise1;

import common.CountingRunner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        CountingRunner runner = new CountingRunner();
        runner.execute(counter);
    }
}
