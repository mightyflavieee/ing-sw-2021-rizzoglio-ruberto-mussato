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

  public DevelopmentCard(CardColor color, CardLevel level, Production production, String id, int victoryPoints) {
    super(victoryPoints);
    this.color = color;
    this.level = level;
    this.production = production;
    this.id = id;
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

  public Map<ResourceType, Integer> getRequiredResources() {
    Map<ResourceType, Integer> mapToReturn = new HashMap<>();
    mapToReturn.putAll(this.production.getRequiredResources());
    return mapToReturn;
  }
}
