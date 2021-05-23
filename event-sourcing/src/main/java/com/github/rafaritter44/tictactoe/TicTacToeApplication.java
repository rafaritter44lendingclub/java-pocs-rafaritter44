package com.github.rafaritter44.tictactoe;

import com.github.rafaritter44.tictactoe.game.BoardConsumer;
import com.github.rafaritter44.tictactoe.game.GameEventConsumer;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class TicTacToeApplication {

  private TopicService topicService;
  private Set<ConsumerRunner<?, ?>> consumers;
  private StreamsService streamsService;

  public TicTacToeApplication(
      TopicService topicService,
      Set<ConsumerRunner<?, ?>> consumers,
      StreamsService streamsService) {
    this.topicService = topicService;
    this.consumers = consumers;
    this.streamsService = streamsService;
  }

  public void start() throws InterruptedException {
    topicService.createTopics();
    consumers.forEach(
        consumer -> {
          new Thread(consumer).start();
          Runtime.getRuntime().addShutdownHook(new Thread(consumer::shutdown));
        });
    var latch = new CountDownLatch(1);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  streamsService.shutdown();
                  latch.countDown();
                }));
    streamsService.start();
    latch.await();
  }

  public static void main(String[] args) throws InterruptedException {
    var topicService = new TopicService();
    var consumers = Set.<ConsumerRunner<?, ?>>of(new GameEventConsumer(), new BoardConsumer());
    var streamsService = new StreamsService();
    new TicTacToeApplication(topicService, consumers, streamsService).start();
  }
}
