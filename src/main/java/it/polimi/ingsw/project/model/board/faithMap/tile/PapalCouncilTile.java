package it.polimi.ingsw.project.model.board.faithMap.tile;


import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.PapalCouncilObserver;

public class PapalCouncilTile extends Observable<PapalCouncilTile> implements ActivableTile{
    private static final long serialVersionUID = 384480592475092704L;
    private final int numTile; // mi dice quale Council Tile sono

    /**
     * it is the constructor of this tile and sets the type to papalCouncilTile for the type adapters
     * @param numTile it contains how many points needs to be added to the players when they go over the papalCouncilTile
     */
    public PapalCouncilTile(int numTile) {
        this.numTile = numTile;
        super.setType("papalCouncilTile");
    }

    public int getNumTile() {
        return numTile;
    }

    /**
     * it notifies the observers with this class
     */
    @Override
    public void activate() {
        super.notify(this);
    }

    /**
     * it re-adds the specificObserver for the persistance
     * @param match it is passed by the server to re-add the objects
     * @param _player it is passed by the server but it is not needed in this case
     */
    @Override
    public void addObserverBasedOnType(Match match, Player _player) {
        this.addObserver(new PapalCouncilObserver(match));
    }

}
