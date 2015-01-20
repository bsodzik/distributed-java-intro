package com.uam.akka.exercise1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.uam.akka.exercise1.actor.Receiver;
import com.uam.akka.exercise1.actor.Sender;
import com.uam.akka.exercise1.message.Start;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");

		ActorRef receiver = system.actorOf(Props.create(Receiver.class), "receiver");
		ActorRef sender = system.actorOf(Props.create(Sender.class, receiver), "sender");

		sender.tell(new Start(), ActorRef.noSender());
	}
}
