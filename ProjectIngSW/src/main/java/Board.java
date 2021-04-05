import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Board {
  private Map<ResourceType, Integer> chest;
  private Map<DevCardPosition, List<DevelopmentCard>> mapTray;
  private Warehouse warehouse;
  private List<LeaderCard> leaderCards;
  private FaithMap faithMap;
  private List<Perk> activePerks = new ArrayList<>();

  public Map<DevCardPosition, List<DevelopmentCard>> getMapTray() {
    return mapTray;
  }

  public Map<ResourceType, Integer> getChest() {
    return chest;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public List<Perk> getActivePerks() {
    return activePerks;
  }

  public List<LeaderCard> getLeaderCards() {
    return leaderCards;
  }

  // verificare che questa funzione serva ---> c'è già in Warehouse
  public Map<ResourceType, Integer> mapAllResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType, Integer>();
    currentResourcesMap = warehouse.mapAllContainedResources();
    return currentResourcesMap;
  }

  private DevelopmentCard getLastFromPosition(DevCardPosition position) {
    return mapTray.get(position).get(mapTray.get(position).size());
  }

  // da implementare per terza opzione di azioni del player
  public boolean activateProductionOnCard() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenDevelopmentCard;
    System.out.println("Which card do you want to activate?");
    if (mapTray.get(DevCardPosition.Left).size() > 0) {
      System.out.println("1. " + getLastFromPosition(DevCardPosition.Left) + ";");
    }
    if (mapTray.get(DevCardPosition.Center).size() > 0) {
      System.out.println("2. " + getLastFromPosition(DevCardPosition.Center) + ";");
    }
    if (mapTray.get(DevCardPosition.Right).size() > 0) {
      System.out.println("3. " + getLastFromPosition(DevCardPosition.Right) + ";");
    }
    Map<ResourceType, Integer> manufacturedResources = new HashMap<ResourceType, Integer>();
    try {
      chosenDevelopmentCard = reader.readLine();
      switch (Integer.parseInt(chosenDevelopmentCard)) {
      case 1:
        manufacturedResources = getLastFromPosition(DevCardPosition.Left)
            .useProduction(warehouse.mapAllContainedResources());
        break;
      case 2:
        manufacturedResources = getLastFromPosition(DevCardPosition.Center)
            .useProduction(warehouse.mapAllContainedResources());
        break;
      case 3:
        manufacturedResources = getLastFromPosition(DevCardPosition.Right)
            .useProduction(warehouse.mapAllContainedResources());
        break;
      default:
        throw new Exception();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    manufacturedResources.forEach((ResourceType type, Integer numberOfResources) -> {
      if (this.chest.containsKey(type)) {
        this.chest.put(type, this.chest.get(type) + numberOfResources);
      } else {
        this.chest.put(type, numberOfResources);
      }
    });

    return true;
  }

  public Map<DevCardPosition, DevelopmentCard> getCurrentProductionCards() { // prog funz?
    Map<DevCardPosition, DevelopmentCard> productionCardsMap = new HashMap<DevCardPosition, DevelopmentCard>();
    productionCardsMap.put(DevCardPosition.Left,
        this.mapTray.get(DevCardPosition.Left).get(this.mapTray.get(DevCardPosition.Left).size() - 1));
    productionCardsMap.put(DevCardPosition.Center,
        this.mapTray.get(DevCardPosition.Center).get(this.mapTray.get(DevCardPosition.Center).size() - 1));
    productionCardsMap.put(DevCardPosition.Right,
        this.mapTray.get(DevCardPosition.Right).get(this.mapTray.get(DevCardPosition.Right).size() - 1));
    return productionCardsMap;
  }

  public void moveForward() {
    faithMap.moveForward();
  }

  private void chooseLeaderCard() {
    System.out.println("Choose which card to eliminate:");
    for (LeaderCard card : this.leaderCards) {
      System.out.println((this.leaderCards.indexOf(card) + 1) + ". ID:" + card.getId());
    }
  }

  private void removeLeaderCard() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenLeaderCard;
    try {
      chosenLeaderCard = reader.readLine();
      this.leaderCards.remove(Integer.parseInt(chosenLeaderCard) - 1);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Cannot read commad!");
    }
  }

  public void discardLeaderCard(boolean isInitialPhase) {
    if (isInitialPhase) {
      for (int i = 0; i < 2; i++) {
        chooseLeaderCard();
        removeLeaderCard();
      }
    } else {
      chooseLeaderCard();
      removeLeaderCard();
      moveForward();
    }
  }

  public void papalCouncil(int numTile) {
    faithMap.papalCouncil(numTile);
  }
}
