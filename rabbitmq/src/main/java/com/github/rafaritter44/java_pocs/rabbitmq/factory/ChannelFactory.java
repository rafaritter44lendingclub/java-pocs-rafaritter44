package com.github.rafaritter44.java_pocs.rabbitmq.factory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelFactory {
	
	private static final Connection connection;
	
	static {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		try {
			connection = connectionFactory.newConnection();
		} catch (IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Channel newChannel() {
		try {
			return connection.createChannel();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
