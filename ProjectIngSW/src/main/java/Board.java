import java.util.*;

public class Board {
  private Map<ResourceType, Integer> chest;
  private Map<DevCardPosition, List<DevelopmentCard>> mapTray;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;
  private FaithMap faithMap;

  public Map<DevCardPosition, List<DevelopmentCard>> getMapTray() {
    return mapTray;
  }

  public Map<ResourceType, Integer> getChest() {
    return chest;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public List<LeaderCard> getLeaderCards() {
    return leaderCards;
  }

  public Map<ResourceType, Integer> mapAllResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType, Integer>();
    currentResourcesMap = warehouse.mapAllResources();
    return currentResourcesMap;
  }

  public boolean activateProductionOnCard() {
    return true;
  }

  public Map<DevCardPosition, DevelopmentCard> getCurrentProductionCards() { //prog funz?
    Map<DevCardPosition, DevelopmentCard> productionCardsMap = new HashMap<DevCardPosition, DevelopmentCard>();
    productionCardsMap.put(DevCardPosition.Left, this.mapTray.get(DevCardPosition.Left).get(this.mapTray.get(DevCardPosition.Left).size()-1));
    productionCardsMap.put(DevCardPosition.Center, this.mapTray.get(DevCardPosition.Center).get(this.mapTray.get(DevCardPosition.Center).size()-1));
    productionCardsMap.put(DevCardPosition.Right, this.mapTray.get(DevCardPosition.Right).get(this.mapTray.get(DevCardPosition.Right).size()-1));
    return productionCardsMap;
  }
  public void moveForward(){
    faithMap.moveForward();
  }
}
