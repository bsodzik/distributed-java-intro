package com.uam.akka.exercise2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.uam.akka.exercise2.actor.Sender;
import com.uam.akka.exercise2.message.Start;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");

		ActorRef sender = system.actorOf(Props.create(Sender.class), "sender");

		sender.tell(new Start(), ActorRef.noSender());
	}
}
