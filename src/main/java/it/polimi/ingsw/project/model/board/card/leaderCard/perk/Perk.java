package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.resource.Resource;

import java.io.Serializable;

public class Perk implements Serializable {
  private static final long serialVersionUID = 3840280592475999704L;
  final Resource resource;
  final PerkType type;

  /**
   * @param resource it is the resource used in the perk
   * @param type it is the enum that define the Type of the perk
   */
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
