package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.resource.Resource;

import java.io.Serializable;

public class Perk implements Serializable {
  final Resource resource;
  final PerkType type;

  public Perk(Resource resource, PerkType type) {
    this.resource = resource;
    this.type = type;
  }

  public Resource getResource() {
    return this.resource;
  }

  public PerkType getType() {
    return this.type;
  }
  public String toString() {
    StringBuilder converted;
    converted = new StringBuilder("Perk type: " + this.type + "\nPerk resource: " + this.resource.getType() + "\n");
    return converted.toString();
  }
}
