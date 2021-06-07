package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.view.View;

public class DisconnectRequestMove implements Controllable {

  @Override
  public void notifyMoveToController(Player player, View view, Controllable requestedMove) {
    view.notify(new PlayerDisconnectedRequestMove(player));
  }

}
