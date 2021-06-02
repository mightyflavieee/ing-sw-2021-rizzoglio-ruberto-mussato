package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.observer.Observable;


public class DiscardActionToken extends Observable<CardColor> implements ActionToken{
    private final CardColor cardColor;

    public DiscardActionToken(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    @Override
    public void Action() {
        super.notify(this.cardColor);
    }

    public String toString(){
        return "Discard Action Token, cardcolor: " + this.cardColor;
    }

}
