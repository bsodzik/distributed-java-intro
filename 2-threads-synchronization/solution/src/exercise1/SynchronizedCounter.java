package exercise1;

import common.Counter;

public class SynchronizedCounter implements Counter {

    private long value = 0;

    @Override
    public synchronized void increment() {
        value += 1;
    }

    @Override
    public long getValue() {
        return value;
    }
}
