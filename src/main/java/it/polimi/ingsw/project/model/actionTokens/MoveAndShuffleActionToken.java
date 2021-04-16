package it.polimi.ingsw.project.model.actionTokens;

import java.util.List;

import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.MoveAndShuffleActionTokenObserver;

public class MoveAndShuffleActionToken extends Observable<MoveAndShuffleActionToken> implements ActionToken {
    @Override
    public void Action() {
        super.notify(this);
    }
}
