package com.uam.akka.exercise2.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.uam.akka.exercise2.message.Answer;
import com.uam.akka.exercise2.message.Question;
import com.uam.akka.exercise2.message.Start;

public class Sender extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public Sender() {
		getContext().actorOf(Props.create(Receiver.class), "receiver");
	}

	@Override
	public void onReceive(Object o) throws Exception {
		if (o instanceof Start) {
			ActorRef receiver = getContext().children().iterator().next();
			receiver.tell(new Question("How are you doing?"), getSelf());
		} else if (o instanceof Answer) {
			log.info("Receiver sent answer: " + o);
			getContext().system().shutdown();
		}
	}
}
