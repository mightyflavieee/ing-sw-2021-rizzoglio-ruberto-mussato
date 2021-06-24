package it.polimi.ingsw.project.model.actionTokens;


import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.MoveActionTokenObserver;

public class MoveActionToken extends Observable<MoveActionToken> implements ActionToken {
    private static final long serialVersionUID = 3830280592475092704L;
    public MoveActionToken() {
        super.setType("moveActionToken");
    }

    @Override
    public void Action() {
        super.notify(this);
    }

    public String toString() {
        return "Move Action Token";
    }

    @Override
    public void addObserverBasedOnType(Match match, ActionTokenContainer _a) {
        this.addObserver(new MoveActionTokenObserver(match));
        
    }
}
