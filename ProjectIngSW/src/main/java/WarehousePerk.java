public class WarehousePerk extends Perk{
  public WarehousePerk(Resource resource, Board board) {
    super(resource, board);
  }

  @Override
  public void usePerk(Resource resource) {
    // TODO Auto-generated method stub
    super.usePerk(this.resource);
  }

  public Resource getResource() {
    return super.getResource();
  }

  public Board getBoard() {
    return super.getBoard();
  }
  
}
