package exercise3;

import common.CountingRunner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        CountingRunner runner = new CountingRunner();
        runner.execute(counter);
    }
}
