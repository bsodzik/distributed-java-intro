package exercise2;

import common.Counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingCounter implements Counter {

    private final Lock lock = new ReentrantLock();
    private long value = 0;

    @Override
    public void increment() {
        lock.lock();
        try {
            value += 1;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getValue() {
        return value;
    }
}
