package com.github.rafaritter44.tictactoe.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {
  private static final int FIRST_POSITION = 1;
  private static final int LAST_POSITION = 9;

  private Map<Integer, Player> squares = new HashMap<>();
  private boolean gameHasStarted = false;

  public void startGame() {
    gameHasStarted = true;
  }

  public boolean gameHasStarted() {
    return gameHasStarted;
  }

  private boolean gameHasEnded() {
    return getAllSquares().allMatch(Objects::nonNull) || winner().isPresent();
  }

  private Stream<Player> getAllSquares() {
    return IntStream.rangeClosed(FIRST_POSITION, LAST_POSITION).mapToObj(squares::get);
  }

  public boolean isPlayable(Move move) {
    if (Optional.ofNullable(move).map(Move::getPlayer).isEmpty()) {
      return false;
    }
    if (move.getPosition() < FIRST_POSITION || move.getPosition() > LAST_POSITION) {
      return false;
    }
    if (squares.get(move.getPosition()) != null) {
      return false;
    }
    if (nextToPlay() != move.getPlayer()) {
      return false;
    }
    if (!gameHasStarted() || gameHasEnded()) {
      return false;
    }
    return true;
  }

  private Player nextToPlay() {
    return getAllSquares().filter(Objects::nonNull).count() % 2L == 0L ? Player.X : Player.O;
  }

  public void play(Move move) {
    if (isPlayable(move)) {
      squares.put(move.getPosition(), move.getPlayer());
    }
  }

  private Optional<Player> winner() {
    return takerOfAll(1, 2, 3)
        .or(() -> takerOfAll(4, 5, 6))
        .or(() -> takerOfAll(7, 8, 9))
        .or(() -> takerOfAll(1, 4, 7))
        .or(() -> takerOfAll(2, 5, 8))
        .or(() -> takerOfAll(3, 6, 9))
        .or(() -> takerOfAll(1, 5, 9))
        .or(() -> takerOfAll(3, 5, 7));
  }

  private Optional<Player> takerOfAll(Integer... positions) {
    return Stream.of(positions)
        .map(squares::get)
        .map(Optional::ofNullable)
        .reduce(
            (firstPlayer, secondPlayer) ->
                firstPlayer.equals(secondPlayer) ? firstPlayer : Optional.empty())
        .orElse(Optional.empty());
  }

  @Override
  public String toString() {
    var squares =
        getAllSquares()
            .map(Optional::ofNullable)
            .map(square -> square.map(Object::toString).orElse("_"))
            .collect(Collectors.toList());
    var board = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        var position = 3 * i + j;
        board.append(squares.get(position));
        board.append(" ");
      }
      board.setCharAt(board.length() - 1, '\n');
    }
    board.append("Started: ");
    board.append(gameHasStarted);
    if (gameHasEnded()) {
      board.append("\n");
      winner()
          .ifPresentOrElse(
              winner -> board.append("Winner: ").append(winner), () -> board.append("Draw"));
    }
    return board.toString();
  }
}
