package com.uam.akka.exercise3.message;

import com.uam.akka.exercise3.util.Interval;

public final class Input {

	private final Interval interval;

	public Input(Interval interval) {
		this.interval = interval;
	}

	public Interval getInterval() {
		return interval;
	}
}
