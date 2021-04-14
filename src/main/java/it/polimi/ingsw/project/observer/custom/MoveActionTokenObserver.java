package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.FaithMap;
import it.polimi.ingsw.project.observer.Observer;

public class MoveActionTokenObserver implements Observer {
    private FaithMap faithMap;
    @Override
    public void update() {
        faithMap.moveForwardBlack();
        faithMap.moveForwardBlack();
    }
}
