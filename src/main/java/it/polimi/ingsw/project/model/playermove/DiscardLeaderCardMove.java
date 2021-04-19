package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;



public class DiscardLeaderCardMove extends Move{
    private String LeaderCardID;
    public DiscardLeaderCardMove (String LeaderCardID) {
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
    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}

