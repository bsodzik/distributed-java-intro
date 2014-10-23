package exercise2;

import common.Counter;
import java.util.concurrent.locks.ReentrantLock;

public class LockingCounter implements Counter {

    private long field=0;
    
    private final ReentrantLock lock = new ReentrantLock();
    
    @Override
    public void increment() {
        lock.lock();  // block until condition holds
        try {
            field++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getValue() {
        return field;
    }
}
