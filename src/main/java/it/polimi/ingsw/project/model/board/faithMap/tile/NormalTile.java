package it.polimi.ingsw.project.model.board.faithMap.tile;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;

public class NormalTile implements ActivableTile, Serializable {
    private static final long serialVersionUID = 3841111112475092704L;

    /**
     * it does nothing because it is a normal tile
     */
    @Override
    public void activate() {

    }

    /**
     * nothing to do here. is just to suppress the error
     * @param _match it is passed by the controller
     * @param _player it is passed by the controller
     */
    @Override
    public void addObserverBasedOnType(Match _match, Player _player) {
        // nothing to do here. is just to suppress the error
    }
}
