package it.polimi.ingsw.project.model.board.card.leaderCard;

import java.util.Map;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.resource.ResourceType;

public class LeaderCard extends Card {
  private static final long serialVersionUID = 2340280592475092704L;
  final private String id;
  final private Perk perk;
  final private Map<ResourceType, Integer> requiredResources;
  final private Map<CardColor, Integer> requiredDevCards;
  final private Map<CardColor, CardLevel> requiredDevCardLevel;
  private Status status;

  // creates a LeaderCard activable with a requirement in resources
  public LeaderCard(String id, Perk assignedPerk, int victoryPoints, Map<ResourceType, Integer> requiredResources,
      Map<CardColor, Integer> requiredDevCards, Map<CardColor, CardLevel> requiredDevCardLevel) {
    super(victoryPoints);
    this.id = id;
    this.perk = assignedPerk;
    this.requiredResources = requiredResources;
    this.requiredDevCards = requiredDevCards;
    this.requiredDevCardLevel = requiredDevCardLevel;
    this.status = Status.Inactive;
  }

  public Perk getPerk() {
    return perk;
  }

  public Status getStatus() {
    return status;
  }

  public String getId() {
    return id;
  }

  public Map<ResourceType, Integer> getRequiredResources() {
    return requiredResources;
  }

  public Map<CardColor, Integer> getRequiredDevCards() {
    return requiredDevCards;
  }

  public Map<CardColor, CardLevel> getRequiredDevCardLevel() {
    return requiredDevCardLevel;
  }

  // changes the status of the LeaderCard to Active
  public void activateCard() {
    this.status = Status.Active;
  }

  // converts the object to a printable string
  public String toString() {
    String converted;
    converted = "Id: " + this.id + "\n" + "Perk: " + this.perk.toString() + "Status: " + this.status.toString() + "\n";
    if (this.requiredDevCardLevel != null) {
      converted = converted + "Required DevelopmentCard level:\n" + this.requiredDevCardLevel + "\n";
    } else if (this.requiredResources != null) {
//      StringBuilder convertedBuilder = new StringBuilder(converted + "Required resources:\n");
//      for (ResourceType type : this.requiredResources.keySet()) {
//        if (type == ResourceType.Coin) {
//          convertedBuilder.append("Coin = ").append(this.requiredResources.get(type)).append("\n");
//        }
//        if (type == ResourceType.Servant) {
//          convertedBuilder.append("Servant = ").append(this.requiredResources.get(type)).append("\n");
//        }
//        if (type == ResourceType.Shield) {
//          convertedBuilder.append("Shield = ").append(this.requiredResources.get(type)).append("\n");
//        }
//        if (type == ResourceType.Stone) {
//          convertedBuilder.append("Stone = ").append(this.requiredResources.get(type)).append("\n");
//        }
//      }
//      converted = convertedBuilder.toString();
      converted = converted + "Required Resources:\n" + this.requiredResources + "\n";
    } else {
//      StringBuilder convertedBuilder = new StringBuilder(converted + "Required Development Cards:\n");
//      for (CardColor cardColor : this.requiredDevCards.keySet()) {
//        if (cardColor == CardColor.Gold) {
//          convertedBuilder.append("Gold = ").append(this.requiredDevCards.get(cardColor)).append("\n");
//        }
//        if (cardColor == CardColor.Amethyst) {
//          convertedBuilder.append("Amethyst = ").append(this.requiredDevCards.get(cardColor)).append("\n");
//        }
//        if (cardColor == CardColor.Emerald) {
//          convertedBuilder.append("Emerald = ").append(this.requiredDevCards.get(cardColor)).append("\n");
//        }
//        if (cardColor == CardColor.Sapphire) {
//          convertedBuilder.append("Sapphire = ").append(this.requiredDevCards.get(cardColor)).append("\n");
//        }
//      }
//      converted = convertedBuilder.toString();
      converted = converted + "Required Develompent Cards:\n" + this.requiredDevCards + "\n";
    }
    return converted;
  }
}
