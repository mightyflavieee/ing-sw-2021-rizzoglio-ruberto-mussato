package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.observer.Observable;

public class MoveActionToken extends Observable<MoveActionToken> implements ActionToken {
    @Override
    public void Action() {
        super.notify(this);
    }
    public String toString(){
        return "Move Action Token";
    }
}
