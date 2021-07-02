package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.observer.Observer;

public class PapalCouncilObserver implements Observer<PapalCouncilTile>, Serializable {
    private static final long serialVersionUID = 3840111111475092704L;
    private final Match match;

    /**
     * @param match it is needed to notify the match when someone steps on a papalCouncil tile
     */
    public PapalCouncilObserver(Match match) {
        this.match = match;
    }

    /**
     * it calls on each player the method to add points to the player
     * @param message it is the number of points we get when we step into this papal councilTile
     */
    @Override
    public void update(PapalCouncilTile message) {
        match.notifyFaithMapsForCouncil(message.getNumTile());
    }
}
