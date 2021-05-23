package com.github.rafaritter44.tictactoe.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.rafaritter44.tictactoe.ObjectMapperHolder;

public class Message {
  private String type;
  private JsonNode payload;

  public static Message fromPayload(Object payload) {
    var message = new Message();
    message.type = payload.getClass().getName();
    message.payload = ObjectMapperHolder.get().valueToTree(payload);
    return message;
  }

  @SuppressWarnings("unchecked")
  public <T> T getPayload() {
    try {
      var tClass = (Class<T>) Class.forName(type);
      return ObjectMapperHolder.get().treeToValue(payload, tClass);
    } catch (ClassNotFoundException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
