package it.polimi.ingsw.project.model.board.faithMap.tile;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.PapalCouncilObserver;

public class PapalCouncilTile extends Observable<PapalCouncilTile> implements ActivableTile{
    private final int numTile; // mi dice quale Council Tile sono

    public PapalCouncilTile(int numTile) {
        this.numTile = numTile;
    }

    public int getNumTile() {
        return numTile;
    }

    @Override
    public void activate() {
        super.notify(this);
    }

}
