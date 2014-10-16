package exercise3;

import common.Counter;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {

    private AtomicLong field = new AtomicLong();
    
    @Override
    public void increment() {
        field.incrementAndGet();
    }

    @Override
    public long getValue() {
        return field.longValue();
    }
}
