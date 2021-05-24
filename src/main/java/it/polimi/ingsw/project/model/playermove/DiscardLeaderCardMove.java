package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.view.View;



public class DiscardLeaderCardMove extends Move{
    private final String leaderCardID;
    public DiscardLeaderCardMove (String leaderCardID) {
        this.leaderCardID = leaderCardID;
    }
    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleDiscardLeaderCardMove(this.leaderCardID);
    }
    @Override
    public void performMove(Match match){
        match.performDiscardLeaderCardMove(this.leaderCardID);
    }
    @Override
    public String toString(){
        return "Discard Leader Card Move, Leader CardID: " + this.leaderCardID;
    }


    public boolean isMainMove(){
        return false;
    }
}

