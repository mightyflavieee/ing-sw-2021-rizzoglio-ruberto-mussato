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

public class Player implements Serializable, Cloneable, Comparable<Player> {
  private static final long serialVersionUID = 3840280592475092888L;

  private Board board;
  private boolean isConnected;
  private String nickname;
  private int victoryPoints;
  private TurnPhase turnPhase;
  private List<String> history;


  /**
   * it is used when you create a normal game
   * @param nickname it is the name of the player
   */
  public Player(String nickname) {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.nickname = nickname;
    this.victoryPoints = 0;
    this.turnPhase = TurnPhase.WaitPhase;
    this.history = new ArrayList<>();
  }

  /**
   * it is used for the clone
   */
  public Player() {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.victoryPoints = 0;
  }

  public void createFaithMapAndWarehouse(Match match) {
    this.board.createFaithMapAndWarehouse(match, this);
  }

  /**
   * @return the clone of the player
   */
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

  /**
   * function called for the persistence when a game is reloaded from the disk on the server
   * @param match passed by the model to re-add the specific observer we need
   */
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

  /**
   * it moves forward Lorenzo for single player game
   * @return the new position of the black Marker for Lorenzo
   */
  public int moveForwardBlack() {
    return this.board.moveForwardBlack();
  }

  /**
   * it is called when someone steps on a PapalFavourSlot
   * @param numTile it is the tile of the position of the player
   * @return it returns the points gained by stepping over this papalFavourSlot
   */
  public void papalCouncil(int numTile) {
    this.victoryPoints += this.board.papalCouncil(numTile);
  }

  /**
   * it checks if the discardAction is feasible
   * @param leaderCardID id to check if present
   * @return true if the id is present, false if not
   */
  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
    return this.board.isFeasibleDiscardLeaderCardMove(leaderCardID);
  }

  /**
   * it removes the LeaderCard with that id
   * @param leaderCardID id of the card to remove
   */
  public void performDiscardLeaderCardMove(String leaderCardID) {
    this.board.performDiscardLeaderCardMove(leaderCardID);
  }

  /**
   * it checks if the move Buy Development Card it makes sense
   * @param devCard card to buy
   * @param resourcesToEliminateWarehouse resources selected from the warehouse
   * @param resourcesToEliminateExtraDeposit resources selected from the extradeposit
   * @param resourcesToEliminateChest resources selected from the chest
   * @param position position of the card where I want to put it
   * @return it returns true if all checks are passed, false if not
   */
  public boolean isFeasibleBuyDevCardMove(DevelopmentCard devCard,
                                          Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                          Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                                          Map<ResourceType, Integer> resourcesToEliminateChest,
                                          DevCardPosition position) {
    return this.board.isFeasibleBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit,
            resourcesToEliminateChest, position);
  }

  /**
   * it buys the requested development card
   * @param devCard card selected to be bought
   * @param resourcesToEliminateWarehouse resources selected from warehouse
   * @param resourcesToEliminateExtraDeposit resources selected from extra deposit
   * @param resourcesToEliminateChest resources selected from chest
   * @param position position selected for the card
   */
  public void performBuyDevCardMove(DevelopmentCard devCard,
                                    Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                                    Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                                    Map<ResourceType, Integer> resourcesToEliminateChest,
                                    DevCardPosition position) {
    this.board.performBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit,
            resourcesToEliminateChest, position);
  }

  /**
   * it checks if the production Move is feasible
   * @param devCardIDs ids of the developmentCards selected fro the production
   * @param leaderCardIDs ids of the leaderCards selected for the production
   * @param requiredResources resources required for the production
   * @param resourcesToEliminateWarehouse resources selected from the warehouse
   * @param resourcesToEliminateExtraDeposit resources selected from the extraDeposit
   * @param resourcesToEliminateChest resources selected from the chest
   * @param productionType enum of the productionType
   * @return true is the action is feasible, false if not
   */
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

  /**
   * performs the production putting the resources manufactured in the strongbox
   * and eliminating the resources required
   * @param devCardIDs ids of developmentCards selected by the user
   * @param resourcesToEliminateWarehouse resources selected from the warehouse
   * @param resourcesToEliminateExtraDeposit resources selected from the extraDeposit
   * @param resourcesToEliminateChest resources selected from the chest
   * @param productionType enum of the productionType
   * @param boardManufacturedResource resources manufactured by the board
   * @param perkManufacturedResource resources manufactured from the perk
   */
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

  /**
   * checks if the current player can activate a LeaderCard
   * @param leaderCardID id of the leaderCard
   * @return it returns true if all checks are passed, false if not
   */
  public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
    return this.board.isFeasibleActivateLeaderCardMove(leaderCardID);
  }

  /**
   * activates a LeaderCard and its respective Perk
   * @param leaderCardID id of the selected leaderCard
   */
  public void performActivateLeaderCardMove(String leaderCardID) {
    this.board.performActivateLeaderCardMove(leaderCardID);
  }

  /**
   * checks if the warehouse is feasible for the takeMarketResourceMove
   * @param warehouse sent by the player to check if it has the right format
   * @return true if it pass all the checks, false if not
   */
  public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
    return this.board.isFeasibleTakeMarketResourcesMove(warehouse);
  }

  /**
   * @param warehouse  sent by the player
   * @param discardedResources list of resources discarded
   * @param hasRedMarble true if he got the red marble from the market
   */
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

  /**
   * it attaches to the history the new move done by the player
   * @param stringMove the move to string that needs to be saved in the History
   */
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

  /**
   * it adds the sum of the victory points of the resources in the deposits + chest / 5 to the player
   */
  public void addResourceVictoryPoints() {
    int victoryPointsToAdd = this.board.calculateResourceVictoryPoints();
    addVictoryPoints(victoryPointsToAdd);
  }

  public void setResources(List<ResourceType> listOfResources) {
    this.board.insertChosenResources(listOfResources);
  }

  @Override
  public int compareTo(Player d) {
    return this.victoryPoints - d.getVictoryPoints();
  }

  public String getActivatedLeaderCardsToString() {
      return this.board.getActivatedLeaderCardsToString();
    }
}
