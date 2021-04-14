package it.polimi.ingsw.project.model;

public class WarehousePerk extends Perk{
  public WarehousePerk(Resource resource, Board board) {
    super(resource, board);
  }

  @Override
  public void usePerk(Resource resource) {
    this.board.getWarehouse().addExtraDeposit(this.resource);
  }

  public Resource getResource() {
    return super.getResource();
  }

}
