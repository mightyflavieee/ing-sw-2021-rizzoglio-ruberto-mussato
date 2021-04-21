package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.TransmutationPerk;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.*;
import java.util.*;

public class Player {
  private Board board;
  private boolean isConnected;
  private String nickname;
  private int victoryPoints;

  public Player(String nickname) {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.nickname = nickname;
    this.victoryPoints = 0;
  }

  public Player() {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.victoryPoints = 0;
  }

  @Override
  protected final Player clone() {
    final Player result = new Player();
    result.board = board;
    result.isConnected = isConnected;
    result.nickname = nickname;
    result.victoryPoints = victoryPoints;
    return result;
  }

  private int chooseAction() {
    System.out.println(
        "Choose which action you want to perform:\n1. Take Resources from the it.polimi.ingsw.project.model.market.Market;\n2. Buy one Development it.polimi.ingsw.project.model.board.cards.Card;\n3. Activate the it.polimi.ingsw.project.model.board.cards.Production.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    try {
      chosenActionString = reader.readLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Integer.parseInt(chosenActionString);
  }

  private void showPossiblePositions(Integer axis) {
    int maximumPosition;
    if (axis == 0) {
      maximumPosition = 4;
    } else {
      maximumPosition = 3;
    }
    for (int i = 0; i < maximumPosition; i++) {
      System.out.printf("\"%d\" cell%n", i);
    }
  }

  private Map<String, Integer> choosePosition() {
    Map<String, Integer> mapOfPositions = new HashMap<>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    System.out.println("In which axis you want to insert the marble?\n\"0\" for X axis. \n\"1\" for Y axis.");
    while (true) {
      try {
        chosenActionString = reader.readLine();
        mapOfPositions.put("axis", Integer.parseInt(chosenActionString));
        break;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Unable to parse integer, insert a Integer!");
      }
    }
    System.out.println("In which position you want to insert the marble?");
    showPossiblePositions(mapOfPositions.get("axis"));
    while (true) {
      try {
        chosenActionString = reader.readLine();
        mapOfPositions.put("position", Integer.parseInt(chosenActionString));
        break;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Unable to parse integer, insert a Integer!");
      }
    }
    return mapOfPositions;
  }

  private boolean takeResourcesFromMarket(Market market) {
    Map<String, Integer> chosenPosition = choosePosition();
    List<Perk> activePerks = this.board.getActivePerks();
    ResourceType transmutationResourceType = null;
    for (Perk perk : activePerks) {
      if (perk instanceof TransmutationPerk) {
        transmutationResourceType = perk.getResource().getType();
      }
    }
    boolean isTransmutationPresent = transmutationResourceType != null ? true : false;
    List<Resource> acquiredResources = market.insertMarble(chosenPosition.get("axis"), chosenPosition.get("position"),
        isTransmutationPresent, transmutationResourceType);
    List<Resource> filteredResources = new ArrayList<>();
    for (Resource resource : acquiredResources) {
      if (resource.getType() == ResourceType.Faith) {
        this.board.moveForward();
      } else {
        filteredResources.add(resource);
      }
    }
    Warehouse warehouse = this.board.getWarehouse();
    warehouse.insertResources(filteredResources);
    return true;
  }

  private void showCurrentResources() {
    Map<ResourceType, Integer> currentResources = this.board.mapAllResources();
    System.out.println("Your total resources are:");
    currentResources.entrySet()
        .forEach(singleMapObject -> System.out.println(singleMapObject.getKey() + ": " + singleMapObject.getValue()));
  }

  private boolean buyDevelopmentCard(CardContainer cardContainer) {
    while (true) {
      showCurrentResources();
      String idCardSelected = cardContainer.showAndSelectFromAvailableCards();
      boolean hasBought = cardContainer.buy(idCardSelected, this.board);
      if (!hasBought) {
        System.out.println("Would you like to try to buy a different card?");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
          String chosenAction = reader.readLine();
          if (chosenAction.equals("NO")) {
            return false;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        return true;
      }
    }
  }

  private boolean activateProduction() {

    return this.board.activateProductionOnCard();
  }

  public Board getBoard() {
    return board;
  }

  public boolean getStatus() {
    return isConnected;
  }

  public String getNickname() {
    return nickname;
  }

  public int getVictoryPoints() {
    return victoryPoints;
  }

  public boolean playTurn(Market market, CardContainer cardContainer, ActionTokenContainer actionTokenContainer) {
    boolean retry = false;

    while (!retry) {
      int chosenAction = chooseAction();
      switch (chosenAction) {
      case 1:
        retry = takeResourcesFromMarket(market);
        break;
      case 2:
        retry = buyDevelopmentCard(cardContainer);
        break;
      case 3:
        retry = activateProduction();
        break;
      default:
        break;
      }
    }
    return retry;
  }

  public void addVictoryPoints(int newVictoryPoints) {
    this.victoryPoints = this.victoryPoints + newVictoryPoints;
  }

  public void moveForward() {
    board.moveForward();
  }

  public void papalCouncil(int numTile) {

    this.victoryPoints += board.papalCouncil(numTile);
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID){
    return this.board.isFeasibleDiscardLeaderCardMove(leaderCardID);
  }

  public void performDiscardLeaderCardMove(String leaderCardID){
    this.board.performDiscardLeaderCardMove(leaderCardID);
  }
  public int moveForwardBlack(){
    return this.board.moveForwardBlack();
  }

}
