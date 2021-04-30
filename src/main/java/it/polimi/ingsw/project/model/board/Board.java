package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.Status;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.*;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.*;
import javax.management.relation.RelationTypeSupport;

public class Board implements Serializable, Cloneable {

  // TODO: fare clone di tutti gli oggetti
  private Map<ResourceType, Integer> chest;
  private Map<DevCardPosition, List<DevelopmentCard>> mapTray;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;
  private FaithMap faithMap;
  private Optional<List<ResourceType>> discounts;
  private Optional<ResourceType> transmutation;

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

  public Optional<List<ResourceType>> getDiscounts() {
    return this.discounts;
  }

  public Optional<ResourceType> getTransmutation() {
    return transmutation;
  }

  // it serves the function mapAllResources to the CardContainer
  public Map<ResourceType, Integer> mapAllResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<>();
    currentResourcesMap = warehouse.mapAllContainedResources();
    return currentResourcesMap;
  }

  // it extracts the last DevelopmentCard in from the mapTray at that position
  private DevelopmentCard getLastFromPosition(DevCardPosition position) {
    return mapTray.get(position).get(mapTray.get(position).size() - 1);
  }

  // it extracts all the last cards for the production
  public Map<DevCardPosition, DevelopmentCard> getCurrentProductionCards() {
    Map<DevCardPosition, DevelopmentCard> productionCardsMap = new HashMap<>();
    productionCardsMap.put(
      DevCardPosition.Left,
      getLastFromPosition(DevCardPosition.Left)
    );
    productionCardsMap.put(
      DevCardPosition.Center,
      getLastFromPosition(DevCardPosition.Center)
    );
    productionCardsMap.put(
      DevCardPosition.Right,
      getLastFromPosition(DevCardPosition.Right)
    );
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
  private DevelopmentCard fetchDevCardById(String devCardID) {
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
  private LeaderCard fetchLeaderCardById(String leaderCardID) {
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
        this.chest.put(type, resourcesToAdd.get(type));
      }
    }
  }

  // double checks if the resources indicated by the user are actually present
  // usable in "isFeasible" and "perform" methods only within BuyDevCardMove
  // and DevCardProductionMove
  private boolean areEnoughResourcesPresentForBuyAndProduction(
    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
    Map<ResourceType, Integer> resourcesToEliminateChest
  ) {
    Map<ResourceType, Integer> warehouseResources =
      this.warehouse.mapAllContainedResources();
    if (resourcesToEliminateWarehouse != null) {
      for (ResourceType type : warehouseResources.keySet()) {
        if (
          warehouseResources.get(type) < resourcesToEliminateWarehouse.get(type)
        ) {
          return false;
        }
      }
    }
    if (resourcesToEliminateChest != null) {
      for (ResourceType type : this.chest.keySet()) {
        if (this.chest.get(type) < resourcesToEliminateChest.get(type)) {
          return false;
        }
      }
    }
    return true;
  }

  // checks if the resources indicated in the parameter are actually present
  private boolean areEnoughResourcesPresent(
    Map<ResourceType, Integer> resourcesToCheck
  ) {
    Map<ResourceType, Integer> warehouseResources =
      this.warehouse.mapAllContainedResources();
    if (resourcesToCheck != null) {
      for (ResourceType type : resourcesToCheck.keySet()) {
        if (warehouseResources.containsKey(type)) {
          if (this.chest.containsKey(type)) {
            if (
              (warehouseResources.get(type) + this.chest.get(type)) <
              resourcesToCheck.get(type)
            ) {
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
  private void eliminateResourcesFromChest(
    Map<ResourceType, Integer> resourcesToEliminate
  ) {
    for (ResourceType type : this.chest.keySet()) {
      if (this.chest.get(type).equals(resourcesToEliminate.get(type))) {
        this.chest.remove(type);
      } else {
        this.chest.put(
            type,
            (this.chest.get(type) - resourcesToEliminate.get(type))
          );
      }
    }
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
    for (int i = 0; i < this.leaderCards.size(); i++) {
      if (this.leaderCards.get(i).getId().equals(leaderCardID)) {
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

  public boolean isFeasibleChangeShelvesMove(
    ShelfFloor aFloor,
    ShelfFloor bFloor
  ) {
    Map<ShelfFloor, List<Resource>> currentWarehouse =
      this.warehouse.getShelfs();
    switch (aFloor) {
      case First:
        if (currentWarehouse.get(bFloor).size() > 1) {
          return false;
        }
        return true;
      case Second:
        if (bFloor == ShelfFloor.First) {
          if (currentWarehouse.get(aFloor).size() > 1) {
            return false;
          }
          return true;
        } else {
          if (currentWarehouse.get(bFloor).size() > 2) {
            return false;
          }
          return true;
        }
      case Third:
        if (bFloor == ShelfFloor.First) {
          if (currentWarehouse.get(aFloor).size() > 1) {
            return false;
          }
          return true;
        } else {
          if (currentWarehouse.get(aFloor).size() > 2) {
            return false;
          }
          return true;
        }
      default:
        return false;
    }
  }

  public void performChangeShelvesMove(ShelfFloor aFloor, ShelfFloor bFloor) {
    this.warehouse.swapShelves(aFloor, bFloor);
  }

  // checks if the current player can buy the card he/she selected
  public boolean isFeasibleBuyDevCardMove(
    DevelopmentCard devCard,
    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
    Map<ResourceType, Integer> resourcesToEliminateChest,
    DevCardPosition position
  ) {
    int intLevel = 0;
    boolean isOneLessCardLevelPresent = false;
    boolean isDiscountPresent = false;
    Map<ResourceType, Integer> warehouseResources =
      this.warehouse.mapAllContainedResources();
    // double checks if the resources indicated by the user are actually present
    if (
      !areEnoughResourcesPresentForBuyAndProduction(
        resourcesToEliminateWarehouse,
        resourcesToEliminateChest
      )
    ) {
      return false;
    }
    // for each type of resource required, if there isn't enough resources, the move
    // is not feasible (return false)
    for (ResourceType type : devCard.getRequiredResources().keySet()) {
      // verifies if there are any discount on the current ResourceType
      isDiscountPresent = false;
      if (this.discounts.isPresent()) {
        for (ResourceType discountType : this.discounts.get()) {
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
            if (
              (devCard.getRequiredResources().get(type) - 1) >
              (
                resourcesToEliminateWarehouse.get(type) +
                resourcesToEliminateChest.get(type)
              )
            ) {
              return false;
            }
          } else {
            if (
              devCard.getRequiredResources().get(type) >
              (
                resourcesToEliminateWarehouse.get(type) +
                resourcesToEliminateChest.get(type)
              )
            ) {
              return false;
            }
          }
        } else {
          if (isDiscountPresent) {
            if (
              (devCard.getRequiredResources().get(type) - 1) >
              resourcesToEliminateWarehouse.get(type)
            ) {
              return false;
            }
          } else {
            if (
              devCard.getRequiredResources().get(type) >
              resourcesToEliminateWarehouse.get(type)
            ) {
              return false;
            }
          }
        }
      } else {
        if (resourcesToEliminateChest.containsKey(type)) {
          if (isDiscountPresent) {
            if (
              (devCard.getRequiredResources().get(type) - 1) >
              resourcesToEliminateChest.get(type)
            ) {
              return false;
            }
          } else {
            if (
              devCard.getRequiredResources().get(type) >
              resourcesToEliminateChest.get(type)
            ) {
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
      CardLevel currentCardLevel =
        this.mapTray.get(position)
          .get(this.mapTray.get(position).size() - 1)
          .getLevel();
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
    if (!isOneLessCardLevelPresent) {
      return false;
    }
    return true;
  }

  // puts the bought DevelopmentCard in the mapTray and eliminates the required
  // resources from the warehouse or the strongbox (this.chest)
  public void performBuyDevCardMove(
    DevelopmentCard devCard,
    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
    Map<ResourceType, Integer> resourcesToEliminateChest,
    DevCardPosition position
  ) {
    this.mapTray.get(position).add(devCard);
    this.warehouse.eliminateResources(resourcesToEliminateWarehouse);
    eliminateResourcesFromChest(resourcesToEliminateChest);
  }

  // checks if the current player can activate the production selected
  public boolean isFeasibleDevCardProductionMove(
    String devCardID,
    String leaderCardId,
    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
    Map<ResourceType, Integer> resourcesToEliminateChest,
    ProductionType productionType
  ) {
    boolean isDevCardInLastPosition = false;
    // checks if the DevelopmentCard selected is in the last position (if the DevelopmentCard is required
    // for the production)
    if (
      productionType == ProductionType.DevCard ||
      productionType == ProductionType.BoardAndDevCard ||
      productionType == ProductionType.BoardAndDevCardAndLeaderCard ||
      productionType == ProductionType.DevCardAndLeader
    ) {
      for (DevCardPosition position : this.mapTray.keySet()) {
        DevelopmentCard lastElement =
          this.mapTray.get(position).get(this.mapTray.get(position).size() - 1);
        if (lastElement.getId().equals(devCardID)) {
          isDevCardInLastPosition = true;
          break;
        }
      }
      // if the card is not in the last position, return false
      if (!isDevCardInLastPosition) {
        return false;
      }
    }
    // checks if the LeaderCard is present (if required for the production)
    if (
      productionType == ProductionType.LeaderCard ||
      productionType == ProductionType.BoardAndLeaderCard ||
      productionType == ProductionType.BoardAndDevCardAndLeaderCard ||
      productionType == ProductionType.DevCardAndLeader
    ) {
      if (fetchLeaderCardById(leaderCardId) == null) {
        return false;
      }
    }
    // double checks if the resources indicated by the user are actually present
    if (
      !areEnoughResourcesPresentForBuyAndProduction(
        resourcesToEliminateWarehouse,
        resourcesToEliminateChest
      )
    ) {
      return false;
    }
    return true;
  }

  // performs the production putting the resources manufactured in the strongbox and eliminating
  // the resources required
  public void performDevCardProductionMove(
    String devCardID,
    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
    Map<ResourceType, Integer> resourcesToEliminateChest,
    ProductionType productionType,
    List<ResourceType> boardOrPerkManufacturedResource
  ) {
    Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
    if (
      productionType == ProductionType.DevCard ||
      productionType == ProductionType.BoardAndDevCard
    ) {
      DevelopmentCard card = fetchDevCardById(devCardID);
      manufacturedResources = card.getProduction().getManufacturedResources();
      if (productionType == ProductionType.BoardAndDevCard) {
        // aggiunge a manufacturedResources le risorse prodotte dalla Board
        for (ResourceType type : manufacturedResources.keySet()) {
          if (boardOrPerkManufacturedResource.get(0) == type) {
            manufacturedResources.put(
              type,
              manufacturedResources.get(type) + 1
            );
          }
        }
      }
    }
    if (
      productionType == ProductionType.LeaderCard ||
      productionType == ProductionType.BoardAndLeaderCard
    ) {
      // aggiunge a manufacturedResources le risorse prodotte dal ProductionPerk (LeaderCard
      moveForward();
      manufacturedResources.put(boardOrPerkManufacturedResource.get(0), 1);
      // aggiungere a manufacturedResources le risorse prodotte dalla Board
      if (productionType == ProductionType.BoardAndLeaderCard) {
        if (
          manufacturedResources.containsKey(
            boardOrPerkManufacturedResource.get(1)
          )
        ) {
          manufacturedResources.put(boardOrPerkManufacturedResource.get(1), 2);
        } else {
          manufacturedResources.put(boardOrPerkManufacturedResource.get(1), 1);
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
      for (ResourceType type : manufacturedResources.keySet()) {
        if (boardOrPerkManufacturedResource.get(0) == type) {
          manufacturedResources.put(type, manufacturedResources.get(type) + 1);
        }
      }
    }
    // aggiunge a manufacturedResources le risorse prodotte dalla DevelopmentCard,
    // dalla LeaderCard e dalla Board
    if (productionType == ProductionType.BoardAndDevCardAndLeaderCard) {
      DevelopmentCard card = fetchDevCardById(devCardID);
      manufacturedResources = card.getProduction().getManufacturedResources();
      moveForward();
      for (ResourceType type : manufacturedResources.keySet()) {
        if (boardOrPerkManufacturedResource.get(0) == type) {
          manufacturedResources.put(type, manufacturedResources.get(type) + 1);
        }
        if (boardOrPerkManufacturedResource.get(1) == type) {
          manufacturedResources.put(type, manufacturedResources.get(type) + 1);
        }
      }
    }
    // adds resources to the Strongbox and eliminates the resources required for the production
    addToStrongbox(manufacturedResources);
    this.warehouse.eliminateResources(resourcesToEliminateWarehouse);
    eliminateResourcesFromChest(resourcesToEliminateChest);
  }

  // checks if the current player can activate a LeaderCard
  public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
    LeaderCard card = fetchLeaderCardById(leaderCardID);
    boolean isDevCardLevelCorrect = false;
    CardLevel currentCardLevel = null;
    // checks if such a card is present in the player hand and if so checks if it is not already active
    if (card != null) {
      if (card.getStatus() == Status.Active) {
        return false;
      }
    } else {
      return false;
    }
    // checks if there is a card with the required (or higher) CardLevel
    // if the LeaderCard requires levels to be activated
    if (card.getRequiredDevCardLevel() != null) {
      int requiredDevCardLevel = 0;
      if (card.getRequiredDevCardLevel() == CardLevel.One) {
        requiredDevCardLevel = 1;
      }
      if (card.getRequiredDevCardLevel() == CardLevel.Two) {
        requiredDevCardLevel = 2;
      }
      if (card.getRequiredDevCardLevel() == CardLevel.Three) {
        requiredDevCardLevel = 3;
      }
      for (DevCardPosition position : this.mapTray.keySet()) {
        if (this.mapTray.get(position).size() > 0) {
          currentCardLevel =
            this.mapTray.get(position)
              .get(this.mapTray.get(position).size() - 1)
              .getLevel();
        }
        int currentIntCardLevel = 0;
        if (currentCardLevel == CardLevel.One) {
          currentIntCardLevel = 1;
        }
        if (currentCardLevel == CardLevel.Two) {
          currentIntCardLevel = 2;
        }
        if (currentCardLevel == CardLevel.Three) {
          currentIntCardLevel = 3;
        }
        if (currentIntCardLevel >= requiredDevCardLevel) {
          isDevCardLevelCorrect = true;
        }
      }
      if (!isDevCardLevelCorrect) {
        return false;
      }
    }
    // checks if there enough resources in the warehouse and the strongbox
    // if the LeaderCard requires resources to be activated
    if (card.getRequiredResources() != null) {
      if (!areEnoughResourcesPresent(card.getRequiredResources())) {
        return false;
      }
    }
    return true;
  }

  // activates a LeaderCard and its respective Perk
  public void performActivateLeaderCardMove(String leaderCardID) {
    for (LeaderCard card : this.leaderCards) {
      if (card.getId().equals(leaderCardID)) {
        card.activateCard();
        if (card.getPerk() instanceof WarehousePerk) {
          this.warehouse.createExtraDeposit(card.getPerk().getResource());
        }
        if (card.getPerk() instanceof DiscountPerk) {
          if (this.discounts.isPresent()) {
            this.discounts.get().add(card.getPerk().getResource().getType());
          } else {
            List<ResourceType> discounts = new ArrayList<>();
            discounts.add(card.getPerk().getResource().getType());
            this.discounts = Optional.of(discounts);
          }
        }
        if (card.getPerk() instanceof TransmutationPerk) {
          this.transmutation =
            Optional.of(card.getPerk().getResource().getType());
        }
        break;
      }
    }
  }

  public boolean isFeasibleTakeMarketResourcesMove(
    Warehouse warehouse,
    List<Resource> discardedResources
  ) {
    return this.warehouse.isFeasibleTakeMarketResourcesMove(
        warehouse,
        discardedResources
      );
  }

  public void performTakeMarketResourceMove(
    Warehouse warehouse,
    List<Resource> discardedResources
  ) {
    this.warehouse = warehouse;
    this.warehouse.discardResourcesInHand(discardedResources);
  }
}