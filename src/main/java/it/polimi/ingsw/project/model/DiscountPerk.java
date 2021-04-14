package it.polimi.ingsw.project.model;

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
