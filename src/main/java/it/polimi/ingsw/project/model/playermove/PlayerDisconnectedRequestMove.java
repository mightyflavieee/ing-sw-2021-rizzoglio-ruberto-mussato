package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.HandableMove;

public class PlayerDisconnectedRequestMove extends GameRequestMove implements HandableMove {
  private Player disconnectedPlayer;

  public PlayerDisconnectedRequestMove(Player disconnectedPlayer) {
    this.disconnectedPlayer = disconnectedPlayer;
  }

  public Player getDisconnectedPlayer() {
    return this.disconnectedPlayer;
  }

  @Override
  public void handleMove(Model model, HandableMove requestedMove) {
    model.playerSkipTurn(disconnectedPlayer);
  }
}
