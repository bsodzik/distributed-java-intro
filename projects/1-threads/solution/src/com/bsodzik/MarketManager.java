package com.bsodzik;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarketManager {

	private final List<Donor> donors = new LinkedList<Donor>();
	private final List<Recipient> recipients = new LinkedList<Recipient>();

	public void registerDonor(Donor donor) {
		donors.add(donor);
	}

	public void registerRecipient(Recipient recipient) {
		recipients.add(recipient);
	}

	public void openMarket() {
		letUsersIn(donors);
		letUsersIn(recipients);
	}

	public void closeMarket() {
		letUsersOut(recipients);
		letUsersOut(donors);
	}

	private void letUsersIn(List<? extends Runnable> users) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (Runnable user : users) {
			executorService.execute(user);
		}
		executorService.shutdown();
	}

	private void letUsersOut(List<? extends Closeable> users) {
		for (Closeable user : users) {
			synchronized (user) {
				try {
					user.close();
				} catch (IOException e) {
				} finally {
					user.notify();
				}
			}
		}
	}
}
