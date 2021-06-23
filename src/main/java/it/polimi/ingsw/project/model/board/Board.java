package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.Status;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.*;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable, Cloneable {

  // TODO: fare clone di tutti gli oggetti
  private Map<ResourceType, Integer> chest;
  private Map<DevCardPosition, List<DevelopmentCard>> mapTray;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;
  private FaithMap faithMap;
  private List<ResourceType> discounts;
  private List<ResourceType> transmutation;

  public Board() {
    this.chest = new HashMap<>();
    this.chest.put(ResourceType.Coin, 0);
    this.chest.put(ResourceType.Stone, 0);
    this.chest.put(ResourceType.Shield, 0);
    this.chest.put(ResourceType.Servant, 0);
    this.mapTray = new HashMap<>();
    List<DevelopmentCard> listOfDevCardsLeft = new ArrayList<>();
    List<DevelopmentCard> listOfDevCardsCenter = new ArrayList<>();
    List<DevelopmentCard> listOfDevCardsRight = new ArrayList<>();
    this.mapTray.put(DevCardPosition.Left, listOfDevCardsLeft);
    this.mapTray.put(DevCardPosition.Center, listOfDevCardsCenter);
    this.mapTray.put(DevCardPosition.Right, listOfDevCardsRight);
    this.leaderCards = new ArrayList<>();
    this.discounts = new ArrayList<>();
    this.transmutation = new ArrayList<>();
  }

  public void createFaithMapAndWarehouse(Match match, Player player) {
    this.faithMap = new FaithMap(match, player);
    this.warehouse = new Warehouse(match);
  }

  @Override
  public final Board clone() {
    // TODO clone interne
    final Board result = new Board();
    result.chest = chest;
    result.mapTray = mapTray;
    result.warehouse = warehouse;
    result.leaderCards = leaderCards;
    result.faithMap = faithMap;
    result.discounts = discounts;
    result.transmutation = transmutation;
    return result;
  }

  public void readdObservers(Match match, Player player) {
    this.warehouse.readdObservers(match);
    this.faithMap.readdObservers(match, player);
  }

  public FaithMap getFaithMap() {
    return faithMap;
  }

  public Map<DevCardPosition, List<DevelopmentCard>> getMapTray() {
    return this.mapTray;
  }

  public Map<ResourceType, Integer> getChest() {
    return this.chest;
  }

  public Warehouse getWarehouse() {
    return this.warehouse;
  }

  public List<LeaderCard> getLeaderCards() {
    return this.leaderCards;
  }

  public List<ResourceType> getDiscounts() { return this.discounts; }

  public List<ResourceType> getTransmutation() {
    return transmutation;
  }

  // it extracts the last DevelopmentCard in from the mapTray at that position
  private DevelopmentCard getLastFromPosition(DevCardPosition position) {
    if (this.mapTray.get(position).size() == 0) {
      return null;
    }
    return mapTray.get(position).get(mapTray.get(position).size() - 1);
  }

  // it extracts all the last cards for the production
  public Map<DevCardPosition, DevelopmentCard> getCurrentProductionCards() {
    Map<DevCardPosition, DevelopmentCard> productionCardsMap = new HashMap<>();
    productionCardsMap.put(DevCardPosition.Left, getLastFromPosition(DevCardPosition.Left));
    productionCardsMap.put(DevCardPosition.Center, getLastFromPosition(DevCardPosition.Center));
    productionCardsMap.put(DevCardPosition.Right, getLastFromPosition(DevCardPosition.Right));
    return productionCardsMap;
  }

  // it moves the player forward on the faithMap
  public void moveForward() {
    faithMap.moveForward();
  }

  public int papalCouncil(int numTile) {
    return faithMap.papalCouncil(numTile);
  }

  public int moveForwardBlack() {
    return this.faithMap.moveForwardBlack();
  }

  // fetches a DevelopmentCard by Id in the mapTray, returns null if not present
  public DevelopmentCard fetchDevCardById(String devCardID) {
    for (DevCardPosition position : this.mapTray.keySet()) {
      for (DevelopmentCard card : this.mapTray.get(position)) {
        if (card.getId().equals(devCardID)) {
          return card;
        }
      }
    }
    // if card not found, return null
    return null;
  }

  // fetches a LeaderCard by Id in this.leaderCards, returns null if not present
  public LeaderCard fetchLeaderCardById(String leaderCardID) {
    for (LeaderCard card : this.leaderCards) {
      if (card.getId().equals(leaderCardID)) {
        return card;
      }
    }
    return null;
  }

  // adds the indicated resources to the Strongbox
  private void addToStrongbox(Map<ResourceType, Integer> resourcesToAdd) {
    for (ResourceType type : resourcesToAdd.keySet()) {
      if (this.chest.containsKey(type)) {
        this.chest.put(type, this.chest.get(type) + resourcesToAdd.get(type));
      } else {
        if (type == ResourceType.Faith) {
          moveForward();
        } else {
          this.chest.put(type, resourcesToAdd.get(type));
        }
      }
    }
  }

  // double checks if the resources indicated by the user are actually present
  // usable in "isFeasible" and "perform" methods only within BuyDevCardMove
  // and DevCardProductionMove
  public boolean areEnoughResourcesPresentForBuyAndProduction(Map<ResourceType, Integer> resourcesToEliminateWarehouse,
      Map<ResourceType, Integer> resourcesToEliminateChest) {
    Map<ResourceType, Integer> warehouseResources = this.warehouse.mapAllContainedResources();
    if (resourcesToEliminateWarehouse != null) {
      for (ResourceType type : resourcesToEliminateWarehouse.keySet()) {
        if (warehouseResources.containsKey(type)) {
          if (warehouseResources.get(type) < resourcesToEliminateWarehouse.get(type)) {
            return false;
          }
        } else {
          return false;
        }
      }
    }
    if (resourcesToEliminateChest != null) {
      for (ResourceType type : resourcesToEliminateChest.keySet()) {
        if (this.chest.containsKey(type)) {
          if (this.chest.get(type) < resourcesToEliminateChest.get(type)) {
            return false;
          }
        } else {
          return false;
        }
      }
    }
    return true;
  }

  // checks if the resources indicated in the parameter are actually present
  public boolean areEnoughResourcesPresent(Map<ResourceType, Integer> resourcesToCheck) {
    Map<ResourceType, Integer> warehouseResources = this.warehouse.mapAllContainedResources();
    if (resourcesToCheck != null) {
      for (ResourceType type : resourcesToCheck.keySet()) {
        if (warehouseResources.containsKey(type)) {
          if (this.chest.containsKey(type)) {
            if ((warehouseResources.get(type) + this.chest.get(type)) < resourcesToCheck.get(type)) {
              return false;
            }
          } else {
            if (warehouseResources.get(type) < resourcesToCheck.get(type)) {
              return false;
            }
          }
        } else {
          if (this.chest.containsKey(type)) {
            if (this.chest.get(type) < resourcesToCheck.get(type)) {
              return false;
            }
          } else {
            return false;
          }
        }
      }
    }
    return true;
  }

  // eliminates resources from the chest, usable only within
  // performBuyDevCardMove()
  private void eliminateResourcesFromChest(Map<ResourceType, Integer> resourcesToEliminate) {
    List<ResourceType> resourceTypeToEliminate = new ArrayList<>();
    for (ResourceType type : this.chest.keySet()) {
      if (resourcesToEliminate.containsKey(type)) {
        // if (this.chest.get(type).equals(resourcesToEliminate.get(type))) {
        // resourceTypeToEliminate.add(type);
        // } else {
        this.chest.put(type, this.chest.get(type) - resourcesToEliminate.get(type));
        // }
      }
    }
    // if (resourceTypeToEliminate.size() > 0) {
    // for (ResourceType type : resourceTypeToEliminate) {
    // this.chest.remove(type);
    // }
    // }
  }

  // converts a CardLevel type to int
  private int getIntegerFromCardLevel(CardLevel level) {
    if (level.equals(CardLevel.One)) {
      return 1;
    } else {
      if (level.equals(CardLevel.Two)) {
        return 2;
      } else {
        return 3;
      }
    }
  }

  // extracts the number of DevelopmentCards by color
  private Map<CardColor, Integer> getCurrentDevCardsNumberByColor() {
    Map<CardColor, Integer> currentDevCards = new HashMap<>();
    int emeraldCount = 0;
    int amethystCount = 0;
    int sapphireCount = 0;
    int goldCount = 0;
    for (DevCardPosition position : this.mapTray.keySet()) {
      for (DevelopmentCard devCard : this.mapTray.get(position)) {
        if (devCard.getColor().equals(CardColor.Emerald)) {
          emeraldCount++;
        }
        if (devCard.getColor().equals(CardColor.Amethyst)) {
          amethystCount++;
        }
        if (devCard.getColor().equals(CardColor.Sapphire)) {
          sapphireCount++;
        }
        if (devCard.getColor().equals(CardColor.Gold)) {
          goldCount++;
        }
      }
    }
    currentDevCards.put(CardColor.Emerald, emeraldCount);
    currentDevCards.put(CardColor.Amethyst, amethystCount);
    currentDevCards.put(CardColor.Sapphire, sapphireCount);
    currentDevCards.put(CardColor.Gold, goldCount);
    return currentDevCards;
  }

  private Map<CardColor, CardLevel> getCurrentDevCardHigherLevelForEachCardColor() {
    Map<CardColor, CardLevel> currentDevCardsHigherLevels = new HashMap<>();
    currentDevCardsHigherLevels.put(CardColor.Gold, null);
    currentDevCardsHigherLevels.put(CardColor.Amethyst, null);
    currentDevCardsHigherLevels.put(CardColor.Emerald, null);
    currentDevCardsHigherLevels.put(CardColor.Sapphire, null);
    for (DevCardPosition position : this.mapTray.keySet()) {
      for (DevelopmentCard devCard : this.mapTray.get(position)) {
        if (devCard.getColor().equals(CardColor.Gold)) {
          if (currentDevCardsHigherLevels.get(CardColor.Gold) == null) {
            currentDevCardsHigherLevels.put(CardColor.Gold, devCard.getLevel());
          } else {
            if (getIntegerFromCardLevel(currentDevCardsHigherLevels.get(CardColor.Gold)) < getIntegerFromCardLevel(
                devCard.getLevel())) {
              currentDevCardsHigherLevels.put(CardColor.Gold, devCard.getLevel());
            }
          }
        }
        if (devCard.getColor().equals(CardColor.Emerald)) {
          if (currentDevCardsHigherLevels.get(CardColor.Emerald) == null) {
            currentDevCardsHigherLevels.put(CardColor.Emerald, devCard.getLevel());
          } else {
            if (getIntegerFromCardLevel(currentDevCardsHigherLevels.get(CardColor.Emerald)) < getIntegerFromCardLevel(
                devCard.getLevel())) {
              currentDevCardsHigherLevels.put(CardColor.Emerald, devCard.getLevel());
            }
          }
        }
        if (devCard.getColor().equals(CardColor.Amethyst)) {
          if (currentDevCardsHigherLevels.get(CardColor.Amethyst) == null) {
            currentDevCardsHigherLevels.put(CardColor.Amethyst, devCard.getLevel());
          } else {
            if (getIntegerFromCardLevel(currentDevCardsHigherLevels.get(CardColor.Amethyst)) < getIntegerFromCardLevel(
                devCard.getLevel())) {
              currentDevCardsHigherLevels.put(CardColor.Amethyst, devCard.getLevel());
            }
          }
        }
        if (devCard.getColor().equals(CardColor.Sapphire)) {
          if (currentDevCardsHigherLevels.get(CardColor.Sapphire) == null) {
            currentDevCardsHigherLevels.put(CardColor.Sapphire, devCard.getLevel());
          } else {
            if (getIntegerFromCardLevel(currentDevCardsHigherLevels.get(CardColor.Sapphire)) < getIntegerFromCardLevel(
                devCard.getLevel())) {
              currentDevCardsHigherLevels.put(CardColor.Sapphire, devCard.getLevel());
            }
          }
        }
      }
    }
    return currentDevCardsHigherLevels;
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
    for (LeaderCard leaderCard : this.leaderCards) {
      if (leaderCard.getId().equals(leaderCardID)) {
        return true;
      }
    }
    return false;
  }

  public void performDiscardLeaderCardMove(String leaderCardID) {
    this.moveForward();
    for (int i = 0; i < this.leaderCards.size(); i++) {
      if (this.leaderCards.get(i).getId().equals(leaderCardID)) {
        this.leaderCards.remove(i);
        return;
      }
    }
  }

  // checks if the current player can buy the card he/she selected
  public boolean isFeasibleBuyDevCardMove(DevelopmentCard devCard,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      DevCardPosition position) {
    int intLevel = 0;
    boolean isOneLessCardLevelPresent = false;
    boolean isDiscountPresent;
    // double checks if the resources indicated by the user are actually present
    if (!areEnoughResourcesPresentForBuyAndProduction(resourcesToEliminateWarehouse, resourcesToEliminateChest)) {
      return false;
    }
    // for each type of resource required, if there isn't enough resources, the move
    // is not feasible (return false)
    for (ResourceType type : devCard.getRequiredResources().keySet()) {
      // verifies if there are any discount on the current ResourceType
      isDiscountPresent = false;
      if (this.discounts != null) {
        for (ResourceType discountType : this.discounts) {
          if (discountType.equals(type)) {
            isDiscountPresent = true;
            break;
          }
        }
      }
      // verifies if there are enough resources to buy the DevelopmentCard
      // (takes into account the eventual discounts)
      if (resourcesToEliminateWarehouse.containsKey(type)) {
        if (resourcesToEliminateChest.containsKey(type)) {
          if (isDiscountPresent) {
            if ((devCard.getRequiredResources().get(type) - 1) > (resourcesToEliminateWarehouse.get(type)
                + resourcesToEliminateChest.get(type))) {
              return false;
            }
          } else {
            if (devCard.getRequiredResources()
                .get(type) > (resourcesToEliminateWarehouse.get(type) + resourcesToEliminateChest.get(type))) {
              return false;
            }
          }
        } else {
          if (isDiscountPresent) {
            if ((devCard.getRequiredResources().get(type) - 1) > resourcesToEliminateWarehouse.get(type)) {
              return false;
            }
          } else {
            if (devCard.getRequiredResources().get(type) > resourcesToEliminateWarehouse.get(type)) {
              return false;
            }
          }
        }
      } else {
        if (resourcesToEliminateChest.containsKey(type)) {
          if (isDiscountPresent) {
            if ((devCard.getRequiredResources().get(type) - 1) > resourcesToEliminateChest.get(type)) {
              return false;
            }
          } else {
            if (devCard.getRequiredResources().get(type) > resourcesToEliminateChest.get(type)) {
              return false;
            }
          }
        } else {
          return false;
        }
      }
    }
    // checks if there is a card of one level less (or none at all if the card is
    // level 1) than the card the player wants to buy on the position indicated
    // by the player (return false if there isn't)
    if (this.mapTray.get(position).size() == 0) {
      if (devCard.getLevel() != CardLevel.One) {
        return false;
      } else {
        isOneLessCardLevelPresent = true;
      }
    } else {
      CardLevel currentCardLevel = this.mapTray.get(position).get(this.mapTray.get(position).size() - 1).getLevel();
      int currentCardIntLevel = 0;
      if (devCard.getLevel() == CardLevel.One) {
        intLevel = 1;
      }
      if (devCard.getLevel() == CardLevel.Two) {
        intLevel = 2;
      }
      if (devCard.getLevel() == CardLevel.Three) {
        intLevel = 3;
      }
      if (currentCardLevel == CardLevel.One) {
        currentCardIntLevel = 1;
      }
      if (currentCardLevel == CardLevel.Two) {
        currentCardIntLevel = 2;
      }
      if (currentCardLevel == CardLevel.Three) {
        currentCardIntLevel = 3;
      }
      if (currentCardIntLevel == intLevel - 1) {
        isOneLessCardLevelPresent = true;
      }
    }
    // if isOneLessCardLevelPresent is false, the function returns false
    return isOneLessCardLevelPresent;
  }

  // puts the bought DevelopmentCard in the mapTray and eliminates the required
  // resources from the warehouse or the strongbox (this.chest)
  public void performBuyDevCardMove(DevelopmentCard devCard, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
      Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
    this.mapTray.get(position).add(devCard);
    this.warehouse.eliminateResources(resourcesToEliminateWarehouse);
    eliminateResourcesFromChest(resourcesToEliminateChest);
  }

  // checks if the current player can activate the production selected
  public boolean isFeasibleProductionMove(String devCardID, String leaderCardId,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      ProductionType productionType) {
    boolean isDevCardInLastPosition = false;
    // checks if the DevelopmentCard selected is in the last position (if the
    // DevelopmentCard is required
    // for the production)
    if (productionType == ProductionType.DevCard || productionType == ProductionType.BoardAndDevCard
        || productionType == ProductionType.BoardAndDevCardAndLeaderCard
        || productionType == ProductionType.DevCardAndLeader) {
      for (DevCardPosition position : this.mapTray.keySet()) {
        if (this.mapTray.get(position).size() > 0) {
          DevelopmentCard lastElement = this.mapTray.get(position).get(this.mapTray.get(position).size() - 1);
          if (lastElement.getId().equals(devCardID)) {
            isDevCardInLastPosition = true;
            break;
          }
        }
      }
      // if the card is not in the last position, return false
      if (!isDevCardInLastPosition) {
        return false;
      }
    }
    // checks if the LeaderCard is present (if required for the production)
    if (productionType == ProductionType.LeaderCard || productionType == ProductionType.BoardAndLeaderCard
        || productionType == ProductionType.BoardAndDevCardAndLeaderCard
        || productionType == ProductionType.DevCardAndLeader) {
      if (fetchLeaderCardById(leaderCardId) == null) {
        return false;
      }
    }
    // double checks if the resources indicated by the user are actually present
    return areEnoughResourcesPresentForBuyAndProduction(resourcesToEliminateWarehouse, resourcesToEliminateChest);
  }

  // performs the production putting the resources manufactured in the strongbox
  // and eliminating
  // the resources required
  public void performProductionMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
      Map<ResourceType, Integer> resourcesToEliminateChest, ProductionType productionType,
      List<ResourceType> boardOrPerkManufacturedResource) {
    Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
    if (productionType == ProductionType.DevCard || productionType == ProductionType.BoardAndDevCard) {
      DevelopmentCard card = fetchDevCardById(devCardID);
      manufacturedResources = card.getProduction().getManufacturedResources();
      if (productionType == ProductionType.BoardAndDevCard) {
        // aggiunge a manufacturedResources le risorse prodotte dalla Board
        ResourceType resourceType = boardOrPerkManufacturedResource.get(0);
        if (manufacturedResources.containsKey(resourceType)) {
          manufacturedResources.put(resourceType, manufacturedResources.get(resourceType) + 1);
        } else {
          manufacturedResources.put(resourceType, 1);
        }
      }
    }
    if (productionType == ProductionType.LeaderCard || productionType == ProductionType.BoardAndLeaderCard) {
      // aggiunge a manufacturedResources le risorse prodotte dal ProductionPerk
      // (LeaderCard
      moveForward();
      manufacturedResources.put(boardOrPerkManufacturedResource.get(0), 1);
      if (productionType == ProductionType.BoardAndLeaderCard) {
        // aggiungere a manufacturedResources le risorse prodotte dalla Board
        ResourceType resourceType = boardOrPerkManufacturedResource.get(1);
        if (manufacturedResources.containsKey(resourceType)) {
          manufacturedResources.put(resourceType, 2);
        } else {
          manufacturedResources.put(resourceType, 1);
        }
      }
    }
    // aggiunge a manufacturedResources le risorse prodotte dalla Board
    if (productionType == ProductionType.Board) {
      manufacturedResources.put(boardOrPerkManufacturedResource.get(0), 1);
    }
    // aggiunge a manufacturedResources le risorse prodotte dalla DevelopmentCard e
    // dalla LeaderCard
    if (productionType == ProductionType.DevCardAndLeader) {
      DevelopmentCard card = fetchDevCardById(devCardID);
      manufacturedResources = card.getProduction().getManufacturedResources();
      moveForward();
      ResourceType resourceType = boardOrPerkManufacturedResource.get(0);
      if (manufacturedResources.containsKey(resourceType)) {
        manufacturedResources.put(resourceType, manufacturedResources.get(resourceType) + 1);
      } else {
        manufacturedResources.put(resourceType, 1);
      }
    }
    // aggiunge a manufacturedResources le risorse prodotte dalla DevelopmentCard,
    // dalla LeaderCard e dalla Board
    if (productionType == ProductionType.BoardAndDevCardAndLeaderCard) {
      DevelopmentCard card = fetchDevCardById(devCardID);
      manufacturedResources = card.getProduction().getManufacturedResources();
      moveForward();
      for (ResourceType resourceType : boardOrPerkManufacturedResource) {
        if (manufacturedResources.containsKey(resourceType)) {
          manufacturedResources.put(resourceType, manufacturedResources.get(resourceType) + 1);
        } else {
          manufacturedResources.put(resourceType, 1);
        }
      }
    }
    // adds resources to the Strongbox and eliminates the resources required for the
    // production
    addToStrongbox(manufacturedResources);
    if (resourcesToEliminateWarehouse != null) {
      this.warehouse.eliminateResources(resourcesToEliminateWarehouse);
    }
    if (resourcesToEliminateChest != null) {
      eliminateResourcesFromChest(resourcesToEliminateChest);
    }
  }

  // checks if the current player can activate a LeaderCard
  public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
    LeaderCard card = fetchLeaderCardById(leaderCardID);
    // checks if such a card is present in the player hand and if so checks if it is
    // not already active
    if (card != null) {
      if (card.getStatus() == Status.Active) {
        return false;
      }
    } else {
      return false;
    }
    // checks if there is a card (or cards) with the required (or higher) CardLevel
    // if the LeaderCard requires levels to be activated
    if (card.getRequiredDevCardLevel() != null) {
      Map<CardColor, CardLevel> currentDevCardsHigherLevels = getCurrentDevCardHigherLevelForEachCardColor();
      for (CardColor color : card.getRequiredDevCardLevel().keySet()) {
        if (currentDevCardsHigherLevels.get(color) == null) {
          return false;
        }
        if (getIntegerFromCardLevel(card.getRequiredDevCardLevel().get(color)) > getIntegerFromCardLevel(
            currentDevCardsHigherLevels.get(color))) {
          return false;
        }
      }
    }
    // checks if there are enough DevelopmentCards (of a specific color)
    // if the LeaderCard requires them
    if (card.getRequiredDevCards() != null) {
      Map<CardColor, Integer> currentDevCards = getCurrentDevCardsNumberByColor();
      for (CardColor color : card.getRequiredDevCards().keySet()) {
        if (currentDevCards.get(color) < card.getRequiredDevCards().get(color)) {
          return false;
        }
      }
    }
    // checks if there enough resources in the warehouse and the strongbox
    // if the LeaderCard requires resources to be activated
    if (card.getRequiredResources() != null) {
      return areEnoughResourcesPresent(card.getRequiredResources());
    }
    return true;
  }

  // activates a LeaderCard and its respective Perk
  public void performActivateLeaderCardMove(String leaderCardID) {
    for (LeaderCard card : this.leaderCards) {
      if (card.getId().equals(leaderCardID)) {
        card.activateCard();
        if (card.getPerk().getType() == PerkType.Warehouse) {
          this.warehouse.createExtraDeposit(card.getPerk().getResource());
        }
        if (card.getPerk().getType() == PerkType.Discount) {
          if (this.discounts != null) {
            this.discounts.add(card.getPerk().getResource().getType());
          } else {
            List<ResourceType> discounts = new ArrayList<>();
            discounts.add(card.getPerk().getResource().getType());
            this.discounts = discounts;
          }
        }
        if (card.getPerk().getType() == PerkType.Transmutation) {
          this.transmutation.add(card.getPerk().getResource().getType());
        }
        break;
      }
    }
  }

  public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
    return this.warehouse.isFeasibleTakeMarketResourcesMove(warehouse);
  }

  public void performTakeMarketResourceMove(Warehouse warehouse, List<Resource> discardedResources,
      boolean hasRedMarble) {
    if (hasRedMarble) {
      this.moveForward();
    }
    this.warehouse = warehouse;
    this.warehouse.discardResourcesInHand(discardedResources);
  }

  public int getMarkerPosition() {
    return this.faithMap.getMarkerPosition();
  }

  public String getLeaderCardsToString() {
    String string = "\n";
    for (int i = 0; i < this.leaderCards.size(); i++) {
      string = string + this.leaderCards.get(i).toString();
    }
    return string;
  }

  public void setLeaderCards(List<LeaderCard> selectedLeaderCards) {
    this.leaderCards = selectedLeaderCards;
  }

  public int getBlackMarkerPosition() {
    return this.faithMap.getBlackMarkerPosition();
  }

  public String resourcesToString() {
    return this.warehouse.toString() + "Chest:\n" + this.chest.toString();
  }

  public int countDevCards() {
    int count = 0;
    for (DevCardPosition position : this.mapTray.keySet()) {
      count = count + this.mapTray.get(position).size();
    }
    return count;
  }

  public int calculateResourceVictoryPoints() {
    int resourceVictoryPointsWarehouse = this.warehouse.calculateResourceVictoryPoints();
    int resourceVictoryPointsChest = calculateChestResourceVictoryPoints();
    return resourceVictoryPointsWarehouse + resourceVictoryPointsChest;
  }

  private int calculateChestResourceVictoryPoints() {
    double totalResources = 0;
    for (ResourceType resourceType : this.chest.keySet()) {
      totalResources = totalResources + this.chest.get(resourceType);
    }
    return (int) Math.floor(totalResources / 5);
  }

  public void insertChosenResources(List<ResourceType> listOfResources) {
    this.warehouse.insertResources(listOfResources);
  }
  public List<ResourceType> getDiscounts() {
    return discounts;
  }
}
