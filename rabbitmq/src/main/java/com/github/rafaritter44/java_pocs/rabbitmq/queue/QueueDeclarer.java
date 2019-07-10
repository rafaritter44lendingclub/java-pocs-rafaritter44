package com.github.rafaritter44.java_pocs.rabbitmq.queue;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.Channel;

public class QueueDeclarer {
	
	private static final String QUEUE = "hello";
	private static final boolean DURABLE = true;
	private static final boolean EXCLUSIVE = true;
	private static final boolean AUTO_DELETE = false;
	private static final Map<String, Object> ARGUMENTS = null;
	
	public static void declareQueue(Channel channel) {
		try {
			channel.queueDeclare(QUEUE, DURABLE, EXCLUSIVE, AUTO_DELETE, ARGUMENTS);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getQueue() {
		return QUEUE;
	}
	
}
