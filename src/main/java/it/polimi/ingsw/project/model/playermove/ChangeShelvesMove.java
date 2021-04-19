package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.view.View;

public class ChangeShelvesMove extends PlayerMove{
    private ShelfFloor aFloor;
    private ShelfFloor bFloor;
    public ChangeShelvesMove(Player player, View view, ShelfFloor aFloor, ShelfFloor bFloor) {
        super(player, view);
        this.aFloor = aFloor;
        this.bFloor = bFloor;
    }
    @Override
    public boolean isFeasibleMove(Match match){
        //TODO
        return false;
    }
    @Override
    public void performMove(Match match){
        //TODO
    }
}
