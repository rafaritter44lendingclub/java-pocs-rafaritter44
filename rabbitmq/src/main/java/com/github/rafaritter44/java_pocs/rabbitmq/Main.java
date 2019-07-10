package com.github.rafaritter44.java_pocs.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.github.rafaritter44.java_pocs.rabbitmq.consumer.Consumer;
import com.github.rafaritter44.java_pocs.rabbitmq.factory.ChannelFactory;
import com.github.rafaritter44.java_pocs.rabbitmq.producer.Producer;

public class Main {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Consumer consumer = new Consumer();
		consumer.consume();
		Producer producer = new Producer();
		for(int i=0; i<1_000_000; i++) {
			producer.produce();
		}
		producer.getChannel().close();
		consumer.getChannel().close();
		ChannelFactory.getConnection().close();
	}
	
}
