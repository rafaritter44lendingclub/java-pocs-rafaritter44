package com.github.rafaritter44.tictactoe.message;

import org.apache.kafka.common.serialization.Serdes.WrapperSerde;

public class MessageSerde extends WrapperSerde<Message> {

  public MessageSerde() {
    super(new MessageSerializer(), new MessageDeserializer());
  }
}
