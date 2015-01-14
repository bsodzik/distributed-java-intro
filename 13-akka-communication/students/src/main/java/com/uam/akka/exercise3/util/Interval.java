package com.uam.akka.exercise3.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Interval {

	private final long from;
	private final long to;

	public Interval(long from, long to) {
		if (from <= 0 || from > to) {
			throw new IllegalArgumentException();
		}
		this.from = from;
		this.to = to;
	}

	public long from() {
		return from;
	}

	public long to() {
		return to;
	}

	public List<Interval> divide(int n) {
		final List<Interval> result = new ArrayList<Interval>(n);

		long distance = to - from + 1;
		if (n >= distance) {
			for (int i = 0; i < distance; ++i) {
				result.add(new Interval(from + i, from + i));
			}
		} else {
			long size = distance / n;
			long extending = distance % n;

			long pointer = from;
			for (int i = 0; i < n; ++i) {
				long newPointer = pointer + size + (extending-- > 0 ? 1 : 0);
				result.add(new Interval(pointer, newPointer - 1));
				pointer = newPointer;
			}
		}
		return Collections.unmodifiableList(result);
	}

	@Override
	public String toString() {
		return "<" + from + ", " + to + ">";
	}
}
