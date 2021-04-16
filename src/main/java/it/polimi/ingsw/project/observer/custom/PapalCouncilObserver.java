package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.observer.Observer;

public class PapalCouncilObserver implements Observer<PapalCouncilTile> {
    private Match match;
    @Override
    public void update(PapalCouncilTile message) {
        match.notifyFaithMapsForCouncil(message.getNumTile());
    }
}
