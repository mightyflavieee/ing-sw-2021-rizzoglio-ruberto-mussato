
import java.util.*;
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

  private void playLastTurn(Player currentPlayer){
    int playerIndex = this.playerList.indexOf(currentPlayer);
    while (true){
      if(playerIndex==this.playerList.size()-1) {
        break;
      }
      currentPlayer = nextPlayer(currentPlayer);
      currentPlayer.playTurn(this.market, this.cardContainer, this.actionTokenContainer);
    }
  }

  private Player nextPlayer(Player currentPlayer){
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
  }

  public void notifyFaithMapsForDiscard(int numDiscardedResources){}

  public void playGame(){
    //definire metodo per estrarre primo player 
    int playerIndex = 0;
    currentPlayer = this.playerList.get(playerIndex);
    while (true) {
      boolean endGame = currentPlayer.playTurn(this.market, this.cardContainer, this.actionTokenContainer);
      if(endGame){
        playLastTurn(currentPlayer);
        break;
      }
      currentPlayer = nextPlayer(currentPlayer);
    }

  }
  public void addVictoryPoints(int newVictoryPoints){
      currentPlayer.addVictoryPoints(newVictoryPoints);
  }
}
