package it.polimi.ingsw.project.model.playermove;

import java.util.List;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;
//messaggio ricevuto dal server
public class PlayerMove {
    private final Player player;
    private final View view;

    public PlayerMove(Player player, View view) {
        this.player = player;
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public boolean isFeasibleMove(Match match){
        //TODO
        return false;
    }
    public void performMove(Match match){
        //TODO
    }
}
