package it.polimi.ingsw.project.model.board.faithMap.tile;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;

/**
 * interface for the Tiles of the faithMap, it contains the methods that needs to be implemented in the child
 */
public interface ActivableTile{
     void activate();

    void addObserverBasedOnType(Match match, Player player);
}
