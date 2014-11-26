package com.bsodzik;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final int id;

    public Item() {
        id = counter.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Item " + id;
    }
}
