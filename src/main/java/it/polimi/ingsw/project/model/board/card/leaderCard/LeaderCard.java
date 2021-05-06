package it.polimi.ingsw.project.model.board.card.leaderCard;

import java.util.Map;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.resource.ResourceType;


public class LeaderCard extends Card {
  final private String id;
  final private Perk perk;
  final private Map<ResourceType, Integer> requiredResources;
  final private CardLevel requiredDevCardLevel;
  private Status status;

  // creates a LeaderCard activable with a requirement in resources
  public LeaderCard(String id, Perk assignedPerk, int victoryPoints, Map<ResourceType, Integer> requiredResources) {
    super(victoryPoints);
    this.id = id;
    this.perk = assignedPerk;
    this.requiredResources = requiredResources;
    this.requiredDevCardLevel = null;
    this.status = Status.Inactive;
  }

  // creates a LeaderCard activable with a requirement in development card levels
  public LeaderCard(String id, Perk assignedPerk, int victoryPoints, CardLevel requiredDevCardLevel) {
    super(victoryPoints);
    this.id = id;
    this.perk = assignedPerk;
    this.requiredResources = null;
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

  public Map<ResourceType, Integer> getRequiredResources() { return requiredResources; }

  public CardLevel getRequiredDevCardLevel() { return requiredDevCardLevel; }

  // changes the status of the LeaderCard to Active
  public void activateCard() {
    this.status = Status.Active;
  }

  // converts the object to a printable string
  public String toString() {
    String converted;
    converted = "Id: " + this.id + "\n" + "Perk: " + this.perk.toString() + "\n" + "Status: " + this.status.toString() + "\n";
    if (this.requiredResources == null) {
      converted = converted + "Required DevelopmentCard level: " + this.requiredDevCardLevel + "\n";
    } else {
      converted = converted + "Required resources:\n";
      for (ResourceType type : this.requiredResources.keySet()) {
        if (type == ResourceType.Coin) {
          converted = converted + "Coin = " + this.requiredResources.get(type) + "\n";
        }
        if (type == ResourceType.Servant) {
          converted = converted + "Servant = " + this.requiredResources.get(type) + "\n";
        }
        if (type == ResourceType.Shield) {
          converted = converted + "Shield = " + this.requiredResources.get(type) + "\n";
        }
        if (type == ResourceType.Stone) {
          converted = converted + "Stone = " + this.requiredResources.get(type) + "\n";
        }
      }
    }
    return converted;
  }
}

