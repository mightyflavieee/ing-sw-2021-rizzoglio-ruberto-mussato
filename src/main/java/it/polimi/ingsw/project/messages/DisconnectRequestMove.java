package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.view.View;

public class DisconnectRequestMove implements Controllable {

  /**
   * it notify the controller with this move to disconnect the player that disconnects (same thing done for the PlayerMove)
   * @param player it is passed by the remoteView
   * @param view it is the view that observes the socketConnection
   * @param requestedMove it is not used in this case
   */
  @Override
  public void notifyMoveToController(Player player, View view, Controllable requestedMove) {
    view.notify(new PlayerDisconnectedRequestMove(player));
  }

}
