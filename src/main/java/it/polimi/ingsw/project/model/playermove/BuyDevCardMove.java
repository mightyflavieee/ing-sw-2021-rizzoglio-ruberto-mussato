package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;

public class BuyDevCardMove extends PlayerMove{
    private String DevCardID;
    private List<Resource> paidResources;
    public BuyDevCardMove(Player player, View view, String DevCardID, List<Resource> paidResources) {
        super(player, view);
        this.DevCardID = DevCardID;
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
