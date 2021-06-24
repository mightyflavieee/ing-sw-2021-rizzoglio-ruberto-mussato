package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.List;
import java.util.Map;

public class ProductionMove extends Move{
    private final String devCardID;
    private final String leaderCardId;
    private final Map<ResourceType, Integer> resourcesToEliminateWarehouse;
    private final Map<ResourceType, Integer> resourcesToEliminateExtraDeposit;
    private final Map<ResourceType, Integer> resourcesToEliminateChest;
    private final ProductionType productionType;
    private final List<ResourceType> boardOrPerkManufacturedResource;

    // constructs the ProductionMove. If either devCardID or leaderCardId (or both) aren't required,
    // put null as a parameter
    public ProductionMove(String devCardID,
                          String leaderCardId,
                          Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                          Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                          Map<ResourceType, Integer> resourcesToEliminateChest,
                          ProductionType productionType,
                          List<ResourceType> boardOrPerkManufacturedResource) {
        this.devCardID = devCardID;
        this.leaderCardId = leaderCardId;
        this.resourcesToEliminateWarehouse = resourcesToEliminateWarehouse;
        this.resourcesToEliminateExtraDeposit = resourcesToEliminateExtraDeposit;
        this.resourcesToEliminateChest = resourcesToEliminateChest;
        this.productionType = productionType;
        this.boardOrPerkManufacturedResource = boardOrPerkManufacturedResource;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleProductionMove(this.devCardID, this.leaderCardId, this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateExtraDeposit, this.resourcesToEliminateChest, this.productionType);
    }

    @Override
    public void performMove(Match match){
        match.performProductionMove(this.devCardID, this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateExtraDeposit, this.resourcesToEliminateChest, this.productionType,
                this.boardOrPerkManufacturedResource);
    }

    @Override
    public String toString(){
        //TODO
        return "Production Move, type: " + this.productionType;
    }
}
