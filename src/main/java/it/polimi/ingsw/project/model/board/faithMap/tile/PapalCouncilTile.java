package it.polimi.ingsw.project.model.board.faithMap.tile;


import it.polimi.ingsw.project.observer.Observable;

public class PapalCouncilTile extends Observable<PapalCouncilTile> implements ActivableTile {
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
