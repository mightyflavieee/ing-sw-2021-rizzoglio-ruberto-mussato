package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.MoveHandler;

public class PlayerDisconnectedRequestMove extends GameRequestMove implements MoveHandler {
  private final Player disconnectedPlayer;

  public PlayerDisconnectedRequestMove(Player disconnectedPlayer) {
    this.disconnectedPlayer = disconnectedPlayer;
  }

  public Player getDisconnectedPlayer() {
    return this.disconnectedPlayer;
  }

  @Override
  public void handleMove(Model model, MoveHandler requestedMove) {
    model.playerSkipTurn(disconnectedPlayer);
  }
}
