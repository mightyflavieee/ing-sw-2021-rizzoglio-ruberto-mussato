package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import java.io.*;
import java.util.*;

public class Player implements Serializable, Cloneable {
  private static final long serialVersionUID = 3840280592475092888L;

  private Board board;
  private boolean isConnected;
  private String nickname;
  private int victoryPoints;
  private TurnPhase turnPhase;
  private List<String> history;

  public Player(String nickname) {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.nickname = nickname;
    this.victoryPoints = 0;
    this.turnPhase = TurnPhase.WaitPhase;
    this.history = new ArrayList<>();
  }

  public Player() {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.victoryPoints = 0;
  }

  public void createFaithMapAndWarehouse(Match match) {
    this.board.createFaithMapAndWarehouse(match, this);
  }

  @Override
  protected final Player clone() {
    final Player result = new Player();
    result.board = board;
    result.isConnected = isConnected;
    result.nickname = nickname;
    result.victoryPoints = victoryPoints;
    result.turnPhase = turnPhase;
    result.history = history;
    return result;
  }

  public void readdObservers(Match match) {
    this.board.readdObservers(match, this);
  }

  public boolean getIsConnected() {
    return this.isConnected;
  }

  public void setIsConnected(boolean isConnected) {
    this.isConnected = isConnected;
  }

  public void setToInitialPhase() {
    this.turnPhase = TurnPhase.InitialPhase;
  }

  public void setToWaitPhase() {
    this.turnPhase = TurnPhase.WaitPhase;
  }

  public void updateTurnPhase() {
    this.turnPhase = turnPhase.next();
  }

  public Board getBoard() {
    return board;
  }

  public String getNickname() {
    return this.nickname;
  }

  public int getVictoryPoints() { return this.victoryPoints; }

  public void addVictoryPoints(int newVictoryPoints) {
    this.victoryPoints = this.victoryPoints + newVictoryPoints;
  }

  public void moveForward() {
    this.board.moveForward();
  }

  public int moveForwardBlack() {
    return this.board.moveForwardBlack();
  }

  public void papalCouncil(int numTile) {
    this.victoryPoints += this.board.papalCouncil(numTile);
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
    return this.board.isFeasibleDiscardLeaderCardMove(leaderCardID);
  }

  public void performDiscardLeaderCardMove(String leaderCardID) {
    this.board.performDiscardLeaderCardMove(leaderCardID);
  }

  public boolean isFeasibleBuyDevCardMove(DevelopmentCard devCard,
                                          Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                          Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                                          Map<ResourceType, Integer> resourcesToEliminateChest,
                                          DevCardPosition position) {
    return this.board.isFeasibleBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit,
            resourcesToEliminateChest, position);
  }

  public void performBuyDevCardMove(DevelopmentCard devCard,
                                    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                    Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                                    Map<ResourceType, Integer> resourcesToEliminateChest,
                                    DevCardPosition position) {
    this.board.performBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit,
            resourcesToEliminateChest, position);
  }

  public boolean isFeasibleProductionMove(List<String> devCardIDs,
                                          List<String> leaderCardIDs,
                                          Map<ResourceType, Integer> requiredResources,
                                          Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                          Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                                          Map<ResourceType, Integer> resourcesToEliminateChest,
                                          ProductionType productionType) {
    return this.board.isFeasibleProductionMove(devCardIDs, leaderCardIDs, requiredResources, resourcesToEliminateWarehouse,
            resourcesToEliminateExtraDeposit, resourcesToEliminateChest, productionType);
  }

  public void performProductionMove(List<String> devCardIDs,
                                    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                    Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                                    Map<ResourceType, Integer> resourcesToEliminateChest,
                                    ProductionType productionType,
                                    ResourceType boardManufacturedResource,
                                    List<ResourceType> perkManufacturedResource) {
    this.board.performProductionMove(devCardIDs, resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit,
            resourcesToEliminateChest, productionType, boardManufacturedResource, perkManufacturedResource);
  }

  public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
    return this.board.isFeasibleActivateLeaderCardMove(leaderCardID);
  }

  public void performActivateLeaderCardMove(String leaderCardID) {
    this.board.performActivateLeaderCardMove(leaderCardID);
  }

  public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
    return this.board.isFeasibleTakeMarketResourcesMove(warehouse);
  }

  public void performTakeMarketResourceMove(Warehouse warehouse, List<Resource> discardedResources,
      Boolean hasRedMarble) {
    this.board.performTakeMarketResourceMove(warehouse, discardedResources, hasRedMarble);
  }

  public TurnPhase getTurnPhase() {
    return this.turnPhase;
  }

  public int getMarkerPosition() {
    return this.board.getMarkerPosition();
  }

  public String getLeaderCardsToString() {
    return this.board.getLeaderCardsToString();
  }

  public Warehouse getWarehouse() {
    return this.board.getWarehouse();
  }

  public List<ResourceType> getTransmutationPerk() {
    return this.board.getTransmutation();
  }

  public void updateHistory(String stringMove) {
    if(stringMove.equals("No Move")) {
      return;
    }
    this.history.add(stringMove);
  }

  public String getHistoryToString() {
    StringBuilder string = new StringBuilder();
    for (String move : history) {
      string.append("\n").append(move);
    }
    return string.toString();
  }

  public void setLeaderCards(List<LeaderCard> selectedLeaderCards) {
    this.board.setLeaderCards(selectedLeaderCards);
  }

  public List<LeaderCard> getLeaderCards() {
    return this.board.getLeaderCards();
  }

  public int getBlackMarkerPosition() {
    return this.board.getBlackMarkerPosition();
  }

  public boolean hasSevenCards() {
    return this.board.countDevCards() == 7;
  }

  public void addResourceVictoryPoints() {
    int victoryPointsToAdd = this.board.calculateResourceVictoryPoints();
    addVictoryPoints(victoryPointsToAdd);
  }

  public void setResources(List<ResourceType> listOfResources) {
    this.board.insertChosenResources(listOfResources);
  }
}
