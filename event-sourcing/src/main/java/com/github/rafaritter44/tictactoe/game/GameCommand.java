package com.github.rafaritter44.tictactoe.game;

public abstract class GameCommand {
  public abstract boolean isApplicableTo(Board board);

  public abstract GameEvent toEvent();
}
