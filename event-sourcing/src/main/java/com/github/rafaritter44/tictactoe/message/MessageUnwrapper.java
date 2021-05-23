package com.github.rafaritter44.tictactoe.message;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

public class MessageUnwrapper<T> implements Processor<String, Message, String, T> {

  private ProcessorContext<String, T> context;

  @Override
  public void init(ProcessorContext<String, T> context) {
    this.context = context;
  }

  @Override
  public void process(Record<String, Message> record) {
    var message = record.value();
    var payload = message.<T>getPayload();
    context.forward(record.withValue(payload));
  }
}
