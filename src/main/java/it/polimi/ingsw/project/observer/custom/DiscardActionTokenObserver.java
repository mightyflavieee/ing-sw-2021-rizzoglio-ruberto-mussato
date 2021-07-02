package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.board.card.CardColor;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observer;

public class DiscardActionTokenObserver implements Observer<CardColor>, Serializable {
    private static final long serialVersionUID = 3840280595555504L;
    private final Match match;

    /**
     * @param match it is needed to notify the match when to discard a card
     */
    public DiscardActionTokenObserver(Match match) {
        this.match = match;
    }

    @Override
    public void update(CardColor message) {
        match.discardForActionToken(message);

    }
}
