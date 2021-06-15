package it.polimi.ingsw.project.model.board.faithMap.tile;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;

public class NormalTile implements ActivableTile, Serializable {
    @Override
    public void activate() {

    }

    @Override
    public void addObserverBasedOnType(Match _match, Player _player) {
        // nothing to do here. is just to suppress the error
    }
}
