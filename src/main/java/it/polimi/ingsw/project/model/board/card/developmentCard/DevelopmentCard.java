package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.*;

public class DevelopmentCard extends Card {
  private static final long serialVersionUID = 2440280592475092704L;
  final private CardColor color;
  final private CardLevel level;
  final private Production production;
  final private String id;
  final private Map<ResourceType, Integer> cost;

  /**
   * @param color it is the enum for the Color of the card
   * @param level it is the enum for the Level of the card
   * @param production it is the Class that contains all the information for the production
   * @param id it is unique identification of the card
   * @param victoryPoints are the points gained by the card when you buy it
   * @param cost it is the cost of the production in a Map of ResourceType,Integer
   */
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

  /**
   * @return returns the formatted string for the cli
   */
  public String toString() {
    StringBuilder converted;
    converted = new StringBuilder("Id: " + this.id + "\n" + "Production:\n" + this.production + "Level: " + this.level + "\nColor: " + this.color + "\n");
    converted.append("Cost:\n");
    for (ResourceType type : this.cost.keySet()) {
      converted.append("\t").append(type).append(" = ").append(this.cost.get(type)).append("\n");
    }
    return converted.toString();
  }

  /**
   * @return it returns the cost of the card
   */
  public Map<ResourceType, Integer> getCost() {
    return cost;
  }
}
