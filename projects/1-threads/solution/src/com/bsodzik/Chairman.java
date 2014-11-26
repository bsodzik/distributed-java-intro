package com.bsodzik;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Chairman implements Runnable {

	private final BlockingQueue<Item> auctionItems = new ArrayBlockingQueue<Item>(10);
	private final List<Recipient> auctionRecipients = new ArrayList<Recipient>(10);
	private final MarketManager marketManager;
	private volatile boolean isMarketOpen = true;

	public Chairman(MarketManager marketManager) {
		this.marketManager = marketManager;
	}

	public boolean registerNewItem(Item item) {
		return isMarketOpen && auctionItems.offer(item);
	}

	public boolean registerRecipientForCurrentAuction(Recipient recipient) {
		if (isMarketOpen) {
			synchronized (auctionRecipients) {
				if (isMarketOpen && auctionRecipients.size() < 10) {
					System.out.println("Adding recipient " + recipient);
					auctionRecipients.add(recipient);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);

				Item item = null;
				try {
					item = auctionItems.poll(5, TimeUnit.SECONDS);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

				if (item == null) {
					isMarketOpen = false;
					System.out.println("No auctions within 5 seconds. Closing the market!");
					marketManager.closeMarket();
					break;
				}

				synchronized (auctionRecipients) {
					if (auctionRecipients.isEmpty()) {
						System.out.println("There is no winner for item " + item);
					} else {
						int winnerIdx = ThreadLocalRandom.current().nextInt(auctionRecipients.size());
						Recipient winner = auctionRecipients.get(winnerIdx);
						System.out.println("Winner for auction " + item + " is " + winner);
						winner.notifyWinner(item);
						for (Recipient recipient : auctionRecipients) {
							synchronized (recipient) {
								recipient.notify();
							}
						}
						auctionRecipients.clear();
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Chairman says good bye");
	}
}
