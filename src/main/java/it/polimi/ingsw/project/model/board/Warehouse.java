package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.WarehouseObserver;

import java.util.*;

public class Warehouse extends Observable<Warehouse> {
  private static final long serialVersionUID = 3840284672475092704L;
  private LinkedHashMap<ShelfFloor, List<Resource>> shelves;
  private LinkedHashMap<ResourceType, Integer> extraDeposit; // da mettere nel costruttore
  private int numResourcesToDiscard;

  /**
   * @param match needed to add observers on this object
   */
  public Warehouse(Match match) {
    this.shelves = this.initShelves();
    this.extraDeposit = null;
    this.numResourcesToDiscard = 0;
    WarehouseObserver warehouseObserver = new WarehouseObserver(match);
    this.addObserver(warehouseObserver);
  }

  /**
   * @param match it is needed to re-add the observers for the persistence
   */
  public void readdObservers(Match match) {
    this.addObserver(new WarehouseObserver(match));
  }

  /**
   * @return all contained resources in the warehouse and extra deposit
   */
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
          currentResourcesLinkedHashMap.put(resourceType, currentResourcesLinkedHashMap.get(resourceType) + numberOfResources);
        } else {
          currentResourcesLinkedHashMap.put(resourceType, numberOfResources);
        }
      });
    }
    return currentResourcesLinkedHashMap;
  }

  /**
   * @return it creates the shelves for the deposit
   */
  private LinkedHashMap<ShelfFloor, List<Resource>> initShelves() {
    LinkedHashMap<ShelfFloor, List<Resource>> tempShelf = new LinkedHashMap<>();
    tempShelf.put(ShelfFloor.First, new ArrayList<>());
    tempShelf.put(ShelfFloor.Second, new ArrayList<>());
    tempShelf.put(ShelfFloor.Third, new ArrayList<>());
    return tempShelf;
  }

  /**
   * it adds to the currentResourcesMap all the resources present in the floor
   * @param currentResourcesLinkedHashMap map of all the resources collected until this moment
   * @param listOfResources list of resources collected in the floor
   */
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

  /**
   * transform a list to a LinkedHashMap
   * @param inputResourcesList input list of resources
   * @return returns the linkHashMap of the list of resources as input
   */
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

  /**
   * method called when we need to discard some resources from the takeMarketResourcesMove
   * @param numDiscardedResources number of resources to be discarded
   */
  private void discardResources(int numDiscardedResources) {
    this.numResourcesToDiscard = numDiscardedResources;
    super.notify(this);
  }

  /**
   * it swaps the two floors one another
   * @param swapper initial shelfFloor
   * @param swappee final shelfFLoor
   */
  public void swapShelves(ShelfFloor swapper, ShelfFloor swappee) {
    List<Resource> listOfResourcesSwappee = shelves.get(swappee);
    shelves.put(swappee, shelves.get(swapper));
    shelves.put(swapper, listOfResourcesSwappee);
  }

  /**
   * creates the extra deposit
   * @param resource input resource of the extraDeposit
   */
  public void createExtraDeposit(Resource resource) {
    if (this.extraDeposit == null) {
      LinkedHashMap<ResourceType, Integer> newExtraDeposit = new LinkedHashMap<>();
      newExtraDeposit.put(resource.getType(), 0);
      this.extraDeposit = newExtraDeposit;
    } else {
      this.extraDeposit.put(resource.getType(), 0);
    }
  }

  /**
   * @param discardedResources list of resources to be discarded in hand
   */
  public void discardResourcesInHand(List<Resource> discardedResources) {
    this.discardResources(discardedResources.size());
  }

  /**
   * eliminates resources from the warehouse (the correctness of the overall
   * elimination must be done beforehand)
   * @param resourcesToEliminateShelves list of resources to be eliminated from the shelf
   * @param resourcesToEliminateExtraDeposit list of resources to be eliminated from the extraDeposit
   */

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

  }

  /**
   * it checks the correctness of the deposit for the resources on the floor
   * @param shelves the entire deposit of a player
   * @return it returns true if all checks are passed, false if not
   */
  private boolean hasMoreResourcesThanFloor(LinkedHashMap<ShelfFloor, List<Resource>> shelves) {
    for (ShelfFloor shelfFloor : shelves.keySet()) {
      final List<Resource> resourcesOnFloor = shelves.get(shelfFloor);
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


  /**
   * it checks the correctness of the deposit for the resources on the floor with same type
   * @param shelves the entire deposit of a player
   * @return it returns true if all checks are passed, false if not
   */
  private boolean floorsHaveSameTypeOfResource(LinkedHashMap<ShelfFloor, List<Resource>> shelves) {
    for (ShelfFloor shelfFloor : shelves.keySet()) {
      if (!shelves.get(shelfFloor).isEmpty()) {
        Resource resourceOfThisFloor = shelves.get(shelfFloor).get(0);
        for (ShelfFloor shelfFloor2 : shelves.keySet()) {
          if (shelfFloor2 != shelfFloor) {
            if (!shelves.get(shelfFloor2).isEmpty()) {
              Resource resourceOnOtherFloor = shelves.get(shelfFloor2).get(0);
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

  /**
   * it calls other function to verify the isFeasible of the TakeMarketResourcesMove
   * @param warehouse sent by the player to verify its correctness
   * @return true if it pass all the checks, false if not
   */
  public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
    final LinkedHashMap<ShelfFloor, List<Resource>> shelvesToCheck = warehouse.getShelves();
    if (hasMoreResourcesThanFloor(shelvesToCheck)) {
      return false;
    }

    return !floorsHaveSameTypeOfResource(shelvesToCheck);
  }

  /**
   * checks if the action of inserting the resources in the floor is possible and it adds them if it is possible
   * @param shelfFloor designated floor to insert the resources
   * @param resourceList list of resources to be inserted in the floor
   * @return true if all checks are passed and resources inserted, false if not
   */
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

  /**
   * it inserts the resources in the extraDeposit if everyThing is okay
   * @param resourceList list of resources to be inserted to the extraDeposit
   * @return true if the resources are isnerted and checks passed, false if not
   */
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

  /**
   * @return it returns the cli representation of this objects
   */
  public String toString() {
    String string = "";
    string = string + "Shelves:\n" + this.getShelvesToString() + "\nExtra Deposit: \n" + this.getExtraDepositToString();
    return string;
  }

  /**
   * @return it returns the cli representation of the deposit
   */
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
  }

  /**
   * @return it returns the cli representation of the extraDeposit
   */
  public String getExtraDepositToString() {
    if (this.extraDeposit == null) {
      return "no extra deposit\n";
    }else{
      StringBuilder string = new StringBuilder();
      for(ResourceType resourceType : extraDeposit.keySet()){
        string.append(resourceType.toString()).append(" ").append(extraDeposit.get(resourceType)).append("\n");
      }
      return string.toString();
    }
  }

  /**
   * @return it returns the sum of the victory points of the resources in the deposits / 5
   */
  public int calculateResourceVictoryPoints() {
    double totalResources = 0;
    LinkedHashMap<ResourceType, Integer> containedResources = mapAllContainedResources();
    for (ResourceType resourceType : containedResources.keySet()) {
      totalResources = totalResources + containedResources.get(resourceType);
    }
    return (int) Math.floor(totalResources / 5);
  }

  /**
   * @param listOfResources it inserts the resources for the first choice of the player
   */
  public void insertResourcesForInitMatch(List<ResourceType> listOfResources) {
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

  public void setExtraDeposit(LinkedHashMap<ResourceType, Integer> extraDeposit) {
    this.extraDeposit = extraDeposit;
  }

  public void setShelves(LinkedHashMap<ShelfFloor, List<Resource>> shelves){
    this.shelves = shelves;
  }
}
