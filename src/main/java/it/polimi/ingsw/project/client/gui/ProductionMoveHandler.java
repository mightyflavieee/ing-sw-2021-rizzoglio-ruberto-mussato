package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.ProductionMove;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionMoveHandler {
    private DevelopmentCard developmentCard;
    private LeaderCard leaderCard;
    private SelectResourcesHandler selectResourcesHandler;
    private ProductionType productionType;
    private List<ResourceType> boardOrPerkManufacturedResource;
    private Map<ResourceType, Integer> resourcesRequired;
    private Map<ResourceType, Integer> boardRequiredResources;

    public DevelopmentCard getDevCard() {
        return developmentCard;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }

    public SelectResourcesHandler getSelectResourcesHandler() {
        return selectResourcesHandler;
    }

    public ProductionType getProductionType() {
        return productionType;
    }

    public List<ResourceType> getBoardOrPerkManufacturedResource() {
        return boardOrPerkManufacturedResource;
    }

    public Map<ResourceType, Integer> getResourcesRequired() { return resourcesRequired; }

    public Map<ResourceType, Integer> getBoardRequiredResources() { return boardRequiredResources; }

    public void setDevCard(DevelopmentCard developmentCard) {
        this.developmentCard = developmentCard;
    }

    public void setLeaderCard(LeaderCard leaderCard) {
        this.leaderCard = leaderCard;
    }

    public void setSelectResourcesHandler(SelectResourcesHandler selectResourcesHandler) {
        this.selectResourcesHandler = selectResourcesHandler;
    }

    public void setProductionType(ProductionType productionType) {
        this.productionType = productionType;
    }

    public void setBoardOrPerkManufacturedResource(ResourceType resourceType) {
        if (this.boardOrPerkManufacturedResource == null) {
            this.boardOrPerkManufacturedResource = new ArrayList<>();
            this.boardOrPerkManufacturedResource.add(resourceType);
        } else {
            this.boardOrPerkManufacturedResource.add(resourceType);
        }
    }

    public void setBoardRequireResources(Map<ResourceType, Integer> boardRequireResources) {
        if (this.boardRequiredResources == null) {
            this.boardRequiredResources = new HashMap<>();
        }
        this.boardRequiredResources = boardRequireResources;
    }

    // calculates the combined required resources for the production
    public Map<ResourceType, Integer> calculateResourcesRequired(DevelopmentCard developmentCard,
                                                                 LeaderCard leaderCard,
                                                                 Map<ResourceType, Integer> boardRequiredResources,
                                                                 ProductionType productionType) {
        Map<ResourceType, Integer> totalResourcesRequired = new HashMap<>();
        List<ResourceType> resourceTypeList = new ArrayList<>();
        resourceTypeList.add(ResourceType.Coin);
        resourceTypeList.add(ResourceType.Servant);
        resourceTypeList.add(ResourceType.Shield);
        resourceTypeList.add(ResourceType.Stone);
        switch (productionType) {
            case DevCard:
                for (ResourceType type : developmentCard.getProduction().getRequiredResources().keySet()) {
                    totalResourcesRequired.put(type, developmentCard.getProduction().getRequiredResources().get(type));
                }
                break;
            case LeaderCard:
                totalResourcesRequired.put(leaderCard.getPerk().getResource().getType(), 1);
                break;
            case Board:
                for (ResourceType type : boardRequiredResources.keySet()) {
                    totalResourcesRequired.put(type, boardRequiredResources.get(type));
                }
                break;
            case BoardAndDevCard:
                for (ResourceType type : resourceTypeList) {
                    if (developmentCard.getProduction().getRequiredResources().containsKey(type)) {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    developmentCard.getProduction().getRequiredResources().get(type) + boardRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, developmentCard.getProduction().getRequiredResources().get(type));
                        }
                    } else {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, boardRequiredResources.get(type));
                        }
                    }
                }
                break;
            case BoardAndLeaderCard:
                for (ResourceType type : resourceTypeList) {
                    if (leaderCard.getPerk().getResource().getType() == type) {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, 1 + boardRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, 1);
                        }
                    } else {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, boardRequiredResources.get(type));
                        }
                    }
                }
                break;
            case DevCardAndLeader:
                for (ResourceType type : resourceTypeList) {
                    if (developmentCard.getProduction().getRequiredResources().containsKey(type)) {
                        if (leaderCard.getPerk().getResource().getType() == type) {
                            totalResourcesRequired.put(type, developmentCard.getProduction().getRequiredResources().get(type) + 1);
                        } else {
                            totalResourcesRequired.put(type, 1);
                        }
                    } else {
                        if (leaderCard.getPerk().getResource().getType() == type) {
                            totalResourcesRequired.put(type, 1);
                        }
                    }
                }
                break;
            case BoardAndDevCardAndLeaderCard:
                for (ResourceType type : resourceTypeList) {
                    if (developmentCard.getProduction().getRequiredResources().containsKey(type)) {
                        if (leaderCard.getPerk().getResource().getType() == type) {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        developmentCard.getProduction().getRequiredResources().get(type) +
                                        boardRequiredResources.get(type) +
                                        1);
                            }
                        } else {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        developmentCard.getProduction().getRequiredResources().get(type) +
                                        boardRequiredResources.get(type));
                            } else {
                                totalResourcesRequired.put(type, developmentCard.getProduction().getRequiredResources().get(type));
                            }
                        }
                    } else {
                        if (leaderCard.getPerk().getResource().getType() == type) {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type, boardRequiredResources.get(type) + 1);
                            } else {
                                totalResourcesRequired.put(type, 1);
                            }
                        } else {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type, boardRequiredResources.get(type));
                            }
                        }
                    }
                }
                break;
        }
        this.resourcesRequired = totalResourcesRequired;
        return totalResourcesRequired;
    }

    public Move getMove() {
        ProductionMove productionMove = null;
        switch (productionType) {
            case Board:
                productionMove = new ProductionMove(null,
                        null,
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardOrPerkManufacturedResource);
                break;
            case DevCard:
            case BoardAndDevCard:
                productionMove = new ProductionMove(this.developmentCard.getId(),
                        null,
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardOrPerkManufacturedResource);
                break;
            case LeaderCard:
            case BoardAndLeaderCard:
                productionMove = new ProductionMove(null,
                        this.leaderCard.getId(),
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardOrPerkManufacturedResource);
                break;
            case DevCardAndLeader:
            case BoardAndDevCardAndLeaderCard:
                productionMove = new ProductionMove(this.developmentCard.getId(),
                        this.leaderCard.getId(),
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardOrPerkManufacturedResource);
                break;
        }
        return productionMove;
    }

    public void reset() {
        this.developmentCard = null;
        this.leaderCard = null;
        this.selectResourcesHandler = null;
        this.productionType = null;
        this.boardOrPerkManufacturedResource = null;
        this.resourcesRequired.clear();
        if (this.boardRequiredResources != null) {
            this.boardRequiredResources.clear();
        }
    }
}
