package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;

public class NoMove extends Move{
    @Override
    public boolean isFeasibleMove(Match match) {
        return true;
    }

    @Override
    public void performMove(Match match) {
        return;
    }

    @Override
    public String toString() {
        return "NoMove";
    }

    @Override
    public boolean isMainMove() {
        return false;
    }
}
