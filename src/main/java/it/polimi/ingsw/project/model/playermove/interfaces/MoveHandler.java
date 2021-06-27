package it.polimi.ingsw.project.model.playermove.interfaces;

import it.polimi.ingsw.project.model.Model;

public interface MoveHandler {

  void handleMove(Model model, MoveHandler requestedMove);
}
