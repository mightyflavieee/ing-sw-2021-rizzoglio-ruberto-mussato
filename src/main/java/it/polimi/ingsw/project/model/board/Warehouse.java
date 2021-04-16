package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.observer.custom.WarehouseObserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Warehouse {
  private Map<ShelfFloor, List<Resource>> shelves;
  private Optional<Map<ResourceType, Integer>> extraDeposit;
  private List<WarehouseObserver> warehouseObservers; // observer che notificano solo ed esclusivamente lo scarto

  public Map<ResourceType, Integer> mapAllContainedResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType, Integer>();
    // getting resources from the entire warehouse
    shelves.forEach((ShelfFloor floor, List<Resource> listOfResources) -> {
      mapResourcesHelper(currentResourcesMap, listOfResources);
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

  private void mapResourcesHelper(Map<ResourceType, Integer> currentResourcesMap, List<Resource> listOfResources) {
    listOfResources.forEach((Resource resource) -> {
      ResourceType type = resource.getType();
      boolean hasKey = currentResourcesMap.containsKey(type);
      if (hasKey) {
        currentResourcesMap.put(type, currentResourcesMap.get(type) + 1);
      } else {
        currentResourcesMap.put(type, 1);
      }
    });
  }

  public Map<ResourceType, Integer> mapResources(List<Resource> inputResourcesList) {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType, Integer>();
    mapResourcesHelper(currentResourcesMap, inputResourcesList);
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
    this.warehouseObservers.stream().forEach(x -> x.update(numDiscardedResources));
  }

  private List<Resource> insertResourcesFromHand(List<Resource> handResources) {
    ShelfFloor floorSelected;
    floorSelected = chooseFloor();
    // da finire 
    return null;
  }

  public void insertResources(List<Resource> resourcesToBeInserted) {
    Map<ResourceType, Integer> mappedResources = mapResources(resourcesToBeInserted);
    System.out.println("Your total resources to insert are:");
    mappedResources.entrySet().stream().forEach((singleMapObject) -> {
      System.out.println(singleMapObject.getKey() + ": " + singleMapObject.getValue());
    });
    System.out.println(
        "What do you want to do?\nOptions:\n1) Swap two Shelves\n2) Insert resources from hand in a Shelf.\n3) Discard current resources in hand.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    while (true) {
      try {
        chosenActionString = reader.readLine();
        switch (Integer.parseInt(chosenActionString)) {
        case 1:
          swapShelves();
          break;
        case 2:
          resourcesToBeInserted = insertResourcesFromHand(resourcesToBeInserted);
          break;
        case 3:
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

  public Optional<Map<ResourceType, Integer>> getExtraDeposit() {
    return extraDeposit;
  }

  // adds resources to the extra deposit or creates it if it is not present
  public void addExtraDeposit(Resource resource) {
    if (!this.extraDeposit.isPresent()) {
      Map<ResourceType, Integer> newExtraDeposit = new HashMap<>();
      newExtraDeposit.put(resource.getType(), 0);
      this.extraDeposit = Optional.of(newExtraDeposit);
    } else {
      if (this.extraDeposit.get().containsKey(resource.getType())) {
        int currentResourcesInExtraDeposit = this.extraDeposit.get().get(resource.getType());
        if (currentResourcesInExtraDeposit < 2) {
          this.extraDeposit.get().put(resource.getType(), currentResourcesInExtraDeposit + 1);
        } else {
          System.out.println("Extra deposit already full!");
        }
      } else {
        System.out.println("Cannot insert " + resource.getType().toString() + "in the extra deposit!");
      }
    }
  }

  // eliminates resource from shelf from the first floor with the same resource type
  // as the one choose for the production
  private void eliminateOneResource(ResourceType resourceType) {
    if (this.shelves.get(ShelfFloor.First).get(0).getType() == resourceType) {
      this.shelves.get(ShelfFloor.First).remove(this.shelves.get(ShelfFloor.First).size()-1);
    } else {
      if (this.shelves.get(ShelfFloor.Second).get(0).getType() == resourceType) {
        this.shelves.get(ShelfFloor.Second).remove(this.shelves.get(ShelfFloor.Second).size()-1);
      } else {
        this.shelves.get(ShelfFloor.Third).remove(this.shelves.get(ShelfFloor.Third).size()-1);
      }
    }
  }

  // eliminates resource from the extra deposit if it has the same resource type
  // as the one choose for the production, otherwise calls eliminateOneResource
  private void eliminateOneResourceFromExtraDeposit(ResourceType resourceType) {
    if (this.extraDeposit.get().containsKey(resourceType)) {
      this.extraDeposit.get().put(resourceType, this.extraDeposit.get().get(resourceType)-1);
    } else {
      System.out.println("Cannot use the extra deposit for production (correct resource type not present)! Using the normal deposit.");
      eliminateOneResource(resourceType);
    }
  }

  // eliminates one resource for the it.polimi.ingsw.project.model.board.card.leaderCard.perk.ProductionPerk production
  public void eliminateResourceForProductionPerk(ResourceType resourceType) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenLocationToEliminateFrom;
    if (mapAllContainedResources().containsKey(resourceType)) {
      if (mapAllContainedResources().get(resourceType) > 0) {
        if (this.extraDeposit.isPresent()) {
          System.out.println("What would you like to use for the production?\n1. Normal Deposit;\n2. Extra Deposit.");
          try {
            chosenLocationToEliminateFrom = reader.readLine();
            switch (Integer.parseInt(chosenLocationToEliminateFrom)) {
              case 1:
                eliminateOneResource(resourceType);
                break;
              case 2:
                eliminateOneResourceFromExtraDeposit(resourceType);
                break;
              default:
                throw new Exception();
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          eliminateOneResource(resourceType);
        }
      }
    } else {
      System.out.println("Cannot use this resource for this perk production!");
    }
  }

  public List<WarehouseObserver> getObservers() {
    return warehouseObservers;
  }

  public void attach(WarehouseObserver observer) {
    // observer che notificano solo ed esclusivamente lo scarto
    this.warehouseObservers.add(observer);

  }

  public void attach(it.polimi.ingsw.project.observer.Observer observer) {
  }

  public void detach(Observer observer) {
  }
}
