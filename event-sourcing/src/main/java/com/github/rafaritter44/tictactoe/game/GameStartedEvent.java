package com.github.rafaritter44.tictactoe.game;

public class GameStartedEvent implements GameEvent {
  @Override
  public void applyTo(Board board) {
    board.startGame();
  }
}
