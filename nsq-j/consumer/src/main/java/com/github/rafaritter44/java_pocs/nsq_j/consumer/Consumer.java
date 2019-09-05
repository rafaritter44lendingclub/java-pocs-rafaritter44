package com.github.rafaritter44.java_pocs.nsq_j.consumer;

import com.sproutsocial.nsq.Subscriber;

public class Consumer {
	
	public static void main(String[] args) {
		Subscriber subscriber = new Subscriber("nsqlookupd");
		subscriber.subscribe("hello_topic", "print_channel", Consumer::handle);
	}
	
	private static void handle(byte[] message) {
		System.out.println(new String(message));
	}
	
}
