package com.github.rafaritter44.tictactoe.game;

public class MovePlayedEvent implements GameEvent {

  private Move move;

  @SuppressWarnings("unused")
  private MovePlayedEvent() {
    // required because of Jackson
  }

  public MovePlayedEvent(Move move) {
    this.move = move;
  }

  @Override
  public void applyTo(Board board) {
    board.play(move);
  }
}
