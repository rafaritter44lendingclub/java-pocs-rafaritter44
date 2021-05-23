package com.github.rafaritter44.tictactoe;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperHolder {

  private static final ObjectMapper INSTANCE =
      new ObjectMapper()
          .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
          .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

  private ObjectMapperHolder() {}

  public static ObjectMapper get() {
    return INSTANCE;
  }
}
