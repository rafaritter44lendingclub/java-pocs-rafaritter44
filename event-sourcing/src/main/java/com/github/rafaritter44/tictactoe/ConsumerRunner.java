package com.github.rafaritter44.tictactoe;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ConsumerRunner<K, V> implements Runnable {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final KafkaConsumer<K, V> consumer;
  private final String topic;
  private final AtomicBoolean closed;

  public ConsumerRunner(String topic) {
    this.consumer = new KafkaConsumer<>(PropertiesHolder.get());
    this.topic = topic;
    this.closed = new AtomicBoolean(false);
  }

  protected abstract void handle(ConsumerRecord<K, V> record);

  @Override
  public final void run() {
    try (consumer) {
      consumer.subscribe(Set.of(topic));
      while (!closed.get()) {
        var records = consumer.poll(Duration.ofMillis(100L));
        for (var record : records) {
          handle(record);
        }
      }
    } catch (WakeupException e) {
      if (!closed.get()) {
        throw e;
      }
    }
  }

  public final void shutdown() {
    closed.set(true);
    consumer.wakeup();
  }
}
