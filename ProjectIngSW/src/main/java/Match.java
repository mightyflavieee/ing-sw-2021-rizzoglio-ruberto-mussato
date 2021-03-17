
import java.util.*;
public class Match {
  private List<Player> playerList;
  private int maxPlayerNumber;

  public List<Player> getPlayerList(){
    return playerList;
  }

  public int getMaxPlayerNumber(){
    return maxPlayerNumber;
  }

  public void notifyFaithMapsForCouncil(){}
  public void notifyFaithMapsForDiscard(int numDiscardedResources){}
}
