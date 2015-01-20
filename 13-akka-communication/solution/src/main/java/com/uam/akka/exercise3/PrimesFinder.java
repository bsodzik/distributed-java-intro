package com.uam.akka.exercise3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.uam.akka.exercise3.actor.Master;
import com.uam.akka.exercise3.message.Input;
import com.uam.akka.exercise3.util.Interval;

public class PrimesFinder {

	public static void main(String[] args) {

		int numberOfWorkers = 8;
		Interval interval = new Interval(1000000L, 9999999L);

		ActorSystem system = ActorSystem.create("system");
		ActorRef master = system.actorOf(Props.create(Master.class, numberOfWorkers), "master");

		master.tell(new Input(interval), ActorRef.noSender());
	}
}
