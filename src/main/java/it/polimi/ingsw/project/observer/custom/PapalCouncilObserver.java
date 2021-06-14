package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.observer.Observer;

public class PapalCouncilObserver implements Observer<PapalCouncilTile>, Serializable {
    private final Match match;

    public PapalCouncilObserver(Match match) {
        this.match = match;
    }

    @Override
    public void update(PapalCouncilTile message) {
        match.notifyFaithMapsForCouncil(message.getNumTile());
    }
}
