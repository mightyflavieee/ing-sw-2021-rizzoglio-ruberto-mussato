package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observer;

public class WarehouseObserver implements Observer {
    private Match match;

    @Override
    public void update() {

    }
    public void update(int numDiscardedResources){
        match.notifyFaithMapsForDiscard(numDiscardedResources);
    }
}
