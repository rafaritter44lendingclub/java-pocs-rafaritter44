package com.github.rafaritter44.tictactoe.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.rafaritter44.tictactoe.ConsumerRunner;
import com.github.rafaritter44.tictactoe.ObjectMapperHolder;
import com.github.rafaritter44.tictactoe.TopicService;
import com.github.rafaritter44.tictactoe.message.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class GameEventConsumer extends ConsumerRunner<String, Message> {

  public GameEventConsumer() {
    super(TopicService.GAME_EVENTS_TOPIC);
  }

  @Override
  public void handle(ConsumerRecord<String, Message> record) {
    var gameId = record.key();
    var message = record.value();
    try {
      var messageJson = ObjectMapperHolder.get().writeValueAsString(message);
      logger.info("Game: {} - Event:\n{}", gameId, messageJson);
    } catch (JsonProcessingException e) {
      logger.error("Error serializing message", e);
    }
  }
}
