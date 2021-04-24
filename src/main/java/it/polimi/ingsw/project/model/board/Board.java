package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Board {
  //TODO: fare clone di tutti gli oggetti
  private Map<ResourceType, Integer> chest;
  private Map<DevCardPosition, List<DevelopmentCard>> mapTray;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;
  private FaithMap faithMap;
  private List<Perk> activePerks = new ArrayList<>();

  public final Board clone() {
    //TODO clone interne
    final Board result = new Board();
    result.chest = chest;
    result.mapTray = mapTray;
    result.warehouse = warehouse;
    result.leaderCards = leaderCards;
    result.faithMap = faithMap;
    result.activePerks = activePerks;
    return result;
  }

  public Map<DevCardPosition, List<DevelopmentCard>> getMapTray() {
    return mapTray;
  }

  public Map<ResourceType, Integer> getChest() {
    return chest;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public List<Perk> getActivePerks() {
    return activePerks;
  }

  public List<LeaderCard> getLeaderCards() {
    return leaderCards;
  }

  // it serves the function mapAllResources to the it.polimi.ingsw.project.model.CardContainer
  public Map<ResourceType, Integer> mapAllResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<>();
    currentResourcesMap = warehouse.mapAllContainedResources();
    return currentResourcesMap;
  }

  // it extracts the last it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard in the list at that position
  private DevelopmentCard getLastFromPosition(DevCardPosition position) {
    return mapTray.get(position).get(mapTray.get(position).size());
  }

  // it activates production on the specific card the user selects
  public boolean activateProductionOnCard() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenDevelopmentCard;
    System.out.println("Which card do you want to activate?");
    if (mapTray.get(DevCardPosition.Left).size() > 0) {
      System.out.println("1. " + getLastFromPosition(DevCardPosition.Left) + ";");
    }
    if (mapTray.get(DevCardPosition.Center).size() > 0) {
      System.out.println("2. " + getLastFromPosition(DevCardPosition.Center) + ";");
    }
    if (mapTray.get(DevCardPosition.Right).size() > 0) {
      System.out.println("3. " + getLastFromPosition(DevCardPosition.Right) + ";");
    }
    Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
    try {
      chosenDevelopmentCard = reader.readLine();
      switch (Integer.parseInt(chosenDevelopmentCard)) {
        case 1:
          manufacturedResources = getLastFromPosition(DevCardPosition.Left)
                  .useProduction(warehouse.mapAllContainedResources());
          break;
        case 2:
          manufacturedResources = getLastFromPosition(DevCardPosition.Center)
                  .useProduction(warehouse.mapAllContainedResources());
          break;
        case 3:
          manufacturedResources = getLastFromPosition(DevCardPosition.Right)
                  .useProduction(warehouse.mapAllContainedResources());
          break;
        default:
          throw new Exception();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    manufacturedResources.forEach((ResourceType type, Integer numberOfResources) -> {
      if (this.chest.containsKey(type)) {
        this.chest.put(type, this.chest.get(type) + numberOfResources);
      } else {
        this.chest.put(type, numberOfResources);
      }
    });

    return true;
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

  // it prompts to the user the cards with the id
  private void chooseLeaderCard() {
    System.out.println("Choose which card to eliminate:");
    for (LeaderCard card : this.leaderCards) {
      System.out.println((this.leaderCards.indexOf(card) + 1) + ". ID:" + card.getId());
    }
  }

  // it removes the leaderCard
  public void discardLeaderCard(boolean isInitialPhase) {
    //TODO
    if (isInitialPhase) {
      for (int i = 0; i < 2; i++) {
        chooseLeaderCard();
      //  removeLeaderCardFromList();
      }
    } else {
      chooseLeaderCard();
     // removeLeaderCardFromList();
      moveForward();
    }
  }

  public int papalCouncil(int numTile) {
    return faithMap.papalCouncil(numTile);
  }

  public int moveForwardBlack(){
    return this.faithMap.moveForwardBlack();
  }

  // fetches a DevelopmentCard by Id in the mapTray, returns null if not present
  private DevelopmentCard fetchCardById(String devCardID) {
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

  // double checks if the resources indicated by the user are actually present
  // usable in "isFeasible" and "perform" methods only
  private boolean areEnoughResourcesPresent(Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest) {
    Map<ResourceType, Integer> warehouseResources = this.warehouse.mapAllContainedResources();
    if (resourcesToEliminateWarehouse != null) {
      for (ResourceType type : warehouseResources.keySet()) {
        if (warehouseResources.get(type) < resourcesToEliminateWarehouse.get(type)) {
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

  // eliminates resources from the chest, usable only within performBuyDevCardMove()
  private void eliminateResourcesFromChest(Map<ResourceType, Integer> resourcesToEliminate) {
    for (ResourceType type : this.chest.keySet()) {
      if (this.chest.get(type).equals(resourcesToEliminate.get(type))) {
        this.chest.remove(type);
      } else {
        this.chest.put(type, (this.chest.get(type) - resourcesToEliminate.get(type)));
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

  public void performDiscardLeaderCardMove(String leaderCardID){
    this.moveForward();
    for (int i = 0; i < this.leaderCards.size(); i++) {
      if (this.leaderCards.get(i).getId().equals(leaderCardID)) {
        this.leaderCards.remove(i);
        return; } }
  }

  // checks if the current player can buy the card he/she selected
  public boolean isFeasibleBuyDevCardMove(DevelopmentCard devCard, Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
    int intLevel = 0;
    boolean isOneLessCardLevelPresent = false;
    Map<ResourceType, Integer> warehouseResources = this.warehouse.mapAllContainedResources();
    // double checks if the resources indicated by the user are actually present
    if (!areEnoughResourcesPresent(resourcesToEliminateWarehouse, resourcesToEliminateChest)) {
      return false;
    }
    // for each type of resource required, if there isn't enough resources, the move is not feasible (return false)
    for (ResourceType type : devCard.getRequiredResources().keySet()) {
      if (resourcesToEliminateWarehouse.containsKey(type)) {
        if (resourcesToEliminateChest.containsKey(type)) {
          if (devCard.getRequiredResources().get(type) > (resourcesToEliminateWarehouse.get(type) + resourcesToEliminateChest.get(type))) {
            return false;
          }
        } else {
          if (devCard.getRequiredResources().get(type) > resourcesToEliminateWarehouse.get(type)) {
            return false;
          }
        }
      } else {
        if (resourcesToEliminateChest.containsKey(type)) {
          if (devCard.getRequiredResources().get(type) > resourcesToEliminateChest.get(type)) {
            return false;
          }
        } else {
          return false;
        }
      }
    }
    // checks if there is a card of one level less (or none at all if the card is level 1) than the card the player
    // wants to buy on the position indicated by the player (return false if there isn't)
    if (this.mapTray.get(position).size() == 0) {
      if (devCard.getLevel() != CardLevel.One) {
        return false;
      } else {
        isOneLessCardLevelPresent = true;
      }
    } else {
      CardLevel currentCardLevel = this.mapTray.get(position).get(this.mapTray.get(position).size()-1).getLevel();
      int currentCardIntLevel = 0;
      if (devCard.getLevel() == CardLevel.One) { intLevel = 1; }
      if (devCard.getLevel() == CardLevel.Two) { intLevel = 2; }
      if (devCard.getLevel() == CardLevel.Three) { intLevel = 3; }
      if (currentCardLevel == CardLevel.One) { currentCardIntLevel = 1; }
      if (currentCardLevel == CardLevel.Two) { currentCardIntLevel = 2; }
      if (currentCardLevel == CardLevel.Three) { currentCardIntLevel = 3; }
      if (currentCardIntLevel == intLevel-1) { isOneLessCardLevelPresent = true; }
    }
    // if isOneLessCardLevelPresent is false, the function returns false
    if (!isOneLessCardLevelPresent) { return false; }
    return true;
  }

  // puts the bought DevelopmentCard in the mapTray and eliminates the required resources from the warehouse or
  // the strongbox (this.chest)
  public void performBuyDevCardMove(DevelopmentCard devCard, Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
    this.mapTray.get(position).add(devCard);
    this.warehouse.eliminateResources(resourcesToEliminateWarehouse);
    eliminateResourcesFromChest(resourcesToEliminateChest);
  }

  // checks if the current player can activate the production from the selected card
  public boolean isFeasibleDevCardProductionMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest) {
    Map<ResourceType, Integer> warehouseResources = this.warehouse.mapAllContainedResources();
    boolean isDevCardInLastPosition = false;
    // checks if the card selected is in the last position (so the production is activable)
    for (DevCardPosition position : this.mapTray.keySet()) {
      DevelopmentCard lastElement = this.mapTray.get(position).get(this.mapTray.get(position).size()-1);
      if (lastElement.getId().equals(devCardID)) {
        isDevCardInLastPosition = true;
        break;
      }
    }
    // if the card is not in the last position, return false
    if (!isDevCardInLastPosition) { return false;}
    // double checks if the resources indicated by the user are actually present
    if (!areEnoughResourcesPresent(resourcesToEliminateWarehouse, resourcesToEliminateChest)) {
      return false;
    }
    return true;
  }

  public void performDevCardProductionMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest) {
    DevelopmentCard card = fetchCardById(devCardID);
    Map<ResourceType, Integer> manufacturedResources = card.getProduction().getManufacturedResources();
    this.warehouse.eliminateResources(resourcesToEliminateWarehouse);
    eliminateResourcesFromChest(resourcesToEliminateChest);
  }
}
