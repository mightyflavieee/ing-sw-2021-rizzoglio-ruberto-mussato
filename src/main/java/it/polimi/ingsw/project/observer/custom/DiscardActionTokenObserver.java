package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.CardColor;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observer;

public class DiscardActionTokenObserver implements Observer {
    private Match match;
    private CardColor cardColor;
    @Override
    public void update() {
        match.discard(this.cardColor);
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }
    @Override
    public void update(Object message) {
        // TODO Auto-generated method stub
        
    }
}

