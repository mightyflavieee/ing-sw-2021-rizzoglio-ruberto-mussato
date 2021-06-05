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
                for (ResourceType type : developmentCard.getRequiredResources().keySet()) {
                    totalResourcesRequired.put(type, leaderCard.getRequiredResources().get(type));
                }
                break;
            case LeaderCard:
                for (ResourceType type : leaderCard.getRequiredResources().keySet()) {
                    totalResourcesRequired.put(type, leaderCard.getRequiredResources().get(type));
                }
                break;
            case Board:
                for (ResourceType type : boardRequiredResources.keySet()) {
                    totalResourcesRequired.put(type, boardRequiredResources.get(type));
                }
                break;
            case BoardAndDevCard:
                for (ResourceType type : resourceTypeList) {
                    if (developmentCard.getRequiredResources().containsKey(type)) {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    developmentCard.getRequiredResources().get(type) + boardRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, developmentCard.getRequiredResources().get(type));
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
                    if (leaderCard.getRequiredResources().containsKey(type)) {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    leaderCard.getRequiredResources().get(type) + boardRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, leaderCard.getRequiredResources().get(type));
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
                    if (developmentCard.getRequiredResources().containsKey(type)) {
                        if (leaderCard.getRequiredResources().containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    developmentCard.getRequiredResources().get(type) + leaderCard.getRequiredResources().get(type));
                        } else {
                            totalResourcesRequired.put(type, leaderCard.getRequiredResources().get(type));
                        }
                    } else {
                        if (boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, leaderCard.getRequiredResources().get(type));
                        }
                    }
                }
                break;
            case BoardAndDevCardAndLeaderCard:
                for (ResourceType type : resourceTypeList) {
                    if (developmentCard.getRequiredResources().containsKey(type)) {
                        if (leaderCard.getRequiredResources().containsKey(type)) {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        developmentCard.getRequiredResources().get(type) +
                                        leaderCard.getRequiredResources().get(type) +
                                        boardRequiredResources.get(type));
                            }
                        } else {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        developmentCard.getRequiredResources().get(type) +
                                        boardRequiredResources.get(type));
                            } else {
                                totalResourcesRequired.put(type, developmentCard.getRequiredResources().get(type));
                            }
                        }
                    } else {
                        if (leaderCard.getRequiredResources().containsKey(type)) {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        leaderCard.getRequiredResources().get(type) +
                                        boardRequiredResources.get(type));
                            } else {
                                totalResourcesRequired.put(type, leaderCard.getRequiredResources().get(type));
                            }
                        } else {
                            totalResourcesRequired.put(type, boardRequiredResources.get(type));
                        }
                    }
                }
                break;
        }
        this.resourcesRequired = totalResourcesRequired;
        return totalResourcesRequired;
    }

    public Move getMove() {
        return new ProductionMove(this.developmentCard.getId(),
                this.leaderCard.getId(),
                this.selectResourcesHandler.getResourcesFromWarehouse(),
                this.selectResourcesHandler.getResourcesFromChest(),
                this.productionType,
                this.boardOrPerkManufacturedResource);
    }

    public void reset() {
        this.developmentCard = null;
        this.leaderCard = null;
        this.selectResourcesHandler = null;
        this.productionType = null;
        this.boardOrPerkManufacturedResource = null;
        this.resourcesRequired.clear();
        this.boardRequiredResources.clear();
    }
}
