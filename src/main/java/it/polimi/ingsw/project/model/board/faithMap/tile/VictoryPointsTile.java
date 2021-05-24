package it.polimi.ingsw.project.model.board.faithMap.tile;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.VictoryPointsObserver;

public class VictoryPointsTile extends Observable<VictoryPointsTile> implements ActivableTile{
    private final int victoryPoints;

    public VictoryPointsTile(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictorypoints() {
        return victoryPoints;
    }

    @Override
    public void activate() {
        super.notify(this);
    }
}
