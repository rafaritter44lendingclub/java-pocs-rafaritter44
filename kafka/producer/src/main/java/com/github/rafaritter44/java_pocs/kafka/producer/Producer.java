package com.github.rafaritter44.java_pocs.kafka.producer;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {
	
	private static final String TOPIC = "hello_topic";
	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
	
	private static final long NO_INITIAL_DELAY = 0L;
	private static final long RUN_EVERY_THREE = 3L;
	
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		KafkaProducer<String, String> producer = new KafkaProducer<>(readProperties());
		Runtime.getRuntime().addShutdownHook(new Thread(producer::close));
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			for (int i = 0; i < 10; i++) {
				ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "key_" + i, "Hello " + i);
				producer.send(record, (recordMetadata, exception) -> {
					if (exception == null) {
						LOGGER.info(recordMetadata.toString());
					} else {
						LOGGER.error("Error while producing", exception);
					}
				});
			}
		}, NO_INITIAL_DELAY, RUN_EVERY_THREE, TimeUnit.SECONDS);
	}
	
	private static Properties readProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(Producer.class.getResourceAsStream("/kafka.properties"));
		return properties;
	}
	
}
