package it.polimi.ingsw.project.model.board.faithMap.tile;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;

public interface ActivableTile{
     void activate();

    void addObserverBasedOnType(Match match, Player player);
}
