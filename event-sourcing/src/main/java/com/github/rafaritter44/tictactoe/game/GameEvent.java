package com.github.rafaritter44.tictactoe.game;

public interface GameEvent {
  void applyTo(Board board);
}
