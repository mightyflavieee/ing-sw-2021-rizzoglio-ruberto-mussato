package it.polimi.ingsw.project.model.playermove;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;
public class PlayerMove implements Serializable {
    private final Player player;
    private final View view;
    private final Move move;
    public PlayerMove(Player player, View view, Move move) {
        this.player = player;
        this.view = view;
        this.move = move;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public void performMove(Match match){
        this.move.performMove(match);
    }
    public boolean isFeasibleMove(Match match) {
            return this.move.isFeasibleMove(match);
    }
    public boolean isMainMove(){
        return this.move.isMainMove();
    }
}
