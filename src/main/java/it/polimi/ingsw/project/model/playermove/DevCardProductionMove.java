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
    private String leaderCardId;
    private Map<ResourceType, Integer> resourcesToEliminateWarehouse;
    private Map<ResourceType, Integer> resourcesToEliminateChest;
    private ProductionType productionType;

    // constructs the DevCardProductionMove. If either devCardID or leaderCardId (or both) aren't required,
    // put null as a parameter
    public DevCardProductionMove(String devCardID, String leaderCardId,
                                 Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                 Map<ResourceType, Integer> resourcesToEliminateChest, ProductionType productionType) {
        this.devCardID = devCardID;
        this.leaderCardId = leaderCardId;
        this.resourcesToEliminateWarehouse = resourcesToEliminateWarehouse;
        this.resourcesToEliminateChest = resourcesToEliminateChest;
        this.productionType = productionType;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleDevCardProductionMove(this.devCardID, this.leaderCardId, this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateChest, this.productionType);
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
