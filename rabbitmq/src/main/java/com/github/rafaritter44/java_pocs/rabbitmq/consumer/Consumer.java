package com.github.rafaritter44.java_pocs.rabbitmq.consumer;

import java.io.IOException;

import com.github.rafaritter44.java_pocs.rabbitmq.factory.ChannelFactory;
import com.github.rafaritter44.java_pocs.rabbitmq.queue.QueueDeclarer;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {
	
	private final Channel channel = ChannelFactory.newChannel();
	
	public void consume() {
		QueueDeclarer.declareQueue(channel);
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody());
			System.out.println(String.format(" [x] Received '%s'", message));
		};
		CancelCallback cancelCallback = consumerTag -> {};
		try {
			channel.basicConsume(QueueDeclarer.getQueue(), deliverCallback, cancelCallback);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Channel getChannel() {
		return channel;
	}
	
}
