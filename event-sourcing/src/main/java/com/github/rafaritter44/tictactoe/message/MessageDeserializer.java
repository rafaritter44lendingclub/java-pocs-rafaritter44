package com.github.rafaritter44.tictactoe.message;

import com.github.rafaritter44.tictactoe.ObjectMapperHolder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class MessageDeserializer implements Deserializer<Message> {

  @Override
  public Message deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }
    try {
      return ObjectMapperHolder.get().readValue(data, Message.class);
    } catch (Exception e) {
      throw new SerializationException("Error deserializing JSON message", e);
    }
  }
}
