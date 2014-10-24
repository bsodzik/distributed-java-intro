package exercise1.equipment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Brushes {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private int available = 3;

    public void takeBrush() throws InterruptedException {
        lock.lock();
        try {
            while (available == 0) {
                condition.await();
            }
            if (available == 0) {
                throw new IllegalStateException("There are no more brushes!");
            }
            available -= 1;
        } finally {
            lock.unlock();
        }
    }

    public void returnBrush() {
        lock.lock();
        try {
            available += 1;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
