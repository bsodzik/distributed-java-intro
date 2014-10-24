package exercise2;

import common.CountingRunner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LockingCounter counter = new LockingCounter();
        CountingRunner runner = new CountingRunner();
        runner.execute(counter);
    }
}
