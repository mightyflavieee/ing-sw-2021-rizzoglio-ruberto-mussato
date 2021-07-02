package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.observer.Observer;

public class MoveActionTokenObserver implements Observer<MoveActionToken>,Serializable {
    private static final long serialVersionUID = 383333592475092704L;
    private final Match match;

    /**
     * @param match it is needed to notify the match to move Lorenzo forward by two steps
     */
    public MoveActionTokenObserver(Match match) {
        this.match = match;
    }

    @Override
    public void update(MoveActionToken message) {
        this.match.moveForwardBlack();
        this.match.moveForwardBlack();
        
    }
}
