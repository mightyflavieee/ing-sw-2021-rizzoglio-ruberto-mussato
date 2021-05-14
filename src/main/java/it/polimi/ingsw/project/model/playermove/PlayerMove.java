package it.polimi.ingsw.project.model.playermove;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;
public class PlayerMove implements Serializable {
    private final Player player;
    private final View view;
    private final MoveList moveList;
    public PlayerMove(Player player, View view, MoveList moveList) {
        this.player = player;
        this.view = view;
        this.moveList = moveList;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }
    public int getSize(){
        return this.moveList.getSize();
    }

    public void performMove(Match match, int i){
        this.moveList.get(i).performMove(match);
    }
    public boolean isFeasibleMove(Match match, int i) {
        if(i < this.getSize()) {
            return this.moveList.get(i).isFeasibleMove(match);
        }
        else return false;
    }
}
