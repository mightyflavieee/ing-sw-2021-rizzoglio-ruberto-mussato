package it.polimi.ingsw.project.model.board.card.leaderCard;

import java.util.Map;
import java.util.Optional;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.ProductionPerk;
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

  // changes the status of the it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard to Active
  public void activateCard() {
    if (this.status == Status.Active) {
      // if the perk is reusable (only it.polimi.ingsw.project.model.board.card.leaderCard.perk.ProductionPerk),
      // utilize the perk again
      if (this.perk instanceof ProductionPerk) {
        this.perk.usePerk(this.perk.getResource());
      } else {
        // if the perk is not a it.polimi.ingsw.project.model.board.card.leaderCard.perk.ProductionPerk
        // and it's active, it is NOT reusable
        System.out.println("Leader it.polimi.ingsw.project.model.board.cards.Card is already active!");
      }
    } else {
      this.status = Status.Active;
      this.perk.usePerk(this.perk.getResource());
    }
  }

  public String toString() {
    String converted;
    converted = "it.polimi.ingsw.project.model.board.cards.Perk: " + this.perk.toString() + "\n" + "it.polimi.ingsw.project.model.board.cards.Status: " + this.status.toString() + "\n" + "Id: " + this.id + "\n";
    return converted;
  }
}


