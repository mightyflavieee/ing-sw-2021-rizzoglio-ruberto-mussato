package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;

public class Match implements Serializable, Cloneable {
  private List<Player> playerList;
  private Market market;
  private CardContainer cardContainer;
  private ActionTokenContainer actionTokenContainer;
  private Player currentPlayer;
  private boolean isLastTurn;
  private boolean isOver;

  public Match(List<Player> playerList) {
    this.playerList = playerList;
    this.market = new Market();
    this.cardContainer = new CardContainer();
    if (playerList.size() == 1) {
      actionTokenContainer = new ActionTokenContainer();
    }
  }


  private Player nextPlayer() {
    int playerIndex = this.playerList.indexOf(currentPlayer);
    playerIndex = playerIndex + 1;
    if (playerIndex > this.playerList.size() - 1) {
      playerIndex = 0;
    }
    return playerList.get(playerIndex);
  }

  public List<Player> getPlayerList() {
    return playerList;
  }

  public Market getMarket() {
    return market;
  }

  public CardContainer getCardContainer() {
    return cardContainer;
  }

  public ActionTokenContainer getActionTokenContainer() {
    return actionTokenContainer;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public boolean getIsLastTurn() {
    return this.isLastTurn;
  }

  public void notifyFaithMapsForCouncil(int numTile) {
    // TODO devo notificare anche lorenzo?
    playerList.forEach(x -> x.papalCouncil(numTile));
    if (numTile == 3) {
      this.isLastTurn = true; // qualcuno è arrivato all'ultima casella
    }
  }

  public void notifyFaithMapsForDiscard(int numDiscardedResources) {
    // TODO devo far avanzare anche lorenzo?
    for (int i = 0; i < numDiscardedResources; i++) {
      playerList.stream().filter(x -> x.getNickname() != currentPlayer.getNickname()).forEach(Player::moveForward);
    }
  }

  public void addVictoryPoints(int newVictoryPoints) {
    currentPlayer.addVictoryPoints(newVictoryPoints);
  }

  public void discardForActionToken(CardColor cardColor) {
    if (cardContainer.discard(cardColor))
      this.youLost();
  }

  private void youLost() {
    // TODO
    this.isOver = true;
  }

  public void end() {
    if (this.isLastTurn == true
        && this.currentPlayer.getNickname() == this.playerList.get(this.playerList.size() - 1).getNickname()) {
      this.isOver = true;
    }
  }

  public void updatePlayer() {
    this.currentPlayer = this.nextPlayer();
  }

  public final Match clone() {
    final Match result = new Match(this.playerList);
    result.actionTokenContainer = actionTokenContainer;
    result.cardContainer = cardContainer;
     result.currentPlayer = currentPlayer;
    result.isLastTurn = isLastTurn;
     result.isOver = isOver;
    result.market = market;
    result.playerList = playerList;
    return result;
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
    return currentPlayer.isFeasibleDiscardLeaderCardMove(leaderCardID);
  }

  public void performDiscardLeaderCardMove(String leaderCardID) {
    this.currentPlayer.performDiscardLeaderCardMove(leaderCardID);
  }

  public boolean isFeasibleChangeShelvesMove(ShelfFloor aFloor, ShelfFloor bFloor) {
    return currentPlayer.isFeasibleChangeShelvesMove(aFloor, bFloor);
  }

  public void performChangeShelvesMove(ShelfFloor aFloor, ShelfFloor bFloor) {
    this.currentPlayer.performChangeShelvesMove(aFloor, bFloor);
  }

  public boolean isFeasibleBuyDevCardMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
      Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
    if (!this.cardContainer.isCardPresent(devCardID)) {
      return false;
    } else {
      DevelopmentCard devCard = this.cardContainer.fetchCard(devCardID);
      return this.currentPlayer.isFeasibleBuyDevCardMove(devCard, resourcesToEliminateWarehouse,
          resourcesToEliminateChest, position);
    }
  }

  public void performBuyDevCardMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
      Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
    DevelopmentCard devCard = this.cardContainer.removeBoughtCard(devCardID);
    this.currentPlayer.performBuyDevCardMove(devCard, resourcesToEliminateWarehouse, resourcesToEliminateChest,
        position);
  }

  public boolean isFeasibleDevCardProductionMove(String devCardID, String leaderCardId,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      ProductionType productionType) {
    if (devCardID != null) {
      if (this.cardContainer.isCardPresent(devCardID)) {
        return false;
      }
    }
    return this.currentPlayer.isFeasibleDevCardProductionMove(devCardID, leaderCardId,
            resourcesToEliminateWarehouse, resourcesToEliminateChest, productionType);
  }

  public void performDevCardProductionMove(String devCardID,
      Map<ResourceType, Integer> resourcesToEliminateWarehouse, Map<ResourceType, Integer> resourcesToEliminateChest,
      ProductionType productionType, List<ResourceType> boardOrPerkManufacturedResource) {
    this.currentPlayer.performDevCardProductionMove(devCardID,
            resourcesToEliminateWarehouse, resourcesToEliminateChest, productionType, boardOrPerkManufacturedResource);
  }

  public boolean isFeasibleExtractActionTokenMove(){
    return this.playerList.size() == 1;
  }

  public void performExtractActionTokenMove(){
    this.actionTokenContainer.drawToken();
  }

  public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
    return this.currentPlayer.isFeasibleActivateLeaderCardMove(leaderCardID);
  }

  public void performActivateLeaderCardMove(String leaderCardID) {
    this.currentPlayer.performActivateLeaderCardMove(leaderCardID);
  }

  public void moveForwardBlack() {
    if (24 == this.currentPlayer.moveForwardBlack()) // cioè se lorenzo è arrivato alla fine
      this.youLost();
  }
}
