package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.observer.Observer;

public class MoveActionTokenObserver implements Observer<MoveActionToken> {
    private FaithMap faithMap;

    @Override
    public void update(MoveActionToken message) {
        faithMap.moveForwardBlack();
        faithMap.moveForwardBlack();
        
    }
}
