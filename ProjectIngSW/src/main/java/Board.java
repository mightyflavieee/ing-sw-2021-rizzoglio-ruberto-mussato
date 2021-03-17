import java.util.*;

public class Board {
  private Map<DevCardPosition,List<DevelopmentCard>> mapTray;
  private Map<ResourceType, List<Resource>> chest;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;
  private FaithMap faithMap;
  private Market market;
  private CardContainer cardContainer;
  private ActionTokenContainer actionTokenContainer;

  public Map<DevCardPosition,List<DevelopmentCard>> getMaptray(){
    return mapTray;
  }

  public Map<ResourceType, List<Resource>> getChest(){
    return chest;
  }

  public Warehouse getWarehouse(){
    return warehouse;
  }

  public FaithMap getFaithMap(){
    return faithMap;
  }
  public Market getMarket(){
    return market;
  }
  public CardContainer getCardContainer(){
    return cardContainer;
  }

  public ActionTokenContainer getActionTokenContainer(){
    return actionTokenContainer;
  }
}
