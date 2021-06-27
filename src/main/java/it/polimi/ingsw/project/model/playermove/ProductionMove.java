package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.List;
import java.util.Map;

public class ProductionMove extends Move{
    private final List<String> devCardIDs;
    private final List<String> leaderCardIDs;
    private final Map<ResourceType, Integer> requiredResources;
    private final Map<ResourceType, Integer> resourcesToEliminateWarehouse;
    private final Map<ResourceType, Integer> resourcesToEliminateExtraDeposit;
    private final Map<ResourceType, Integer> resourcesToEliminateChest;
    private final ProductionType productionType;
    private final ResourceType boardManufacturedResource;
    private final List<ResourceType> perkManufacturedResource;

    // constructs the ProductionMove. If either devCardID or leaderCardId (or both) aren't required,
    // put null as a parameter
    public ProductionMove(List<String> devCardIDs,
                          List<String> leaderCardIDs,
                          Map<ResourceType, Integer> requiredResources,
                          Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                          Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                          Map<ResourceType, Integer> resourcesToEliminateChest,
                          ProductionType productionType,
                          ResourceType boardManufacturedResource,
                          List<ResourceType> perkManufacturedResource) {
        this.devCardIDs = devCardIDs;
        this.leaderCardIDs = leaderCardIDs;
        this.requiredResources = requiredResources;
        this.resourcesToEliminateWarehouse = resourcesToEliminateWarehouse;
        this.resourcesToEliminateExtraDeposit = resourcesToEliminateExtraDeposit;
        this.resourcesToEliminateChest = resourcesToEliminateChest;
        this.productionType = productionType;
        this.boardManufacturedResource = boardManufacturedResource;
        this.perkManufacturedResource = perkManufacturedResource;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleProductionMove(this.devCardIDs,
                this.leaderCardIDs,
                this.requiredResources,
                this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateExtraDeposit,
                this.resourcesToEliminateChest,
                this.productionType);
    }

    @Override
    public void performMove(Match match){
        match.performProductionMove(this.devCardIDs,
                this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateExtraDeposit,
                this.resourcesToEliminateChest,
                this.productionType,
                this.boardManufacturedResource,
                this.perkManufacturedResource);
    }

    @Override
    public String toString(){
        //TODO
        return "Production Move, type: " + this.productionType;
    }
}
