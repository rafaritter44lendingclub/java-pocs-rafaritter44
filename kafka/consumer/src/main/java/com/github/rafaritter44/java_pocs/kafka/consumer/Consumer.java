package com.github.rafaritter44.java_pocs.kafka.consumer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
	
	private static final String TOPIC = "hello_topic";
	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
	
	public static void main(String[] args) throws IOException {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(readProperties());
		consumer.subscribe(Collections.singleton(TOPIC));
		Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));
				for (ConsumerRecord<String, String> record : records) {
					LOGGER.info(record.toString());
				}
			}
		} catch (WakeupException e) {
			LOGGER.info("Received shutdown signal");
		} finally {
			consumer.close();
		}
	}
	
	private static Properties readProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(Consumer.class.getResourceAsStream("/kafka.properties"));
		return properties;
	}
	
}
