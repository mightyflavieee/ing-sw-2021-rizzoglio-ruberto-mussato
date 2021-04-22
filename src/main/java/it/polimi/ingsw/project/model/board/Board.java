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
  public boolean isFeasibleBuyDevCardMove(Map<ResourceType, Integer> requiredResources, CardLevel level, DevCardPosition position) {
    int intLevel = 0;
    boolean isOneLessCardLevelPresent = false;
    Map<ResourceType, Integer> warehouseResources = this.warehouse.mapAllContainedResources();
    Map<ResourceType, Integer> currentResources = new HashMap<>();
    List<ResourceType> types = new ArrayList<>();
    types.add(ResourceType.Stone);
    types.add(ResourceType.Coin);
    types.add(ResourceType.Servant);
    types.add(ResourceType.Shield);
    // this for each loop constructs a map of the current overall resources of the player
    for (ResourceType type : types) {
      if (warehouseResources.containsKey(type)) {
        if (chest.containsKey(type)) {
          currentResources.put(type, warehouseResources.get(type) + this.chest.get(type));
        } else {
          currentResources.put(type, warehouseResources.get(type));
        }
      } else {
        currentResources.put(type, this.chest.get(type));
      }
    }
    // for each type of resource required, if there isn't enough resources, the move is not feasible (return false)
    for (ResourceType type : requiredResources.keySet()) {
      if (requiredResources.get(type) > currentResources.get(type)) {
        return false;
      }
    }
    // checks if there is a card of one level less (or none at all if the card is level 1) than the card the player
    // wants to buy on the position indicated by the player (return false if there isn't)
    if (!this.mapTray.containsKey(position)) {
      if (level != CardLevel.One) {
        return false;
      }
    } else {
      CardLevel currentCardLevel = this.mapTray.get(position).get(this.mapTray.get(position).size()-1).getLevel();
      int currentCardIntLevel = 0;
      if (level == CardLevel.One) { intLevel = 1; }
      if (level == CardLevel.Two) { intLevel = 2; }
      if (level == CardLevel.Three) { intLevel = 3; }
      if (currentCardLevel == CardLevel.One) { currentCardIntLevel = 1; }
      if (currentCardLevel == CardLevel.Two) { currentCardIntLevel = 2; }
      if (currentCardLevel == CardLevel.Three) { currentCardIntLevel = 3; }
      if (currentCardIntLevel == intLevel-1) { isOneLessCardLevelPresent = true; }
    }
    // if isOneLessCardLevelPresent is false, the function returns false
    if (!isOneLessCardLevelPresent) { return false; }
    return true;
  }

  public void performBuyDevCardMove(String devCardID) {

  }

}
