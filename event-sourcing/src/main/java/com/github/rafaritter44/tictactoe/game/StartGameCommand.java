package com.github.rafaritter44.tictactoe.game;

public class StartGameCommand extends GameCommand {
  @Override
  public boolean isApplicableTo(Board board) {
    return !board.gameHasStarted();
  }

  @Override
  public GameEvent toEvent() {
    return new GameStartedEvent();
  }
}
