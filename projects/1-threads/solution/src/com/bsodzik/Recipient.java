package com.bsodzik;

import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Recipient implements Runnable, Closeable {

    private final String name;
    private final Chairman chairman;
    private final List<Item> items = new LinkedList<Item>();
    private volatile boolean isMarketOpen;
    private volatile boolean isAuctionWon;

    public Recipient(String name, Chairman chairman) {
        this.name = name;
        this.chairman = chairman;
        this.isMarketOpen = true;
    }

    @Override
    public void run() {
        while (isMarketOpen) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
                isAuctionWon = false;

                if (isMarketOpen && chairman.registerRecipientForCurrentAuction(this)) {
                    synchronized (this) {
                        wait();
                        if (isMarketOpen && isAuctionWon) {
                            Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 15000));
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " says good bye leaving with items " + items);
    }

    public void notifyWinner(Item item) {
        System.out.println(name + " won " + item);
        items.add(item);
        isAuctionWon = true;
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
