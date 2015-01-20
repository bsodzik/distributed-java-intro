package com.uam.akka.exercise3.actor;

import java.util.HashSet;
import java.util.Set;

import akka.actor.UntypedActor;
import com.uam.akka.exercise3.message.Input;
import com.uam.akka.exercise3.message.Output;
import com.uam.akka.exercise3.util.Interval;
import com.uam.akka.exercise3.util.PrimeChecker;

public class Worker extends UntypedActor {

	private final Set<Long> primes = new HashSet<Long>();

	@Override
	public void onReceive(Object o) throws Exception {

		if (o instanceof Input) {
			Interval interval = ((Input) o).getInterval();

			for (long number = interval.from(); number <= interval.to(); ++number) {
				if (PrimeChecker.isPrime(number)) {
					primes.add(number);
				}
			}
			getSender().tell(new Output(primes), getSelf());
		}
	}
}
