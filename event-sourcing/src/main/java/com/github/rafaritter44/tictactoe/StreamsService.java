package com.github.rafaritter44.tictactoe;

import com.github.rafaritter44.tictactoe.game.Board;
import com.github.rafaritter44.tictactoe.game.BoardDeserializer;
import com.github.rafaritter44.tictactoe.game.BoardSerializer;
import com.github.rafaritter44.tictactoe.game.GameCommand;
import com.github.rafaritter44.tictactoe.game.GameCommandHandler;
import com.github.rafaritter44.tictactoe.game.GameEvent;
import com.github.rafaritter44.tictactoe.game.GameEventAggregator;
import com.github.rafaritter44.tictactoe.message.MessageUnwrapper;
import com.github.rafaritter44.tictactoe.message.MessageWrapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.Stores;

public class StreamsService {

  public static final String BOARDS_STORE = "BoardsStore";
  private static final String GAME_COMMANDS_SOURCE = "GameCommandsSource";
  private static final String GAME_COMMANDS_UNWRAPPER = "GameCommandsUnwrapper";
  private static final String GAME_COMMANDS_HANDLER = "GameCommandsHandler";
  private static final String GAME_EVENTS_AGGREGATOR = "GameEventsAggregator";
  private static final String GAME_EVENTS_WRAPPER = "GameEventsWrapper";
  private static final String BOARDS_WRAPPER = "BoardsWrapper";
  private static final String GAME_EVENTS_SINK = "GameEventsSink";
  private static final String BOARDS_SINK = "BoardsSink";

  private KafkaStreams kafkaStreams;

  public void start() {
    var boardsStoreBuilder =
        Stores.keyValueStoreBuilder(
            Stores.persistentKeyValueStore(BOARDS_STORE),
            Serdes.String(),
            Serdes.serdeFrom(new BoardSerializer(), new BoardDeserializer()));
    var topology =
        new Topology()
            .addSource(GAME_COMMANDS_SOURCE, TopicService.GAME_COMMANDS_TOPIC)
            .addProcessor(
                GAME_COMMANDS_UNWRAPPER, MessageUnwrapper<GameCommand>::new, GAME_COMMANDS_SOURCE)
            .addProcessor(GAME_COMMANDS_HANDLER, GameCommandHandler::new, GAME_COMMANDS_UNWRAPPER)
            .addProcessor(GAME_EVENTS_AGGREGATOR, GameEventAggregator::new, GAME_COMMANDS_HANDLER)
            .addProcessor(
                GAME_EVENTS_WRAPPER, MessageWrapper<GameEvent>::new, GAME_COMMANDS_HANDLER)
            .addProcessor(BOARDS_WRAPPER, MessageWrapper<Board>::new, GAME_EVENTS_AGGREGATOR)
            .addSink(GAME_EVENTS_SINK, TopicService.GAME_EVENTS_TOPIC, GAME_EVENTS_WRAPPER)
            .addSink(BOARDS_SINK, TopicService.BOARDS_TOPIC, BOARDS_WRAPPER)
            .addStateStore(boardsStoreBuilder, GAME_COMMANDS_HANDLER, GAME_EVENTS_AGGREGATOR);
    kafkaStreams = new KafkaStreams(topology, PropertiesHolder.get());
    kafkaStreams.start();
  }

  public void shutdown() {
    if (kafkaStreams != null) {
      kafkaStreams.close();
    }
  }
}
