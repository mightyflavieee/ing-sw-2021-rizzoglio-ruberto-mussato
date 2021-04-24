package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DevCardProductionMove extends Move{
    private String devCardID;
    private Map<ResourceType, Integer> resourcesToEliminateWarehouse;
    private Map<ResourceType, Integer> resourcesToEliminateChest;

    public DevCardProductionMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest) {
        this.devCardID = devCardID;
        this.resourcesToEliminateWarehouse = resourcesToEliminateWarehouse;
        this.resourcesToEliminateChest = resourcesToEliminateChest;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleDevCardProductionMove(this.devCardID, this.resourcesToEliminateWarehouse, this.resourcesToEliminateChest);
    }

    @Override
    public void performMove(Match match){
        match.performDevCardProductionMove(this.devCardID, this.resourcesToEliminateWarehouse, this.resourcesToEliminateChest);
    }

    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}
