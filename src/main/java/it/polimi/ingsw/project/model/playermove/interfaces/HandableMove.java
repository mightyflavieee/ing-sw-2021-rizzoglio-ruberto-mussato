package it.polimi.ingsw.project.model.playermove.interfaces;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;

public interface HandableMove {

  public void handleMove(Model model, HandableMove requestedMove);
}
