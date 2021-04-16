package it.polimi.ingsw.project.model.actionTokens;

import java.util.List;

import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.MoveActionTokenObserver;

public class MoveActionToken extends Observable<MoveActionToken> implements ActionToken {
    @Override
    public void Action() {
        super.notify(this);
    }

}
