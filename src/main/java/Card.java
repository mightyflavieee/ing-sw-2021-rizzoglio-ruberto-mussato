import java.util.*;

public abstract class Card {
  private int victoryPoints;
  private List<Object> resourcesRequired;
  
  public int getPoints(){
    return victoryPoints;
  }

  public List<Object> getResourcesRequired(){
    return resourcesRequired;
  }
}
