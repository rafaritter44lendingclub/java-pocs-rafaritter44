package com.github.rafaritter44.tictactoe;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.errors.TopicExistsException;

public class TopicService {

  public static final String GAME_COMMANDS_TOPIC = "game-commands-topic";
  public static final String GAME_EVENTS_TOPIC = "game-events-topic";
  public static final String BOARDS_TOPIC = "boards-topic";

  public void createTopics() {
    try (var adminClient = AdminClient.create(PropertiesHolder.get())) {
      var topics =
          Stream.of(GAME_COMMANDS_TOPIC, GAME_EVENTS_TOPIC, BOARDS_TOPIC)
              .map(
                  topic ->
                      new NewTopic(topic, 3, (short) 1)
                          .configs(Map.of(TopicConfig.RETENTION_MS_CONFIG, "-1")))
              .collect(Collectors.toSet());
      adminClient.createTopics(topics).all().get();
    } catch (InterruptedException | ExecutionException e) {
      if (!(e.getCause() instanceof TopicExistsException)) {
        throw new RuntimeException("Error creating topics", e);
      }
    }
  }
}
