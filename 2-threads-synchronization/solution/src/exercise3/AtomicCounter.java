package exercise3;

import common.Counter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {

    private final AtomicLong value = new AtomicLong();

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public long getValue() {
        return value.longValue();
    }
}
