package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;

public class ActivateLeaderCardMove extends PlayerMove{
    private String LeaderCardID;
    private List<Resource> paidResources;
    public ActivateLeaderCardMove(Player player, View view, String LeaderCardID, List<Resource> paidResources) {
        super(player, view);
        this.LeaderCardID = LeaderCardID;
        Collections.copy(this.paidResources, paidResources);
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
