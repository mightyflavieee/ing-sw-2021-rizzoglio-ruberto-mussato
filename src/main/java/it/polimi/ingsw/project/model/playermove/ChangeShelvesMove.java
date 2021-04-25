package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.view.View;

public class ChangeShelvesMove extends Move {
    private ShelfFloor aFloor;
    private ShelfFloor bFloor;

    public ChangeShelvesMove(ShelfFloor aFloor, ShelfFloor bFloor) {
        this.aFloor = aFloor;
        this.bFloor = bFloor;
    }

    @Override
    public boolean isFeasibleMove(Match match) {
        return match.isFeasibleChangeShelvesMove(aFloor, bFloor);
    }

    @Override
    public void performMove(Match match) {
        match.performChangeShelvesMove(aFloor, bFloor);
    }

    @Override
    public String toString() {
        // TODO
        return new String("Generic Move");
    }
}
