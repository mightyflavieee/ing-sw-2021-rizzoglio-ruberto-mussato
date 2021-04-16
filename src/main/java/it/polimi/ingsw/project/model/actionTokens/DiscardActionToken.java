package it.polimi.ingsw.project.model.actionTokens;

import java.util.List;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.observer.custom.DiscardActionTokenObserver;


public class DiscardActionToken extends Observable<CardColor> implements ActionToken{
    private CardColor cardColor;

    public DiscardActionToken(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    @Override
    public void Action() {
        super.notify(this.cardColor);
    }


}
