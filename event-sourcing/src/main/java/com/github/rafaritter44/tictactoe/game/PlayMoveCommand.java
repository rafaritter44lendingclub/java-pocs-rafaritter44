package com.github.rafaritter44.tictactoe.game;

public class PlayMoveCommand implements GameCommand {

  private Move move;

  @Override
  public boolean isApplicableTo(Board board) {
    return board.isPlayable(move);
  }

  @Override
  public GameEvent toEvent() {
    return new MovePlayedEvent(move);
  }
}
