package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.observer.Observer;

public class WarehouseObserver implements Observer<Warehouse> {
    private Match match;


    @Override
    public void update(Warehouse message) {
        match.notifyFaithMapsForDiscard(message.getNumResourcesToDiscard());
    }
}
