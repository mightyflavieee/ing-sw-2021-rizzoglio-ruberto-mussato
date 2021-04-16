package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.actionTokens.MoveAndShuffleActionToken;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.observer.Observer;

public class MoveAndShuffleActionTokenObserver implements Observer<MoveAndShuffleActionToken> {
    private FaithMap faithMap;
    private ActionTokenContainer actionTokenContainer;
    @Override
    public void update(MoveAndShuffleActionToken message) {
        faithMap.moveForwardBlack();
        actionTokenContainer.shuffle();
    }
}
