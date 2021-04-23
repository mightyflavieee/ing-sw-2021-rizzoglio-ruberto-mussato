package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.*;

public class DevelopmentCard extends Card {
  final private CardColor color;
  final private CardLevel level;
  final private Production production;
  final private String id;
  final private Map<ResourceType, Integer> requiredResources;

  public DevelopmentCard(CardColor color, CardLevel level, Production production, String id, Map<ResourceType, Integer> requiredResources) {
    this.color = color;
    this.level = level;
    this.production = production;
    this.id = id;
    this.requiredResources = requiredResources;
  }

  public CardColor getColor() {
    return this.color;
  }

  public CardLevel getLevel() {
    return this.level;
  }

  public Production getProduction(){
    return this.production;
  }

  public String getId() {
    return this.id;
  }

  public Map<ResourceType, Integer> getRequiredResources() { return this.requiredResources; }

  public Map<ResourceType, Integer> useProduction(Map<ResourceType,Integer> currentResourcesInWarehouse) {
    Map<ResourceType, Integer> requiredResources = this.production.getRequiredResources();
    List<ResourceType> resourceTypesRequired = new ArrayList<>();
    Map<ResourceType, Integer> manufacturedResources = new HashMap<>();

    // adds to a temporary list 'resourceTypesRequired' the resource types needed for the production
    if (requiredResources.get(ResourceType.Coin) > 0) {
      resourceTypesRequired.add(ResourceType.Coin);
    }
    if (requiredResources.get(ResourceType.Stone) > 0) {
      resourceTypesRequired.add(ResourceType.Stone);
    }
    if (requiredResources.get(ResourceType.Servant) > 0) {
      resourceTypesRequired.add(ResourceType.Servant);
    }
    if (requiredResources.get(ResourceType.Shield) > 0) {
      resourceTypesRequired.add(ResourceType.Shield);
    }

    // for each resource type needed for the production, verify if there is enough (or any) in the warehouse
    for (ResourceType type: resourceTypesRequired) {
      if (currentResourcesInWarehouse.get(type) == null || requiredResources.get(type) > currentResourcesInWarehouse.get(type) ) {
        System.out.println("Not enough resources");
        return manufacturedResources;
      }
    }

    manufacturedResources = this.production.getManufacturedResources();
    return manufacturedResources;
  }

}
