package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.custom.WarehouseObserver;

import java.io.Serializable;
import java.util.*;

public class Warehouse extends Observable<Warehouse> implements Serializable {

  private final Map<ShelfFloor, List<Resource>> shelves;
  private Map<ResourceType, Integer> extraDeposit; // da mettere nel costruttore
  private int numResourcesToDiscard;

  public Warehouse(Match match) {
    this.shelves = this.initShelves();
    this.extraDeposit = null;
    this.numResourcesToDiscard = 0;
    this.addObserver(new WarehouseObserver(match));
  }

  // returns ALL resources presents in the warehouse
  public Map<ResourceType, Integer> mapAllContainedResources() {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<>();
    // getting resources from the entire warehouse
    shelves.forEach((ShelfFloor floor, List<Resource> listOfResources) -> mapResourcesHelper(currentResourcesMap, listOfResources));
    // getting resources from extradeposit
    if (extraDeposit != null) {
      extraDeposit.forEach((ResourceType resourceType, Integer numberOfResources) -> {
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

  private Map<ShelfFloor, List<Resource>> initShelves() {
    Map<ShelfFloor, List<Resource>> tempShelf = new HashMap<>();
    tempShelf.put(ShelfFloor.First, new ArrayList<>());
    tempShelf.put(ShelfFloor.Second, new ArrayList<>());
    tempShelf.put(ShelfFloor.Third, new ArrayList<>());
    return tempShelf;
  }

  // helper for listToMapResources
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

  // transform a list to a map
  public Map<ResourceType, Integer> listToMapResources(List<Resource> inputResourcesList) {
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<>();
    mapResourcesHelper(currentResourcesMap, inputResourcesList);
    return currentResourcesMap;
  }

  public Map<ShelfFloor, List<Resource>> getShelves() {
    return shelves;
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
    if (this.extraDeposit != null) {
      Map<ResourceType, Integer> newExtraDeposit = new HashMap<>();
      newExtraDeposit.put(resource.getType(), 0);
      this.extraDeposit = newExtraDeposit;
    }
  }

  public void discardResourcesInHand(List<Resource> discardedResources) {
    this.discardResources(discardedResources.size());
  }

  // eliminates resources from the warehouse (the correctness of the overall
  // elimination must be done beforehand)
  public void eliminateResources(Map<ResourceType, Integer> resourcesToEliminate) {

    for (ShelfFloor floor : this.shelves.keySet()) {
      if (this.shelves.get(floor).size() != 0) {
        for (ResourceType type : resourcesToEliminate.keySet()) {
          if (this.shelves.get(floor).get(0).getType() == type) {
            // if the resources to eliminate are higher then the ones the floor can contain,
            // I remove some of them
            // first from the extraDeposit (so the correctness of the overall elimination
            // must be done beforehand)
            if (((resourcesToEliminate.get(type) > 1 && floor == ShelfFloor.First)
                || (resourcesToEliminate.get(type) > 2 && floor == ShelfFloor.Second)
                || (resourcesToEliminate.get(type) > 3 && floor == ShelfFloor.Third)) && this.extraDeposit != null) {
              int currentResourcesInExtraDeposit = this.extraDeposit.get(type);
              int newExtraDepositResources = Math.max(currentResourcesInExtraDeposit - resourcesToEliminate.get(type),
                  0);
              this.extraDeposit.put(type, newExtraDepositResources);
              resourcesToEliminate.put(type, resourcesToEliminate.get(type) - currentResourcesInExtraDeposit);
            }
            // here the resources are simply removed from the floor
            for (int i = 0; i < resourcesToEliminate.get(type); i++) {
              this.shelves.get(floor).remove(this.shelves.get(floor).get(0));
            }
          }
        }
      }
    }
  }

  public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
    final Map<ShelfFloor, List<Resource>> shelfs = warehouse.getShelves();
    for (ShelfFloor shelfFloor : shelfs.keySet()) {
      final List<Resource> resourcesOnFloor = shelfs.get(shelfFloor);
      if (shelfFloor == ShelfFloor.First) {
        if (resourcesOnFloor.size() > 1) {
          return false;
        } else {
          continue;
        }
      }
      if (shelfFloor == ShelfFloor.Second) {
        if (resourcesOnFloor.size() > 2) {
          return false;
        } else {
          continue;
        }
      }
      if (shelfFloor == ShelfFloor.Third) {
        if (resourcesOnFloor.size() > 3) {
          return false;
        }
      }
    }
    Resource oldResourceOnFloor;
    List<Resource> oldResourceOnPreviousFloor = new ArrayList<>();
    for (ShelfFloor shelfFloor : shelfs.keySet()) {
      oldResourceOnFloor = null;
      for (Resource resource : shelfs.get(shelfFloor)) {
        if (oldResourceOnFloor != null) {
          if (oldResourceOnFloor.getType() != resource.getType()) {
            return false;
          }
        }
        oldResourceOnFloor = resource;
      }
      oldResourceOnPreviousFloor.add(oldResourceOnFloor);
    }
    for (int i = 0; i < oldResourceOnPreviousFloor.size(); i++) {
      final Resource resourceToAnalyze = oldResourceOnPreviousFloor.get(i);
      oldResourceOnPreviousFloor.remove(i);
      for (Resource resource : oldResourceOnPreviousFloor) {
        if (resourceToAnalyze == resource) {
          return false;
        }
      }
    }
    return true;
  }
  public boolean insertInShelves(ShelfFloor shelfFloor, List<Resource> resourceList){
    switch (shelfFloor) {
      case First:
        if(shelves.get(ShelfFloor.First).size()>0)
          return false;
        break;
      case Second:
        if(shelves.get(ShelfFloor.Second).size() + resourceList.size() > 2) {
          return false;
        }
        else {
          if(shelves.get(ShelfFloor.Second).size()==0){
            break;
          }
          if(shelves.get(ShelfFloor.Second).get(0).getType() != resourceList.get(0).getType()){
            return false;
          }
        }
        break;
      case Third:
        if(shelves.get(ShelfFloor.Third).size()==0){
          break;
        }
        if(shelves.get(ShelfFloor.Third).size() + resourceList.size() > 3) {
          return false;
        }
        else {
          if(shelves.get(ShelfFloor.Third).get(0).getType() != resourceList.get(0).getType()){
            return false;
          }
        }
        break;
      default:
        return false;
    }
    List<Resource> temp = shelves.get(shelfFloor);
    temp.addAll(resourceList);
    shelves.put(shelfFloor,temp);
    return true;
  }
  public boolean insertInExtraDeposit(List<Resource> resourceList){
    if(extraDeposit.size() + resourceList.size() > 2) {
      return false;
    }
    else {
      if(!extraDeposit.containsKey(resourceList.get(0).getType())){
        return false;
      }
    }
    extraDeposit.put(resourceList.get(0).getType(),extraDeposit.get(resourceList.get(0).getType()) + resourceList.size()) ;
    return true;
  }
  public String toString(){
    String string = "";
    string = string + "Shelves:\n" + this.getShelvesToString() + "\nExtra Deposit: \n" +this.getExtraDepositToString();
    return string;
  }
  public String getShelvesToString(){
    StringBuilder string = new StringBuilder();
    for (Map.Entry<ShelfFloor, List<Resource>> entry : shelves.entrySet()) {
      ShelfFloor floor = entry.getKey();
      List<Resource> listOfResources = entry.getValue();
      string.append(floor.toString());
      for(Resource resource : listOfResources){
        string.append(" ").append(resource.toString());
      }
      string.append("\n");
    }
    return string.toString();
    //    string =   this.shelves
//            .entrySet()
//            .stream()
//            .map(x -> x.getKey().toString() + x.getValue().stream().map(y -> " " + y.toString()) + "\n")
//            .collect(Collectors.toList())
//            .stream()
//            .reduce("", (a,b)-> a + b);

    //.stream()
            //.collect(Collectors.joining(" "));
  }
  public String getExtraDepositToString(){
    if(this.extraDeposit == null){
      return "no extra deposit";
    }
    return this.extraDeposit
            .entrySet()
            .stream()
            .map(x -> x.getKey().toString() + " " + x.getValue().toString())
            .toString();
  }
}
