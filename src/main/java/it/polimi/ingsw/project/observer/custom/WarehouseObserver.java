package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.observer.Observer;

public class WarehouseObserver implements Observer<Warehouse>, Serializable {
    private final Match match;

    public WarehouseObserver(Match match) {
        this.match = match;
    }

    @Override
    public void update(Warehouse message) {
        match.notifyFaithMapsForDiscard(message.getNumResourcesToDiscard());
    }
}
