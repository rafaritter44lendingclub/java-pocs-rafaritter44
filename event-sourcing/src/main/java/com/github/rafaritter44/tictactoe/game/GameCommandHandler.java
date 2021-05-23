package com.github.rafaritter44.tictactoe.game;

import com.github.rafaritter44.tictactoe.StreamsService;
import java.util.Optional;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameCommandHandler implements Processor<String, GameCommand, String, GameEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GameCommandHandler.class);

  private ProcessorContext<String, GameEvent> context;
  private KeyValueStore<String, Board> boardStore;

  @Override
  public void init(ProcessorContext<String, GameEvent> context) {
    this.context = context;
    this.boardStore = context.getStateStore(StreamsService.BOARDS_STORE);
  }

  @Override
  public void process(Record<String, GameCommand> record) {
    var gameId = record.key();
    var gameCommand = record.value();
    var board = Optional.of(gameId).map(boardStore::get).orElseGet(Board::new);
    if (gameCommand.isApplicableTo(board)) {
      var gameEvent = gameCommand.toEvent();
      context.forward(record.withValue(gameEvent));
    } else {
      LOGGER.warn("Received invalid command for game {}", gameId);
    }
  }
}
