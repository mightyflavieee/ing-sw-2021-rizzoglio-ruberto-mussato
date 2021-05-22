package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;

public class Perk implements Serializable {
  Resource resource;
  PerkType type;

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
    converted = new StringBuilder("Perk type: " + this.type + "\nResource utilized: " + this.resource.getType() + "\n");
    return converted.toString();
  }
}
