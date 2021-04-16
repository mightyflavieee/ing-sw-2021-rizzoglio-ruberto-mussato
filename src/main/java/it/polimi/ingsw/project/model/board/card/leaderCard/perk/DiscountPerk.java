package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;

public class DiscountPerk extends Perk{
  public DiscountPerk(Resource resource, Board board) {
    super(resource, board);
  }

  @Override
  public void usePerk(Resource resource) {
    super.usePerk(this.resource);
  }

  public Resource getResource() {
    return super.getResource();
  }

}
