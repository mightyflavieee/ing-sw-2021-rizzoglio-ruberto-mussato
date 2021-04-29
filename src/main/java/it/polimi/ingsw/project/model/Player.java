package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.TransmutationPerk;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.*;
import java.util.*;

public class Player implements Serializable, Cloneable {
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

  private boolean takeResourcesFromMarket(Market market) {
    /*Map<String, Integer> chosenPosition = choosePosition();
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
    warehouse.insertResourcesInHand(filteredResources);*/
    return true;
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

  public void addVictoryPoints(int newVictoryPoints) {
    this.victoryPoints = this.victoryPoints + newVictoryPoints;
  }

  public void moveForward() {
    board.moveForward();
  }

  public int moveForwardBlack() {
    return this.board.moveForwardBlack();
  }

  public void papalCouncil(int numTile) {
    this.victoryPoints += board.papalCouncil(numTile);
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
    return this.board.isFeasibleDiscardLeaderCardMove(leaderCardID);
  }

  public void performDiscardLeaderCardMove(String leaderCardID) {
    this.board.performDiscardLeaderCardMove(leaderCardID);
  }

  public boolean isFeasibleChangeShelvesMove(ShelfFloor aFloor, ShelfFloor bFloor) {
    return this.board.isFeasibleChangeShelvesMove(aFloor, bFloor);
  }

  public void performChangeShelvesMove(ShelfFloor aFloor, ShelfFloor bFloor) {
    this.board.performChangeShelvesMove(aFloor, bFloor);
  }

  public boolean isFeasibleBuyDevCardMove(DevelopmentCard devCard,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      DevCardPosition position) {
    return this.board.isFeasibleBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateChest,
        position);
  }

  public void performBuyDevCardMove(DevelopmentCard devCard, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
      Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
    this.board.performBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateChest, position);
  }

  public boolean isFeasibleDevCardProductionMove(String devCardID, String leaderCardId,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      ProductionType productionType) {
    return this.board.isFeasibleDevCardProductionMove(devCardID, leaderCardId, resourcesToEliminateWarehouse,
        resourcesToEliminateChest, productionType);
  }

  public void performDevCardProductionMove(String devCardID,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      ProductionType productionType, List<ResourceType> boardOrPerkManufacturedResource) {
    this.board.performDevCardProductionMove(devCardID, resourcesToEliminateWarehouse,
        resourcesToEliminateChest, productionType, boardOrPerkManufacturedResource);
  }

  public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
    return this.board.isFeasibleActivateLeaderCardMove(leaderCardID);
  }

  public void performActivateLeaderCardMove(String leaderCardID) {
    this.board.performActivateLeaderCardMove(leaderCardID);
  }

}
