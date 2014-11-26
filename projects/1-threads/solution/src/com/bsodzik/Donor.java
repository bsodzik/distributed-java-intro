package com.bsodzik;

import java.io.Closeable;
import java.util.concurrent.ThreadLocalRandom;

public class Donor implements Runnable, Closeable {

    private final String name;
    private final Chairman chairman;
    private volatile boolean isMarketOpen;

    public Donor(String name, Chairman chairman) {
        this.name = name;
        this.chairman = chairman;
        this.isMarketOpen = true;
    }

    @Override
    public void run() {
        while (isMarketOpen) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 30000));
                Item newItem = new Item();

                while (isMarketOpen && !chairman.registerNewItem(newItem)) {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " says good bye");
    }

    @Override
    public void close() {
        isMarketOpen = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
