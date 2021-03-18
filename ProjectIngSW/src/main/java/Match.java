
import java.util.*;
public class Match {
  private List<Player> playerList;
  private int maxPlayerNumber;
  private FaithMap faithMap;
  private Market market;
  private CardContainer cardContainer;
  private ActionTokenContainer actionTokenContainer;

  public List<Player> getPlayerList(){
    return playerList;
  }

  public int getMaxPlayerNumber(){
    return maxPlayerNumber;
  }
  
  public FaithMap getFaithMap() {
    return faithMap;
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

  public void notifyFaithMapsForCouncil(){}
  public void notifyFaithMapsForDiscard(int numDiscardedResources){}
}
