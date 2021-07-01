package it.polimi.ingsw.project.model.board.faithMap.tile;


import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.VictoryPointsObserver;

public class VictoryPointsTile extends Observable<VictoryPointsTile> implements ActivableTile {
    private static final long serialVersionUID = 3840280548375092704L;
    private final int victoryPoints;

    /**
     * it is the constructor of this tile and sets the type to papalCouncilTile for the type adapters
     * @param victoryPoints it contains how many points needs to be added to the players when they go over the papalCouncilTile
     */
    public VictoryPointsTile(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        super.setType("victoryPointsTile");
    }

    public int getVictorypoints() {
        return victoryPoints;
    }

    @Override
    public void activate() {
        super.notify(this);
    }

    @Override
    public void addObserverBasedOnType(Match _match, Player player) {
        this.addObserver(new VictoryPointsObserver(player));
    }
}
