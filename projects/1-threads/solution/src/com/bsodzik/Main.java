package com.bsodzik;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		MarketManager marketManager = new MarketManager();
		Chairman chairman = new Chairman(marketManager);

		for (int i = 0; i < 10; ++i) {
			marketManager.registerDonor(new Donor("Donor " + i, chairman));
		}
		for (int i = 0; i < 50; ++i) {
			marketManager.registerRecipient(new Recipient("Recipient " + i, chairman));
		}

		marketManager.openMarket();
		ExecutorService e = Executors.newSingleThreadExecutor();
		e.execute(chairman);
		e.shutdown();
	}
}
