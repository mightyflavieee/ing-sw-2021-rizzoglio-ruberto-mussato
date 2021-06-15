package it.polimi.ingsw.project.model.board.faithMap.tile;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.PapalCouncilObserver;

public class PapalCouncilTile extends Observable<PapalCouncilTile> implements ActivableTile {
    private final int numTile; // mi dice quale Council Tile sono

    public PapalCouncilTile(int numTile) {
        this.numTile = numTile;
        super.setType("papalCouncilTile");
    }

    public int getNumTile() {
        return numTile;
    }

    @Override
    public void activate() {
        super.notify(this);
    }

    @Override
    public void addObserverBasedOnType(Match match, Player _player) {
        this.addObserver(new PapalCouncilObserver(match));
    }

}
