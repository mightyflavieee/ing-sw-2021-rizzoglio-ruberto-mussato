package it.polimi.ingsw.project.model.playermove.interfaces;

import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;

public interface Controllable {

  public void notifyMoveToController(Player player, View view, Controllable requestedMove);
}
