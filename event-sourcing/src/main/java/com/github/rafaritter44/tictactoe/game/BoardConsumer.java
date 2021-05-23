package com.github.rafaritter44.tictactoe.game;

import com.github.rafaritter44.tictactoe.ConsumerRunner;
import com.github.rafaritter44.tictactoe.TopicService;
import com.github.rafaritter44.tictactoe.message.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class BoardConsumer extends ConsumerRunner<String, Message> {

  public BoardConsumer() {
    super(TopicService.BOARDS_TOPIC);
  }

  @Override
  public void handle(ConsumerRecord<String, Message> record) {
    var gameId = record.key();
    var message = record.value();
    var board = message.<Board>getPayload();
    logger.info("Game: {} - Board:\n{}", gameId, board);
  }
}
