#!/bin/bash
winpty docker-compose exec kafka bash -c "echo $'$1-{\"type\":\"com.github.rafaritter44.tictactoe.game.StartGameCommand\",\"payload\":{}}' | kafka-console-producer --bootstrap-server localhost:9092 --topic game-commands-topic --property parse.key=true --property key.separator=-"
