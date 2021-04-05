import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Warehouse {
  private Map<ShelfFloor, List<Resource>> shelves;
  private Optional<Map<ResourceType, Integer>> extraDeposit;
  private List<Observer> observers;

  public Map<ResourceType, Integer> mapAllContainedResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType, Integer>();
    // getting resources from the entire warehouse
    shelves.forEach((ShelfFloor floor, List<Resource> listOfResources) -> {
      listOfResources.forEach((Resource resource) -> {
        ResourceType type = resource.getType();
        boolean hasKey = currentResourcesMap.containsKey(type);
        if (hasKey) {
          currentResourcesMap.put(type, currentResourcesMap.get(type) + 1);
        } else {
          currentResourcesMap.put(type, 1);
        }
      });
    });
    // getting resources from extradeposit
    if (extraDeposit.isPresent()) {
      extraDeposit.get().forEach((ResourceType resourceType, Integer numberOfResources) -> {
        boolean hasKey = currentResourcesMap.containsKey(resourceType);
        if (hasKey) {
          currentResourcesMap.put(resourceType, currentResourcesMap.get(resourceType) + 1);
        } else {
          currentResourcesMap.put(resourceType, 1);
        }
      });
    }
    return currentResourcesMap;
  }

  public Map<ResourceType, Integer> mapResources(List<Resource> inputResourcesList) {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType, Integer>();
    inputResourcesList.forEach((Resource resource) -> {
      ResourceType type = resource.getType();
      boolean hasKey = currentResourcesMap.containsKey(type);
      if (hasKey) {
        currentResourcesMap.put(type, currentResourcesMap.get(type) + 1);
      } else {
        currentResourcesMap.put(type, 1);
      }
    });
    return currentResourcesMap;
  }

  public Map<ShelfFloor, List<Resource>> getShelfs() {
    return shelves;
  }

  private ShelfFloor chooseFloor() {
    System.out.println("Which floor do you want to swap?\nOptions:\n1) First\n2) Second\n3) Third\n");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    while (true) {
      try {
        chosenActionString = reader.readLine();
        switch (Integer.parseInt(chosenActionString)) {
        case 1:
          return ShelfFloor.First;
        case 2:
          return ShelfFloor.Second;
        case 3:
          return ShelfFloor.Third;
        default:
          throw new Exception();
        }
      } catch (Exception e) {
        System.out.println("Please insert a valid integer from those options.");
        e.printStackTrace();
      }
    }
  }

  private void discardResources(int numDiscardedResources) {
    // we need to insert here the notification for the discarded resources to the
    // other players
  }

  private List<Resource> insertResourcesFromHand(List<Resource> handResources) {
    ShelfFloor floorSelected;
    floorSelected = chooseFloor();
    
    return null;
  }

  public void insertResources(List<Resource> resourcesToBeInserted) {
    Map<ResourceType, Integer> mappedResources = mapResources(resourcesToBeInserted);
    System.out.println("Your total resources to insert are:");
    mappedResources.entrySet().stream().forEach((singleMapObject) -> {
      System.out.println(singleMapObject.getKey() + ": " + singleMapObject.getValue());
    });
    System.out.println(
        "What do you want to do?\nOptions:\n1) Clear a Shelf\n2) Swap two Shelves\n3) Insert resources from hand in a Shelf.\n4) Discard current resources in hand.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    while (true) {
      try {
        chosenActionString = reader.readLine();
        switch (Integer.parseInt(chosenActionString)) {
        case 1:
          clearShelf();
          break;
        case 2:
          swapShelves();
          break;
        case 3:
          resourcesToBeInserted = insertResourcesFromHand(resourcesToBeInserted);
          break;
        case 4:
          discardResources(resourcesToBeInserted.size());
          break;
        default:
          throw new Exception();
        }
      } catch (Exception e) {
        System.out.println("Please insert a valid integer in the possible range.");
        e.printStackTrace();
      }
    }
  }

  public void swapShelves() {
    ShelfFloor swapper;
    ShelfFloor swappee;
    swapper = chooseFloor();
    swappee = chooseFloor();
    List<Resource> listOfResourcesSwappee = shelves.get(swappee);
    shelves.put(swappee, shelves.get(swapper));
    shelves.put(swapper, listOfResourcesSwappee);
  }

  public void clearShelf() {
    List<Resource> emptyList = new ArrayList<Resource>();
    ShelfFloor floor;
    floor = chooseFloor();
    shelves.put(floor, emptyList);
  }

  public Optional<Map<ResourceType, Integer>> getExtraDeposit() {
    return extraDeposit;
  }

  public List<Observer> getObservers() {
    return observers;
  }

  public void attach(Observer observer) {
  }

  public void detach(Observer observer) {
  }
}
