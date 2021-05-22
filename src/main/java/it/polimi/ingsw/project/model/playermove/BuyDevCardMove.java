package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.Map;

public class BuyDevCardMove extends Move{
    private String devCardID;
    private DevCardPosition position;
    private Map<ResourceType, Integer> resourcesToEliminateWarehouse;
    private Map<ResourceType, Integer> resourcesToEliminateChest;

    public BuyDevCardMove(String devCardID, DevCardPosition position, Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest) {
        this.devCardID = devCardID;
        this.position = position;
        this.resourcesToEliminateWarehouse = resourcesToEliminateWarehouse;
        this.resourcesToEliminateChest = resourcesToEliminateChest;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleBuyDevCardMove(this.devCardID, this.resourcesToEliminateWarehouse, this.resourcesToEliminateChest, this.position);
    }

    @Override
    public void performMove(Match match) {
        match.performBuyDevCardMove(this.devCardID, this.resourcesToEliminateWarehouse, this.resourcesToEliminateChest, this.position);
    }

    @Override
    public String toString(){
        return new String("Buy Dev Card Move, Dev Card ID:" + this.devCardID);
    }
}
