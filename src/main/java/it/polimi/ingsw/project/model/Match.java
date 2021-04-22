package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.*;

public class Match {
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

  private void playLastTurn() {
    int playerIndex = this.playerList.indexOf(currentPlayer);
    while (playerIndex != this.playerList.size() - 1) {
      currentPlayer = nextPlayer();
      currentPlayer.playTurn(this.market, this.cardContainer, this.actionTokenContainer);
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

  public boolean getIsLastTurn() { return this.isLastTurn; }

  public void notifyFaithMapsForCouncil(int numTile) {
    // TODO devo notificare anche lorenzo?
    playerList.forEach(x -> x.papalCouncil(numTile));
    if (numTile == 3) {
      this.isLastTurn = true; //qualcuno è arrivato all'ultima casella
      }
  }

  public void notifyFaithMapsForDiscard(int numDiscardedResources) {
    // TODO devo far avanzare anche lorenzo?
    for (int i = 0; i < numDiscardedResources; i++) {
      playerList.stream().filter(x -> x.getNickname() != currentPlayer.getNickname()).forEach(Player::moveForward);
    }
  }

  public void playGame() {
    //da modificare per MVC
    int playerIndex = 0;
    Collections.shuffle(this.playerList);
    currentPlayer = this.playerList.get(playerIndex);
    while (true) {
      boolean endGame = currentPlayer.playTurn(this.market, this.cardContainer, this.actionTokenContainer);
      if (endGame) {
        playLastTurn();
        break;
      }
      this.updatePlayer();
    }
  }

  public void addVictoryPoints(int newVictoryPoints) {
    currentPlayer.addVictoryPoints(newVictoryPoints);
  }

  public void discard(CardColor cardColor) {
    if (cardContainer.discard(cardColor))
      this.youLost();
  }

  private void youLost() {
    // TODO
    this.isOver = true;
  }

  public void end() {
    if (this.isLastTurn == true &&
            this.currentPlayer.getNickname() == this.playerList.get(this.playerList.size()-1).getNickname()){
      this.isOver = true;
    }
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public void updatePlayer(){
    this.currentPlayer = this.nextPlayer();
  }

  public boolean performMove(PlayerMove playerMove){
    // TODO
    return false;
  };

  public Match clone(){
    //TODO
    return null;
  }

  public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID){
    return currentPlayer.isFeasibleDiscardLeaderCardMove(leaderCardID);
  }

  public void performDiscardLeaderCardMove(String leaderCardID){
    this.currentPlayer.performDiscardLeaderCardMove(leaderCardID);
  }

  public boolean isFeasibleBuyDevCardMove(String devCardID, Map<ResourceType, Integer> requiredResources) {
    if (!this.cardContainer.isCardPresent(devCardID)) {
      return false;
    } else {
      DevelopmentCard card = this.cardContainer.fetchCard(devCardID);
      return this.currentPlayer.isFeasibleBuyDevCardMove(requiredResources, card.getLevel());
    }
  }

  public void performBuyDevCardMove(String devCardID, DevCardPosition position) {
    this.currentPlayer.performBuyDevCardMove(devCardID);
  }

  public void soloGame(){
    if(this.playerList.size() != 1){
      return;
    }else{
      this.actionTokenContainer.drawToken();
    }
  }

  public void moveForwardBlack(){
    if(24 == this.currentPlayer.moveForwardBlack()) //cioè se lorenzo è arrivato alla fine
      this.youLost();
  }
}
