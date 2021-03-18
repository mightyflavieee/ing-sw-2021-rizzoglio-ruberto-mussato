import java.util.*;

public class Board {
  private Map<DevCardPosition, List<DevelopmentCard>> mapTray;
  private Map<ResourceType, List<Resource>> chest;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;

  public Map<DevCardPosition, List<DevelopmentCard>> getMaptray() {
    return mapTray;
  }

  public Map<ResourceType, List<Resource>> getChest() {
    return chest;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public List<LeaderCard> getLeaderCards() {
    return leaderCards;
  }
}
