package com.github.rafaritter44.tictactoe.message;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

public class MessageWrapper<T> implements Processor<String, T, String, Message> {

  private ProcessorContext<String, Message> context;

  @Override
  public void init(ProcessorContext<String, Message> context) {
    this.context = context;
  }

  @Override
  public void process(Record<String, T> record) {
    var payload = record.value();
    var message = Message.fromPayload(payload);
    context.forward(record.withValue(message));
  }
}
