package it.polimi.ingsw.project.observer.custom;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;
import it.polimi.ingsw.project.observer.Observer;

public class VictoryPointsObserver implements Observer<VictoryPointsTile> , Serializable {
    private static final long serialVersionUID = 2140280592475092704L;
    private final Player myplayer;

    public VictoryPointsObserver(Player player) {
        this.myplayer = player;
    }

    @Override
    public void update(VictoryPointsTile message) {
        myplayer.addVictoryPoints(message.getVictorypoints());
    }
}
