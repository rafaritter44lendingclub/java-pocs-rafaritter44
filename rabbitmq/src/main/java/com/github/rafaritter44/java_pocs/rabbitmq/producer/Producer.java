package com.github.rafaritter44.java_pocs.rabbitmq.producer;

import java.io.IOException;

import com.github.rafaritter44.java_pocs.rabbitmq.factory.ChannelFactory;
import com.github.rafaritter44.java_pocs.rabbitmq.queue.QueueDeclarer;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;

public class Producer {
	
	private static final String EXCHANGE = "";
	private static final String ROUTING_KEY = QueueDeclarer.getQueue();
	private static final BasicProperties BASIC_PROPERTIES = null;
	private static final String MESSAGE = "Hello, World!";
	
	private final Channel channel = ChannelFactory.newChannel();
	
	public Producer() {
		QueueDeclarer.declareQueue(channel);
	}
	
	public void produce() {
		try {
			channel.basicPublish(EXCHANGE, ROUTING_KEY, BASIC_PROPERTIES, MESSAGE.getBytes());
			System.out.println(String.format(" [x] Sent '%s'", MESSAGE));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Channel getChannel() {
		return channel;
	}
	
}
