package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.WarehouseObserver;

import java.util.*;

public class Warehouse extends Observable<Warehouse> {
  private static final long serialVersionUID = 3840284672475092704L;
  private final LinkedHashMap<ShelfFloor, List<Resource>> shelves;
  private LinkedHashMap<ResourceType, Integer> extraDeposit; // da mettere nel costruttore
  private int numResourcesToDiscard;

  public Warehouse(Match match) {
    this.shelves = this.initShelves();
    this.extraDeposit = null;
    this.numResourcesToDiscard = 0;
    this.addObserver(new WarehouseObserver(match));
  }

  public void readdObservers(Match match) {
    this.addObserver(new WarehouseObserver(match));
  }

  // returns ALL resources presents in the warehouse
  public LinkedHashMap<ResourceType, Integer> mapAllContainedResources() {
    LinkedHashMap<ResourceType, Integer> currentResourcesLinkedHashMap = new LinkedHashMap<>();
    currentResourcesLinkedHashMap.put(ResourceType.Coin, 0);
    currentResourcesLinkedHashMap.put(ResourceType.Stone, 0);
    currentResourcesLinkedHashMap.put(ResourceType.Shield, 0);
    currentResourcesLinkedHashMap.put(ResourceType.Servant, 0);

    // getting resources from the entire warehouse
    shelves.forEach((ShelfFloor floor,
        List<Resource> listOfResources) -> mapResourcesHelper(currentResourcesLinkedHashMap, listOfResources));
    // getting resources from extradeposit
    if (extraDeposit != null) {
      extraDeposit.forEach((ResourceType resourceType, Integer numberOfResources) -> {
        boolean hasKey = currentResourcesLinkedHashMap.containsKey(resourceType);
        if (hasKey) {
          currentResourcesLinkedHashMap.put(resourceType, currentResourcesLinkedHashMap.get(resourceType) + 1);
        } else {
          currentResourcesLinkedHashMap.put(resourceType, 1);
        }
      });
    }
    return currentResourcesLinkedHashMap;
  }

  private LinkedHashMap<ShelfFloor, List<Resource>> initShelves() {
    LinkedHashMap<ShelfFloor, List<Resource>> tempShelf = new LinkedHashMap<>();
    tempShelf.put(ShelfFloor.First, new ArrayList<>());
    tempShelf.put(ShelfFloor.Second, new ArrayList<>());
    tempShelf.put(ShelfFloor.Third, new ArrayList<>());
    return tempShelf;
  }

  // helper for listToLinkedHashMapResources
  private void mapResourcesHelper(LinkedHashMap<ResourceType, Integer> currentResourcesLinkedHashMap,
      List<Resource> listOfResources) {
    listOfResources.forEach((Resource resource) -> {
      ResourceType type = resource.getType();
      boolean hasKey = currentResourcesLinkedHashMap.containsKey(type);
      if (hasKey) {
        currentResourcesLinkedHashMap.put(type, currentResourcesLinkedHashMap.get(type) + 1);
      } else {
        currentResourcesLinkedHashMap.put(type, 1);
      }
    });
  }

  // transform a list to a LinkedHashMap
  public LinkedHashMap<ResourceType, Integer> listToMapResources(List<Resource> inputResourcesList) {
    LinkedHashMap<ResourceType, Integer> currentResourcesLinkedHashMap = new LinkedHashMap<>();
    mapResourcesHelper(currentResourcesLinkedHashMap, inputResourcesList);
    return currentResourcesLinkedHashMap;
  }

  public LinkedHashMap<ShelfFloor, List<Resource>> getShelves() {
    return shelves;
  }

  public LinkedHashMap<ResourceType, Integer> getExtraDeposit() {
    return extraDeposit;
  }

  public int getNumResourcesToDiscard() {
    return this.numResourcesToDiscard;
  }

  private void discardResources(int numDiscardedResources) {
    this.numResourcesToDiscard = numDiscardedResources;
    super.notify(this);
  }

  public void swapShelves(ShelfFloor swapper, ShelfFloor swappee) {
    List<Resource> listOfResourcesSwappee = shelves.get(swappee);
    shelves.put(swappee, shelves.get(swapper));
    shelves.put(swapper, listOfResourcesSwappee);
  }

  // creates the extra deposit
  public void createExtraDeposit(Resource resource) {
    if (this.extraDeposit == null) {
      LinkedHashMap<ResourceType, Integer> newExtraDeposit = new LinkedHashMap<>();
      newExtraDeposit.put(resource.getType(), 0);
      this.extraDeposit = newExtraDeposit;
    } else {
      this.extraDeposit.put(resource.getType(), 0);
    }
  }

  public void discardResourcesInHand(List<Resource> discardedResources) {
    this.discardResources(discardedResources.size());
  }

  // eliminates resources from the warehouse (the correctness of the overall
  // elimination must be done beforehand)
  public void eliminateResources(Map<ResourceType, Integer> resourcesToEliminateShelves,
                                 Map<ResourceType, Integer> resourcesToEliminateExtraDeposit) {
    if (!resourcesToEliminateShelves.isEmpty()) {
      for (ShelfFloor floor : this.shelves.keySet()) {
        if (this.shelves.get(floor).size() != 0) {
          for (ResourceType type : resourcesToEliminateShelves.keySet()) {
            if (this.shelves.get(floor).get(0).getType() == type) {
              for (int i = 0; i < resourcesToEliminateShelves.get(type); i++) {
                this.shelves.get(floor).remove(this.shelves.get(floor).get(0));
              }
              break;
            }
          }
        }
      }
    }
    if (!resourcesToEliminateExtraDeposit.isEmpty()) {
      for (ResourceType type : resourcesToEliminateExtraDeposit.keySet()) {
        for (int i = 0; i < resourcesToEliminateExtraDeposit.get(type); i++) {
          this.extraDeposit.put(type, this.extraDeposit.get(type) - 1);
        }
      }
    }

/*    for (ShelfFloor floor : this.shelves.keySet()) {
      if (this.shelves.get(floor).size() != 0) {
        for (ResourceType type : resourcesToEliminateShelves.keySet()) {
          if (this.shelves.get(floor).get(0).getType() == type) {
            // if the resources to eliminate are higher then the ones the floor can contain,
            // I remove some of them
            // first from the extraDeposit (so the correctness of the overall elimination
            // must be done beforehand)
            if (((resourcesToEliminateShelves.get(type) > 1 && floor == ShelfFloor.First)
                || (resourcesToEliminateShelves.get(type) > 2 && floor == ShelfFloor.Second)
                || (resourcesToEliminateShelves.get(type) > 3 && floor == ShelfFloor.Third)) && this.extraDeposit != null) {
              int currentResourcesInExtraDeposit = this.extraDeposit.get(type);
              int newExtraDepositResources = Math.max(currentResourcesInExtraDeposit - resourcesToEliminateShelves.get(type),
                  0);
              this.extraDeposit.put(type, newExtraDepositResources);
              resourcesToEliminateShelves.put(type, resourcesToEliminateShelves.get(type) - currentResourcesInExtraDeposit);
            }
            // here the resources are simply removed from the floor
            for (int i = 0; i < resourcesToEliminateShelves.get(type); i++) {
              this.shelves.get(floor).remove(this.shelves.get(floor).get(0));
            }
            break;
          }
        }
      }
    }*/
  }

  private boolean hasMoreResourcesThanFloor(LinkedHashMap<ShelfFloor, List<Resource>> shelfs) {
    for (ShelfFloor shelfFloor : shelfs.keySet()) {
      final List<Resource> resourcesOnFloor = shelfs.get(shelfFloor);
      if (shelfFloor == ShelfFloor.First) {
        if (resourcesOnFloor.size() > 1) {
          return true;
        } else {
          continue;
        }
      }
      if (shelfFloor == ShelfFloor.Second) {
        if (resourcesOnFloor.size() > 2) {
          return true;
        } else {
          continue;
        }
      }
      if (shelfFloor == ShelfFloor.Third) {
        if (resourcesOnFloor.size() > 3) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean oneFloorHasDifferentTypesOfResources(LinkedHashMap<ShelfFloor, List<Resource>> shelfs) {
    for (ShelfFloor shelfFloor : shelfs.keySet()) {
      Resource oldResourceOnFloor = null;
      for (Resource resource : shelfs.get(shelfFloor)) {
        if (oldResourceOnFloor != null && resource != null) {
          if (oldResourceOnFloor.getType() != resource.getType()) {
            return true;
          }
          oldResourceOnFloor = resource;
        }
      }
    }
    return false;
  }

  private boolean floorsHaveSameTypeOfResource(LinkedHashMap<ShelfFloor, List<Resource>> shelfs) {
    for (ShelfFloor shelfFloor : shelfs.keySet()) {
      if (!shelfs.get(shelfFloor).isEmpty()) {
        Resource resourceOfThisFloor = shelfs.get(shelfFloor).get(0);
        for (ShelfFloor shelfFloor2 : shelfs.keySet()) {
          if (shelfFloor2 != shelfFloor) {
            if (!shelfs.get(shelfFloor2).isEmpty()) {
              Resource resourceOnOtherFloor = shelfs.get(shelfFloor2).get(0);
              if (resourceOnOtherFloor != null && resourceOfThisFloor != null) {
                if (resourceOnOtherFloor.getType() == resourceOfThisFloor.getType()) {
                  return true;
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
    final LinkedHashMap<ShelfFloor, List<Resource>> shelvesToCheck = warehouse.getShelves();
    if (hasMoreResourcesThanFloor(shelvesToCheck)) {
      return false;
    }

    if (oneFloorHasDifferentTypesOfResources(shelvesToCheck)) {
      return false;
    }

    if (floorsHaveSameTypeOfResource(shelvesToCheck)) {
      return false;
    }
    return true;
  }

  public boolean insertInShelves(ShelfFloor shelfFloor, List<Resource> resourceList) {
    switch (shelfFloor) {
      case First:
        if (shelves.get(ShelfFloor.First).size() > 0)
          return false;
        break;
      case Second:
        if (shelves.get(ShelfFloor.Second).size() + resourceList.size() > 2) {
          return false;
        } else {
          if (shelves.get(ShelfFloor.Second).size() == 0) {
            break;
          }
          if (shelves.get(ShelfFloor.Second).get(0).getType() != resourceList.get(0).getType()) {
            return false;
          }
        }
        break;
      case Third:
        if (shelves.get(ShelfFloor.Third).size() == 0) {
          break;
        }
        if (shelves.get(ShelfFloor.Third).size() + resourceList.size() > 3) {
          return false;
        } else {
          if (shelves.get(ShelfFloor.Third).get(0).getType() != resourceList.get(0).getType()) {
            return false;
          }
        }
        break;
      default:
        return false;
    }
    List<Resource> temp = shelves.get(shelfFloor);
    temp.addAll(resourceList);
    shelves.put(shelfFloor, temp);
    return true;
  }

  public boolean insertInExtraDeposit(List<Resource> resourceList) {
    if (this.extraDeposit == null) {
      return false;
    }
    if (!extraDeposit.containsKey(resourceList.get(0).getType())) {
      return false;
    }
    if (extraDeposit.get(resourceList.get(0).getType()) + resourceList.size() > 2) {
      return false;
    }
    extraDeposit.put(resourceList.get(0).getType(),
        extraDeposit.get(resourceList.get(0).getType()) + resourceList.size());
    return true;
  }

  public String toString() {
    String string = "";
    string = string + "Shelves:\n" + this.getShelvesToString() + "\nExtra Deposit: \n" + this.getExtraDepositToString();
    return string;
  }

  public String getShelvesToString() {
    StringBuilder string = new StringBuilder();
    for (Map.Entry<ShelfFloor, List<Resource>> entry : shelves.entrySet()) {
      ShelfFloor floor = entry.getKey();
      List<Resource> listOfResources = entry.getValue();
      string.append(floor.toString());
      for (Resource resource : listOfResources) {
        string.append(" ").append(resource.toString());
      }
      string.append("\n");
    }
    return string.toString();
    // string = this.shelves
    // .entrySet()
    // .stream()
    // .LinkedHashMap(x -> x.getKey().toString() +
    // x.getValue().stream().LinkedHashMap(y -> " " +
    // y.toString()) + "\n")
    // .collect(Collectors.toList())
    // .stream()
    // .reduce("", (a,b)-> a + b);

    // .stream()
    // .collect(Collectors.joining(" "));
  }

  public String getExtraDepositToString() {
    if (this.extraDeposit == null) {
      return "no extra deposit\n";
    }else{
      String string = "";
      for(ResourceType resourceType : extraDeposit.keySet()){
        string = string + resourceType.toString() + " "+ extraDeposit.get(resourceType) + "\n";
      }
      return string;
    }
  }

  public int calculateResourceVictoryPoints() {
    double totalResources = 0;
    LinkedHashMap<ResourceType, Integer> containedResources = mapAllContainedResources();
    for (ResourceType resourceType : containedResources.keySet()) {
      totalResources = totalResources + containedResources.get(resourceType);
    }
    return (int) Math.floor(totalResources / 5);
  }

  public void insertResources(List<ResourceType> listOfResources) {
    for (ResourceType resourceType : listOfResources) {
      if (shelves.get(ShelfFloor.Third).isEmpty()) {
        shelves.get(ShelfFloor.Third).add(new Resource(resourceType));
      } else {
        if (shelves.get(ShelfFloor.Third).get(0).getType().equals(resourceType)) {
          shelves.get(ShelfFloor.Third).add(new Resource(resourceType));
        } else {
          shelves.get(ShelfFloor.Second).add(new Resource(resourceType));
        }
      }
    }
  }
}
