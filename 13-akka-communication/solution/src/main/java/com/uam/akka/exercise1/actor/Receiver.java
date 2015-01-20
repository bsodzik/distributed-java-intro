package com.uam.akka.exercise1.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.uam.akka.exercise1.message.Answer;
import com.uam.akka.exercise1.message.Question;

public class Receiver extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public void onReceive(Object o) throws Exception {
		if (o instanceof Question) {
			log.info("Received question: {}", o);
			getSender().tell(new Answer("Excellent!"), getSelf());
		}
	}
}
