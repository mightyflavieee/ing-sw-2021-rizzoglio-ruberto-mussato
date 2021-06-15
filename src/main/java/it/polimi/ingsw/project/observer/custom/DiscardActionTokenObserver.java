package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.board.card.CardColor;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.observer.Observer;

public class DiscardActionTokenObserver implements Observer<CardColor>, Serializable {
    private final Match match;

    public DiscardActionTokenObserver(Match match) {
        this.match = match;
    }

    @Override
    public void update(CardColor message) {
        match.discardForActionToken(message);

    }
}
