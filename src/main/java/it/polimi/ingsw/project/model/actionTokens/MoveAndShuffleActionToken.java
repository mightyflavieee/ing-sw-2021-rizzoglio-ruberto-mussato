package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.MoveAndShuffleActionTokenObserver;

public class MoveAndShuffleActionToken extends Observable<MoveAndShuffleActionToken> implements ActionToken {
    public MoveAndShuffleActionToken() {
        super.setType("moveAndShuffleActionToken");
    }

    @Override
    public void Action() {
        super.notify(this);
    }

    public String toString() {
        return "Move and Shuffle Action Token";
    }

    @Override
    public void addObserverBasedOnType(Match match, ActionTokenContainer actionTokenContainer) {
        this.addObserver(new MoveAndShuffleActionTokenObserver(match, actionTokenContainer));

    }
}
