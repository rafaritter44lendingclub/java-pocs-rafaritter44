# Event-Sourced Tic-Tac-Toe

This is an event-sourced tic-tac-toe system implemented using Kafka Streams.

## Architecture

Game commands are processed and validated against current board state. If they are valid, game events are raised and the board state is updated.

![architecture](/event-sourcing/architecture.png)

## Commands

Here is an example of a command processed by the system:

```json
{
  "type": "com.github.rafaritter44.tictactoe.game.PlayMoveCommand",
  "payload": {
    "move": {
      "player": "X",
      "position": 5
    }
  }
}
```

## Events

Here is an example of an event raised by the system:

```json
{
  "type": "com.github.rafaritter44.tictactoe.game.MovePlayedEvent",
  "payload": {
    "move": {
      "player": "X",
      "position": 5
    }
  }
}
```

## Get Started

After starting the system with the `build-and-run.sh` script, you can start a new game like so:

`$ ./start-game.sh YourGameID`

And you can play moves like so:

`$ ./play-move.sh YourGameID X 5`

## Parallelism

In order to run 3 instances of the application, allowing parallel processing of partitioned data, use the following command:

`$ docker-compose up --scale tictactoe=3`
