package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.actionTokens.MoveAndShuffleActionToken;
import it.polimi.ingsw.project.observer.Observer;

public class MoveAndShuffleActionTokenObserver implements Observer<MoveAndShuffleActionToken>, Serializable {
    private static final long serialVersionUID = 3840259247509211114L;
    private final Match match;
    private final ActionTokenContainer actionTokenContainer;

    /**
     * @param match it is needed to notify the match to move Lorenzo forward by one step
     * @param actionTokenContainer it is needed to shuffle the container on each step
     */
    public MoveAndShuffleActionTokenObserver(Match match, ActionTokenContainer actionTokenContainer) {
        this.match = match;
        this.actionTokenContainer = actionTokenContainer;
    }

    @Override
    public void update(MoveAndShuffleActionToken message) {
        actionTokenContainer.shuffle();
        this.match.moveForwardBlack();
    }
}
