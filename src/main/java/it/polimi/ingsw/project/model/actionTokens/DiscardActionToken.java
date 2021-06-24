package it.polimi.ingsw.project.model.actionTokens;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.DiscardActionTokenObserver;

public class DiscardActionToken extends Observable<CardColor> implements ActionToken, Serializable {
    private final CardColor cardColor;

    public DiscardActionToken(CardColor cardColor) {
        this.cardColor = cardColor;
        super.setType("discardActionToken");
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    @Override
    public void Action() {
        super.notify(this.cardColor);
    }

    public String toString() {
        return "Discard Action Token, cardcolor: " + this.cardColor;
    }

    @Override
    public void addObserverBasedOnType(Match match, ActionTokenContainer _a) {
        this.addObserver(new DiscardActionTokenObserver(match));
    }

}
