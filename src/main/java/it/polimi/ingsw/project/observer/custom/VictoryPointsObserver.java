package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;
import it.polimi.ingsw.project.observer.Observer;

public class VictoryPointsObserver implements Observer<VictoryPointsTile> {
    private final Player myplayer;

    public VictoryPointsObserver(Player player) {
        this.myplayer = player;
    }

    @Override
    public void update(VictoryPointsTile message) {
        myplayer.addVictoryPoints(message.getVictorypoints());
    }
}
