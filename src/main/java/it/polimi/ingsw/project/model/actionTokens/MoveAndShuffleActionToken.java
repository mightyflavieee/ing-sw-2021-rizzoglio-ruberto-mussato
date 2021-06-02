package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.observer.Observable;

public class MoveAndShuffleActionToken extends Observable<MoveAndShuffleActionToken> implements ActionToken {
    @Override
    public void Action() {
        super.notify(this);
    }
    public String toString(){
        return "Move and Shuffle Action Token";
    }
}
