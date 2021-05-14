package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;

public class ActivateLeaderCardMove extends Move{
    private String leaderCardID;

    public ActivateLeaderCardMove(String leaderCardID) {
        this.leaderCardID = leaderCardID;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleActivateLeaderCardMove(this.leaderCardID);
    }

    @Override
    public void performMove(Match match){
        match.performActivateLeaderCardMove(this.leaderCardID);
    }

    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }

    public boolean isMainMove(){
        return false;
    }
}
