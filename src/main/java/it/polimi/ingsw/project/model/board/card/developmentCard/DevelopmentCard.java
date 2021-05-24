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
  final private Map<ResourceType, Integer> cost;

  public DevelopmentCard(CardColor color, CardLevel level, Production production, String id, int victoryPoints,
      Map<ResourceType, Integer> cost) {
    super(victoryPoints);
    this.color = color;
    this.level = level;
    this.production = production;
    this.id = id;
    this.cost = cost;
  }

  public CardColor getColor() {
    return this.color;
  }

  public CardLevel getLevel() {
    return this.level;
  }

  public Production getProduction() {
    return this.production;
  }

  public String getId() {
    return this.id;
  }

  public Map<ResourceType, Integer> getRequiredResources() {
      return new HashMap<>(this.cost);
  }

  public String toString() {
    StringBuilder converted;
    converted = new StringBuilder("Id: " + this.id + "\n" + "Production: " + this.production + "\nLevel: " + this.level + "\nColor: " + this.color + "\n");
    converted.append("Cost:\n");
    for (ResourceType type : this.cost.keySet()) {
      converted.append("\t").append(type).append(" = ").append(this.cost.get(type)).append("\n");
    }
    return converted.toString();
  }
}
