package com.uam.akka.exercise3.actor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.uam.akka.exercise3.message.Input;
import com.uam.akka.exercise3.message.Output;
import com.uam.akka.exercise3.util.Interval;

public class Master extends UntypedActor {

	private final int numberOfWorkers;
	private final Set<Long> primes;
	private int numberOfResponses = 0;
	private long starTime;

	public Master(int numberOfWorkers) {
		this.primes = new HashSet<Long>();
		this.numberOfWorkers = numberOfWorkers;
		this.starTime = System.currentTimeMillis();

		for (int i = 0; i < numberOfWorkers; i++) {
			context().actorOf(Props.create(Worker.class), "worker-" + i);
		}
	}

	@Override
	public void onReceive(Object o) throws Exception {

		if (o instanceof Input) {
			List<Interval> intervals = ((Input) o).getInterval().divide(numberOfWorkers);
			Iterator<Interval> iterator = intervals.iterator();

			for (ActorRef worker : getContext().getChildren()) {
				worker.tell(new Input(iterator.next()), getSelf());
			}
		} else if (o instanceof Output) {
			primes.addAll(((Output) o).getPrimes());

			if (++numberOfResponses == numberOfWorkers) {
				System.out.printf("There is %d prime numbers. Time taken: %d ms",
						primes.size(), System.currentTimeMillis() - starTime);
				getContext().system().shutdown();
			}
		}
	}
}
