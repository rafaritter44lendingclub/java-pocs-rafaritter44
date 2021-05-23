package com.github.rafaritter44.tictactoe.game;

public class GameStartedEvent extends GameEvent {

  @Override
  public void applyTo(Board board) {
    board.startGame();
  }
}
