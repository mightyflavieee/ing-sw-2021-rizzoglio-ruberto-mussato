
import java.util.*;
import java.util.stream.Collectors;

public class Match {
  private List<Player> playerList;
  private int maxPlayerNumber;
  private Market market;
  private CardContainer cardContainer;
  private ActionTokenContainer actionTokenContainer;
  private Player currentPlayer;

  public Match(List<Player> playerList, int maxPlayerNumber) {
    this.playerList = playerList;
    this.maxPlayerNumber = maxPlayerNumber;
    this.market = new Market();
    this.cardContainer = new CardContainer();
    if (playerList.size() == 1) {
      actionTokenContainer = new ActionTokenContainer();
    }
  }

  private void playLastTurn(){
    int playerIndex = this.playerList.indexOf(currentPlayer);
      while (playerIndex != this.playerList.size() - 1) {
          currentPlayer = nextPlayer();
          currentPlayer.playTurn(this.market, this.cardContainer, this.actionTokenContainer);
      }
  }

  private Player nextPlayer(){
    int playerIndex = this.playerList.indexOf(currentPlayer);
    playerIndex = playerIndex + 1;
     if(playerIndex> this.playerList.size()-1){
       playerIndex = 0;
     }
     return playerList.get(playerIndex);
 }

  public List<Player> getPlayerList(){
    return playerList;
  }

  public int getMaxPlayerNumber(){
    return maxPlayerNumber;
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

  public void notifyFaithMapsForCouncil(int numTile){
      //notifica il consiglio ad ogni player che agisce in base alla posizione in cui si trova
      playerList.stream().forEach(x -> x.papalCouncil(numTile));
  }

  public void notifyFaithMapsForDiscard(int numDiscardedResources){
      //metodo chiamato quando il currentPlayer scarta delle risorse
      for(int i = 0; i < numDiscardedResources; i++){
          playerList.stream().filter(x -> x != currentPlayer).forEach(Player::moveForward);
      }
  }

  public void playGame(){
    //definire metodo per estrarre primo player 
    int playerIndex = 0;
    currentPlayer = this.playerList.get(playerIndex);
    while (true) {
      boolean endGame = currentPlayer.playTurn(this.market, this.cardContainer, this.actionTokenContainer);
      if(endGame){
        playLastTurn();
        break;
      }
      currentPlayer = nextPlayer();
    }

  }
  public void addVictoryPoints(int newVictoryPoints){
      currentPlayer.addVictoryPoints(newVictoryPoints);
  }
  public void discard(CardColor cardColor){
      if(cardContainer.discard(cardColor))
          this.youLost();
  }
  private void youLost(){
      //da implementare
  }
}
