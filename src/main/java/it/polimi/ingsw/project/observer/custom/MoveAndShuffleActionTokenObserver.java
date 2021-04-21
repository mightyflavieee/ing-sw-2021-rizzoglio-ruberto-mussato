package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.actionTokens.MoveAndShuffleActionToken;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.observer.Observer;

public class MoveAndShuffleActionTokenObserver implements Observer<MoveAndShuffleActionToken> {
    private Match match;
    private ActionTokenContainer actionTokenContainer;
    @Override
    public void update(MoveAndShuffleActionToken message) {
        actionTokenContainer.shuffle();
        this.match.moveForwardBlack();
    }
}
