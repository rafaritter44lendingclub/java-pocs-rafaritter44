package com.github.rafaritter44.tictactoe.game;

public abstract class GameEvent {
  public abstract void applyTo(Board board);
}
