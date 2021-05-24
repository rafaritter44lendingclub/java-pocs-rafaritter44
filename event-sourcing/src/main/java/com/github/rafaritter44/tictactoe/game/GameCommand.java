package com.github.rafaritter44.tictactoe.game;

public interface GameCommand {
  boolean isApplicableTo(Board board);

  GameEvent toEvent();
}
