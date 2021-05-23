package com.github.rafaritter44.tictactoe.game;

import com.github.rafaritter44.tictactoe.ObjectMapperHolder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class BoardSerializer implements Serializer<Board> {

  @Override
  public byte[] serialize(String topic, Board data) {
    if (data == null) {
      return null;
    }
    try {
      return ObjectMapperHolder.get().writeValueAsBytes(data);
    } catch (Exception e) {
      throw new SerializationException("Error serializing JSON board", e);
    }
  }
}
