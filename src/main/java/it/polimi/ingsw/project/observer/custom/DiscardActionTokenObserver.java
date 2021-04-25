package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observer;

public class DiscardActionTokenObserver implements Observer<CardColor> {
    private Match match;

    @Override
    public void update(CardColor message) {
        match.discardForActionToken(message);
        
    }
}

