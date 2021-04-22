package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.Map;

public class BuyDevCardMove extends Move{
    private String devCardID;
    private DevCardPosition position;
    private Map<ResourceType, Integer> requiredResources;

    public BuyDevCardMove(String devCardID, DevCardPosition position, Map<ResourceType, Integer> requiredResources) {
        this.devCardID = devCardID;
        this.position = position;
        this.requiredResources = requiredResources;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleBuyDevCardMove(this.devCardID, this.requiredResources);
    }

    @Override
    public void performMove(Match match) {
        match.performBuyDevCardMove(this.devCardID, this.position);
    }

    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}
