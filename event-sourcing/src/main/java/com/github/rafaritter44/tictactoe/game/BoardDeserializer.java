package com.github.rafaritter44.tictactoe.game;

import com.github.rafaritter44.tictactoe.ObjectMapperHolder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class BoardDeserializer implements Deserializer<Board> {

  @Override
  public Board deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }
    try {
      return ObjectMapperHolder.get().readValue(data, Board.class);
    } catch (Exception e) {
      throw new SerializationException("Error deserializing JSON board", e);
    }
  }
}
