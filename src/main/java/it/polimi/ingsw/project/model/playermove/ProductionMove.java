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

    /**
     * constructs the ProductionMove. If either devCardID or leaderCardId (or both) aren't required,
     * put null as a parameter
     * @param devCardIDs id of the developmentCards selected for the production
     * @param leaderCardIDs id of the leaderCards selected for the production
     * @param requiredResources the map of the resources required for the production
     * @param resourcesToEliminateWarehouse resources selected from the warehouse
     * @param resourcesToEliminateExtraDeposit resources selected from the extra deposit
     * @param resourcesToEliminateChest resources selected from the chest
     * @param productionType type of the production chosen
     * @param boardManufacturedResource resources manufactured by the board
      * @param perkManufacturedResource resources manufactured by the leaderCard perk
     */

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

    /**
     * it checks the correctness of the move
     * @param match it is passed by the controller to check the move
     * @return true if all checks are passed, false if not
     */
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

    /**
     * it performs the productionMove on the current match
     * @param match it is passed by the controller to check the move
     */
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
        return "Production Move, type: " + this.productionType;
    }
}
