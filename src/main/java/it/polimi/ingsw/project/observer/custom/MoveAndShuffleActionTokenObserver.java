package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.ActionTokenContainer;
import it.polimi.ingsw.project.model.FaithMap;
import it.polimi.ingsw.project.observer.Observer;

public class MoveAndShuffleActionTokenObserver implements Observer {
    private FaithMap faithMap;
    private ActionTokenContainer actionTokenContainer;
    @Override
    public void update() {
        faithMap.moveForwardBlack();
        actionTokenContainer.shuffle();
    }
    @Override
    public void update(Object message) {
        // TODO Auto-generated method stub
        
    }
}
