package com.uam.akka.exercise3.actor;

import akka.actor.UntypedActor;

public class Master extends UntypedActor {

	private final int numberOfWorkers;

	public Master(int numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}

	@Override
	public void onReceive(Object o) throws Exception {
	}
}
