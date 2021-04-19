package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;



public class DiscardLeaderCardMove extends PlayerMove{
    private String LeaderCardID;
    public DiscardLeaderCardMove(Player player, View view, String LeaderCardID) {
        super(player, view);
        this.LeaderCardID = LeaderCardID;
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

