package com.github.rafaritter44.tictactoe.message;

import com.github.rafaritter44.tictactoe.ObjectMapperHolder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class MessageSerializer implements Serializer<Message> {

  @Override
  public byte[] serialize(String topic, Message data) {
    if (data == null) {
      return null;
    }
    try {
      return ObjectMapperHolder.get().writeValueAsBytes(data);
    } catch (Exception e) {
      throw new SerializationException("Error serializing JSON message", e);
    }
  }
}
