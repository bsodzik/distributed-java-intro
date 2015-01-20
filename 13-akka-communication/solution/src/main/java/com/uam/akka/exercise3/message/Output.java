package com.uam.akka.exercise3.message;

import java.util.Collections;
import java.util.Set;

public class Output {

	private final Set<Long> primes;

	public Output(Set<Long> primes) {
		this.primes = Collections.unmodifiableSet(primes);
	}

	public Set<Long> getPrimes() {
		return primes;
	}

	@Override
	public String toString() {
		return "Primes: " + primes;
	}
}
