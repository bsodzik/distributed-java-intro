package com.uam.akka.exercise1.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.uam.akka.exercise1.message.Answer;
import com.uam.akka.exercise1.message.Question;
import com.uam.akka.exercise1.message.Start;

public class Sender extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private final ActorRef receiver;

	public Sender(ActorRef receiver) {
		this.receiver = receiver;
	}

	@Override
	public void onReceive(Object o) throws Exception {
		if (o instanceof Start) {
			receiver.tell(new Question("How are you doing?"), getSelf());
		} else if (o instanceof Answer) {
			log.info("Receiver sent answer: " + o);
			getContext().system().shutdown();
		}
	}
}
