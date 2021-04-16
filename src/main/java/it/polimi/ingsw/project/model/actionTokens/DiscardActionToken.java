package it.polimi.ingsw.project.model.actionTokens;

import java.util.List;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.observer.custom.DiscardActionTokenObserver;

public class DiscardActionToken implements ActionToken {
    private CardColor cardColor;
    private List<DiscardActionTokenObserver> observers;

    public DiscardActionToken(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    @Override
    public void Action() {
        observers.forEach(DiscardActionTokenObserver::update);
    }
    public void attach(DiscardActionTokenObserver observer){
        this.observers.add(observer);
        observer.setCardColor(this.cardColor);
    }
    public void detach(DiscardActionTokenObserver observer){
        this.observers.remove(observer);
    }
}
