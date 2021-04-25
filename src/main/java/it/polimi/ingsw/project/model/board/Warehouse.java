package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Warehouse extends Observable<Warehouse> {
  private Map<ShelfFloor, List<Resource>> shelves;
  private Optional<Map<ResourceType, Integer>> extraDeposit;
  private Map<ResourceType, Integer> temporaryResources;
  private int numResourcesToDiscard;

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
  public int getNumResourcesToDiscard() {
    return this.numResourcesToDiscard;
  }
  private void discardResources(int numDiscardedResources) {
    this.numResourcesToDiscard = numDiscardedResources;
    super.notify(this);
  }

  public void insertResourcesInHand(List<Resource> resourcesToBeInserted) {
    this.temporaryResources = mapResources(resourcesToBeInserted);
  }

  public void swapShelves(ShelfFloor swapper, ShelfFloor swappee) {
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

  // eliminates resource from shelf from the first floor with the same resource
  // type as the one choose for the production
  private void eliminateOneResource(ResourceType resourceType) {
    if (this.shelves.get(ShelfFloor.First).get(0).getType() == resourceType) {
      this.shelves.get(ShelfFloor.First).remove(this.shelves.get(ShelfFloor.First).size() - 1);
    } else {
      if (this.shelves.get(ShelfFloor.Second).get(0).getType() == resourceType) {
        this.shelves.get(ShelfFloor.Second).remove(this.shelves.get(ShelfFloor.Second).size() - 1);
      } else {
        this.shelves.get(ShelfFloor.Third).remove(this.shelves.get(ShelfFloor.Third).size() - 1);
      }
    }
  }

  // eliminates resource from the extra deposit if it has the same resource type
  // as the one choose for the production, otherwise calls eliminateOneResource
  private void eliminateOneResourceFromExtraDeposit(ResourceType resourceType) {
    if (this.extraDeposit.get().containsKey(resourceType)) {
      this.extraDeposit.get().put(resourceType, this.extraDeposit.get().get(resourceType) - 1);
    } else {
      System.out.println(
          "Cannot use the extra deposit for production (correct resource type not present)! Using the normal deposit.");
      eliminateOneResource(resourceType);
    }
  }

  // eliminates one resource for the
  // it.polimi.ingsw.project.model.board.card.leaderCard.perk.ProductionPerk
  // production
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

  public void discardResourcesInHand() {
    this.temporaryResources = null;
  }

  // eliminates resources from the warehouse (the correctness of the overall
  // elimination must be done beforehand)
  public void eliminateResources(Map<ResourceType, Integer> resourcesToEliminate) {
    List<Resource> newFloorResources = null;
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
                || (resourcesToEliminate.get(type) > 3 && floor == ShelfFloor.Third))
                && this.extraDeposit.isPresent()) {
              int currentResourcesInExtraDeposit = this.extraDeposit.get().get(type);
              int newExtraDepositResources = Math.max(currentResourcesInExtraDeposit - resourcesToEliminate.get(type),
                  0);
              this.extraDeposit.get().put(type, newExtraDepositResources);
              resourcesToEliminate.put(type, resourcesToEliminate.get(type) - currentResourcesInExtraDeposit);
            }
            // here the resources are simply removed from the floor
            for (int i = 0; i < resourcesToEliminate.get(type) - 1; i++) {
              this.shelves.get(floor).remove(this.shelves.get(floor).get(i));
            }
          }
        }
      }
    }
  }
}
