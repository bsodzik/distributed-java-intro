package exercise1;

import common.Counter;

public class SynchronizedCounter implements Counter {

    private long field=0;
    
    @Override
    public synchronized void increment() {
        field++;
    }

    @Override
    public long getValue() {
        return field;
    }
}
