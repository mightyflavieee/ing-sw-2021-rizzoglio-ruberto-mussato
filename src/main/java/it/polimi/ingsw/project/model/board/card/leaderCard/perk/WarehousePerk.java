package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;

public class WarehousePerk extends Perk{
  public WarehousePerk(Resource resource, Board board) {
    super(resource, board);
  }

  @Override
  public void usePerk(Resource resource) {
    this.board.getWarehouse().createExtraDeposit(this.resource);
  }

  public Resource getResource() {
    return super.getResource();
  }

}
