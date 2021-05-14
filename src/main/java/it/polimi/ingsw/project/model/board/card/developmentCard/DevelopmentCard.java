package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

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

  public void toJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    try {
      String proba = gson.toJson(this);
      gson.toJson(this, new FileWriter("src/main/resources/developmentCards.json", true));
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    Map<ResourceType, Integer> mapToReturn = new HashMap<>();
    mapToReturn.putAll(this.cost);
    return mapToReturn;
  }
}
