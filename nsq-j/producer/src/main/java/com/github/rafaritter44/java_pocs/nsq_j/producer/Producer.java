package com.github.rafaritter44.java_pocs.nsq_j.producer;

import java.util.concurrent.TimeUnit;

import com.sproutsocial.nsq.Publisher;

public class Producer {
	
	public static void main(String[] args) throws InterruptedException {
		Publisher publisher = new Publisher("nsqd:4150");
		while (true) {
			publisher.publishBuffered("hello_topic", "Hello, World!".getBytes());
			TimeUnit.SECONDS.sleep(2L);
		}
	}
	
}
