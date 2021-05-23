package com.github.rafaritter44.tictactoe.game;

import com.github.rafaritter44.tictactoe.StreamsService;
import java.util.Optional;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;

public class GameEventAggregator implements Processor<String, GameEvent, String, Board> {

  private ProcessorContext<String, Board> context;
  private KeyValueStore<String, Board> boardsStore;

  @Override
  public void init(ProcessorContext<String, Board> context) {
    this.context = context;
    this.boardsStore = context.getStateStore(StreamsService.BOARDS_STORE);
  }

  @Override
  public void process(Record<String, GameEvent> record) {
    var gameId = record.key();
    var gameEvent = record.value();
    var board = Optional.of(gameId).map(boardsStore::get).orElseGet(Board::new);
    gameEvent.applyTo(board);
    boardsStore.put(gameId, board);
    context.forward(record.withValue(board));
  }
}
