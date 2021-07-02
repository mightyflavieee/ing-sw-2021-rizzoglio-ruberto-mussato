package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.observer.Observer;

public class WarehouseObserver implements Observer<Warehouse>, Serializable {
    private static final long serialVersionUID = 2240280592475092704L;
    private final Match match;

    /**
     * @param match it is needed to notify the other players map for the discard of a resource
     */
    public WarehouseObserver(Match match) {
        this.match = match;
    }

    /**
     * it calls on each player the method to move forward the player (if the name is different from the currentPlayer)
     * @param message contains the number of resources to discard
     */
    @Override
    public void update(Warehouse message) {
        match.notifyFaithMapsForDiscard(message.getNumResourcesToDiscard());
    }
}
