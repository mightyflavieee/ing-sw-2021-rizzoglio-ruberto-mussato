package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.ProductionMove;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionMoveHandler {
    private List<DevelopmentCard> developmentCards;
    private List<LeaderCard> leaderCards;
    private Map<ResourceType, Integer> resourcesRequired;
    private SelectResourcesHandler selectResourcesHandler;
    private ProductionType productionType;
    private ResourceType boardManufacturedResource;
    private List<ResourceType> perkManufacturedResources;
    private Map<ResourceType, Integer> boardRequiredResources;

    public List<DevelopmentCard> getDevCards() {
        return developmentCards;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public ProductionType getProductionType() {
        return productionType;
    }

    public Map<ResourceType, Integer> getResourcesRequired() { return resourcesRequired; }

    public Map<ResourceType, Integer> getBoardRequiredResources() { return boardRequiredResources; }

    public ResourceType getBoardManufacturedResource() { return boardManufacturedResource; }

    public List<ResourceType> getPerkManufacturedResource() { return perkManufacturedResources; }

    public void setDevCard(DevelopmentCard developmentCard) {
        if (this.developmentCards == null) {
            this.developmentCards = new ArrayList<>();
            this.developmentCards.add(developmentCard);
        } else {
            this.developmentCards.add(developmentCard);
        }
    }

    public void setLeaderCard(LeaderCard leaderCard) {
        if (this.leaderCards == null) {
            this.leaderCards = new ArrayList<>();
            this.leaderCards.add(leaderCard);
        } else {
            this.leaderCards.add(leaderCard);
        }
    }

    public void setSelectResourcesHandler(SelectResourcesHandler selectResourcesHandler) {
        this.selectResourcesHandler = selectResourcesHandler;
    }

    public void setProductionType(ProductionType productionType) {
        this.productionType = productionType;
    }

//    public void setBoardOrPerkManufacturedResource(ResourceType resourceType) {
//        if (this.boardOrPerkManufacturedResource == null) {
//            this.boardOrPerkManufacturedResource = new ArrayList<>();
//            this.boardOrPerkManufacturedResource.add(resourceType);
//        } else {
//            this.boardOrPerkManufacturedResource.add(resourceType);
//        }
//    }

    public void setBoardManufacturedResource(ResourceType boardManufacturedResource) {
        this.boardManufacturedResource = boardManufacturedResource;
    }

    public void setPerkManufacturedResources(ResourceType perkManufacturedResource) {
        if (this.perkManufacturedResources == null) {
            this.perkManufacturedResources = new ArrayList<>();
        }
        this.perkManufacturedResources.add(perkManufacturedResource);
    }

    public void setBoardRequireResources(Map<ResourceType, Integer> boardRequireResources) {
        if (this.boardRequiredResources == null) {
            this.boardRequiredResources = new HashMap<>();
        }
        this.boardRequiredResources = boardRequireResources;
    }

    // calculates the combined required resources for the production
    public Map<ResourceType, Integer> calculateResourcesRequired() {
        Map<ResourceType, Integer> totalResourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> devCardsRequiredResources = new HashMap<>();
        Map<ResourceType, Integer> leaderCardsRequiredResources = new HashMap<>();
        if (this.developmentCards != null) {
            devCardsRequiredResources = sumDevCardRequiredResources();
        }
        if (this.leaderCards != null) {
            leaderCardsRequiredResources = sumLeaderCardRequiredResources();
        }
        List<ResourceType> resourceTypeList = new ArrayList<>();
        resourceTypeList.add(ResourceType.Coin);
        resourceTypeList.add(ResourceType.Servant);
        resourceTypeList.add(ResourceType.Shield);
        resourceTypeList.add(ResourceType.Stone);
        switch (this.productionType) {
            case DevCard:
                totalResourcesRequired = devCardsRequiredResources;
//                for (ResourceType type : developmentCard.getProduction().getRequiredResources().keySet()) {
//                    totalResourcesRequired.put(type, developmentCard.getProduction().getRequiredResources().get(type));
//                }
                break;
            case LeaderCard:
                totalResourcesRequired = leaderCardsRequiredResources;
//                totalResourcesRequired.put(leaderCard.getPerk().getResource().getType(), 1);
                break;
            case Board:
                totalResourcesRequired = this.boardRequiredResources;
//                for (ResourceType type : boardRequiredResources.keySet()) {
//                    totalResourcesRequired.put(type, boardRequiredResources.get(type));
//                }
                break;
            case BoardAndDevCard:
                for (ResourceType type : resourceTypeList) {
                    if (devCardsRequiredResources.containsKey(type)) {
                        if (this.boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    devCardsRequiredResources.get(type) + this.boardRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, devCardsRequiredResources.get(type));
                        }
                    } else {
                        if (this.boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, this.boardRequiredResources.get(type));
                        }
                    }
                }
                break;
            case BoardAndLeaderCard:
                for (ResourceType type : resourceTypeList) {
                    if (leaderCardsRequiredResources.containsKey(type)) {
                        if (this.boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    leaderCardsRequiredResources.get(type) + this.boardRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, leaderCardsRequiredResources.get(type));
                        }
                    } else {
                        if (this.boardRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, this.boardRequiredResources.get(type));
                        }
                    }
                }
                break;
            case DevCardAndLeader:
                for (ResourceType type : resourceTypeList) {
                    if (devCardsRequiredResources.containsKey(type)) {
                        if (leaderCardsRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type,
                                    devCardsRequiredResources.get(type) + leaderCardsRequiredResources.get(type));
                        } else {
                            totalResourcesRequired.put(type, devCardsRequiredResources.get(type));
                        }
                    } else {
                        if (leaderCardsRequiredResources.containsKey(type)) {
                            totalResourcesRequired.put(type, leaderCardsRequiredResources.get(type));
                        }
                    }
                }
                break;
            case BoardAndDevCardAndLeaderCard:
                for (ResourceType type : resourceTypeList) {
                    if (devCardsRequiredResources.containsKey(type)) {
                        if (leaderCardsRequiredResources.containsKey(type)) {
                            if (this.boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        devCardsRequiredResources.get(type) +
                                        leaderCardsRequiredResources.get(type) +
                                        this.boardRequiredResources.get(type));
                            } else {
                                totalResourcesRequired.put(type,
                                        devCardsRequiredResources.get(type) + leaderCardsRequiredResources.get(type));
                            }
                        } else {
                            if (this.boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        devCardsRequiredResources.get(type) +
                                        this.boardRequiredResources.get(type));
                            } else {
                                totalResourcesRequired.put(type, devCardsRequiredResources.get(type));
                            }
                        }
                    } else {
                        if (leaderCardsRequiredResources.containsKey(type)) {
                            if (this.boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type,
                                        leaderCardsRequiredResources.get(type) + this.boardRequiredResources.get(type));
                            } else {
                                totalResourcesRequired.put(type, leaderCardsRequiredResources.get(type));
                            }
                        } else {
                            if (boardRequiredResources.containsKey(type)) {
                                totalResourcesRequired.put(type, this.boardRequiredResources.get(type));
                            }
                        }
                    }
                }
                break;
        }
        this.resourcesRequired = totalResourcesRequired;
        return totalResourcesRequired;
    }

    private Map<ResourceType, Integer> sumLeaderCardRequiredResources() {
        Map<ResourceType, Integer> leaderCardsRequiredResources = new HashMap<>();
        for (LeaderCard leaderCard: this.leaderCards) {
            ResourceType leaderCardPerkResourceType = leaderCard.getPerk().getResource().getType();
            if (leaderCardsRequiredResources.containsKey(leaderCardPerkResourceType)) {
                leaderCardsRequiredResources.put(leaderCardPerkResourceType,
                        leaderCardsRequiredResources.get(leaderCardPerkResourceType) + 1);
            } else {
                leaderCardsRequiredResources.put(leaderCardPerkResourceType, 1);
            }
        }
        return leaderCardsRequiredResources;
    }

    private Map<ResourceType, Integer> sumDevCardRequiredResources() {
        Map<ResourceType, Integer> devCardsRequiredResources = new HashMap<>();
        for (DevelopmentCard devCard: this.developmentCards) {
            devCardsRequiredResources = Utils.sumResourcesMaps(devCardsRequiredResources, devCard.getProduction().getRequiredResources());
        }
        return devCardsRequiredResources;
    }

    public Move getMove() {
        ProductionMove productionMove = null;
        List<String> devCardIDs = new ArrayList<>();
        List<String> leaderCardIDs = new ArrayList<>();
        if (this.developmentCards != null) {
            for (DevelopmentCard devCard : this.developmentCards) {
                devCardIDs.add(devCard.getId());
            }
        }
        if (this.leaderCards != null) {
            for (LeaderCard leaderCard : this.leaderCards) {
                leaderCardIDs.add(leaderCard.getId());
            }
        }
        switch (productionType) {
            case Board:
                productionMove = new ProductionMove(null,
                        null,
                        this.resourcesRequired,
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardManufacturedResource,
                        this.perkManufacturedResources);
                break;
            case DevCard:
            case BoardAndDevCard:
                productionMove = new ProductionMove(devCardIDs,
                        null,
                        this.resourcesRequired,
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardManufacturedResource,
                        this.perkManufacturedResources);
                break;
            case LeaderCard:
            case BoardAndLeaderCard:
                productionMove = new ProductionMove(null,
                        leaderCardIDs,
                        this.resourcesRequired,
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardManufacturedResource,
                        this.perkManufacturedResources);
                break;
            case DevCardAndLeader:
            case BoardAndDevCardAndLeaderCard:
                productionMove = new ProductionMove(devCardIDs,
                        leaderCardIDs,
                        this.resourcesRequired,
                        this.selectResourcesHandler.getResourcesFromWarehouse(),
                        this.selectResourcesHandler.getResourcesFromExtraDeposit(),
                        this.selectResourcesHandler.getResourcesFromChest(),
                        this.productionType,
                        this.boardManufacturedResource,
                        this.perkManufacturedResources);
                break;
        }
        return productionMove;
    }

    public void reset() {
        this.developmentCards = null;
        this.leaderCards = null;
        this.resourcesRequired.clear();
        this.selectResourcesHandler = null;
        this.productionType = null;
        this.boardManufacturedResource = null;
        if (this.perkManufacturedResources != null) {
            this.perkManufacturedResources.clear();
        }
        if (this.boardRequiredResources != null) {
            this.boardRequiredResources.clear();
        }
    }
}
