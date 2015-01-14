package com.uam.akka.exercise1.message;

public class Question {

	private final String text;

	public Question(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}
}
