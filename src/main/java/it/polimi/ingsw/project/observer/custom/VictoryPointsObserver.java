package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;
import it.polimi.ingsw.project.observer.Observer;

public class VictoryPointsObserver implements Observer<VictoryPointsTile> {
    private Match match;
    @Override
    public void update(VictoryPointsTile message) {
        match.addVictoryPoints(message.getVictorypoints());
    }
}
