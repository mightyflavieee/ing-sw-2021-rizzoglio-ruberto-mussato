package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.observer.Observer;

public class MoveActionTokenObserver implements Observer<MoveActionToken> {
    private Match match;

    public MoveActionTokenObserver(Match match) {
        this.match = match;
    }

    @Override
    public void update(MoveActionToken message) {
        this.match.moveForwardBlack();
        this.match.moveForwardBlack();
        
    }
}
