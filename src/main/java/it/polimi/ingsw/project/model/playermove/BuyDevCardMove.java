package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;

public class BuyDevCardMove extends Move{
    private String DevCardID;
    private List<Resource> paidResources;
    public BuyDevCardMove(String DevCardID, List<Resource> paidResources) {
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
    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}
